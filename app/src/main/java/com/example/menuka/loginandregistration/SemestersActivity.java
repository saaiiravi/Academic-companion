package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.SemesterAdapter;
import firebase.Connection;
import gpa.GPACalculator;
import models.Semester;

public class SemestersActivity extends AppCompatActivity {
    private TextView ogpaLabel;
    private Button btnAddSemester;
    private DatabaseReference databaseReference;
    private List<Semester> semesterList;
    private SemesterAdapter semesterAdapter;
    private ListView semesterListView;
    private static FirebaseAuth auth;
    private String ogpa;

    public void startSingleSemesterActivity(String number){
        Intent i = new Intent(this, SingleSemesterActivity.class);
        i.putExtra("semester", number);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters);

        initComponents();

        Intent intent = getIntent();
        String semester = intent.getStringExtra("semester");

        databaseReference = Connection.getINSTANCE().getDatabaseReference();
        semesterList = new ArrayList<>();

        // value listener for semesters
        databaseReference
                .child("semesters")
                .child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // values are read from the database
                semesterList.clear();
                semesterAdapter = new SemesterAdapter(SemestersActivity.this,
                        R.layout.single_semester_on_profile, semesterList, SemestersActivity.this);
                semesterListView.setAdapter(semesterAdapter);
                /*if(databaseReference.child("semesters").child(auth.getCurrentUser().getUid())==null)
                {
                    Toast.makeText(SemestersActivity.this, "Value is null", Toast.LENGTH_SHORT).show();
                }*/
                for(DataSnapshot semesterDataSnapshot: dataSnapshot.getChildren()){
                    // for each semester related to the authenticated Student
                    databaseReference.child("semesters")
                            .child(auth.getCurrentUser().getUid())
                            .child(semesterDataSnapshot.getKey())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                Semester semester = dataSnapshot.getValue(Semester.class);
                                semesterList.add(semester);
                                semesterAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                System.out.println("Error: " + e.toString());
                            }

                            ogpa = Double.toString(GPACalculator.getInstance().getOGPA((ArrayList<Semester>)semesterList));
                            System.out.println("Printing OGPA: " + ogpa);
                            if(ogpa.length() > 5){
                                ogpa = ogpa.substring(0, 6);
                            }
                            ogpaLabel.setText("OGPA: " + ogpa);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // end of listener


        btnAddSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SemestersActivity.this, AddSemesterActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SemestersActivity.this, StudentProfileActivity.class);
        startActivity(i);
        this.finish();
    }

    private void initComponents() {
        ogpaLabel = (TextView) findViewById(R.id.ogpaLabel);
        btnAddSemester = (Button) findViewById(R.id.btnAddSemester);
        auth = FirebaseAuth.getInstance();
        semesterListView = (ListView) findViewById(R.id.semesters_list_view);
        ogpa = "0.00";
    }
}

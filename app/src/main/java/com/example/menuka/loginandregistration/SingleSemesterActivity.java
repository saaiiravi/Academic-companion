package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ModuleAdapter;
import firebase.Connection;
import gpa.GPACalculator;
import models.Module;

public class SingleSemesterActivity extends AppCompatActivity {
    private List<Module> moduleList;
    private ModuleAdapter moduleAdapter;
    private DatabaseReference databaseRef;
    private Button btnAddModule;
    private FirebaseAuth auth;
    private ListView modulesListView;
    private TextView sgpaTextView;
    private String sgpa;
    private String semester;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SingleSemesterActivity.this, SemestersActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_semester);

        moduleList = new ArrayList<>();
        modulesListView = (ListView) findViewById(R.id.modules_list_view);
        sgpaTextView = (TextView) findViewById(R.id.sgpa_text_view);

        semester = getIntent().getStringExtra("semester");

        auth = FirebaseAuth.getInstance();

        databaseRef = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(auth.getCurrentUser().getUid())
                .child(semester)
                .child("modules");

        DatabaseReference sgpaRef = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(auth.getCurrentUser().getUid())
                .child(semester)
                .child("sgpa");

        sgpaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("SGPA: " + dataSnapshot.getValue().toString());
                sgpaTextView.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                moduleList = new ArrayList<>();
                // dataSnapshot contains every module in the semester
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Module m = child.getValue(Module.class);
                    moduleList.add(m);
                }

                String sgpa = GPACalculator.getInstance().getSGPA((ArrayList<Module>)moduleList);

                try{
                    sgpa = sgpa.substring(0, 4);
                }catch (Exception e){
                    System.out.println("Error in truncating SGPA: " + e.toString());
                }

                double totalCredits = GPACalculator.getInstance().getTotalCredits((ArrayList<Module>)moduleList);

                System.out.println("sgpa: " + sgpa);
                sgpaTextView.setText("SGPA: " + sgpa);
                // set sgpa in database
                DatabaseReference currentSemesterReference = Connection.getINSTANCE().getDatabaseReference()
                        .child("semesters")
                        .child(auth.getCurrentUser().getUid())
                        .child(semester)
                        .child("sgpa");

                currentSemesterReference.setValue(sgpa);

                Map<String, Object> totalCreditsValue = new HashMap<>();
                totalCreditsValue.put("totalCredits", Double.toString(totalCredits));

                Connection.getINSTANCE().getDatabaseReference()
                        .child("semesters")
                        .child(auth.getCurrentUser().getUid())
                        .child(semester)
                        .updateChildren(totalCreditsValue);

                moduleAdapter = new ModuleAdapter(SingleSemesterActivity.this, R.layout.module_card, moduleList, semester);
                modulesListView.setAdapter(moduleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAddModule = (Button) findViewById(R.id.btnAddModule);

        databaseRef = Connection.getINSTANCE().getDatabaseReference().child("semesters");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleSemesterActivity.this, AddModuleActivity.class);
                i.putExtra("semester", semester);
                startActivity(i);
                finish();
            }
        });
    }

}

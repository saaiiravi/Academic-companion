package com.example.menuka.loginandregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import firebase.Connection;
import models.Semester;

public class AddSemesterActivity extends AppCompatActivity {
    private EditText numberEditText;
    private EditText yearEditText;
    private Button btnAdd;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        initComponents();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Semester semester = new Semester();
                // input validation
                String yearInput = yearEditText.getText().toString().trim();
                String numberInput = numberEditText.getText().toString();
                if(numberInput.isEmpty()){
                    Toast.makeText(AddSemesterActivity.this, "Number cannot be blank", Toast.LENGTH_SHORT).show();
                }else if(yearInput.isEmpty()){
                    Toast.makeText(AddSemesterActivity.this, "Year cannot be blank", Toast.LENGTH_SHORT).show();
                }else if(!Utils.isInteger(numberInput)){
                    Toast.makeText(AddSemesterActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(numberInput.trim()) > 10 ||
                        Integer.parseInt(numberInput.trim()) < 1){
                    Toast.makeText(AddSemesterActivity.this, "Number should be between 0 and 10", Toast.LENGTH_SHORT).show();
                }else if(!Utils.isInteger(yearInput)){
                    Toast.makeText(AddSemesterActivity.this, "Enter a valid year", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(yearInput.trim()) > 2018 ||
                        Integer.parseInt(yearInput.trim()) < 1980){
                    Toast.makeText(AddSemesterActivity.this, "Enter a valid year", Toast.LENGTH_SHORT).show();
//                }else if(){

                }else{
                    // everything's good
                    // end of input validation
                    semester.setYear(yearInput.trim());
                    semester.setNumber(numberInput.trim());
                    semester.setEnabled(true);
                    semester.setSgpa("0.00");
                    semester.setTotalCredits("0.00");

                    final DatabaseReference dbRef = databaseReference.child("semesters").child(auth.getCurrentUser().getUid());
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("DataSnapshot: " + dataSnapshot.toString());
                            // check if semester already exists before saving
                            boolean semesterExists = dataSnapshot.hasChild(semester.getNumber());
                            if (semesterExists) {
                                AlertDialog alertDialog = new AlertDialog.Builder(AddSemesterActivity.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Semester" + semester.getNumber() + " already exists");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
//                            Toast.makeText(AddSemesterActivity.this, "Semester Already exists", Toast.LENGTH_LONG).show();
                            } else {
                                dbRef.child(semester.getNumber()).setValue(semester);

                                startActivity(new Intent(AddSemesterActivity.this, SemestersActivity.class));
                                AddSemesterActivity.this.finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }




            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddSemesterActivity.this, SemestersActivity.class);
        startActivity(i);
        finish();
    }

    private void initComponents() {
        numberEditText = (EditText) findViewById(R.id.numberEditText);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        databaseReference = Connection.getINSTANCE().getDatabaseReference();
        auth = FirebaseAuth.getInstance();
    }
}

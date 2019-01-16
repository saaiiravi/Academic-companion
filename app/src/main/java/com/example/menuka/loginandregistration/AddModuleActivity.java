package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import firebase.Connection;
import models.Module;

public class AddModuleActivity extends AppCompatActivity {
    private Spinner gradesSpinner;
    private EditText codeEditText;
    private EditText nameEditText;
    private EditText creditsEditText;
    private String grade;
    private Button btnAdd;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private static String semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        auth = FirebaseAuth.getInstance();
        semester = getIntent().getStringExtra("semester");

        databaseReference = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(auth.getCurrentUser().getUid())
                .child(semester)
                .child("modules");

        gradesSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> gradesAdapter = ArrayAdapter.createFromResource(this, R.array.grades_array, android.R.layout.simple_spinner_dropdown_item);
        gradesSpinner.setAdapter(gradesAdapter);

        codeEditText = (EditText) findViewById(R.id.codeEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        creditsEditText = (EditText) findViewById(R.id.creditsEditText);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        gradesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        grade = gradesSpinner.getSelectedItem().toString();


        System.out.println("Selected Grade: " + grade);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Module m = new Module();
                // input validation
                String codeInput = codeEditText.getText().toString().trim();
                String nameInput = nameEditText.getText().toString().trim();
                String creditsInput = creditsEditText.getText().toString().trim();
                if(codeInput.isEmpty()){
                    Toast.makeText(AddModuleActivity.this, "Code cannot be blank", Toast.LENGTH_SHORT).show();
                }else if(nameInput.isEmpty()){
                    Toast.makeText(AddModuleActivity.this, "Name cannot be blank", Toast.LENGTH_SHORT).show();
                }else if(creditsInput.isEmpty()){
                    Toast.makeText(AddModuleActivity.this, "Credits cannot be blank", Toast.LENGTH_SHORT).show();
                }else if(!Utils.isLegitModuleCode(codeInput)){
                    Toast.makeText(AddModuleActivity.this, "Invalid module code", Toast.LENGTH_SHORT).show();
                }else if(!Utils.isDouble(creditsInput)){
                    Toast.makeText(AddModuleActivity.this, "Invalid credits value", Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(creditsInput) > 10.0 ||
                        Double.parseDouble(creditsInput) < 1.00) {
                    Toast.makeText(AddModuleActivity.this, "Credits should be between 1.0 and 10.0", Toast.LENGTH_SHORT).show();
                }else if(nameInput.length() < 2){
                    Toast.makeText(AddModuleActivity.this, "Module name is too short", Toast.LENGTH_SHORT).show();
                }else if(nameInput.length() > 100){
                    Toast.makeText(AddModuleActivity.this, "Module name is too long", Toast.LENGTH_SHORT).show();
                }else{
                    // everything's good
                    // end of input validation
                    m.setCode(codeInput.trim().toUpperCase());
                    m.setName(nameInput.trim());
                    m.setCredits(creditsInput.trim());
                    m.setGrade(grade);

                    databaseReference.child(m.getCode()).setValue(m);

                    onBackPressed();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, SingleSemesterActivity.class);
        i.putExtra("semester", semester);
        startActivity(i);
        finish();
    }
}
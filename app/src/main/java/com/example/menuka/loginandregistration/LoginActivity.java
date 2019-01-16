package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import firebase.Connection;
import models.Student;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignUp, btnLogin, btnReset;
    private int hasStudentFilledDetails = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // Student has logged in
            startActivity(new Intent(LoginActivity.this, StudentProfileActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email_edit_text_login);
        inputPassword = (EditText) findViewById(R.id.password_edit_text_login);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });

        /*
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        */

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Just After: ", "Login Clicked");
                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                // Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // error occurred
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // login successful
                                    // redirect to dashboard only if personal information is filled
                                    String uid = auth.getCurrentUser().getUid();

                                    // redirectStudent to next Activity
                                    redirectStudent(uid);

                                }
                            }
                        });
            }

        });
    }


    // to check if the firstName, lastName, indexNo, birthday, and department of a Student is filled
    private void redirectStudent(String uid) {
        final DatabaseReference databaseReference = Connection.getINSTANCE().getDatabaseReference().child("students").child(uid);
        final Student[] student = new Student[1];

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hasStudentFilledDetails() => onDataChange()");
                if(student[0]==null)
                {
                    startActivity(new Intent(LoginActivity.this, StudentDetailsForm.class));
                }
                else
                {

                    student[0] = dataSnapshot.getValue(Student.class);
                    String firstName = student[0].getFirstName();
                    String lastName = student[0].getLastName();
                    String department = student[0].getDepartment();
                    String birthday = student[0].getBirthday();
                    String indexNo = student[0].getIndexNo();

                    if (isStringNullOrEmpty(firstName) ||
                            isStringNullOrEmpty(lastName) ||
                            isStringNullOrEmpty(department) ||
                            isStringNullOrEmpty(birthday) ||
                            isStringNullOrEmpty(indexNo)) {

                        startActivity(new Intent(LoginActivity.this, StudentDetailsForm.class));
                    } else {
                        // every detail is filled
                        startActivity(new Intent(LoginActivity.this, StudentProfileActivity.class));
                    }

                }
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private boolean isStringNullOrEmpty(String string) {
        if (string == null) {
            return true;
        } else if (string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
//        Intent i = new Intent(this, SignupActivity.class);
//        startActivity(i);
//        this.finish();

        super.onBackPressed();
    }
}

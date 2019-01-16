package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class StudentProfileActivity extends AppCompatActivity {
    private Button btnAccountSettings;
    private Button btnGradesManagement;
    private Button btnReceivedFeedback;
    private Button btnSignOut;
    private FirebaseAuth auth;
    private TextView emailLabel;
    private static final String TAG = StudentProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        initComponents();
        if(auth.getCurrentUser() != null){
            emailLabel.setText("Logged in as: " + auth.getCurrentUser().getEmail());
        }

        btnAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, StudentDetailsForm.class));
            }
        });

        btnGradesManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, SemestersActivity.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth.getCurrentUser() != null){
                    auth.signOut();
                    startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
                    StudentProfileActivity.this.finish();
                }
            }
        });

        btnReceivedFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentProfileActivity.this, ReceivedFeedbackActivity.class);
                startActivity(i);
//                StudentProfileActivity.this.finish();
            }
        });

    }

    private void initComponents() {
        auth = FirebaseAuth.getInstance();
        btnAccountSettings = (Button) findViewById(R.id.btnAccountSettings);
        btnGradesManagement = (Button) findViewById(R.id.btnGradesManagement);
        btnReceivedFeedback = (Button) findViewById(R.id.btnReceivedFeedback);
        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        emailLabel = (TextView) findViewById(R.id.emailLabel);
    }
}

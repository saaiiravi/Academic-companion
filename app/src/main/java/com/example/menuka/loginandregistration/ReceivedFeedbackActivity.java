package com.example.menuka.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.FeedbackAdapter;
import firebase.Connection;
import models.Feedback;

public class ReceivedFeedbackActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView feedbackListView;
    private FeedbackAdapter adapter;
    private FirebaseAuth auth;
    private ArrayList<Feedback> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_feedback);

        initComponents();
        auth = FirebaseAuth.getInstance();
        feedbackList = new ArrayList<>();
        feedbackListView = (ListView) findViewById(R.id.feedback_list_view);

        DatabaseReference databaseReference = Connection.getINSTANCE().getDatabaseReference();
        Query query = databaseReference.child("feedback")
                .orderByChild("studentId")
                .equalTo(auth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // all feedback nodes are received
                feedbackList.clear();
                System.out.println("Student's Feedback: " + dataSnapshot.toString());
                for(DataSnapshot feedback: dataSnapshot.getChildren()){
                    // for every feedback given to the student
                    Feedback f = feedback.getValue(Feedback.class);
                    feedbackList.add(f);
                    System.out.println("f: " + f);
                }

                adapter = new FeedbackAdapter(ReceivedFeedbackActivity.this, R.layout.single_feedback_card, feedbackList);
                feedbackListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ReceivedFeedbackActivity.this, StudentProfileActivity.class);
        startActivity(i);
        this.finish();
    }

    private void initComponents() {
        databaseReference = Connection.getINSTANCE().getDatabaseReference();
        feedbackListView = (ListView) findViewById(R.id.feedback_list_view);
    }

}

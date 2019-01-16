package controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import firebase.Connection;
import models.Reviewer;

/**
 * Created by menuka on 5/10/17.
 */

public class ReviewersController {
    public void getAllReviewers(){

    }

    public static Reviewer getReviewerById(String reviewerId){
        final Reviewer[] reviewer = new Reviewer[1];
        DatabaseReference databaseReference = Connection.getINSTANCE().getDatabaseReference();
        Query query = databaseReference.child("reviewers")
                .orderByChild("reveiwerId")
                .equalTo(reviewerId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    reviewer[0] = dataSnapshot.getValue(Reviewer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return reviewer[0];
    }

}

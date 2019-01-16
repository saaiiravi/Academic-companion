package adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.menuka.loginandregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import firebase.Connection;
import models.Feedback;
import models.Reviewer;

/**
 * Created by menuka on 4/6/17.
 */

public class FeedbackAdapter extends ArrayAdapter<Feedback> {
    private static final String TAG = FeedbackAdapter.class.getSimpleName();

    List<Feedback> feedbackList;

    public FeedbackAdapter(@NonNull Context context, @LayoutRes int resource, List<Feedback> feedbackList) {
        super(context, resource, feedbackList);
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View feedbackItemView = convertView;
        Feedback currentFeedback = feedbackList.get(position);
        Log.i("currentFeedbackObtained", "");

        if (feedbackItemView == null) {
            feedbackItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_feedback_card, parent, false);
        }

        TextView ratingTextView = (TextView) feedbackItemView.findViewById(R.id.rating_text_view);
        ratingTextView.setText(currentFeedback.getRating());

        TextView commentTextView = (TextView) feedbackItemView.findViewById(R.id.comment_text_view);
        commentTextView.setText(currentFeedback.getComment());

        final TextView reviewerTextView = (TextView) feedbackItemView.findViewById(R.id.reviewer_text_view);
        reviewerTextView.setText(currentFeedback.getReviewerId());

        DatabaseReference databaseReference = Connection.getINSTANCE().getDatabaseReference();
        Query query = databaseReference.child("reviewers")
                .orderByChild("uid")
                .equalTo(currentFeedback.getReviewerId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reviewer r = null;
                for(DataSnapshot reviewer: dataSnapshot.getChildren()){
                     r = reviewer.getValue(Reviewer.class);
                }

                if(r != null){
                    reviewerTextView.setText(r.getFirstName() + " " + r.getLastName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView dateTextView = (TextView) feedbackItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentFeedback.getDate());

        return feedbackItemView;
    }
}

package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.menuka.loginandregistration.R;
import com.example.menuka.loginandregistration.SemestersActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import firebase.Connection;
import models.Semester;

/**
 * Created by menuka on 3/25/17.
 */

public class SemesterAdapter extends ArrayAdapter<Semester> {
    private static final String TAG = SemesterAdapter.class.getSimpleName();

    private Button btnMore;
    private Button btnRemove;
    List<Semester> semesterList;
    private SemestersActivity semestersActivity;


    public SemesterAdapter(@NonNull Context context, @LayoutRes int resource, List<Semester> semesterList, SemestersActivity semestersActivity) {
        super(context, resource, semesterList);
        this.semesterList = semesterList;
        this.semestersActivity = semestersActivity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View semesterItemView= convertView;
        final Semester currentSemester = semesterList.get(position);
        System.out.println("SemesterList: " + semesterList);

        if(semesterItemView == null){
            semesterItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_semester_on_profile, parent, false);
        }

        TextView numberTextView = (TextView) semesterItemView.findViewById(R.id.number_text_view);
        numberTextView.setText("Semester " + currentSemester.getNumber());

        TextView sgpaTextView = (TextView) semesterItemView.findViewById(R.id.sgpa_text_view);
        sgpaTextView.setText("SGPA: " + currentSemester.getSgpa());

        btnMore = (Button) semesterItemView.findViewById(R.id.btnMore);
        btnRemove = (Button) semesterItemView.findViewById(R.id.btnRemove);

        // see more about the semester
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semestersActivity.startSingleSemesterActivity(currentSemester.getNumber());
            }
        });

        // remove semester
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Semester")
                        .setMessage("Are you sure you want to delete this semester")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                DatabaseReference dbRef = Connection.getINSTANCE().getDatabaseReference()
                                        .child("semesters")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(currentSemester.getNumber());

                                dbRef.removeValue(); // remove from firebase

                                semesterList.remove(currentSemester);
                                SemesterAdapter.this.notifyDataSetChanged();

                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return semesterItemView;
    }
}

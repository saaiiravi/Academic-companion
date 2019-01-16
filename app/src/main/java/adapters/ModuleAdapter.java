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

import com.example.menuka.loginandregistration.EditModuleActivity;
import com.example.menuka.loginandregistration.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import firebase.Connection;
import models.Module;

/**
 * Created by menuka on 3/24/17.
 */

public class ModuleAdapter extends ArrayAdapter<Module> {
    private static final String TAG = ModuleAdapter.class.getSimpleName();
    private Button btnEdit;
    private Button btnRemove;

    List<Module> moduleList;
    private static Context context;
    private String semester;

    public ModuleAdapter(@NonNull Context context, @LayoutRes int resource, List<Module> moduleList, String semester) {
        super(context, resource, moduleList);
        this.moduleList = moduleList;
        ModuleAdapter.context = context;
        this.semester = semester;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View singleModuleView = convertView;
        final Module currentModule = moduleList.get(position);

        if (singleModuleView == null) {
            singleModuleView = LayoutInflater.from(getContext()).inflate(R.layout.module_card, parent, false);
        }

        btnEdit = (Button) singleModuleView.findViewById(R.id.btnEdit);
        btnRemove = (Button) singleModuleView.findViewById(R.id.btnRemove);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditModuleActivity.class);
                i.putExtra("name", currentModule.getName());
                i.putExtra("code", currentModule.getCode());
                i.putExtra("credits", currentModule.getCredits());
                i.putExtra("grade", currentModule.getGrade());
                i.putExtra("semester", semester);
                context.startActivity(i);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Module")
                        .setMessage("Are you sure you want to delete this module?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                DatabaseReference dbRef = Connection.getINSTANCE().getDatabaseReference()
                                        .child("semesters")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(semester)
                                        .child("modules")
                                        .child(currentModule.getCode());

                                dbRef.removeValue(); // remove from firebase

                                moduleList.remove(currentModule);
                                ModuleAdapter.this.notifyDataSetChanged();

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

        TextView codeTextView = (TextView) singleModuleView.findViewById(R.id.code_text_view);
        codeTextView.setText(currentModule.getCode());

        TextView nameTextView = (TextView) singleModuleView.findViewById(R.id.name_text_view);
        nameTextView.setText(currentModule.getName());

        TextView creditsTextView = (TextView) singleModuleView.findViewById(R.id.credits_text_view);
        creditsTextView.setText("Credits: "+currentModule.getCredits());

        TextView gradeTextView = (TextView) singleModuleView.findViewById(R.id.grade_text_view);
        gradeTextView.setText(currentModule.getGrade());

        return singleModuleView;
    }
}

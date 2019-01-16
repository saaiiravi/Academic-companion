package subjects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import firebase.Connection;
import models.Module;

public class ITsubjects {
    private DatabaseReference databaseReference2;
    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public void insertsem1()
    {
        databaseReference2 = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("1")
                .child("modules");
        Module m = new Module();
        m.setCode("HS7151");
        m.setName("Foundation English");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("MA7151");
        m.setName("Mathematics 1");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("PH7151");
        m.setName("Engineering Physics");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("CY7151");
        m.setName("Engineering Chemistry");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("GE7151");
        m.setName("Computing Techniques");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("BS7161");
        m.setName("Basic Sciences Laboratory");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("GE7161");
        m.setName("Computer Practices Laboratory");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);
    }

    public void insertsem2()
    {
        databaseReference2 = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("2")
                .child("modules");
        Module m = new Module();
        m.setCode("HS7251");
        m.setName("Technical English");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("MA7251");
        m.setName("Mathematics 2");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("PH7255");
        m.setName("Physics for Electronics and Information Science");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7202");
        m.setName("Data Structures");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("GE7152");
        m.setName("Engineering Graphics");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7201");
        m.setName("IT Essentials");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7211");
        m.setName("IT Essentials and DS Lab");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("GE7162");
        m.setName("Engineering Practices Laboratory");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);
    }

    public void insertsem3()
    {
        databaseReference2 = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("3")
                .child("modules");
        Module m = new Module();
        m.setCode("GE7251");
        m.setName("EVS");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7301");
        m.setName("Database Systems");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7302");
        m.setName("Digital Communication");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7303");
        m.setName("OOPS and Advanced Data Structures");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7351");
        m.setName("Digital Principles and Design");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("MA7355");
        m.setName("Probability and Queueing Theory");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7311");
        m.setName("Digital and DBMS lab");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7312");
        m.setName("OOPS and Advanced Data Structures Lab");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);
    }
    public void insertsem4()
    {
        databaseReference2 = Connection.getINSTANCE().getDatabaseReference()
                .child("semesters")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("4")
                .child("modules");
        Module m = new Module();
        m.setCode("CS7351");
        m.setName("Software Engineering");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("CS7451");
        m.setName("Computer Architecture");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("CS7452");
        m.setName("Operating Systems");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7401");
        m.setName("Algorithmics");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7402");
        m.setName("Web Technology");
        m.setCredits("3");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("MA7451");
        m.setName("Discrete Mathematics");
        m.setCredits("4");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7411");
        m.setName("Operating Systems Laboratory");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);

        m = new Module();
        m.setCode("IT7411");
        m.setName("Web Technology Laboratory");
        m.setCredits("2");
        m.setGrade("NA");
        databaseReference2.child(m.getCode()).setValue(m);
    }
}

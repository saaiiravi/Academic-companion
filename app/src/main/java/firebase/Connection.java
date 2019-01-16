package firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by menuka on 3/24/17.
 */

public class Connection {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private static Connection INSTANCE;

    public static Connection getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new Connection();
        }
        return INSTANCE;
    }

    private Connection(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public DatabaseReference getDatabaseReference(){
        return this.databaseReference;
    }

}

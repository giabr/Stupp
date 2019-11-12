package com.example.stupp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    String uid;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String email = "joni@gmail.com";
    String password = "ayamgeprek";
    static String order_id;

    TextView exmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

         exmp = findViewById(R.id.exm);

        signIn(email, password);
    }

    //SIGN IN
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("LOGIN", "signIn:onComplete:" + task.isSuccessful());
                        //hideProgressDialog();

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();
                            readData("user", "user_id", uid);
                            Log.i("LOGIN", uid);
                        } else {
                            Log.i("FAILED", "" + task.getException());
                        }
                    }
                });
    }

    //READ
    public void readData(String path, String param, String value) {
        db.collection(path)
                .whereEqualTo(param, value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("DATA", document.getId() + " => " + document.getData());
                                exmp.setText("Data User\n" +
                                        document.getData().get("username") + "\n" +
                                        document.getData().get("user_id") + "\n" +
                                        document.getData().get("order_id"));
                            }
                        } else {
                            Log.d("FAILED", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void order(String id){
        readData("order", "order_id", id);
    }

}
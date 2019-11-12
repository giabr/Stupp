package com.example.stupp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stupp.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    FirestoreRecyclerAdapter<User, UserHolder> adapter;
    RecyclerView recyclerView;

    String email = "joni@gmail.com";
    String password = "ayamgeprek";
    String uid = "FnJOziO2haVXqbYnzDeDxitYeKx2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZE
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rv_list);

//        signIn(email, password);
    }

    //FIRESTORE
    @Override
    protected void onStart() {
        super.onStart();
        //SETTING QUERY
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query("user","user_id",uid), User.class)
                .build();

        //ADAPTER
        adapter = new FirestoreRecyclerAdapter<User, UserHolder>(options) {
            @Override
            public void onBindViewHolder(UserHolder holder, int position, User model) {
                holder.setData(
                        model.getUser_id()+ "\n" +
                                model.getUsername()+ "\n" +
                                model.getOrder_id());
                Log.i("GET DATA", model.getUsername());
                // Bind the Chat object to the ChatHolder
                // ...
            }

            @Override
            public UserHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_item, group, false);

                return new UserHolder(view);
            }
        };

        //SET ADAPTER
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    //USER HOLDER
    private class UserHolder extends RecyclerView.ViewHolder {
        private View view;

        UserHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setData(String text) {
            TextView tv_item = view.findViewById(R.id.item);
            tv_item.setText(text);
        }
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
                            Log.i("LOGIN", uid);
                        } else {
                            Log.i("FAILED", "" + task.getException());
                        }
                    }
                });
    }

    //QUERY
    public Query query (String path, String param, String value) {
        Query query;
        query = db.collection(path).whereEqualTo(param, value);
        return  query;
    }
}
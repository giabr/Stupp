package com.example.stupp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stupp.models.Studio;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    FirestoreRecyclerAdapter<Studio, StudioHolder> adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

//    CardView cardView;

    String email = "joni@gmail.com";
    String password = "ayamgeprek";
    String uid = "FnJOziO2haVXqbYnzDeDxitYeKx2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZE
        recyclerView = findViewById(R.id.list);

        init();
        getStudioList();
//        signIn(email, password);
    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }

    private void getStudioList(){
        Query query = db.collection("studio");

        FirestoreRecyclerOptions<Studio> options = new FirestoreRecyclerOptions.Builder<Studio>()
                .setQuery(query, Studio.class)
                .build();

        //ADAPTER
        adapter = new FirestoreRecyclerAdapter<Studio, StudioHolder>(options) {
            @Override
            public void onBindViewHolder(StudioHolder holder, int position, Studio model) {
                holder.bind(model);
                // Bind the Chat object to the ChatHolder
                // ...
            }

            @Override
            public StudioHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_item, group, false);

                return new StudioHolder(view);
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    //FIRESTORE
    @Override
    protected void onStart() {
        super.onStart();
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
//    private class UserHolder extends RecyclerView.ViewHolder {
//        private View view;
//
//        UserHolder(View itemView) {
//            super(itemView);
//            view = itemView;
//        }
//
//        void setData(String text) {
//            TextView tv_item = view.findViewById(R.id.tv_studio_detail);
//            tv_item.setText(text);
//        }
//    }

    //STUDIO HOLDER
    private class StudioHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView details;
        private ImageView avatar;

        StudioHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_studio_name);
            details = itemView.findViewById(R.id.tv_studio_detail);
            details.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
            avatar = itemView.findViewById(R.id.avatar);
        }

        public void bind(final Studio studio){
            name.setText(studio.studio_name);
            details.setText(studio.details);
            Glide.with(itemView).load(studio.avatar).into(avatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), StudioActivity.class);
                    intent.putExtra("studio", studio);
                    startActivity(intent);
                }
            });
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

}
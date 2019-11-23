package com.example.stupp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stupp.models.Studio;

public class StudioActivity extends AppCompatActivity {

    Studio studio;
    TextView studioName;
    TextView studioDetail;
    ImageView studioAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio);

        Intent intent = getIntent();
        studio = (Studio) intent.getSerializableExtra("studio");
        init(studio);

        intentDataStatus(studio);

    }

    public void init(Studio studio){
        studioName = findViewById(R.id.tv_studioName_sa);
        studioName.setText(studio.studio_name);

        studioDetail = findViewById(R.id.tv_studioDetail_sa);
        studioDetail.setText(studio.details);

        studioAvatar = findViewById(R.id.iv_studioAva_sa);
        Glide.with(this).load(studio.avatar).into(studioAvatar);
    }

    public void intentDataStatus(Studio studio){
        if (studio == null){
            Toast.makeText(this, "Error retrieve data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, studio.studio_name, Toast.LENGTH_SHORT).show();
        }
    }
}



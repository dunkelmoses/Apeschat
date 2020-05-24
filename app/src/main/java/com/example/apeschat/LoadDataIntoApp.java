package com.example.apeschat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;

import java.util.Timer;
import java.util.TimerTask;

public class LoadDataIntoApp extends AppCompatActivity {
    int i = 0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data_into_app);


        progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(20);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(LoadDataIntoApp.this,MyProfile.class));
            }
        },5000);
    }
}

package com.example.apeschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainAppPage extends AppCompatActivity {
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_page);
    logout = findViewById(R.id.logout);
    logout.setOnClickListener(l->{
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainAppPage.this,MainActivity.class));
        finish();
    });
    }
}

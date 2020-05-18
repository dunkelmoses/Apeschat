package com.example.apeschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, MyProfile.class));
        }

        Button  register = findViewById(R.id.RegisterButton);
        Button login = findViewById(R.id.LoginButton);

        register.setOnClickListener(r-> {
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        });
        login.setOnClickListener(r-> {
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        });
    }
}

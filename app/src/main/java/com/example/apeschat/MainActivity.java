package com.example.apeschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

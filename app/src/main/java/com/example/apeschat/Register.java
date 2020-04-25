package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private TextView fullName, userName, email, password, confirmPassword;
    private EditText fullNameEdit, userNameEdit, emailEdit, passwordEdit, confirmPasswordEdit;
    private Button signUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigester);
        fullNameEdit = findViewById(R.id.fullNameEdit);
        userNameEdit = findViewById(R.id.userNameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        passwordEdit = findViewById(R.id.confirmPasswordEdit);
        signUp = findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(Register.this,MainAppPage.class));
            finish();
        }
        signUp.setOnClickListener(s -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"Account Created",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Register.this,MainAppPage.class));
                }
                else {
                    Toast.makeText(Register.this,"Error",Toast.LENGTH_LONG).show();

                }
                }
            });

        });
    }
}

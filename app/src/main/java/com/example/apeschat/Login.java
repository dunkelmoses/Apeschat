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

public class Login extends AppCompatActivity {
    private TextView forgotPassword;
    private EditText passwordEdit, emailEdit;
    private Button login;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordEdit = findViewById(R.id.passwordEditLogin);
        emailEdit = findViewById(R.id.emailEditLogin);
        login = findViewById(R.id.loginToTheAccount);
        forgotPassword = findViewById(R.id.forgetPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(s -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(Login.this,MyProfile.class));
                            }
                            else {
                                Toast.makeText(Login.this,"Error"+task.toString(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        });
        forgotPassword.setOnClickListener(f->{
            startActivity(new Intent(Login.this,ForgotPassword.class));
        });
    }
}

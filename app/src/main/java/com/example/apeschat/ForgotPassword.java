package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgotPasswordText;
    private Button forgotPasswordButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordText = findViewById(R.id.emailForgotPasswordEdit);
        forgotPasswordButton = findViewById(R.id.forgetPasswordButton);
        firebaseAuth = FirebaseAuth.getInstance();
        forgotPasswordButton.setOnClickListener(f->{
            String email  = forgotPasswordText.getText().toString();
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ForgotPassword.this,"Email sent",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgotPassword.this,"Error!"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        });
    }
}

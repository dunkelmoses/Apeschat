package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText fullNameEdit, userNameEdit, emailEdit, passwordEdit, confirmPasswordEdit;
    private Button signUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String userID;
    public static final String FULL_NAME = "fullName";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String BIO = "bio";
    public static final String AGE = "age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rigester);
        fullNameEdit = findViewById(R.id.fullNameEdit);
        userNameEdit = findViewById(R.id.userNameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        confirmPasswordEdit = findViewById(R.id.confirmPasswordEdit);
        signUp = findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(Register.this, MainAppPage.class));
        }


        signUp.setOnClickListener(s -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString();
            String confirmPassword = confirmPasswordEdit.getText().toString();
            String fullName = fullNameEdit.getText().toString();
            String username = userNameEdit.getText().toString();

            if (email.isEmpty()) {
                emailEdit.setError("Can not be empty");
            } else if (!email.contains(".")) {
                emailEdit.setError("Invalid email 21");
            } else if (!email.contains("@")) {
                emailEdit.setError("Invalid email 20");
            } else if (!password.equals(confirmPassword)) {
                confirmPasswordEdit.setError("The password does not match");
            } else if (password.length() <= 5) {
                passwordEdit.setError("The password must be more than 6 characters");
            } else {

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser verifyUser = firebaseAuth.getCurrentUser();
                                    verifyUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Register.this, "Check your email account!",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Error! " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_LONG).show();
                                    userID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firestore.collection("users").document(userID);
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put(FULL_NAME, fullName);
                                    userMap.put(EMAIL, email);
                                    userMap.put(USERNAME, username);
                                    documentReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "created new data: " + userID);
                                        }
                                    });
                                    startActivity(new Intent(Register.this, MainAppPage.class));
                                } else {
                                    Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });

    }
}

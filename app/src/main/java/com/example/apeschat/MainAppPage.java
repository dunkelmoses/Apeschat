package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainAppPage extends AppCompatActivity {
    private Button logout,verifyButton;
    private TextView fullName, username, email,verifyMessage;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String userID;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbaritems,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(MainAppPage.this,Settings.class));
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_page);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        logout = findViewById(R.id.logout);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        verifyMessage = findViewById(R.id.verifyMessage);
        verifyButton = findViewById(R.id.verifyButton);

        logout.setOnClickListener(l -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainAppPage.this, MainActivity.class));
        });
        userID = firebaseAuth.getUid();
        FirebaseUser checkVerifyUser = firebaseAuth.getCurrentUser();
        if(!checkVerifyUser.isEmailVerified()){
            verifyMessage.setVisibility(View.VISIBLE);
            verifyButton.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(v->{
                checkVerifyUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainAppPage.this, "Check your email account!",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainAppPage.this, "Error! " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            });
        }
        DocumentReference documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString(Register.FULL_NAME));
                username.setText(documentSnapshot.getString(Register.USERNAME));
                email.setText(documentSnapshot.getString(Register.EMAIL));
            }
        });
    }
}

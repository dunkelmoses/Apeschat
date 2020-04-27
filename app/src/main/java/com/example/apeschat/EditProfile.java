package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private EditText aboutMeEdit, ageEdit;
    private Button done;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent2 = getIntent();
        String ageData = intent2.getStringExtra("age");
        String aboutMeData = intent2.getStringExtra("bio");

        aboutMeEdit = findViewById(R.id.aboutMeEdit);
        ageEdit = findViewById(R.id.ageEdit);
        aboutMeEdit.setText(aboutMeData);
        ageEdit.setText(ageData);
        done = findViewById(R.id.done);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        done.setOnClickListener(d -> {

            Intent intent = new Intent(EditProfile.this, MainAppPage.class);
            String sendDataAge = ageEdit.getText().toString();
            String sendDataAboutMe = aboutMeEdit.getText().toString();

            intent.putExtra("age", sendDataAge);
            intent.putExtra("bio", sendDataAboutMe);

            String userID = firebaseAuth.getUid();
            reference.child(userID).child("age").setValue(sendDataAge);
            reference.child(userID).child("bio").setValue(sendDataAboutMe).

                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditProfile.this, "Sent", Toast.LENGTH_LONG).show();
                        }
                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "did not send", Toast.LENGTH_LONG).show();
                            Log.e("onFailure", e.getMessage());
                        }
                    });
            startActivity(intent);
        });
    }
}

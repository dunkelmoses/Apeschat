package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class FriendProfile extends AppCompatActivity {
    private Button logout;
    private TextView fullName, username, ageView, verifyMessage, aboutMeText;
    private EditText editMyBio;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser fUser;
    private DocumentReference documentReference;
    private DatabaseReference reference;
    private String userID;
    public final int IMAGE_CODE = 1001;
    public final String ON_SUCCESS = "ON_SUCCESS";
    public final String ON_FAILURE = "ON_FAILURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout = findViewById(R.id.logout);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.userName);
        ageView = findViewById(R.id.age);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        verifyMessage = findViewById(R.id.verifyMessage);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        aboutMeText = findViewById(R.id.bio);

        logout.setOnClickListener(l -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FriendProfile.this, MainActivity.class));
        });
        userID = "H4qajgGE5uORgYSWmLjNkydlKQk2";
        FirebaseUser checkVerifyUser = firebaseAuth.getCurrentUser();

        documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString(Register.FULL_NAME));
                username.setText(documentSnapshot.getString(Register.USERNAME));
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String bio = dataSnapshot.child(Register.BIO).getValue(String.class);
                        String age = dataSnapshot.child(Register.AGE).getValue(String.class);
                        aboutMeText.setText(bio);
                        ageView.setText(age);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("FUCK", databaseError.getMessage());
                    }
                });
            }
        });
    }
}

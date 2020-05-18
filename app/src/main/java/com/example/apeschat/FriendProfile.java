package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import android.view.MenuItem;

import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class FriendProfile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore;
    private FirebaseUser fUser;

    String userId = firebaseAuth.getCurrentUser().getUid();
    DatabaseReference databaseReference;
    DocumentReference documentReference;

    TextView fullName;
    CircleImageView profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullName = findViewById(R.id.fullName);
        firestore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profilePhoto = findViewById(R.id.profilePic);

        documentReference = firestore.collection("users").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String sFullName = documentSnapshot.getString("fullName");
                String sAge = documentSnapshot.getString("age");
                fullName.setText(sFullName);
            }
        });
        if (fUser.getPhotoUrl() != null) {
            Picasso.get().load(fUser.getPhotoUrl()).fit().into(profilePhoto);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.peopleIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.myProfileIcon:
//                        startActivity(new Intent(FriendProfile.this,Home_fragment.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.peopleIcon:
                        return true;
                }
                return true;
            }
        });
    }
}
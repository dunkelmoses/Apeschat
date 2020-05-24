package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    private static final String TAG = "TAG";
    TextView name, age, bio;
    ImageView profileImage;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private CardView layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.myName);
        age = findViewById(R.id.myAge);
        bio = findViewById(R.id.myBio);
        profileImage = findViewById(R.id.myProfileImage);
        layout = findViewById(R.id.progressBar2);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();


        String userUdi = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("user_data").child(userUdi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sName = dataSnapshot.child("name").getValue(String.class);
                String sAge = dataSnapshot.child("age").getValue(String.class);
                String sImage = dataSnapshot.child("image").getValue(String.class);
                String sBio = dataSnapshot.child("bio").getValue(String.class);

                name.setText(sName + ", ");
                age.setText(sAge);
                bio.setText(sBio);

                Picasso.get().load(sImage).into(profileImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
//                        Log.d("TAG","NAME: "+s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//                Log.d("TAG","WHAT:"+s);
//
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.myProfileIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myProfileIcon:

                        return true;
                    case R.id.peopleIcon:
                        startActivity(new Intent(MyProfile.this, SwipeClass.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.chatIcon:
                        startActivity(new Intent(MyProfile.this, ChatList.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbaritems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(MyProfile.this, Settings.class));
                break;
        }
        return true;
    }
}

package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class EditProfile extends AppCompatActivity {
    private EditText bioEdit, ageEdit, nameEdit;
    private Button done;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.myAgeEdit);
        bioEdit = findViewById(R.id.bioEdit);
        imageView = findViewById(R.id.profilePic);
        done = findViewById(R.id.done);
        String userUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refForGender = FirebaseDatabase.getInstance().getReference("Gender").child(userUid);
        refForGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gender = dataSnapshot.getValue(String.class);
                done.setOnClickListener(d -> {
                    reference.child("user_data").child(gender).child(userUid).child("name")
                            .setValue(nameEdit.getText().toString());
                    reference.child("user_data").child(gender).child(userUid).child("age")
                            .setValue(ageEdit.getText().toString());
                    reference.child("user_data").child(gender).child(userUid).child("bio")
                            .setValue(bioEdit.getText().toString());
                    startActivity(new Intent(EditProfile.this,MyProfile.class));
                });
                Log.d("TAG","GENDER IS "+ gender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String userUdi = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Gender").child(userUdi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                reference.child("user_data").child(s).child(userUdi).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String sName = dataSnapshot.child("name").getValue(String.class);
                        String sAge = dataSnapshot.child("age").getValue(String.class);
                        String sImage = dataSnapshot.child("image").getValue(String.class);
                        String sBio = dataSnapshot.child("bio").getValue(String.class);
                        nameEdit.setText(sName);
                        ageEdit.setText(sAge);
                        bioEdit.setText(sBio);
                        Picasso.get().load(sImage).into(imageView);
                        Log.d("TAG", "NAME: " + s);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.d("TAG","WHAT:"+s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

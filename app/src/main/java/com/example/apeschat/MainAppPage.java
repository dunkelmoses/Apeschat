package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;

import javax.annotation.Nullable;

public class MainAppPage extends AppCompatActivity {
    private Button logout,chatPage,goToSearch,friendProfile;
    private TextView fullName, username, ageView, verifyMessage, aboutMeText;
    private EditText editMyBio;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser fUser;
    private DocumentReference documentReference;
    private DatabaseReference reference;
    private String userID;
    private ImageView profileImageView;
    public final int IMAGE_CODE = 1001;
    public final String ON_SUCCESS = "ON_SUCCESS";
    public final String ON_FAILURE = "ON_FAILURE";


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
                Intent intent = new Intent(MainAppPage.this, Settings.class);
                intent.putExtra("age",ageView.getText().toString());
                intent.putExtra("bio",aboutMeText.getText().toString());
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profileImageView.setImageBitmap(bitmap);
                    handlUploadImage(bitmap);
            }
        }
    }

    protected void getDownloadUrl(StorageReference storageReference) {
        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(ON_SUCCESS, "This is URI: " + uri);
                        setUserImageUri(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(ON_FAILURE, "The exception is: " + e.getMessage());

            }
        });
    }

    protected void setUserImageUri(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainAppPage.this, "Good", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainAppPage.this, "Failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void handlUploadImage(Bitmap bitmap) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile.Images")
                .child(uid + "jpeg");
        storageReference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(storageReference);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_page);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent data = getIntent();

        logout = findViewById(R.id.logout);
        fullName = findViewById(R.id.fullName);
        username = findViewById(R.id.userName);
        ageView = findViewById(R.id.age);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        verifyMessage = findViewById(R.id.verifyMessage);
        profileImageView = findViewById(R.id.profilePic);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        aboutMeText = findViewById(R.id.bio);
        chatPage = findViewById(R.id.chatPage);
        goToSearch = findViewById(R.id.goToSearch);
        friendProfile = findViewById(R.id.friendProfile);

        chatPage.setOnClickListener(c->{
            startActivity(new Intent(MainAppPage.this,Chat.class));
        });
        goToSearch.setOnClickListener(c->{
            startActivity(new Intent(MainAppPage.this,SearchForUser.class));
        });
        friendProfile.setOnClickListener(c->{
            startActivity(new Intent(MainAppPage.this,FriendProfile.class));
        });

        if (fUser.getPhotoUrl() != null) {
            Picasso.with(this).load(fUser.getPhotoUrl()).into(profileImageView);
        }
        profileImageView.setOnClickListener(i -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, IMAGE_CODE);
            }
        });


        logout.setOnClickListener(l -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainAppPage.this, MainActivity.class));
        });

        userID = firebaseAuth.getUid();
        FirebaseUser checkVerifyUser = firebaseAuth.getCurrentUser();
        if (!checkVerifyUser.isEmailVerified()) {
            verifyMessage.setVisibility(View.VISIBLE);

            verifyMessage.setOnClickListener(v -> {
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

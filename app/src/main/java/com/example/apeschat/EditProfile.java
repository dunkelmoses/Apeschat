package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class EditProfile extends AppCompatActivity {
    private EditText bioEdit, ageEdit, nameEdit;
    private Button done;
    private ImageView imageView;
    private StorageReference storageReference;
    private static final int GALLERY_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        storageReference = FirebaseStorage.getInstance().getReference();

        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.myAgeEdit);
        bioEdit = findViewById(R.id.bioEdit);
        imageView = findViewById(R.id.profilePic);
        done = findViewById(R.id.done);

        imageView.setOnClickListener(i -> {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(intent, IMAGE_CODE);
//            }
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(gallery, GALLERY_PICK);
        });

        String userUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                done.setOnClickListener(d -> {
                    reference.child("user_data").child(userUid).child("name")
                            .setValue(nameEdit.getText().toString());
                    reference.child("user_data").child(userUid).child("age")
                            .setValue(ageEdit.getText().toString());
                    reference.child("user_data").child(userUid).child("bio")
                            .setValue(bioEdit.getText().toString());
                    startActivity(new Intent(EditProfile.this,MyProfile.class));
                });
//                Log.d("TAG","GENDER IS "+ gender);
            }


    @Override
    protected void onStart() {
        super.onStart();
        String userUdi = FirebaseAuth.getInstance().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("user_data").child(userUdi).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String sName = dataSnapshot.child("name").getValue(String.class);
                        String sAge = dataSnapshot.child("age").getValue(String.class);
                        String sImage = dataSnapshot.child("image").getValue(String.class);
                        String sBio = dataSnapshot.child("bio").getValue(String.class);
                        nameEdit.setText(sName);
                        ageEdit.setText(sAge);
                        bioEdit.setText(sBio);
                        Picasso.get().load(sImage).fit().into(imageView);
//                        Log.d("TAG", "NAME: " + s);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                Log.d("TAG","WHAT:"+s);

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2, 3)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

//                final String[] imageUrl = {resultUri.toString()};
                imageView.setImageURI(resultUri);
//                Picasso.get().load(resultUri).fit().into(imageView);

//                UUID.randomUUID();
                StorageReference reference = storageReference.child("Images").child(FirebaseAuth.getInstance().getUid() + ".jpg");
                reference.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    imageUrl[0] = resultUri.toString();
                                    DatabaseReference reference = FirebaseDatabase.getInstance()
                                            .getReference("user_data")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("image");
                                    reference.setValue(uri.toString());
                                    Log.d("TAG", "This is Image: " + uri.toString());

                                }
                            });
                            Toast.makeText(EditProfile.this, "Image Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}

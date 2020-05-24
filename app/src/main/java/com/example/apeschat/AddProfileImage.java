package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddProfileImage extends AppCompatActivity {
    private static final int GALLERY_PICK = 2;
    private ImageView profileImageView;
    private StorageReference storageReference;
//    private String genderString;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_image);
        profileImageView = findViewById(R.id.profilePic);
        storageReference = FirebaseStorage.getInstance().getReference();
        done = findViewById(R.id.done);

        done.setOnClickListener(d -> {
            Intent intent = new Intent(AddProfileImage.this, MyProfile.class);
            startActivity(intent);
        });

//        Intent data = getIntent();
//        genderString = data.getStringExtra("gender");
        profileImageView.setOnClickListener(i -> {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(intent, IMAGE_CODE);
//            }
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(gallery, GALLERY_PICK);
        });
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
                profileImageView.setImageURI(resultUri);

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
//                                            .child(genderString)
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("image");
                                    reference.setValue(uri.toString());
                                    Log.d("TAG", "This is Image: " + uri.toString());

                                }
                            });
                            Toast.makeText(AddProfileImage.this, "Image Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}

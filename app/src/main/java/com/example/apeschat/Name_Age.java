package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Name_Age extends AppCompatActivity {
    private EditText name, age;
    private Button done;
    private RadioGroup sexGroup, lookingForGroup;
    private RadioButton sexChoice, lookingForChoice;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public final int IMAGE_CODE = 1001;
    public final String ON_SUCCESS = "ON_SUCCESS";
    public final String ON_FAILURE = "ON_FAILURE";
    private String genderString, lookingFor;
    private String sName, sAge;
    private StorageReference storageReference;
    private String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_age);

        name = findViewById(R.id.nameEdit);
        age = findViewById(R.id.myAgeEdit);
        done = findViewById(R.id.done);
        sexGroup = findViewById(R.id.groupSex);
        lookingForGroup = findViewById(R.id.groupLookingFor);

        storageReference = FirebaseStorage.getInstance().getReference();

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sexChoice = sexGroup.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.maleButton:
                        genderString = sexChoice.getText().toString();
                        break;
                    case R.id.femaleButton:
                        genderString = sexChoice.getText().toString();
                        break;
                    default:
                }
                Log.d("TAG", genderString);
            }
        });
        lookingForGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                lookingForChoice = lookingForGroup.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.maleLookingFor:
                        lookingFor = lookingForChoice.getText().toString();
                        break;
                    case R.id.femaleLookingFor:
                        lookingFor = lookingForChoice.getText().toString();
                        break;
                    default:
                }
                Log.d("TAG", genderString);
            }
        });
//        if (genderString!=null || genderString.isEmpty()) {

//        }
//        else {
//            Toast.makeText(this, "Choose a gender first", Toast.LENGTH_SHORT).show();
//        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_data");
//        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Gender");
        done.setOnClickListener(d -> {
            if (genderString.isEmpty() || lookingFor.isEmpty()) {
                Toast.makeText(this, "You must select a gender", Toast.LENGTH_SHORT).show();
            } else {
                sName = name.getText().toString();
                sAge = age.getText().toString();
                CardsModel cardsModel = new CardsModel();
                cardsModel.setName(sName);
                cardsModel.setAge(sAge);
                cardsModel.setGender(genderString);
                cardsModel.setLookingFor(lookingFor);
                cardsModel.setImage("");
                cardsModel.setBio("");
                cardsModel.setId(FirebaseAuth.getInstance().getUid());
//                reference2.child(FirebaseAuth.getInstance().getUid()).setValue(genderString);
                reference.child(FirebaseAuth.getInstance().getUid()).setValue(cardsModel);
                Intent intent = new Intent(Name_Age.this, AddProfileImage.class);
//                intent.putExtra("gender",genderString);
                startActivity(intent);
            }
        });

    }





    public void lookingForChoice(View view) {
        int radio = lookingForGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radio);
    }

    public void sexChoice(View view) {
        int radio = sexGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radio);
    }

}

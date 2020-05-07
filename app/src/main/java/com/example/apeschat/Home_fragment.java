//package com.example.apeschat;
//
//import android.app.FragmentManager;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//
//import com.example.apeschat.R;
//import com.example.apeschat.models.MyLocation;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.GeoPoint;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.ByteArrayOutputStream;
//
//import static android.app.Activity.RESULT_OK;
//
//public class Home_fragment extends Fragment {
//    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
//    private Button goToSearch, friendProfile;
//    private TextView fullName, username, ageView, verifyMessage, aboutMeText;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseFirestore firestore;
//    private FirebaseUser fUser;
//    private DocumentReference documentReference;
//    private String userID;
//    private ImageView profileImageView;
//    public final int IMAGE_CODE = 1001;
//    public final String ON_SUCCESS = "ON_SUCCESS";
//    public final String ON_FAILURE = "ON_FAILURE";
//    public GeoPoint geoPoint;
//    public FusedLocationProviderClient fusedLocationClient;
//    private MyLocation myLocation;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.home_page,container,false);
//
//        Toolbar toolbar = view.findViewById(R.id.toolBar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        return view;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbaritems, menu);
//        super.onCreateOptionsMenu(menu,inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.setting:
//                Intent intent = new Intent(getActivity(), Settings.class);
//                intent.putExtra("age", ageView.getText().toString());
//                intent.putExtra("bio", aboutMeText.getText().toString());
//                startActivity(intent);
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//        if (requestCode == IMAGE_CODE) {
//            switch (resultCode) {
//                case RESULT_OK:
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    profileImageView.setImageBitmap(bitmap);
//                    handlUploadImage(bitmap);
//
//            }
//        }
//    }
//
//
//
//    protected void getDownloadUrl(StorageReference storageReference) {
//        storageReference.getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Log.d(ON_SUCCESS, "This is URI: " + uri);
//                        setUserImageUri(uri);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(ON_FAILURE, "The exception is: " + e.getMessage());
//
//            }
//        });
//    }
//
//    protected void setUserImageUri(Uri uri) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                .setPhotoUri(uri)
//                .build();
//        user.updateProfile(request)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MainAppPage.this, "Good", Toast.LENGTH_LONG).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainAppPage.this, "Failed", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//
//    private void handlUploadImage(Bitmap bitmap) {
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile.Images")
//                .child(uid + "jpeg");
//        storageReference.putBytes(byteArrayOutputStream.toByteArray())
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        getDownloadUrl(storageReference);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//}

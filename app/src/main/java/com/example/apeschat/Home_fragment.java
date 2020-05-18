//package com.example.apeschat;
//
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.apeschat.models.UsersRegister;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class Home_fragment extends AppCompatActivity {
//    private DocumentReference documentReference;
//    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//    private List<String> list;
//    private TextView fullName, age, bio;
//    private ImageView profilePicture;
//    private CircleImageView yseButton;
//    private int nextUser;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.home_page);
//
//        fullName = findViewById(R.id.myName);
////        age = findViewById(R.id.myAge);
//        bio = findViewById(R.id.myBio);
//        profilePicture = findViewById(R.id.profileImage);
////        yseButton = findViewById(R.id.yesButton);
//
//
//
//
//        list = new ArrayList<>();
//        firestore = FirebaseFirestore.getInstance();
//        CollectionReference collectionReference = firestore.collection("users");
//        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot qDocSnap : queryDocumentSnapshots) {
//                    UsersRegister usersRegister  = qDocSnap.toObject(UsersRegister.class);
//                    list.add(usersRegister.getId());
//                }
//
//                Log.e("FUCK", "FUCK: "+ list.get(nextUser));
//
//                    nextUser = 0;
//                    getAllAttributes(list.get(nextUser));
//                    yseButton.setOnClickListener(yes -> {
//                        nextUser++;
//                        getAllAttributes(list.get(nextUser));
//                        int size = list.size()-1;
//                        if (nextUser == size) {
//                            yseButton.setVisibility(View.GONE);
//                        }
//
//                    });
//            }
//        });
//
//
//
//    }
//
//    public void getAllAttributes(String i) {
//        firestore.collection("users").document(i).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    String sFulleName = documentSnapshot.getString("fullName");
//                    fullName.setText(sFulleName);
////                String sEmail = documentSnapshot.getString("email");
////                bio.setText(sEmail);
//                }
//                else {
//                    Log.e("FUCK", "FUCKKKKKK");
//
//                }
//            }
//        });
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }
//}

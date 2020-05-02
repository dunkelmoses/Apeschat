package com.example.apeschat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        editText = findViewById(R.id.sendMessage);
        imageView = findViewById(R.id.sendButton);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        imageView.setOnClickListener(i->{

        });

    }

    //this method store what is in the edittext to database
//    public void myMessage() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
//        String theMessage = sendMessage.getText().toString();
//        savedMessages.setTheMessage(theMessage);
//        databaseReference.push().setValue(savedMessages);
//    }

}
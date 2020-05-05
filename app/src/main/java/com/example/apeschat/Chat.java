package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apeschat.models.Messages;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    RecyclerAdapterChat adapterChat;
    RecyclerView recyclerView;
    EditText editMessage;
    ImageView sendButton;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    String userUid = fUser.getUid();
    Intent dataFromIntet;
    String friendUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerChat);
        editMessage = findViewById(R.id.sendMessage);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(s -> {
            sendMessage();
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Date date = new Date();
//                long timeMili = date.getTime();
//                String time = String.valueOf(timeMili);
                Map<String, Object> map = new HashMap<>();
                map.put("lastTime", "time");
                reference.child("states").child(userUid).updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dataFromIntet = getIntent();
        friendUid = dataFromIntet.getStringExtra("userID");
        Query query = reference.child("Messages").child(userUid + friendUid).child(userUid);
        FirebaseRecyclerOptions<Messages> options =
                new FirebaseRecyclerOptions.Builder<Messages>()
                        .setQuery(query, Messages.class)
                        .build();

        adapterChat = new RecyclerAdapterChat(options);
        recyclerView.setAdapter(adapterChat);

        adapterChat.setOnItemClickListener(new RecyclerAdapterChat.ClickInterfaceForRecycler() {
            @Override
            public void onItemClicked(DataSnapshot documentSnapshot, int position) {
//                Messages messages = documentSnapshot.getValue(Messages.class);
//                String userID = messages.getMessage();
////                String username = messages.getUsername();
//                Intent intent = new Intent(Chat.this, FriendProfile.class);
//                intent.putExtra("userID", userID);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterChat.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterChat.stopListening();
    }

    public void sendMessage() {
        String message = editMessage.getText().toString();

        Map<String, Object> myMap = new HashMap<>();
        myMap.put("message", message);
        myMap.put("myId", userUid);

        dataFromIntet = getIntent();
        friendUid = dataFromIntet.getStringExtra("userID");
        reference.child("Messages").child(userUid + friendUid).child(userUid).push().updateChildren(myMap)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Chat.this, "what happend?" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        Map<String, Object> hisMap = new HashMap<>();
        hisMap.put("message", message);
        hisMap.put("myId", userUid);

        dataFromIntet = getIntent();
        friendUid = dataFromIntet.getStringExtra("userID");
        reference.child("Messages").child(friendUid+userUid ).child(friendUid).push().updateChildren(hisMap)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Chat.this, "what happend?" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        editMessage.setText("");
    }
}
package com.example.apeschat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;

import android.widget.ImageView;

import com.example.apeschat.models.UsersData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchForUser extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UsersData");
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    EditText searchBar;
    ImageView searchButton;
    String search;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_user);
        recyclerView = findViewById(R.id.rList);
        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchButton.setOnClickListener(s -> {
        });

        query = reference.orderByChild("username").startAt("").endAt("\uf8ff");

        FirebaseRecyclerOptions<UsersData> options =
                new FirebaseRecyclerOptions.Builder<UsersData>()
                        .setQuery(query, UsersData.class)
                        .build();

        adapter = new RecyclerAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.ClickInterfaceForRecycler() {
            @Override
            public void onItemClicked(DataSnapshot documentSnapshot, int position) {
                UsersData usersData = documentSnapshot.getValue(UsersData.class);
                String userID = usersData.getUserID();
                String username = usersData.getUsername();
                Intent intent = new Intent(SearchForUser.this, FriendProfile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

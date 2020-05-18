package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {
    private RecyclerView recyList;
    private FirebaseRecyclerOptions<CardsModel> options;
    private CardHolder adapter;
    private DatabaseReference ref;
    private List<CardsModel> list;
    private LinearLayoutManager linearLayoutManager;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        recyList = findViewById(R.id.chat_list_recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(linearLayoutManager);
        recyList.setHasFixedSize(false);

        list = new ArrayList<CardsModel>();
        ref = FirebaseDatabase.getInstance().getReference();
        ref.keepSynced(true);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.chatIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.chatIcon:

                        return true;
                    case R.id.peopleIcon:
                        startActivity(new Intent(ChatList.this, SwipeClass.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.myProfileIcon:
                        startActivity(new Intent(ChatList.this, MyProfile.class));
                        overridePendingTransition(0, 0);
                        break;

                }
                return true;
            }
        });
    }

    private void setRecyclerList() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("likeList")
                .child(FirebaseAuth.getInstance().getUid());


    }
}

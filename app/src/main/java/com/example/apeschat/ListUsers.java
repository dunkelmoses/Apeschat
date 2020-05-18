package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity {

    private RecyclerView recyList;
    private FirebaseRecyclerOptions<CardsModel> options;
    private CardHolder adapter;
    private DatabaseReference ref;
    private List<CardsModel> list;
//    private String gender;
    private LinearLayoutManager linearLayoutManager;
    Query query;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        recyList = findViewById(R.id.recyList);
        linearLayoutManager  = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(linearLayoutManager);
        recyList.setHasFixedSize(false);

        list = new ArrayList<CardsModel>();
        ref = FirebaseDatabase.getInstance().getReference();
        String s = ref.toString();
        Log.d("FUCK", s);
        ref.keepSynced(true);

        setRecyList();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.peopleIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.peopleIcon:

                        return true;
                    case R.id.chatIcon:
                        startActivity(new Intent(ListUsers.this,ChatList.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.myProfileIcon:
                        startActivity(new Intent(ListUsers.this,MyProfile.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }

    private void setRecyList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Gender").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gender = dataSnapshot.getValue(String.class);
                if (gender.equals("Male")){
                    query = ref.child("user_data").child("Female");
                    options = new FirebaseRecyclerOptions.Builder<CardsModel>()
                            .setQuery(query, CardsModel.class)
                            .build();
                    adapter = new CardHolder(options);
                    recyList.setAdapter(adapter);
                    adapter.startListening();
                }
                else {
                    query = ref.child("user_data").child("Male");
                    options = new FirebaseRecyclerOptions.Builder<CardsModel>()
                            .setQuery(query, CardsModel.class)
                            .build();
                    adapter = new CardHolder(options);
                    recyList.setAdapter(adapter);
                    adapter.startListening();
                }

                Log.e("TAG", "Gender?: INSIDE ListUsers"+gender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG_DATABASE", databaseError.getMessage());
            }
        });
    }
}

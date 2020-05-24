package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {
    private RecyclerView recyList;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private ChatListAdapter adapter;
    private List<CardsModel> list;
    private List<CardsModel> whomILikedList;
    private List<String> whomLikedMeList;
    String myUid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        list = new ArrayList<>();
        whomILikedList = new ArrayList<>();
        whomLikedMeList = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ChatListAdapter(list, this);

        recyList = findViewById(R.id.chat_list_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(linearLayoutManager);
        recyList.setHasFixedSize(true);

        getILikedList();

        recyList.setAdapter(adapter);
        displayList();
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void getILikedList() {
        myRef = FirebaseDatabase.getInstance().getReference().child("LikeList")
                .child(myUid).child("whomILiked");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);
                    CardsModel cardsModel = new CardsModel();
                    cardsModel.setName(name);
                    cardsModel.setImage(image);
                    cardsModel.setId(id);
                    whomILikedList.add(cardsModel);
//                    Log.d("TAG : I liked: ",likedList);

                }
                getLikedMeList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLikedMeList() {
        userRef = FirebaseDatabase.getInstance().getReference().child("LikeList")
                .child(myUid).child("whomLikedMe");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String likedList = ds.getValue(String.class);
                    whomLikedMeList.add(likedList);
                    Log.d("TAG : Who liked me: ", likedList);
                }
                compareLists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void compareLists() {
        for (CardsModel s : whomILikedList) {
            String id = s.getId();
            String name = s.getName();
            String image = s.getImage();
            if (whomLikedMeList.contains(id)) {
                CardsModel cardsModel = new CardsModel();
                cardsModel.setId(id);
                cardsModel.setName(name);
                cardsModel.setImage(image);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("friendsList").child(FirebaseAuth.getInstance().getUid()).child("myFriends");
                reference.child(id).setValue(cardsModel);
                Log.d("TAG: The real list: ", id);
            }

//            Log.d("TAG: 222The real list: ",s);
        }
    }

    private void displayList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("friendsList").child(FirebaseAuth.getInstance().getUid()).child("myFriends");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String name = ds.child("name").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);
                    CardsModel cardsModel = new CardsModel();
                    cardsModel.setName(name);
                    cardsModel.setImage(image);
                    cardsModel.setId(id);
                    list.add(cardsModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();

    }
}


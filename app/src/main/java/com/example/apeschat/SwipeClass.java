package com.example.apeschat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class SwipeClass extends AppCompatActivity {
    private List<String> keys;
    private CardAdapter adapter;
    //    private List<CardsModel> list;
    private DatabaseReference reference;
    private List<CardsModel> list;
    private SwipeFlingAdapterView swipeFlingAdapterView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_class);
        list = new ArrayList<>();
        adapter = new CardAdapter(SwipeClass.this, list);
        reference = FirebaseDatabase.getInstance().getReference();
        keys = new ArrayList<>();

        swipeFlingAdapterView = findViewById(R.id.frame);
        swipeFlingAdapterView.setAdapter(adapter
        );


        getListofUsers();

        swipeView("moses");
        bottomBar();
    }

    private void getListofUsers() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_data").child("Female");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String age = ds.child("age").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);
                    list.add(new CardsModel(name, age, "", "", "", image));
//                    CardsModel cardsModel = new CardsModel();
                    String key = ds.getKey();
                    keys.add(key);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void swipeView(String key){
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                list.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(SwipeClass.this, "LEFT", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                String myUid = FirebaseAuth.getInstance().getUid();
                CardsModel obj = (CardsModel) o;
                String userId = obj.getName();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("likeList").child(myUid).child("whomILiked")
                        .child(myUid).push().setValue(userId);

                reference.child("likeList").child(userId).child("whomLikedMe")
                        .push().setValue(userId);

                Toast.makeText(SwipeClass.this, "RIGHT", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });
    }

    private void bottomBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.peopleIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.peopleIcon:
                        return true;
                    case R.id.myProfileIcon:
                        startActivity(new Intent(SwipeClass.this,MyProfile.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
    }
}
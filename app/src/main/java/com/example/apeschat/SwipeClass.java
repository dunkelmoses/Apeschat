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
    //    private List<String> keys;
    private CardAdapter adapter;
    //    private List<CardsModel> list;
    private DatabaseReference reference;
    private List<CardsModel> list;
    private List<CardsModel> filterList;
    private SwipeFlingAdapterView swipeFlingAdapterView;
    private TextView lastMessage;
    private String userIdFromRightSwipe, nameFromRightSwipe, imageFromRightSwipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_class);
        lastMessage = findViewById(R.id.lastMessage);
        list = new ArrayList<>();
        filterList = new ArrayList<>();

        adapter = new CardAdapter(SwipeClass.this, list);
        reference = FirebaseDatabase.getInstance().getReference();
//        keys = new ArrayList<>();

        swipeFlingAdapterView = findViewById(R.id.frame);
        swipeFlingAdapterView.setAdapter(adapter
        );

//        reference.keepSynced(true);
        getListofUsers();
        swipeView();
        bottomBar();

    }

    private void getListofUsers() {
        //this reference is to find the user gender
        final DatabaseReference referenceForGender = FirebaseDatabase.getInstance().getReference().child("user_data");
        referenceForGender.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gender = dataSnapshot.child("gender").getValue(String.class);
                String lookingFor = dataSnapshot.child("lookingFor").getValue(String.class);

                setList(gender, lookingFor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void swipeView() {
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                list.remove(0);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    lastMessage.setVisibility(View.VISIBLE);
                }
                Log.d("TAG : List size? " , String.valueOf(list.size()));
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(SwipeClass.this, "LEFT", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                String myUid = FirebaseAuth.getInstance().getUid();
                CardsModel obj = (CardsModel) o;
                o = new CardsModel();
                userIdFromRightSwipe = obj.getId();
                nameFromRightSwipe = obj.getName();
                imageFromRightSwipe = obj.getImage();

                CardsModel model = new CardsModel();
                model.setId(userIdFromRightSwipe);
                model.setImage(imageFromRightSwipe);
                model.setName(nameFromRightSwipe);
//

                reference = FirebaseDatabase.getInstance().getReference();
//                //this two lines create a list of IDs whom I liked
//                CardsModel cardsModel = new CardsModel();
//                cardsModel.setName(nameFromRightSwipe);
//                cardsModel.setId(userIdFromRightSwipe);
//                cardsModel.setImage(imageFromRightSwipe);
                reference.child("LikeList").child(FirebaseAuth.getInstance().getUid()).child("whomILiked")
                        .child(userIdFromRightSwipe).setValue(model);
                reference.child("LikeList").child(userIdFromRightSwipe).child("whomLikedMe")
                        .child(myUid).setValue(myUid);
//                myDetails(FirebaseAuth.getInstance().getUid(), userIdFromRightSwipe);
                Toast.makeText(SwipeClass.this, "RIGHT", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });
    }

    private void bottomBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.peopleIcon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.peopleIcon:
                        return true;
                    case R.id.myProfileIcon:
                        startActivity(new Intent(SwipeClass.this, MyProfile.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.chatIcon:
                        startActivity(new Intent(SwipeClass.this, ChatList.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }

//    private void match(String userId, String name, String image) {
//        List<String> list = new ArrayList<>();
//        List<String> newList = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference();
//        reference.child("likeList").child(FirebaseAuth.getInstance().getUid()).child("whomLikedMe")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            String s = ds.getValue(String.class);
//                            list.add(s);
////                    Log.d("FOUND",s);
//
//                        }
//                        for (String s : list) {
//                            if (!newList.contains(s)) {
//                                newList.add(s);
//                            }
//                        }
//                        for (String s : newList) {
//                            if (s.contains(userId)) {
//                                CardsModel cardsModel = new CardsModel();
//                                cardsModel.setName(name);
//                                cardsModel.setId(userId);
//                                cardsModel.setImage(image);
//                                reference.child("user_data").child("Female").child(FirebaseAuth.getInstance().getUid())
//                                        .child("myFriends").push()
//                                        .setValue(cardsModel);
//                                Log.d("FOUND", s);
//                            }
//                        }
//
////                Log.d("FOUND","list:" + list.toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//    }

    private void setList(String myGender, String myLookingFor) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String age = ds.child("age").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
//                    String lookingFor = ds.child("lookingFor").getValue(String.class);
                    String gender = ds.child("gender").getValue(String.class);

//                    this if statement filter the list(delete my card and the repated)
                    if (dataSnapshot.exists()) {
                        if (myGender.equals("Female")) {
                            if (!id.equals(FirebaseAuth.getInstance().getUid()) && gender.equals(myLookingFor)) {
                                list.add(new CardsModel(name, age, "", "", "", id, image));
                            }
                        } else if (myGender.equals("Male")) {
                            if (!id.equals(FirebaseAuth.getInstance().getUid()) && gender.equals(myLookingFor)) {
                                list.add(new CardsModel(name, age, "", "", "", id, image));
                            }
                        }
                    }
                    Log.d("TAG: LOOKING FOR?:", myGender);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void myDetails(String myUid, String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("user_data").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String imageUrl = dataSnapshot.child("image").getValue(String.class);

                        //this two lines add my IDs into a list of the user I liked
                        CardsModel cardsModel2 = new CardsModel();
                        cardsModel2.setName(name);
                        cardsModel2.setId(myUid);
                        cardsModel2.setImage(imageUrl);
                        reference.child("user_data").child(userId).child("whomLikedMe")
                                .push().setValue(cardsModel2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private interface PushValues{
        void getPush();
    }
}
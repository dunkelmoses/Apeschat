package com.example.apeschat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardHolder extends FirebaseRecyclerAdapter<CardsModel, CardHolder.Holder> {
    private FirebaseUser firebaseUser;

    public CardHolder(@NonNull FirebaseRecyclerOptions<CardsModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull CardsModel model) {

        holder.textView1.setText(model.getName());
        holder.textView2.setText(model.getAge());
        Picasso.get().load(model.getImage()).fit().into(holder.imageView);


        Log.d("FUCKKK", model.getName());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_users, parent, false);
        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2;
        public CircleImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.nameUserList);
            textView2 = itemView.findViewById(R.id.ageUserList);
            imageView = itemView.findViewById(R.id.iconProfileList);
        }
    }
}

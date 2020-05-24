package com.example.apeschat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyRowAdapter> {

    List<CardsModel> list;
    Context context;

    public ChatListAdapter(List<CardsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRowAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_chat_list, parent, false);
        return new MyRowAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRowAdapter holder, int position) {
        CardsModel cardsModel = list.get(position);
        holder.textView.setText(cardsModel.getName());
        Picasso.get().load(cardsModel.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class  MyRowAdapter extends RecyclerView.ViewHolder {
        TextView textView;
        CircleImageView imageView;
        public MyRowAdapter(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.friendName);
            imageView = itemView.findViewById(R.id.profilePicChatList);
        }
    }
}

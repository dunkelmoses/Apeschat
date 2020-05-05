package com.example.apeschat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apeschat.models.Messages;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class RecyclerAdapterChat extends FirebaseRecyclerAdapter<Messages, RecyclerAdapterChat.MyViewHolder> implements Filterable {
    public ClickInterfaceForRecycler listener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String user = firebaseUser.getUid();
    final static int RIGHT_SIDE = 1;
    final static int LEFT_SIDE = 2;

    public RecyclerAdapterChat(@NonNull FirebaseRecyclerOptions<Messages> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Messages model) {
//        String myUid = model.getMyId();
//        if (myUid.equals(user)) {
//            holder.textView.setBackgroundColor(Color.GREEN);
//        } else {
//            holder.textView.setBackgroundColor(Color.BLUE);
//        }
        holder.textView.setText(model.getMessage());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case RIGHT_SIDE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_message_right, parent, false);
                return new MyViewHolder(view);
            case LEFT_SIDE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_message_left, parent, false);
                return new MyViewHolder(view);
        }
        return null;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rowMessage);
            textView.setOnClickListener(t -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClicked(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }

    public interface ClickInterfaceForRecycler {
        void onItemClicked(DataSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(ClickInterfaceForRecycler listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Messages model = getItem(position);
        if (model.getMyId().equals(user)) {
            return RIGHT_SIDE;
        } else {
            return LEFT_SIDE;
        }
    }
}

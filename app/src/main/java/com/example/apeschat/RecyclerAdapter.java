package com.example.apeschat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apeschat.models.UsersData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<UsersData, RecyclerAdapter.MyViewHolder> implements Filterable {

    public ClickInterfaceForRecycler listener;

    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<UsersData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UsersData model) {
    holder.textView.setText(model.getUsername());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_list,parent,false);
        return new MyViewHolder(view);
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
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rowMessages);
            textView.setOnClickListener(t->{
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION && listener!=null){
                listener.onItemClicked(getSnapshots().getSnapshot(position),position);
            }
            });
        }
    }
    public interface ClickInterfaceForRecycler {
        void onItemClicked(DataSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(ClickInterfaceForRecycler listener){
        this.listener=listener;
    }
}

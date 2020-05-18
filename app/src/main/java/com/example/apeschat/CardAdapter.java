package com.example.apeschat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends BaseAdapter {

    Context context;
    List<CardsModel> list;

    public CardAdapter(Context context, List<CardsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.home_page2, parent, false);
        }
        CardsModel cardsModel = (CardsModel) getItem(position);
        TextView textView1 = convertView.findViewById(R.id.name);
        TextView textView2 = convertView.findViewById(R.id.age);
        ImageView imageView = convertView.findViewById(R.id.imageProfile);

        textView1.setText(cardsModel.getName());
        textView2.setText(cardsModel.getAge());
        Picasso.get().load(cardsModel.getImage()).into(imageView);


        return convertView;
    }
}

package com.example.apeschat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Settings extends AppCompatActivity {
    private ListView listView;
//    private String [] settingList =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listView = findViewById(R.id.listItems);
        MyOwnAdapter adapter = new MyOwnAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((arg0, arg1, position, arg3) -> {

        });


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public class MyOwnAdapter extends BaseAdapter {
        String[] data = new String[]{"Change Password","Change Username"};

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.rowsettings, parent, false);
            TextView oneRow = convertView.findViewById(R.id.itemSettingList);
            ImageView arow = convertView.findViewById(R.id.arow);
            oneRow.setText(data[position]);
            arow.setImageResource(R.drawable.arow);
            oneRow.setOnClickListener(e->{
                if (position==0){
                    Toast.makeText(Settings.this,"this is 0"+position,Toast.LENGTH_LONG).show();
                }
                else if (position==1){
                    Toast.makeText(Settings.this,"this is 1"+position,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Settings.this,"idk"+position,Toast.LENGTH_LONG).show();

                }
//                startActivity(new Intent(Settings.this,MainAppPage.class));
            });
            return convertView;
        }
    }
}

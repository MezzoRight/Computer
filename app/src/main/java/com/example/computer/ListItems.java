package com.example.computer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListItems extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    List<Item> _listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Items");

        Intent intent = new Intent(this, MainActivity.class);
        findViewById(R.id.btnGoBack).setOnClickListener(v -> startActivity(intent));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Item> t = new GenericTypeIndicator<Item>() {};

                for (DataSnapshot ds : dataSnapshot.getChildren())
                    _listItems.add(ds.getValue(t));

                ListView listView = findViewById(R.id.listView);
                listView.setAdapter(new ListAdapter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListItems.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    class ListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;

        public ListAdapter() {
            super();

            layoutInflater = LayoutInflater.from(getBaseContext());
        }

        @Override
        public int getCount() {
            return _listItems.size();
        }

        @Override
        public Item getItem(int i) {
            return _listItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            if(view == null)
                view = layoutInflater.inflate(R.layout.list_item, null);

            TextView name = view.findViewById(R.id.Name);
            TextView price = view.findViewById(R.id.Price);
            TextView size = view.findViewById(R.id.Size);

            name.setText(_listItems.get(i).getName());
            price.setText(_listItems.get(i).getPrice().toString());
            size.setText(_listItems.get(i).getSize());

            return view;
        }
    }
}
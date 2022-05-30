package com.example.computer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Items");

        Intent intent = new Intent(this, ListItems.class);
        findViewById(R.id.viewRecord).setOnClickListener(v -> startActivity(intent));

        EditText name = findViewById(R.id.Name);
        EditText price = findViewById(R.id.Price);
        EditText size = findViewById(R.id.Size);

        Map<String, Item> items = new HashMap<>();

        final long[] totalRecords = {0};

        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalRecords[0] = dataSnapshot.getChildrenCount();

                for (DataSnapshot ds : dataSnapshot.getChildren())
                    items.put(ds.getKey().toString(), ds.getValue(Item.class));
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        findViewById(R.id.addRecord).setOnClickListener(v -> {
            items.put("Item" + totalRecords[0], new Item(name.getText().toString(),
                    Integer.parseInt(price.getText().toString()), size.getText().toString()));

            myRef.setValue(items);

            name.setText("");
            price.setText("");
            size.setText("");

            Toast.makeText(MainActivity.this, "Данные добавлены!", Toast.LENGTH_LONG).show();
        });
    }
}
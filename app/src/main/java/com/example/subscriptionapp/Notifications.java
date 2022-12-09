package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.subscriptionapp.Adapters.Notificationadapter;
import com.example.subscriptionapp.Models.eventmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    ArrayList<eventmodel> notificationholder;
    Notificationadapter adapter;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.recyclerView);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        notificationholder = new ArrayList<>();
        adapter = new Notificationadapter(notificationholder, Notifications.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        loadnotifications();

    }

    private void loadnotifications() {

        String id = auth.getCurrentUser().getUid();

        database.getReference().child("Notifications")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notificationholder.clear();
                        for ( DataSnapshot dates : snapshot.getChildren() ) {
                            for ( DataSnapshot users : dates.getChildren() ) {
                                eventmodel event = users.getValue(eventmodel.class);
                                notificationholder.add(event);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
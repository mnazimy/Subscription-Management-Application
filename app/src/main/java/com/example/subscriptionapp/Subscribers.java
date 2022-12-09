package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.subscriptionapp.Adapters.Newspaperadapter;
import com.example.subscriptionapp.Adapters.Subscriberadapter;
import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.Models.Newspapermodel;
import com.example.subscriptionapp.Models.Usermodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Subscribers extends AppCompatActivity {

    FloatingActionButton add;

    ArrayList<Usermodel> subscriberholder;
    Subscriberadapter adapter;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribers);

        add = findViewById(R.id.add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(Subscribers.this));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        subscriberholder = new ArrayList<>();
//        adapter = new Subscriberadapter(Subscribers.this, subscriberholder);
//        recyclerView.setAdapter(adapter);

        loaddata();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(Subscribers.this, Signup.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_serach, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }

    private void loaddata() {

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                subscriberholder.clear();
                subscriberholder = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Usermodel subscribermodel = dataSnapshot.getValue(Usermodel.class);
                    subscriberholder.add(subscribermodel);
                }
//                adapter.notifyDataSetChanged();
                adapter = new Subscriberadapter(Subscribers.this, subscriberholder);
                recyclerView.setLayoutManager(new LinearLayoutManager(Subscribers.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
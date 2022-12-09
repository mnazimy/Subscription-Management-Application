package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import com.example.subscriptionapp.Adapters.Magazineadapter;
import com.example.subscriptionapp.Adapters.Newspaperadapter;
import com.example.subscriptionapp.Adapters.Subscriberadapter;
import com.example.subscriptionapp.Adapters.Subscriptionadapter;
import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.Models.Subscriptionmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Currentsubscription extends AppCompatActivity {

    ArrayList<Subscriptionmodel> subscriptionholder;
    Subscriptionadapter adapter;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentsubscription);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Currentsubscription.this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        subscriptionholder = new ArrayList<>();
//        adapter = new Subscriptionadapter(Currentsubscription.this, subscriptionholder);
//        recyclerView.setAdapter(adapter);

        loaddata();
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
        database.getReference().child("Subscriptions")
                .child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                subscriptionholder.clear();
                subscriptionholder = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Subscriptionmodel subscriptionmodel = dataSnapshot.getValue(Subscriptionmodel.class);
                    subscriptionholder.add(subscriptionmodel);
                }
//                adapter.notifyDataSetChanged();
                adapter = new Subscriptionadapter(Currentsubscription.this, subscriptionholder);
                recyclerView.setAdapter(adapter);

                adapter.setOnLongClickListener(new Subscriptionadapter.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(int position) {
                        removeItem(position);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void removeItem(int position){
        AlertDialog dialog = new AlertDialog.Builder(Currentsubscription.this)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle("Delete")
                .setMessage("Are you sure to delete")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = subscriptionholder.get(position).getName();
                Log.i("Subscriptionname---->>>", name);
                subscriptionholder.remove(position);
                adapter.notifyItemRemoved(position);
                database.getReference().child("Subscriptions")
                        .child(auth.getCurrentUser().getUid())
                        .child(name).removeValue();
                dialog.dismiss();
            }
        });
    }
}
package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.subscriptionapp.Adapters.Eventadapter;
import com.example.subscriptionapp.Models.eventmodel;
import com.example.subscriptionapp.databinding.ActivityCalendareventeditBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Calendareventedit extends AppCompatActivity {

    Button addButton, deleteButton;
    TextInputLayout editText;
    String mobnumber;
    Intent intent;
    int id;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendareventedit);


        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        editText = findViewById(R.id.event);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


//        intent = getIntent();
//        String text = intent.getStringExtra("Event");
        Gson gson = new Gson();
        eventmodel data = gson.fromJson(getIntent().getStringExtra("Eventdata"), eventmodel.class);
        editText.getEditText().setText(data.getEvent());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate_text(editText)) {
                    return;
                }
                String event = editText.getEditText().getText().toString();
                Log.i("event", event);
                database.getReference().child("Notifications")
                        .child(data.getDate())
                        .child(auth.getCurrentUser().getUid())
                        .child("event")
                        .setValue(event);
                Toast.makeText(Calendareventedit.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Notifications")
                        .child(data.getDate())
                        .child(auth.getCurrentUser().getUid())
                        .removeValue();
                Toast.makeText(Calendareventedit.this, "Updated Successfully", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void StoreEventData(int id, String event) {


    }

    public void DeleteEventData(int id, String event) {


    }

    private boolean validate_text(TextInputLayout id) {
        String val = id.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            id.setError("Field can not be empty");
            return false;
        } else {
            id.setError(null);
            id.setErrorEnabled(false);
            return true;
        }
    }
}
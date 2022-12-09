package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.util.GregorianCalendar;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.subscriptionapp.Adapters.Eventadapter;
import com.example.subscriptionapp.Models.Usermodel;
import com.example.subscriptionapp.Models.eventmodel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Calendar extends AppCompatActivity {

    Button addButton;
    TextInputLayout editText;
    CalendarView calender;
    String selectedDate;
    ArrayList<eventmodel> eventholder;
    Eventadapter adapter;
    RecyclerView recyclerView;
    String mobnumber;
    Date date1;

    FirebaseAuth auth;
    FirebaseDatabase database;
    Usermodel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        addButton = findViewById(R.id.addButton);
        editText = findViewById(R.id.event);
        calender = findViewById(R.id.calendarView);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        eventholder = new ArrayList<>();
        adapter = new Eventadapter(Calendar.this, eventholder);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        loaduser();
        loadevents();

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                if ( month+1 < 10 ) {
                    if ( dayOfMonth < 10 ) {
                        selectedDate = "0" + String.valueOf(dayOfMonth) + "-0" + String.valueOf(month + 1) + "-"  + String.valueOf(year);
                    }else {
                        selectedDate = String.valueOf(dayOfMonth) + "-0" + String.valueOf(month + 1) + "-"  + String.valueOf(year);
                    }
                } else {
                    if ( dayOfMonth < 10 ) {
                        selectedDate = "0" + String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1) + "-"  + String.valueOf(year);
                    }else {
                        selectedDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1) + "-"  + String.valueOf(year);
                    }
                }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate_text(editText)) {
                    return;
                }

//                Log.i("selected date ------->", selectedDate);
                try {
                    String address = user.getAddress() + " " +
                            user.getTown() + " " +
                            user.getCity() + " " +
                            user.getState() + " " +
                            user.getPincode();
                    eventmodel data = new eventmodel(user.getId(),
                            editText.getEditText().getText().toString(),
                            selectedDate, user.getName(), user.getPhone(), address);
                    database.getReference().child("Notifications")
                            .child(selectedDate)
                            .child(auth.getCurrentUser().getUid())
                            .setValue(data);
                } catch ( NullPointerException e ) {
                    Toast.makeText(Calendar.this, "Vendor Cannot add", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loaduser(){

        database.getReference().child("Users").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(Usermodel.class);
//                        Log.i("user data -->>", snapshot.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadevents(){

        String id = auth.getCurrentUser().getUid();
        database.getReference().child("Notifications")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eventholder.clear();
                        for ( DataSnapshot dates : snapshot.getChildren() ) {
                           for ( DataSnapshot users : dates.getChildren() ) {
                               if (users.getKey().equals(id)) {
                                   eventmodel event = users.getValue(eventmodel.class);
                                   eventholder.add(event);
                               }
                           }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
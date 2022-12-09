package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.subscriptionapp.Models.Usermodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.viewbadger.BadgeView;

public class MainActivity extends AppCompatActivity {

    Button currentsubscription,
            addnewsubscription,
            bill,
            payment,
            calendar,
            editdetails;

    FirebaseAuth auth;
    FirebaseDatabase database;

    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentsubscription = findViewById(R.id.current_subscription);
        addnewsubscription = findViewById(R.id.add_new_subscription);
        payment = findViewById(R.id.payment);
        payment.setVisibility(View.GONE);
        calendar = findViewById(R.id.calendar);
        bill = findViewById(R.id.bill);
        username = findViewById(R.id.username);
        editdetails = findViewById(R.id.editdetails);


        auth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();



        database.getReference().child("Users")
                .child(auth.getCurrentUser().getUid())
                .child("name")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                try {
                    if (!name.equals(null)) {
                        username.setText("Hi " + name);
                    }
                } catch (NullPointerException e) {
                    username.setText("Hi Vendor");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Currentsubscription.class));
            }
        });

        addnewsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Addnewsubscription.class));
            }
        });

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Bill.class));
            }
        });

//        View target = addnewsubscription;
//        BadgeView badge = new BadgeView(this, target);
//        badge.setText("1");
//        badge.show();

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Payment.class));
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
            }
        });

        editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Editdetails.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.logout) {

            auth.signOut();
            Intent intent = new Intent(MainActivity.this, Selectusertype.class);
            startActivity(intent);
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
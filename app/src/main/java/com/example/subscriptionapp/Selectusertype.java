package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selectusertype extends AppCompatActivity {

    Button vendor, subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectusertype);

        vendor = findViewById(R.id.vendor);
        subscriber = findViewById(R.id.subscriber);

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Selectusertype.this, Vendorlogin.class));
            }
        });

        subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Selectusertype.this, Login.class));
            }
        });
    }
}
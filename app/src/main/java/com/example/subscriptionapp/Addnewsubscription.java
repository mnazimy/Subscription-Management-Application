package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Addnewsubscription extends AppCompatActivity {

    Button magazines, newspapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewsubscription);

        magazines = findViewById(R.id.magazines);
        newspapers = findViewById(R.id.newspapers);

        magazines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Addnewsubscription.this, Magazines.class));
            }
        });

        newspapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Addnewsubscription.this, Newspapers.class));
            }
        });
    }
}
package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Users;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnterVenddorid extends AppCompatActivity {

    TextInputLayout vendorid;
    MaterialButton next;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_venddorid);

        vendorid = findViewById(R.id.vendorid);
        next = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !validate_text(vendorid) ) {
                    return;
                }
                database.getReference().child("Vendors").child(auth.getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users vendor = snapshot.getValue(Users.class);
                        try {
                        if (vendorid.getEditText().getText().toString().equals(vendor.getVendorid())) {
                            startActivity(new Intent(EnterVenddorid.this, VendorMainactivity.class));
                            finish();
                        }
                        } catch (NullPointerException nullPointerException) {
                            Toast.makeText(EnterVenddorid.this,
                                    "Not a Vendor",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
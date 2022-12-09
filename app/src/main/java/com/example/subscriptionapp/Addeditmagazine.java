package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Magazinemodel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Addeditmagazine extends AppCompatActivity {

    TextInputLayout magazinename, Language, Type, Status, Price, Frequency;
    MaterialButton update;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addeditmagazine);
        magazinename = findViewById(R.id.magazinename);
        Language = findViewById(R.id.language);
        Status = findViewById(R.id.status);
        Type = findViewById(R.id.type);
        update = findViewById(R.id.update);
        Price = findViewById(R.id.price);
        Frequency = findViewById(R.id.frequency);

        Gson gson = new Gson();
        Magazinemodel data = gson.fromJson(getIntent().getStringExtra("Magazine"), Magazinemodel.class);

        try {
            if (!data.equals(null)) {
                magazinename.getEditText().setText(data.getName());
                Language.getEditText().setText(data.getLanguage());
                Status.getEditText().setText(data.getStatus());
                Type.getEditText().setText(data.getType());
                Price.getEditText().setText(data.getPrice());
                Frequency.getEditText().setText(data.getFrequency());
            }
        } catch (NullPointerException e) {

        }

        database = FirebaseDatabase.getInstance();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate_text(magazinename) | !validate_text(Language) | !validate_text(Type)
                        | !validate_text(Status) | !validate_text(Price) | !validate_text(Frequency)) {
                    return;
                }

                String name = magazinename.getEditText().getText().toString();
                String language = Language.getEditText().getText().toString();
                String type = Type.getEditText().getText().toString();
                String status = Status.getEditText().getText().toString();
                String price = Price.getEditText().getText().toString();
                String frequency = Frequency.getEditText().getText().toString();
                Magazinemodel magazinemodel = new Magazinemodel(name, language, type, status, price, frequency);
                database.getReference().child("Magazines").child(name).setValue(magazinemodel);
                Toast.makeText(Addeditmagazine.this, "Magazine Added Successfully",
                        Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
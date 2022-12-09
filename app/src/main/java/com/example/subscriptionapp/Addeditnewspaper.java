package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.Models.Newspapermodel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Addeditnewspaper extends AppCompatActivity {

    TextInputLayout newspapername, Language, Status, Price;
    MaterialButton update;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addeditnewspaper);

        newspapername = findViewById(R.id.newspapername);
        Language = findViewById(R.id.language);
        Status = findViewById(R.id.status);
        update = findViewById(R.id.update);
        Price = findViewById(R.id.price);

        Gson gson = new Gson();
        Newspapermodel data = gson.fromJson(getIntent().getStringExtra("Newspaper"), Newspapermodel.class);

        try {
            if (!data.equals(null)) {
                newspapername.getEditText().setText(data.getName());
                Language.getEditText().setText(data.getLanguage());
                Status.getEditText().setText(data.getStatus());
                Price.getEditText().setText(data.getPrice());
            }
        } catch (NullPointerException e) {

        }

        database = FirebaseDatabase.getInstance();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate_text(newspapername) | !validate_text(Language)
                        | !validate_text(Status) | !validate_text(Price)) {
                    return;
                }

                String name = newspapername.getEditText().getText().toString();
                String language = Language.getEditText().getText().toString();
                String status = Status.getEditText().getText().toString();
                String price = Price.getEditText().getText().toString();
                Newspapermodel newspapermodel = new Newspapermodel(name, language, status, price);
                database.getReference().child("Newspapers").child(name).setValue(newspapermodel);
                Toast.makeText(Addeditnewspaper.this, "Newspaper Added Successfully",
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
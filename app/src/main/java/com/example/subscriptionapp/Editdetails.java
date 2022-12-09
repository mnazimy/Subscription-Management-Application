package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Usermodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Editdetails extends AppCompatActivity {

    MaterialButton update;
    TextView login;
    TextInputLayout phonenumber, password
            , confirmpassword, address, town, city, pincode, state;

    FirebaseDatabase database;
    FirebaseAuth auth;

    Usermodel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdetails);

        update = findViewById(R.id.update);
        login = findViewById(R.id.login);
        phonenumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);
        address = findViewById(R.id.address);
        town = findViewById(R.id.town);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Users")
                .child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(Usermodel.class);
                        phonenumber.getEditText().setText(user.getPhone());
                        password.getEditText().setText(user.getPassword());
                        address.getEditText().setText(user.getAddress());
                        town.getEditText().setText(user.getTown());
                        city.getEditText().setText(user.getCity());
                        pincode.getEditText().setText(user.getPincode());
                        state.getEditText().setText(user.getState());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate_text(phonenumber)
                        | !validate_text(password) | !validate_text(confirmpassword)
                        | !validate_text(address) | !validate_text(town) | !validate_text(city)
                        | !validate_text(pincode) | !validate_text(state)) {
                    return;
                }

                if (!validate_password()) {
                    return;
                }

                user.setPhone(phonenumber.getEditText().getText().toString());
                user.setAddress(address.getEditText().getText().toString());
                user.setTown(town.getEditText().getText().toString());
                user.setCity(city.getEditText().getText().toString());
                user.setState(state.getEditText().getText().toString());
                user.setPincode(pincode.getEditText().getText().toString());
                user.setPassword(password.getEditText().getText().toString());
                database.getReference().child("Users")
                        .child(auth.getCurrentUser().getUid())
                        .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Editdetails.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
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
    private boolean validate_password(){
        String pass = password.getEditText().getText().toString();
        String cpass = confirmpassword.getEditText().getText().toString();
        if (pass.equals(cpass)){
            confirmpassword.setError(null);
            confirmpassword.setErrorEnabled(false);
            return true;
        } else {
            confirmpassword.setError("Password does not match");
            return false;
        }
    }
}
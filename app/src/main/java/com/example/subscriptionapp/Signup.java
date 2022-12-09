package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subscriptionapp.Models.Usermodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    MaterialButton register;
    TextView login;
    TextInputLayout fullname, phonenumber, mail, password
            , confirmpassword, address, town, city, pincode, state;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        fullname = findViewById(R.id.full_name);
        phonenumber = findViewById(R.id.phone_number);
        mail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);
        address = findViewById(R.id.address);
        town = findViewById(R.id.town);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validate_text(fullname) | !validate_text(phonenumber)
                | !validate_text(mail) | !validate_text(password) | !validate_text(confirmpassword)
                | !validate_text(address) | !validate_text(town) | !validate_text(city)
                | !validate_text(pincode) | !validate_text(state)) {
                    return;
                }

                if (!validate_password()) {
                    return;
                }


                String name, Phonenumber, Password, Address, Town, City, Pincode, State, Email;
                name = fullname.getEditText().getText().toString();
                Phonenumber = phonenumber.getEditText().getText().toString();
                Password = password.getEditText().getText().toString();
                Address = address.getEditText().getText().toString();
                Town = town.getEditText().getText().toString();
                City = city.getEditText().getText().toString();
                Pincode = pincode.getEditText().getText().toString();
                State = state.getEditText().getText().toString();
                Email = mail.getEditText().getText().toString();
                auth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = task.getResult().getUser().getUid();
                                    Usermodel user = new Usermodel(name, Phonenumber, Email, Password
                                    , Address, Town, City, Pincode, State, id);
                                    database.getReference().child("Users").child(id).setValue(user);
                                    startActivity(new Intent(Signup.this, Login.class));
                                } else {
                                    Toast.makeText(Signup.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
                finish();
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
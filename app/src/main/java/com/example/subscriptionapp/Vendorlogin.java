package com.example.subscriptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Vendorlogin extends AppCompatActivity {

    MaterialButton login;
    TextView forgotpassword;
    TextInputLayout email, password;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorlogin);


        login = findViewById(R.id.login);
        forgotpassword = findViewById(R.id.forgot_password);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validate_text(email) | !validate_text(password)){
                    return;
                }

                String uname = email.getEditText().getText().toString();
                String upass = password.getEditText().getText().toString();

                auth.signInWithEmailAndPassword(uname, upass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(Vendorlogin.this, EnterVenddorid.class));
                                    finish();
                                } else {
                                    Toast.makeText(Vendorlogin.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(Vendorlogin.this, EnterVenddorid.class);
            startActivity(intent);
            finish();
        }
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
package com.example.subscriptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Spalshscreen extends AppCompatActivity {

    public static int SPLASH_SCREEN = 4000;
    ImageView logoimage;
    Animation topanim, bottomanim;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalshscreen);

        //Animations
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logoimage = findViewById(R.id.logo);
        logoimage.setAnimation(topanim);
        title = findViewById(R.id.title);
        title.setAnimation(topanim);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(Spalshscreen.this, Selectusertype.class);
            startActivity(intent);
            finish();

        },SPLASH_SCREEN);
    }
}
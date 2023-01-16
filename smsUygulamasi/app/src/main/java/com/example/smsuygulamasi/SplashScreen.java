package com.example.smsuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        findViewById(R.id.splash_kayitol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this, KayitOlEkrani.class));
            }
        });

        findViewById(R.id.splash_girisyap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this, GirisEkrani.class));
            }
        });

    mauth = FirebaseAuth.getInstance();
    if(mauth.getCurrentUser() != null){
                startActivity(new Intent(SplashScreen.this, MainActivity.class));

     }
    }
}
package com.example.smsuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class KayitOlEkrani extends AppCompatActivity {

    EditText emailedittext,sifreedittext;
    Button girisyap;
    Button kayitol;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol_ekrani);

        emailedittext=findViewById(R.id.kayit_emailedittext);
        sifreedittext=findViewById(R.id.kayit_sifreedittext);
        girisyap=findViewById(R.id.kayit_girisbuton);
        kayitol=findViewById(R.id.kayit_kayitbuton);

        mAuth = FirebaseAuth.getInstance();

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailedittext.getText().toString();
                String password = sifreedittext.getText().toString();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    startActivity(new Intent(KayitOlEkrani.this,GirisEkrani.class));
                    finish();
                });
            }
        });

        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KayitOlEkrani.this,GirisEkrani.class));
                finish();
            }
        });

    }
}
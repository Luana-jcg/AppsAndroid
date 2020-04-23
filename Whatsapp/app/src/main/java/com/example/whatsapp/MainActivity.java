package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button botaoConcordar;
    private TextView textViewTermos;
    private FirebaseAuth auth = ConfiguracaoFirebase.auth();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = auth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
            finish();
        }

        botaoConcordar = findViewById(R.id.botaoConcordar);
        textViewTermos = findViewById(R.id.textViewTermos);

        textViewTermos.setMovementMethod(LinkMovementMethod.getInstance());

        botaoConcordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhoneNumberActivity.class));
            }
        });
    }
}

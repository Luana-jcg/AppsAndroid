package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.organizze.R;
import com.example.organizze.config.StatusBarColor;

public class ConfirmacaoEmailRedefinirSenhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_email_redefinir_senha);

        new StatusBarColor().statusBarColorDark(getWindow());

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent = new Intent(ConfirmacaoEmailRedefinirSenhaActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}

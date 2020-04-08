package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.organizze.R;
import com.example.organizze.config.StatusBarColor;

public class ConfirmacaoCadastroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_cadastro);

        new StatusBarColor().statusBarColorLight(getWindow());

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override public void run() {
                startActivity(new Intent(ConfirmacaoCadastroActivity.this, PrincipalActivity.class));
                finish();
            }
        }, 4000);
    }
}

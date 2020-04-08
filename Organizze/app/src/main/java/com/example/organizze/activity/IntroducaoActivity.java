package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.organizze.R;
import com.example.organizze.config.StatusBarColor;

public class IntroducaoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton botaoVoltar;
    private Button botaoCriarConta;
    private TextView botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducao);

        new StatusBarColor().statusBarColorLight(getWindow());

        botaoVoltar = findViewById(R.id.botaoVoltarIntroducao);
        botaoCriarConta = findViewById(R.id.botaoCriarConta);
        botaoLogin = findViewById(R.id.botaoLogin);

        botaoVoltar.setOnClickListener(this);
        botaoCriarConta.setOnClickListener(this);
        botaoLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botaoVoltarIntroducao:
                finish();
                break;
            case R.id.botaoCriarConta:
                startActivity(new Intent(IntroducaoActivity.this,
                        CadastroActivity.class));
                break;
            case R.id.botaoLogin:
                startActivity(new Intent(IntroducaoActivity.this,
                        LoginActivity.class));
                break;
        }
    }
}

package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Resultado extends AppCompatActivity {

    private ImageView imagemResultado;
    private int[] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        imagemResultado = findViewById(R.id.imagemResultado);

        int[] img = {
                R.drawable.moeda_cara,
                R.drawable.moeda_coroa
        };

        Bundle dados = getIntent().getExtras();
        int num = dados.getInt("numAleatorio");
        imagemResultado.setImageResource(img[num]);
    }

    public void voltar(View view){
        finish();
    }
}

package com.example.sorteio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void numAleatorio(View view){
        TextView textoSecundario = findViewById(R.id.textoSecundario);
        int numero = new Random().nextInt(11);
        textoSecundario.setText("O número selecionado: " + numero);
    }
}

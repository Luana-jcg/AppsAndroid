package com.example.frasesdodia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView frase;
    private String[] frases = {
            "O importante não é vencer todos os dias, mas lutar sempre.",
            "Maior que a tristeza de não haver vencido é a vergonha de não ter lutado!",
            "É melhor conquistar a si mesmo do que vencer mil batalhas.",
            "Quem ousou conquistar e saiu pra lutar, chega mais longe!",
            "Enquanto houver vontade de lutar haverá esperança de vencer.",
            "Difícil é ganhar um amigo em uma hora; fácil é ofendê-lo em um minuto.",
            "O medo de perder tira a vontade de ganhar.",
            "Aquele que não tem confiança nos outros, não lhes pode ganhar a confiança."};
    private int num;

    public void gerarFrase(){
        num = new Random().nextInt(8);
        frase.setText(frases[num]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frase = findViewById(R.id.frase);
        gerarFrase();
    }

    public void alterarFrase(View view){
        gerarFrase();
    }
}

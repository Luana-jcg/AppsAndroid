package com.example.caraoucoroa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void jogar(View view){
        Intent intent = new Intent(getApplicationContext(), Resultado.class);
        int num = new Random().nextInt(2);
        intent.putExtra("numAleatorio", num);
        startActivity(intent);
    }
}

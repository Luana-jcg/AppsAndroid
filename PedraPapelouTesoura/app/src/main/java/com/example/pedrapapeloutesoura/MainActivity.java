package com.example.pedrapapeloutesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selecionadoPedra(View view){
        opcaoSelecionada(0);
    }

    public void selecionadoPapel(View view){
        opcaoSelecionada(1);
    }

    public void selecionadoTesoura(View view){
        opcaoSelecionada(2);
    }

    public void opcaoSelecionada(int opcaoSelecionada){
        ImageView imagemUsuario = findViewById(R.id.imagemUsuario);
        ImageView imagemComputador = findViewById(R.id.imagemComputador);
        TextView textoResultado = findViewById(R.id.textoResultado);

        int num = new Random().nextInt(3);

        int[] imagens = {
                R.drawable.pedra,
                R.drawable.papel,
                R.drawable.tesoura
        };

        imagemUsuario.setImageResource(imagens[opcaoSelecionada]);
        imagemComputador.setImageResource(imagens[num]);

        // 0 - PEDRA
        // 1 - PAPEL
        // 2 - TESOURA

        if(opcaoSelecionada == num){
            textoResultado.setText("Empate  ");
            textoResultado.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_neutro_24dp,0);
        } else if(
                (opcaoSelecionada == 0 && num == 1) ||
                        (opcaoSelecionada == 1 && num == 2) ||
                        (opcaoSelecionada == 2 && num == 0)){
            textoResultado.setText("Você perdeu  ");
            textoResultado.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_insatisfeito_24dp,0);
        } else if(
                (opcaoSelecionada == 1 && num == 0) ||
                        (opcaoSelecionada == 2 && num == 1) ||
                        (opcaoSelecionada == 0 && num == 2)){
            textoResultado.setText("Você venceu  ");
            textoResultado.setCompoundDrawablesWithIntrinsicBounds(0,0,
                    R.drawable.ic_satisfeito_24dp,0);
        }
    }
}

package com.example.minhasanotacoes;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AnotacoesPreferencias anotacoesPreferencias;
    private EditText anotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anotacoes = findViewById(R.id.anotacao);
        anotacoesPreferencias = new AnotacoesPreferencias(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoRecuperado = anotacoes.getText().toString();
                if(textoRecuperado.equals("")){
                    Toast.makeText(getApplicationContext(), "Digite algo para salvar",
                            Toast.LENGTH_SHORT).show();
                }else{
                    anotacoesPreferencias.salvarAnotacao(textoRecuperado);
                    Toast.makeText(getApplicationContext(), "Anotação salva com sucesso",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        String anotacaoSalva = anotacoesPreferencias.recuperarAnotacao();
        if(!anotacaoSalva.equals("")){
            anotacoes.setText(anotacaoSalva);
            anotacoes.requestFocus();
        }
    }
}

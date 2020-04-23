package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.example.whatsapp.helper.BaseCustom64;
import com.example.whatsapp.model.Usuario;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;

public class DadosPerfilActivity extends AppCompatActivity {

    private Button botaoAvancarDadosPerfil;
    private EditText nome;
    private String num;
    private FirebaseAuth auth = ConfiguracaoFirebase.auth();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_perfil);

        botaoAvancarDadosPerfil = findViewById(R.id.botaoAvancarDadosPerfil);
        nome = findViewById(R.id.nome);

        Bundle dados = getIntent().getExtras();
        num = dados.getString("num");

        botaoAvancarDadosPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
                Intent intent = new Intent(DadosPerfilActivity.this, PrincipalActivity.class);
                intent.putExtra("nome", nome.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    public void salvar(){
        Usuario usuario = new Usuario();
        usuario.setNum(num);
        usuario.setNome(nome.getText().toString());
        usuario.setId(BaseCustom64.codificarNum(num));
        usuario.salvar();
    }
}

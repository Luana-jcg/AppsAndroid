package com.example.cardview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.cardview.R;
import com.example.cardview.adapter.AdapterPostagem;
import com.example.cardview.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Postagem> postagem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        gerarPostagem();
        AdapterPostagem adapterPostagem = new AdapterPostagem(postagem);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterPostagem);
    }

    public void gerarPostagem(){
        Postagem p = new Postagem(
                "Luana Galdino",
                "23-03-2020",
                R.drawable.imagem1,
                "viajando para França :)");
        postagem.add(p);

        p = new Postagem(
                "Kleison da Silva",
                "22-03-2020",
                R.drawable.imagem3,
                "Parissss");
        postagem.add(p);

        p = new Postagem(
                "Cintia Carvalho",
                "21-03-2020",
                R.drawable.imagem2,
                ":)");
        postagem.add(p);

        p = new Postagem(
                "Lucas Galdino",
                "20-03-2020",
                R.drawable.imagem4,
                "Trilha");
        postagem.add(p);
    }
}

package com.example.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.organizze.R;
import com.example.organizze.adapter.IntroViewPagerAdapter;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.config.StatusBarColor;
import com.example.organizze.model.ScreenItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private final List<ScreenItem> mList = new ArrayList<>();
    private TabLayout tabIndicador;
    private Button botaoProximo;
    private Button botaoPular;
    private int posicao = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        verificarUsuarioLogado();

        new StatusBarColor().statusBarColorLight(getWindow());

        // ini views
        botaoProximo = findViewById(R.id.botaoProximo);
        botaoPular = findViewById(R.id.botaoPular);
        tabIndicador = findViewById(R.id.tabLayout);

        // fill list screen

        mList.add(new ScreenItem("Registre suas \ndespesas",
                "As moedinhas do carro, do cofrinho, a conta poupança ou corrente: " +
                        "controle tudo! Categorize cada lançamento e saiba de uma vez por todas " +
                        "de onde vem e para onde vai o seu dinheiro!",
                R.drawable.onboarding_helper_one));
        mList.add(new ScreenItem("Crie metas e realize \nseus sonhos",
                "Um recurso fabuloso para você poupar dinheiro! Estipule quanto você " +
                        "deseja gastar por mês e chegue no final do período com mais dinheiro " +
                        "no bolso!",
                R.drawable.onboarding_helper_two));
        mList.add(new ScreenItem("Crie hábitos \nfinanceiros",
                "Através de uma sequência de ações diárias, semanais ou mensais, " +
                        "você vai conseguir um controle bem maior da sua vida financeira. " +
                        "Exercite essa disciplina!",
                R.drawable.onboarding_helper_three));

        // setup viewpager
        screenPager = findViewById(R.id.viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager
        tabIndicador.setupWithViewPager(screenPager);

        // next button click Listner
        botaoProximo.setOnClickListener(this);
        botaoPular.setOnClickListener(this);

        // tablayout add change listener
        tabIndicador.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    botaoProximo.setText("Começar");
                    botaoPular.setVisibility(View.INVISIBLE);
                } else {
                    botaoProximo.setText("Próximo");
                    botaoPular.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        posicao = screenPager.getCurrentItem();

        switch (v.getId()){
            case R.id.botaoProximo:
                if (posicao < mList.size()) {
                    posicao++;
                    screenPager.setCurrentItem(posicao);
                }
                if (posicao == mList.size()) {
                    Intent i = new Intent(getApplicationContext(), IntroducaoActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.botaoPular:
                Intent i = new Intent(getApplicationContext(), IntroducaoActivity.class);
                startActivity(i);
                break;
        }
    }

    public void verificarUsuarioLogado(){
        auth = ConfiguracaoFirebase.getAuth();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(IntroActivity.this, PrincipalActivity.class));
            finish();
        }
    }
}

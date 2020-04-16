package com.example.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.organizze.R;
import com.example.organizze.activity.ui.adicionar_despesas.AdicionarDespesasActivity;
import com.example.organizze.activity.ui.inicio.InicioFragment;
import com.example.organizze.activity.ui.lancamentos.LancamentosFragment;
import com.example.organizze.activity.ui.metas.MetasFragment;
import com.example.organizze.activity.ui.relatorios.RelatoriosFragment;
import com.example.organizze.config.StatusBarColor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class PrincipalActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        loadFragment(new InicioFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_inicio:
                fragment = new InicioFragment();
                break;
            case R.id.navigation_lancamento:
                fragment = new LancamentosFragment();
                break;
            case R.id.navigation_adicionar_despesa:
                startActivity(new Intent(PrincipalActivity.this, AdicionarDespesasActivity.class));
                break;
            case R.id.navigation_relatorio:
                fragment = new RelatoriosFragment();
                break;
            case R.id.navigation_meta:
                fragment = new MetasFragment();
                break;
        }

        return loadFragment(fragment);
    }
}

package com.example.organizze.activity.ui.adicionar_despesas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.StatusBarColor;
import com.example.organizze.fragment.DespesaFragment;
import com.example.organizze.fragment.ReceitaFragment;
import com.example.organizze.fragment.TransferenciaFragment;
import com.google.android.material.tabs.TabLayout;

public class AdicionarDespesasActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesas);

        new StatusBarColor().statusBarColorDark(
                getWindow());

        loadFragment(new DespesaFragment());

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0:
                        fragment = new DespesaFragment();
                        break;
                    case 1:
                        fragment = new ReceitaFragment();
                        break;
                    case 2:
                        fragment = new TransferenciaFragment();
                        break;
                }
                loadFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_adicionar_despesas, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}

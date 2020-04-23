package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.example.whatsapp.fragments.ChamadasFragment;
import com.example.whatsapp.fragments.ConversasFragment;
import com.example.whatsapp.fragments.StatusFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth auth = ConfiguracaoFirebase.auth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.tabConversas, ConversasFragment.class)
                .add(R.string.tabStatus, StatusFragment.class)
                .add(R.string.tabChamadas, ChamadasFragment.class)
                .create());

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuPesquisar:

                break;
            case R.id.menuConfiguracao:

                break;
            case R.id.menuSair:
                auth.signOut();
                startActivity(new Intent(PrincipalActivity.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.slider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    final List<ScreenItem> mList = new ArrayList<>();
    TabLayout tabIndicador;
    Button botaoProximo;
    Button botaoVoltar;
    int posicao = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // when this activity is about to be launch we need to check if its openened before or not
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);

        // hide the action bar
        getSupportActionBar().hide();

        // ini views
        botaoProximo = findViewById(R.id.botaoProximo);
        botaoVoltar = findViewById(R.id.botaoVoltar);
        tabIndicador = findViewById(R.id.tabLayout);

        // fill list screen

        mList.add(new ScreenItem("Fresh Food",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.ic_discover));
        mList.add(new ScreenItem("Fast Delivery",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.ic_offers));
        mList.add(new ScreenItem("Easy Payment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.ic_reward));

        // setup viewpager
        screenPager = findViewById(R.id.viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager
        tabIndicador.setupWithViewPager(screenPager);

        // next button click Listner
        botaoProximo.setOnClickListener(this);
        botaoVoltar.setOnClickListener(this);

        // tablayout add change listener
        tabIndicador.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    botaoProximo.setText("Começar");
                } else {
                    botaoProximo.setText("Próximo");
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

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
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
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    savePrefsData();
                    finish();
                }
                break;
            case R.id.botaoVoltar:
                if (posicao < mList.size()) {
                    posicao--;
                    screenPager.setCurrentItem(posicao);
                }
                break;
        }
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    //private void loaddLastScreen() {
    //    botaoProximo.setVisibility(View.INVISIBLE);
    //    btnGetStarted.setVisibility(View.VISIBLE);
    //    tvSkip.setVisibility(View.INVISIBLE);
    //    tabIndicador.setVisibility(View.INVISIBLE);
    //}
}

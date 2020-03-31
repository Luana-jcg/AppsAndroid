package com.example.aprendaingles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.aprendaingles.fragment.NumerosFragment;
import com.example.aprendaingles.fragment.AnimaisFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.viewPager);
        viewPagerTab = findViewById(R.id.viewPagerTab);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add("Animais", AnimaisFragment.class)
                .add("Números", NumerosFragment.class)
                .create()
        );

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }
}

package com.example.slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context c;
    List<ScreenItem> listScreenItem;

    public IntroViewPagerAdapter(Context c, List<ScreenItem> listScreenItem) {
        this.c = c;
        this.listScreenItem = listScreenItem;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = layoutInflater.inflate(R.layout.layout_screen,null);

        ImageView imagem = layoutScreen.findViewById(R.id.imagem);
        TextView titulo = layoutScreen.findViewById(R.id.titulo);
        TextView descricao = layoutScreen.findViewById(R.id.descricao);

        titulo.setText(listScreenItem.get(position).getTitulo());
        descricao.setText(listScreenItem.get(position).getDescricao());
        imagem.setImageResource(listScreenItem.get(position).getImagem());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return listScreenItem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

package com.example.aprendaingles.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aprendaingles.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimaisFragment extends Fragment implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private ImageView somCachorro, somGato, somLeao,
            somMacaco, somOvelha, somVaca;
    int idSom = 0;

    public AnimaisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animais, container, false);

        somCachorro = view.findViewById(R.id.somCachorro);
        somCachorro.setOnClickListener(this);

        somGato = view.findViewById(R.id.somGato);
        somGato.setOnClickListener(this);

        somLeao = view.findViewById(R.id.somLeao);
        somLeao.setOnClickListener(this);

        somMacaco = view.findViewById(R.id.somMacaco);
        somMacaco.setOnClickListener(this);

        somOvelha = view.findViewById(R.id.somOvelha);
        somOvelha.setOnClickListener(this);

        somVaca = view.findViewById(R.id.somVaca);
        somVaca.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.somCachorro:
                idSom = R.raw.dog;
                break;
            case R.id.somGato:
                idSom = R.raw.cat;
                break;
            case R.id.somLeao:
                idSom = R.raw.lion;
                break;
            case R.id.somMacaco:
                idSom = R.raw.monkey;
                break;
            case R.id.somOvelha:
                idSom = R.raw.sheep;
                break;
            case R.id.somVaca:
                idSom = R.raw.cow;
                break;
        }
        startSom(idSom);
    }

    public void startSom(int id){
        mediaPlayer = MediaPlayer.create(getActivity(), id);
        if(mediaPlayer != null){
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}

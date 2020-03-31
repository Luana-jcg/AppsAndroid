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
public class NumerosFragment extends Fragment implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private ImageView somNumeroUm, somNumeroDois, somNumeroTres,
            somNumeroQuatro, somNumeroCinco, somNumeroSeis;
    private int idSomNumeros = 0;

    public NumerosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_numeros, container, false);

        somNumeroUm = view.findViewById(R.id.somNumeroUm);
        somNumeroUm.setOnClickListener(this);

        somNumeroDois = view.findViewById(R.id.somNumeroDois);
        somNumeroDois.setOnClickListener(this);

        somNumeroTres = view.findViewById(R.id.somNumeroTres);
        somNumeroTres.setOnClickListener(this);

        somNumeroQuatro = view.findViewById(R.id.somNumeroQuatro);
        somNumeroQuatro.setOnClickListener(this);

        somNumeroCinco = view.findViewById(R.id.somNumeroCinco);
        somNumeroCinco.setOnClickListener(this);

        somNumeroSeis = view.findViewById(R.id.somNumeroSeis);
        somNumeroSeis.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.somNumeroUm:
                idSomNumeros = R.raw.one;
                break;
            case R.id.somNumeroDois:
                idSomNumeros = R.raw.two;
                break;
            case R.id.somNumeroTres:
                idSomNumeros = R.raw.three;
                break;
            case R.id.somNumeroQuatro:
                idSomNumeros = R.raw.four;
                break;
            case R.id.somNumeroCinco:
                idSomNumeros = R.raw.five;
                break;
            case R.id.somNumeroSeis:
                idSomNumeros = R.raw.six;
                break;
        }
        startSom(idSomNumeros);
    }

    public void startSom(int id){
        mediaPlayer = MediaPlayer.create(getContext(), id);
        if(mediaPlayer != null) {
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

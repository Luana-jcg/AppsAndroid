package com.example.atmconsultoria.ui.sobre;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atmconsultoria.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {

    public SobreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String descricao = "Nossa missão é organizar as informações do mundo " +
                "para que sejam universalmente acessíveis e úteis para todos";

        Element versao = new Element().setTitle("Versão 1.0");
        Element desenvolvedor = new Element().setTitle("Desenvolvido por: Luana Galdino");

        View view = new AboutPage(getActivity())
                .setImage(R.drawable.logo)
                .setDescription(descricao)
                .addItem(versao)
                .addItem(desenvolvedor)

                /*.addGroup("Redes Sociais")
                .addFacebook("#", "Facebook")
                .addInstagram("#", "Instagram")*/

                .create();
        return view;
    }
}

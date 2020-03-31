package com.example.atmconsultoria.ui.contato;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atmconsultoria.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatoFragment extends Fragment {

    private TextView linkFacebook;
    private TextView linkInstagram;
    private TextView linkSite;
    private TextView linkEmail;

    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        linkEmail = view.findViewById(R.id.linkEmail);
        linkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent("email","atendimento@atmconsultoria.com");
            }
        });

        linkSite = view.findViewById(R.id.linkSite);
        linkSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent("web","https://www.google.com/#");
            }
        });

        linkFacebook = view.findViewById(R.id.linkFacebook);
        linkFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent("web", "https://www.facebook.com/#");
            }
        });

        linkInstagram = view.findViewById(R.id.linkInstagram);
        linkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent("web","https://www.instagram.com/#");
            }
        });

        return view;
    }

    public void intent(String tipo, String url){
        Intent intent = new Intent();

        if(tipo == "email"){
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {url});
        } else if(tipo == "web"){
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
        }
        startActivity(intent);
    }
}

package com.example.minhasanotacoes;

import android.content.Context;
import android.content.SharedPreferences;

public class AnotacoesPreferencias {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "anotacao_preferencias";
    private final String CHAVE_NOME = "anotacoes";

    public AnotacoesPreferencias(Context c) {
        this.context = c;
        sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = sharedPreferences.edit();
    }

    public void salvarAnotacao(String anotacoes){
        editor.putString(CHAVE_NOME, anotacoes);
        editor.commit();
    }

    public String recuperarAnotacao(){
        return sharedPreferences.getString(CHAVE_NOME, "");
    }

}

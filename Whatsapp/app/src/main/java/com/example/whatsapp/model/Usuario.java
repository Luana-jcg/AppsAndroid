package com.example.whatsapp.model;

import android.util.Log;

import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String id, num, nome;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.database();
        DatabaseReference usuario = reference.child("usuarios").child(getId());
        usuario.setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

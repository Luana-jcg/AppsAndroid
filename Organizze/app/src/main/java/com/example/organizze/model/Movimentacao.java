package com.example.organizze.model;

import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private Double valor;
    private String descricao, categoria, conta_origem, conta_destino, data, tipo, id;

    public Movimentacao() {

    }

    public void salvar(){
        FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
        String id = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        String dataFormatada = DateCustom.mesAnoEscolhido(getData());
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabase();
        databaseReference.child("movimentacao").child(id).child(dataFormatada).push().setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getContaOrigem() {
        return conta_origem;
    }

    public void setContaOrigem(String conta_origem) {
        this.conta_origem = conta_origem;
    }

    public String getContaDestino() {
        return conta_destino;
    }

    public void setContaDestino(String conta_destino) {
        this.conta_destino = conta_destino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

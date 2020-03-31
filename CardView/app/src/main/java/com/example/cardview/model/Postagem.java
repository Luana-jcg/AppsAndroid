package com.example.cardview.model;

public class Postagem {
    private String autor;
    private String data;
    private int imagem;
    private String descricao;

    public Postagem() {

    }

    public Postagem(String autor, String data, int imagem, String descricao) {
        this.autor = autor;
        this.data = data;
        this.imagem = imagem;
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

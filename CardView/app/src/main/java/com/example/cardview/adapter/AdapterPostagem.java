package com.example.cardview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardview.R;
import com.example.cardview.model.Postagem;

import java.util.List;

public class AdapterPostagem extends RecyclerView.Adapter<AdapterPostagem.MyHolderView> {
    private List<Postagem> postagem;

    public AdapterPostagem(List<Postagem> p){
        postagem = p;
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_postagem, parent, false);
        return new MyHolderView(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        Postagem p = postagem.get(position);
        holder.autor.setText(p.getAutor());
        holder.data.setText(p.getData());
        holder.imagem.setImageResource(p.getImagem());
        holder.descricao.setText(p.getDescricao());
    }

    @Override
    public int getItemCount() {
        return postagem.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder{
        private TextView autor;
        private TextView data;
        private ImageView imagem;
        private TextView descricao;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.autor);
            data = itemView.findViewById(R.id.data);
            imagem = itemView.findViewById(R.id.imagem);
            descricao = itemView.findViewById(R.id.descricao);
        }
    }
}

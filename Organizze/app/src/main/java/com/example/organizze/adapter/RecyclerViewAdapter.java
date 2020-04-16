package com.example.organizze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizze.R;
import com.example.organizze.model.Movimentacao;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Movimentacao> m;
    private Context context;

    public RecyclerViewAdapter(List<Movimentacao> movimentacao, Context c) {
        this.m = movimentacao;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recyclerview, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movimentacao movimentacao = m.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if(!movimentacao.getDescricao().equals("")){
            holder.titulo_list.setText(movimentacao.getDescricao());
        }else{
            holder.titulo_list.setText(movimentacao.getCategoria());
        }

        holder.subtitulo_list.setText(movimentacao.getContaOrigem());
        holder.valor_list.setText(decimalFormat.format(movimentacao.getValor()));
        holder.valor_list.setTextColor(context.getResources().getColor(R.color.colorPrimaryVerde));

        if(movimentacao.getTipo().equals("Despesa")){
            holder.status_list.setText("pago");
            holder.valor_list.setText("-" + decimalFormat.format(movimentacao.getValor()));
            holder.valor_list.setTextColor(context.getResources().getColor(R.color.colorDespesa));
        }else if(movimentacao.getTipo().equals("Receita")){
            holder.status_list.setText("recebido");
        }else if(movimentacao.getTipo().equals("Transferência")){
            holder.status_list.setText("");
            holder.subtitulo_list.setText(movimentacao.getContaDestino());
        }

    }

    @Override
    public int getItemCount() {
        return m.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView backgroud_img_list, img_list;
        private TextView titulo_list, subtitulo_list, valor_list, status_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            backgroud_img_list = itemView.findViewById(R.id.backgroud_img_list);
            img_list = itemView.findViewById(R.id.img_list);
            titulo_list = itemView.findViewById(R.id.titulo_list);
            subtitulo_list = itemView.findViewById(R.id.subtitulo_list);
            valor_list = itemView.findViewById(R.id.valor_list);
            status_list = itemView.findViewById(R.id.status_list);

        }
    }
}

package com.example.organizze.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.config.StatusBarColor;
import com.example.organizze.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class InicioFragment extends Fragment {

    private DatabaseReference usuarioRef;
    private Double despesaTotal = 0.0, receitaTotal = 0.0, saldo = 0.0;
    private TextView textViewSaldo;
    private ConstraintLayout cabecalhoInicio;
    private ConstraintLayout cabecalhoInicioVerde;
    private ConstraintLayout cabecalhoInicioVermelho;
    private ValueEventListener valueEventListenerUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, null);

        new StatusBarColor().statusBarColorDark(getActivity().getWindow());

        textViewSaldo = view.findViewById(R.id.textViewSaldo);
        cabecalhoInicio = view.findViewById(R.id.cabecalhoInicio);
        cabecalhoInicioVerde = view.findViewById(R.id.cabecalhoInicioVerde);
        cabecalhoInicioVermelho = view.findViewById(R.id.cabecalhoInicioVermelho);

        return view;
    }

    public void recuperarResumo(){
        usuarioRef = ConfiguracaoFirebase.getEmailCodificado();
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                saldo = receitaTotal - despesaTotal;
                if(saldo < 0.0){
                    cabecalhoInicio.setVisibility(View.INVISIBLE);
                    cabecalhoInicioVerde.setVisibility(View.INVISIBLE);
                    cabecalhoInicioVermelho.setVisibility(View.VISIBLE);
                }else if(saldo >= 0.0){
                    cabecalhoInicio.setVisibility(View.INVISIBLE);
                    cabecalhoInicioVerde.setVisibility(View.VISIBLE);
                    cabecalhoInicioVermelho.setVisibility(View.INVISIBLE);
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                textViewSaldo.setText("R$ " + decimalFormat.format(saldo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarResumo();
    }
}

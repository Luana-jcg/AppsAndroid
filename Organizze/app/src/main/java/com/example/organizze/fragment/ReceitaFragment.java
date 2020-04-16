package com.example.organizze.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.helper.DateCustom;
import com.example.organizze.model.Movimentacao;
import com.example.organizze.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceitaFragment extends Fragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private EditText editTextValorReceita, editTextDescricaoReceita;
    private Spinner spinnerCategoriaReceita, spinnerContaReceita;
    private ArrayAdapter<CharSequence> arrayCategoriaReceita, arrayContaReceita;
    private TextView textViewDataReceita;
    private Button botaoFixoReceita, botaoParceladoReceita;
    private FloatingActionButton botaoConfirmarReceita;
    private Movimentacao movimentacao;
    private String valorSpinnerCategoria, valorSpinnerConta;
    private Double receitaTotal = 0.0, receitaAtualizada = 0.0;
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receita, null);

        editTextValorReceita = view.findViewById(R.id.editTextValorReceita);
        editTextDescricaoReceita = view.findViewById(R.id.editTextDescricaoReceita);
        spinnerCategoriaReceita = view.findViewById(R.id.arrayCategoriaReceita);
        spinnerContaReceita = view.findViewById(R.id.arrayContaReceita);
        textViewDataReceita = view.findViewById(R.id.textViewDataReceita);
        botaoFixoReceita = view.findViewById(R.id.botaoFixoReceita);
        botaoParceladoReceita = view.findViewById(R.id.botaoParceladoReceita);
        botaoConfirmarReceita = view.findViewById(R.id.botaoConfirmarReceita);

        textViewDataReceita.setOnClickListener(this);
        botaoFixoReceita.setOnClickListener(this);
        botaoParceladoReceita.setOnClickListener(this);
        botaoConfirmarReceita.setOnClickListener(this);

        textViewDataReceita.setText(DateCustom.dataAtual());

        recuperarReceita();

        arrayCategoriaReceita = ArrayAdapter.createFromResource(
                getContext(), R.array.array_categoria, android.R.layout.simple_spinner_item
        );
        arrayCategoriaReceita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoriaReceita.setAdapter(arrayCategoriaReceita);

        arrayContaReceita = ArrayAdapter.createFromResource(
                getContext(), R.array.array_pagamento, android.R.layout.simple_spinner_item
        );
        arrayContaReceita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContaReceita.setAdapter(arrayContaReceita);

        spinnerCategoriaReceita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valorSpinnerCategoria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerContaReceita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valorSpinnerConta = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewDataReceita:
                abrirCalendario();
                break;
            case R.id.botaoFixoReceita:
                break;
            case R.id.botaoParceladoReceita:
                break;
            case R.id.botaoConfirmarReceita:
                salvarReceita();
                break;
        }
    }

    public void abrirCalendario(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show(getActivity().getSupportFragmentManager(), "DatePickerDialog");
        dialog.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int ano, int mes, int dia) {
        textViewDataReceita.setText(dia + "/" + (mes+1) + "/" + ano);
    }

    private void salvarReceita(){
        if(!editTextValorReceita.getText().toString().equals("")) {
            movimentacao = new Movimentacao();
            Double valor = Double.parseDouble(editTextValorReceita.getText().toString());
            movimentacao.setValor(valor);
            movimentacao.setDescricao(editTextDescricaoReceita.getText().toString());
            movimentacao.setCategoria(valorSpinnerCategoria);
            movimentacao.setContaOrigem(valorSpinnerConta);
            movimentacao.setData(textViewDataReceita.getText().toString());
            movimentacao.setTipo("Receita");
            receitaAtualizada = receitaTotal + valor;
            atualizarReceita();
            movimentacao.salvar();
            Toast.makeText(getContext(),
                    "Sua receita foi salva com sucesso.",
                    Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }else{
            Toast.makeText(getContext(),
                    "Por favor, preencha o valor da receita.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarReceita(){
        usuarioRef = ConfiguracaoFirebase.getEmailCodificado();
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void atualizarReceita(){
        usuarioRef = ConfiguracaoFirebase.getEmailCodificado();
        usuarioRef.child("receitaTotal").setValue(receitaAtualizada);
    }
}

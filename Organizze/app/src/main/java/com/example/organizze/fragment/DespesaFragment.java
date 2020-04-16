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
public class DespesaFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener {

    private Spinner spinner_categoria, spinner_pagamento;
    private ArrayAdapter<CharSequence> adapter_categoria, adapter_pagamento;
    private EditText editTextValorDespesa, editTextDescricaoDespesa;
    private TextView textViewDataDespesa;
    private Button botaoFixoDespesa, botaoParceladoDespesa;
    private FloatingActionButton botaoConfirmarDespesa;
    private Movimentacao movimentacao;
    private String valorSpinnerCategoria;
    private String valorSpinnerPagamento;
    private DatabaseReference usuarioRef;
    private Double despesaTotal = 0.0, despesaAtualizada = 0.0;
    private ValueEventListener valueEventListenerUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_despesa, null);

        editTextValorDespesa = view.findViewById(R.id.editTextValorDespesa);
        editTextDescricaoDespesa = view.findViewById(R.id.editTextDescricaoDespesa);
        spinner_categoria = view.findViewById(R.id.arrayCategoriaDespesa);
        spinner_pagamento = view.findViewById(R.id.arrayPagamentoDespesa);
        textViewDataDespesa = view.findViewById(R.id.textViewDataDespesa);
        botaoFixoDespesa = view.findViewById(R.id.botaoFixoDespesa);
        botaoParceladoDespesa = view.findViewById(R.id.botaoParceladoDespesa);
        botaoConfirmarDespesa = view.findViewById(R.id.botaoConfirmarDespesa);

        textViewDataDespesa.setOnClickListener(this);
        botaoFixoDespesa.setOnClickListener(this);
        botaoParceladoDespesa.setOnClickListener(this);
        botaoConfirmarDespesa.setOnClickListener(this);

        textViewDataDespesa.setText(DateCustom.dataAtual());

        recuperarDespesa();

        adapter_categoria = ArrayAdapter.createFromResource(
                getContext(), R.array.array_categoria, android.R.layout.simple_spinner_item
        );
        adapter_categoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoria.setAdapter(adapter_categoria);

        adapter_pagamento = ArrayAdapter.createFromResource(
                getContext(), R.array.array_pagamento, android.R.layout.simple_spinner_item
        );
        adapter_pagamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pagamento.setAdapter(adapter_pagamento);

        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valorSpinnerCategoria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_pagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valorSpinnerPagamento = parent.getItemAtPosition(position).toString();
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
            case R.id.textViewDataDespesa:
                abrirCalendario();
                break;
            case R.id.botaoFixoDespesa:
                break;
            case R.id.botaoParceladoDespesa:
                break;
            case R.id.botaoConfirmarDespesa:
                salvarDespesa();
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

        dialog.show(getActivity().getSupportFragmentManager(), "DataPickerDialog");
        dialog.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int ano, int mes, int dia) {
        String data = dia+"/"+(mes+1)+"/"+ano;
        textViewDataDespesa.setText(data);
    }

    public void salvarDespesa(){
        if(!editTextValorDespesa.getText().toString().equals("")){
            movimentacao = new Movimentacao();
            Double valor = Double.parseDouble(editTextValorDespesa.getText().toString());
            movimentacao.setValor(valor);
            movimentacao.setDescricao(editTextDescricaoDespesa.getText().toString());
            movimentacao.setCategoria(valorSpinnerCategoria);
            movimentacao.setContaOrigem(valorSpinnerPagamento);
            movimentacao.setData(textViewDataDespesa.getText().toString());
            movimentacao.setTipo("Despesa");
            despesaAtualizada = despesaTotal + valor;
            atualizarDespesa();
            movimentacao.salvar();
            Toast.makeText(getContext(),
                    "Sua despesa foi salva com sucesso.",
                    Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }else{
            Toast.makeText(getContext(),
                    "Por favor, preencha o valor da despesa.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarDespesa(){
        usuarioRef = ConfiguracaoFirebase.getEmailCodificado();
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarDespesa(){
        usuarioRef = ConfiguracaoFirebase.getEmailCodificado();
        usuarioRef.child("despesaTotal").setValue(despesaAtualizada);
    }
}

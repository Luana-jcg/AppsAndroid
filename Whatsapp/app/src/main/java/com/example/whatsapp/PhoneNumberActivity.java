package com.example.whatsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.whatsapp.helper.EstilosText;
import com.example.whatsapp.helper.MaskEditUtil;

public class PhoneNumberActivity extends AppCompatActivity {

    private Spinner spinnerPais;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private TextView codPais, textView4;
    private EditText numTelefone;
    private Button botaoAvancar;
    private String num, cod, numSemMask;
    private SpannableString s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        textView4 = findViewById(R.id.textView4);
        spinnerPais = findViewById(R.id.arrayPais);
        codPais = findViewById(R.id.codPais);
        numTelefone = findViewById(R.id.numTelefone);
        botaoAvancar = findViewById(R.id.botaoAvancar);

        s = EstilosText.color(getString(R.string.subTituloLogin), 75, 93);
        textView4.setText(s);

        arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.arrayPais,
                R.layout.simple_spinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(arrayAdapter);

        numTelefone.addTextChangedListener(MaskEditUtil.mask(numTelefone, MaskEditUtil.FORMAT_FONE));

        botaoAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = numTelefone.getText().toString();
                cod = codPais.getText().toString();
                if(!num.equals("")){
                    numSemMask = MaskEditUtil.unmask(numTelefone.getText().toString());
                    if((numSemMask.length() == 11)){
                        if(!(numSemMask.charAt(0) == '0') && !(numSemMask.charAt(1) == '0') && !(numSemMask.charAt(2) == '0')){
                            alertDialog("Nós confirmaremos o número:\n\n" +
                                            "+" + cod + " " + num + "\n\nEsse número está correto ou " +
                                            "deseja editá-lo?" ,
                                    "OK", "EDITAR");
                        }else{
                            alertDialog("+" + cod + " " + num +
                                            " não é um número válido para o país Brasil.",
                                    "OK", "");
                        }
                    }else{
                        alertDialog("O número de telefone que você inseriu é muito curto para " +
                                        "o país: Brasil.\n\nInclua o código de área (DDD) caso você " +
                                        "não o tenha colocado.",
                                "OK", "");
                    }
                }else{
                    alertDialog("Insira seu número de telefone.",
                            "OK", "");
                }
            }
        });
    }

    public void alertDialog(String msg, String botaoPositivo, String botaoNegativo){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PhoneNumberActivity.this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        if(botaoNegativo.equals("EDITAR")){
            alertDialog.setPositiveButton(botaoPositivo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(PhoneNumberActivity.this, VerificarCodActivity.class);
                    i.putExtra("numTelefone", "+" + cod + " " + num);
                    startActivity(i);
                }
            });
        }else{
            alertDialog.setPositiveButton(botaoPositivo, null);
        }
        alertDialog.setNegativeButton(botaoNegativo, null);
        alertDialog.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        numTelefone.setText("");
    }
}

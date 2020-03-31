package com.example.alcoolougasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText precoAlcool;
    private TextInputEditText precoGasolina;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = findViewById(R.id.precoAlcool);
        precoGasolina = findViewById(R.id.precoGasolina);
        resultado = findViewById(R.id.resultado);
    }

    public void calcular(View view){

        String valorAlcool = precoAlcool.getText().toString();
        String valorGasolina = precoGasolina.getText().toString();

        boolean campos = validarCampos(valorAlcool, valorGasolina);

        if(campos){
            resultado.setText("Verifique se todos os campos foram preenchidos");
        } else{
            float vAlcool = Float.parseFloat(valorAlcool);
            float vGasolina = Float.parseFloat(valorGasolina);
            float res = vAlcool / vGasolina;

            if(res < 0.7){
                resultado.setText("Melhor utilizar Álcool");
            } else{
                resultado.setText("Melhor utilizar Gasolina");
            }
        }
    }

    public Boolean validarCampos(String valorAlcool, String valorGasolina){
        boolean campos = false;
        if(valorAlcool.equals("") || valorGasolina.equals("")){
            campos = true;
        }
        return campos;
    }

    public void limpar(View view){
        precoAlcool.setText("");
        precoGasolina.setText("");
        resultado.setText("");
        precoAlcool.requestFocus();
    }
}

package com.example.calculadoradegorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private EditText valorConsumo;
    private SeekBar seekBarProgresso;
    private TextView textoProgresso;
    private TextView valorGorjeta;
    private TextView valorTotal;
    private float porcentagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorConsumo = findViewById(R.id.valorConsumo);
        seekBarProgresso = findViewById(R.id.seekBarProgresso);
        textoProgresso = findViewById(R.id.textoProgresso);
        valorGorjeta = findViewById(R.id.valorGorjeta);
        valorTotal = findViewById(R.id.valorTotal);

        seekBarProgresso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                porcentagem = progress;
                calcular();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calcular(){
        String vConsumoFinal = valorConsumo.getText().toString();
        if(vConsumoFinal == null || vConsumoFinal.equals("")){
            Toast.makeText(getApplicationContext(), "Digite um valor", Toast.LENGTH_SHORT).show();
        }else{
            float valorConsumoFinal = Float.parseFloat(vConsumoFinal);
            float valorGorjetaFinal = valorConsumoFinal * (porcentagem/100);
            float valorTotalFinal = valorConsumoFinal + valorGorjetaFinal;
            textoProgresso.setText(seekBarProgresso.getProgress() + "%");
            valorGorjeta.setText("R$ " + valorGorjetaFinal);
            valorTotal.setText("R$ " + valorTotalFinal);
        }
    }
}

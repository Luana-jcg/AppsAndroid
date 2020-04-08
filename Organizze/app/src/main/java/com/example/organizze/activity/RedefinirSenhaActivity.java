package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.config.StatusBarColor;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedefinirSenhaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button botaoContinuarRedefinirSenha;
    private ImageButton botaoVoltarRedefinirSenha;
    private EditText editTextEmailRedefinirSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        new StatusBarColor().statusBarColorLight(getWindow());

        botaoContinuarRedefinirSenha = findViewById(R.id.botaoContinuarRedefinirSenha);
        botaoVoltarRedefinirSenha = findViewById(R.id.botaoVoltarRedefinirSenha);
        editTextEmailRedefinirSenha = findViewById(R.id.editTextEmailRedefinirSenha);

        botaoContinuarRedefinirSenha.setOnClickListener(this);
        botaoVoltarRedefinirSenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botaoContinuarRedefinirSenha:

                String textoEmail = editTextEmailRedefinirSenha.getText().toString();

                if(!textoEmail.equals("")){
                    if(validarEmail(textoEmail)){
                        redefinirSenha(textoEmail);
                        configBotao(false, R.color.colorCinza);
                    }else{
                        toast("Digite um e-mail válido.");
                    }
                }else{
                    toast("Digite um e-mail.");
                }

                break;

            case R.id.botaoVoltarRedefinirSenha:
                finish();
                break;
        }
    }

    private boolean validarEmail(String email){
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }
        return false;
    }

    private void redefinirSenha(String textoEmail) {
        auth = ConfiguracaoFirebase.getAuth();
        auth.sendPasswordResetEmail(textoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(RedefinirSenhaActivity.this,
                            ConfirmacaoEmailRedefinirSenhaActivity.class));
                    finish();
                }else{
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        toast("E-mail não encontrado.");
                    } catch (Exception e){
                        toast("Erro ao enviar o email. " + e.getMessage());
                        e.printStackTrace();
                    }
                    configBotao(true, R.color.colorAccent);
                }
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(RedefinirSenhaActivity.this,
                msg,
                Toast.LENGTH_SHORT).show();
    }

    private void configBotao(boolean status, int cor){
        botaoContinuarRedefinirSenha.setEnabled(status);
        botaoContinuarRedefinirSenha.setBackgroundColor(
                getResources().getColor(cor));
    }

}

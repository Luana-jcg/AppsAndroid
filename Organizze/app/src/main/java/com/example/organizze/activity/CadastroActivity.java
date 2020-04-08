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
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.config.StatusBarColor;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imagemProgressoCadastro;
    private Button botaoContinuarCadastro;
    private ImageButton botaoVoltarCadastro;
    private EditText editTextEmailCadastro, editTextNomeCadastro, editTextSenhaCadastro;
    private ConstraintLayout constraintLayoutEmailCadastro, constraintLayoutNomeCadastro, constraintLayoutSenhaCadastro;

    private int pagina = 1;

    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        new StatusBarColor().statusBarColorLight(getWindow());

        imagemProgressoCadastro = findViewById(R.id.imagemProgressoCadastro);
        botaoContinuarCadastro = findViewById(R.id.botaoContinuarCadastro);
        botaoVoltarCadastro = findViewById(R.id.botaoVoltarCadastro);
        editTextEmailCadastro = findViewById(R.id.editTextEmailCadastro);
        editTextNomeCadastro = findViewById(R.id.editTextNomeCadastro);
        editTextSenhaCadastro = findViewById(R.id.editTextSenhaCadastro);
        constraintLayoutEmailCadastro = findViewById(R.id.constraintLayoutEmailCadastro);
        constraintLayoutNomeCadastro = findViewById(R.id.constraintLayoutNomeCadastro);
        constraintLayoutSenhaCadastro = findViewById(R.id.constraintLayoutSenhaCadastro);

        botaoContinuarCadastro.setOnClickListener(this);
        botaoVoltarCadastro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botaoContinuarCadastro:

                String textoEmail = editTextEmailCadastro.getText().toString();
                String textoNome = editTextNomeCadastro.getText().toString();
                String textoSenha = editTextSenhaCadastro.getText().toString();

                switch(pagina){
                    case 1:
                        email(textoEmail);
                        break;
                    case 2:
                        nome(textoNome);
                        break;
                    case 3:
                        senha(textoEmail, textoNome, textoSenha);
                        break;
                }

                break;

            case R.id.botaoVoltarCadastro:
                finish();
                break;
        }
    }

    private void email(String textoEmail){
        if(!textoEmail.equals("")){
            if(validarEmail(textoEmail)){
                constraintLayoutEmailCadastro.setVisibility(View.INVISIBLE);
                constraintLayoutNomeCadastro.setVisibility(View.VISIBLE);
                imagemProgressoCadastro.setImageResource(R.drawable.progresso_3);
                pagina = 2;
            }else{
                toast("Digite um e-mail válido.");
            }
        }else{
            toast("Digite um e-mail.");
        }
    }

    private void nome(String textoNome){
        if(!textoNome.equals("")){
            if(textoNome.length() >= 5){
                constraintLayoutNomeCadastro.setVisibility(View.INVISIBLE);
                constraintLayoutSenhaCadastro.setVisibility(View.VISIBLE);
                imagemProgressoCadastro.setImageResource(R.drawable.progresso_final);
                pagina = 3;
            }else{
                toast("Digite o seu nome completo.");
            }
        }else{
            toast("Digite o seu nome.");
        }
    }

    private void senha(String textoEmail, String textoNome, String textoSenha){
        if(!textoSenha.equals("")){
            if(textoSenha.length() >= 6){
                usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setNome(textoNome);
                usuario.setSenha(textoSenha);
                cadastrarUsuario();
                configBotao(false, R.color.colorCinza);
            }else{
                toast("A senha deve ter no mínimo 6 caracteres.");
            }
        }else{
            toast("Digite uma senha.");
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

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(CadastroActivity.this,
                            ConfirmacaoCadastroActivity.class));
                    finish();
                }else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e){
                        toast("Esse e-mail já existe.");
                    } catch (Exception e){
                        toast("Erro ao cadastrar o usuário " + e.getMessage());
                        e.printStackTrace();
                    }
                    configBotao(true, R.color.colorAccent);
                }
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(CadastroActivity.this,
                msg,
                Toast.LENGTH_SHORT).show();
    }

    private void configBotao(boolean status, int cor){
        botaoContinuarCadastro.setEnabled(status);
        botaoContinuarCadastro.setBackgroundColor(
                getResources().getColor(cor));
    }

}

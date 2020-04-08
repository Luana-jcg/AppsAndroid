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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imagemProgressoLogin;
    private Button botaoContinuarLogin;
    private ImageButton botaoVoltarLogin;
    private TextView textEsqueciSenha;
    private EditText editTextEmailLogin, editTextSenhaLogin;
    private ConstraintLayout constraintLayoutEmailLogin, constraintLayoutSenhaLogin;

    private int pagina = 1;

    private FirebaseAuth auth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new StatusBarColor().statusBarColorLight(getWindow());

        imagemProgressoLogin = findViewById(R.id.imagemProgressoLogin);
        botaoContinuarLogin = findViewById(R.id.botaoContinuarLogin);
        botaoVoltarLogin = findViewById(R.id.botaoVoltarLogin);
        textEsqueciSenha = findViewById(R.id.textEsqueciSenha);
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextSenhaLogin = findViewById(R.id.editTextSenhaLogin);
        constraintLayoutEmailLogin = findViewById(R.id.constraintLayoutEmailLogin);
        constraintLayoutSenhaLogin = findViewById(R.id.constraintLayoutSenhaLogin);

        botaoContinuarLogin.setOnClickListener(this);
        botaoVoltarLogin.setOnClickListener(this);
        textEsqueciSenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botaoContinuarLogin:

                String textoEmail = editTextEmailLogin.getText().toString();
                String textoSenha = editTextSenhaLogin.getText().toString();

                switch(pagina){
                    case 1:
                        email(textoEmail);
                        break;
                    case 2:
                        senha(textoEmail, textoSenha);
                        break;
                }

                break;

            case R.id.botaoVoltarLogin:
                finish();
                break;
            case R.id.textEsqueciSenha:
                startActivity(new Intent(LoginActivity.this,
                        RedefinirSenhaActivity.class));
                finish();
                break;
        }
    }

    private void email(String textoEmail){
        if(!textoEmail.equals("")){
            constraintLayoutEmailLogin.setVisibility(View.INVISIBLE);
            constraintLayoutSenhaLogin.setVisibility(View.VISIBLE);
            imagemProgressoLogin.setImageResource(R.drawable.progresso_final);
            pagina = 2;
        }else{
            toast("Digite um e-mail.");
        }
    }

    private void senha(String textoEmail, String textoSenha){
        if(!textoSenha.equals("")){
            usuario = new Usuario();
            usuario.setEmail(textoEmail);
            usuario.setSenha(textoSenha);
            logarUsuario();
            configBotao(false, R.color.colorCinza);
        }else{
            toast("Digite uma senha.");
        }
    }

    private void logarUsuario(){
        auth = ConfiguracaoFirebase.getAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,
                            PrincipalActivity.class));
                    finish();
                }else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        toast("E-mail não cadastrado.");
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        toast("Senha incorreta.");
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
        Toast.makeText(LoginActivity.this,
                msg,
                Toast.LENGTH_SHORT).show();
    }

    private void configBotao(boolean status, int cor){
        botaoContinuarLogin.setEnabled(status);
        botaoContinuarLogin.setBackgroundColor(
                getResources().getColor(cor));
    }
}

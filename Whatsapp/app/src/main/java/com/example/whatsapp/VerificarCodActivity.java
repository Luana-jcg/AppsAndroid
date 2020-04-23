package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.config.ConfiguracaoFirebase;
import com.example.whatsapp.helper.EstilosText;
import com.example.whatsapp.helper.MaskEditUtil;
import com.example.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.Provider;
import java.util.concurrent.TimeUnit;

public class VerificarCodActivity extends AppCompatActivity {

    private EditText cod;
    private TextView textView6, textView7, textReenviarSms;
    private String num, subTit, vericacaoId, numSemMask;
    private SpannableString s1;
    private FirebaseAuth auth = ConfiguracaoFirebase.auth();
    private PhoneAuthProvider.ForceResendingToken token;
    private ProgressBar progressBar;
    private Boolean statusValido = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_cod);

        cod = findViewById(R.id.cod);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        progressBar = findViewById(R.id.progressBar);
        textReenviarSms = findViewById(R.id.textReenviarSms);

        Bundle dados = getIntent().getExtras();
        num = dados.getString("numTelefone");

        textView6.setText(getString(R.string.tituloVerificarCod, num));

        subTit = getString(R.string.subTituloVerificarCod, num);
        s1 = EstilosText.color(subTit, 80, 94);
        s1 = EstilosText.bold(s1, 61, 78);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                finish();
            }
        };

        s1.setSpan(clickableSpan,80, 94, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView7.setMovementMethod(LinkMovementMethod.getInstance());
        textView7.setHighlightColor(Color.TRANSPARENT);
        textView7.setText(s1);

        numSemMask = MaskEditUtil.unmask(num);

        textReenviarSms.setEnabled(false);

        enviarCodVerificacao(numSemMask);

        cod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 6){
                    if(statusValido){
                        verificarNumComCod(s.toString());
                    }else{
                        alertDialog("Toque em 'Reenviar SMS' antes.");
                    }
                }
            }
        });

        textReenviarSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textReenviarSms.setEnabled(false);
                textReenviarSms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reenviar_sms_cinza_24dp,
                        0, 0, 0);
                textReenviarSms.setTextColor(getResources().getColor(R.color.colorAvisos));
                reenviarSms(numSemMask, token);
            }
        });

    }

    private void enviarCodVerificacao(String num) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                num,
                60,
                TimeUnit.SECONDS,
                VerificarCodActivity.this,
                callBacks
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            vericacaoId = s;
            token = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verificarNumComCod(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if(e instanceof FirebaseAuthInvalidCredentialsException){
                alertDialog("Número inválido");
            }else if(e instanceof FirebaseTooManyRequestsException){
                alertDialog("Cota de sms excedida. Tente novamente mais tarde.");
            }
            textReenviarSms.setEnabled(true);
            textReenviarSms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reenviar_sms_verde_24dp,
                    0, 0,0);
            textReenviarSms.setTextColor(getResources().getColor(R.color.colorTitulo));
            statusValido = false;
        }
    };

    public void verificarNumComCod(String codEnviado){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vericacaoId, codEnviado);
        entrarComCredencial(credential);
    }

    private void entrarComCredencial(final PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(VerificarCodActivity.this, DadosPerfilActivity.class);
                    intent.putExtra("num", numSemMask);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        alertDialog("O código inserido está incorreto. Por favor, tente novamente.");
                    }
                    textReenviarSms.setEnabled(true);
                    textReenviarSms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reenviar_sms_verde_24dp,
                            0, 0,0);
                    textReenviarSms.setTextColor(getResources().getColor(R.color.colorTitulo));
                }
            }
        });
    }

    private void reenviarSms(String num, PhoneAuthProvider.ForceResendingToken token){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            num,
            60,
            TimeUnit.SECONDS,
            this,
            callBacks,
            token
        );
    }

    public void alertDialog(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("", null);
        alertDialog.create();
        alertDialog.show();
    }
}

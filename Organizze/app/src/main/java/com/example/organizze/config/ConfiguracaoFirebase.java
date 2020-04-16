package com.example.organizze.config;

import androidx.annotation.NonNull;

import com.example.organizze.helper.Base64Custom;
import com.example.organizze.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfiguracaoFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference database, usuarioRef, movimentaoRef;
    Usuario usuario;
    ValueEventListener valueEventListener;

    public static DatabaseReference getDatabase(){
        if(database == null){
            database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }

    public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static DatabaseReference getEmailCodificado(){
        database = getDatabase();
        auth = getAuth();

        String emailUsuario = auth.getCurrentUser().getEmail();
        String emailCodificado = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef = database.child("usuarios").child(emailCodificado);
        return usuarioRef;
    }

    public static DatabaseReference getMovimentacao(String mesDoAno){
        database = getDatabase();
        auth = getAuth();

        String emailUsuario = auth.getCurrentUser().getEmail();
        String emailCodificado = Base64Custom.codificarBase64(emailUsuario);
        movimentaoRef = database.child("movimentacao").child(emailCodificado).child(mesDoAno);
        return movimentaoRef;
    }
}

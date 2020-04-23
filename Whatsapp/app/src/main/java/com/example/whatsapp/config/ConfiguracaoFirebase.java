package com.example.whatsapp.config;

import android.util.Log;

import com.example.whatsapp.helper.BaseCustom64;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth auth;
    private static DatabaseReference database, usuarioRef;

    public static FirebaseAuth auth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static DatabaseReference database(){
         if(database == null){
             database = FirebaseDatabase.getInstance().getReference();
         }
         return database;
    }

    public static DatabaseReference numCodificado(){
        database = database();
        auth = auth();

        String num = auth.getCurrentUser().getPhoneNumber();
        String numCodificado = BaseCustom64.codificarNum(num);
        usuarioRef = database.child("usuarios").child(numCodificado);

        return usuarioRef;
    }
}

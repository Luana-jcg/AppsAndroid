package com.example.organizze.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT)
                .replaceAll("(\\n|\\r)", "");
    }

    public static String decodificarBase64(String texto){
        return String.valueOf(Base64.decode(texto, Base64.DEFAULT));
    }
}

package com.example.whatsapp.helper;

import android.util.Base64;

public class BaseCustom64 {

    public static String codificarNum(String num){
        return Base64.encodeToString(num.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodificarNum(String num){
        return new String(Base64.decode(num, Base64.DEFAULT));
    }
}


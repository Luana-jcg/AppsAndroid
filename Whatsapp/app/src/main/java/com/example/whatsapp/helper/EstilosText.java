package com.example.whatsapp.helper;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

public class EstilosText {

    public static SpannableString bold(SpannableString string, int start, int end){
        SpannableString spannableString = new SpannableString(string);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(bold, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString color(String string, int start, int end){
        SpannableString spannableString = new SpannableString(string);
        ForegroundColorSpan fcsAzul = new ForegroundColorSpan(Color.rgb( 33, 150, 243));
        spannableString.setSpan(fcsAzul, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}

package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    Context context;

    public Utils(Context context){
        this.context=context;
    }

    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    public boolean[] cargarPreferencias() {
        boolean prefs[] = new boolean[2];
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        prefs[0] = sharedPreferences.getBoolean("musica", true);
        prefs[1] = sharedPreferences.getBoolean("efectos", true);
        return prefs;
    }
}

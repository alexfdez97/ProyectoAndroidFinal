package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

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

    public int dist(Point p1, Point p2) {
        int dist = (int)Math.sqrt(Math.pow(p2.x - p1.x, 2)
                + Math.pow(p2.y - p1.y, 2));
        return dist;
    }
}

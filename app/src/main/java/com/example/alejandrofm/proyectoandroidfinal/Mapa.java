package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Mapa {
    private Bitmap suelo;
    private Utils utils;
    private int anchoPantalla, altoPantalla;

    public Mapa(Context context, int anchoPantalla, int altoPantalla) {
        utils = new Utils(context);
        suelo = utils.getBitmapFromAssets("mapa/sueloMapa.png");
        suelo = Bitmap.createBitmap(suelo, 0, 0, 48, 48);
        suelo = Bitmap.createScaledBitmap(suelo, anchoPantalla * 1 / 6, altoPantalla * 1/4, false);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
    }

    public void dibujaMapa(Canvas c) {
        for (int i = 0; i < anchoPantalla; i = suelo.getWidth() + i) {
            for (int j = 0; j < altoPantalla; j = suelo.getHeight() + j) {
                c.drawBitmap(suelo, i, j, null);
            }
        }
    }
}

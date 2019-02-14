package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Parallax {

    Bitmap cielo;
    Bitmap nubes[] = new Bitmap[2];
    Utils utils;

    public Parallax(boolean day, int anchoPantalla, int altoPantalla,Context context) {
        utils = new Utils(context);
        if (day) {
            cielo = utils.getBitmapFromAssets("parallax/dia/sky.png");
        } else {
            cielo = utils.getBitmapFromAssets("parallax/noche/sky.png");
        }
        cielo = Bitmap.createScaledBitmap(cielo, anchoPantalla, altoPantalla, false);
    }

    public void dibujaParallax(Canvas c) {

    }


}

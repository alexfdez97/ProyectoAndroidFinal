package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;

public class Protagonista extends Personaje {

    public Protagonista(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        super(x, y, anchoPantalla, altoPantalla, context);
        sprite = new Bitmap[20];
        for (int i = 0; i < 20; i++) {
            Bitmap bitmap = utils.getBitmapFromAssets("protagonista/handgun/idle/survivor-idle_handgun_" + i + ".png");
            bitmap = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
            sprite[i] = bitmap;
        }
    }
}

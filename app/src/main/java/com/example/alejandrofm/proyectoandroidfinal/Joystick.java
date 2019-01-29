package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Joystick {
    private Bitmap joyVacio, joyFlechas;
    private Utils utils;
    private float jVacioX, jVacioY, jFlechasX, jFlechasY;

    public Joystick(Context context, float jVacioX, float jVacioY, int anchoPantalla, int altoPantalla) {
        utils = new Utils(context);
        joyVacio = utils.getBitmapFromAssets("joystick/joyVacio.png");
        joyVacio = Bitmap.createScaledBitmap(joyVacio, anchoPantalla * 1/8, anchoPantalla * 1/8, false);
        joyFlechas = utils.getBitmapFromAssets("joystick/joyFlechas.png");
        joyFlechas = Bitmap.createScaledBitmap(joyFlechas, anchoPantalla * 1/18, anchoPantalla * 1/18, false);
        this.jVacioX = jVacioX;
        this.jVacioY = jVacioY;
        this.jFlechasX = jVacioX;
        this.jFlechasY = jVacioY;
    }

    public void dibujaJoystick(Canvas c) {
        c.drawBitmap(joyVacio, jVacioX - joyVacio.getWidth() / 2, jVacioY - joyVacio.getHeight() / 2, null);
//        if (jFlechasX < jVacioX && jFlechasY < jVacioY) {
            c.drawBitmap(joyFlechas, jFlechasX - joyFlechas.getWidth() / 2, jFlechasY - joyFlechas.getHeight() / 2, null);
//        }
    }

    public float getjVacioX() {
        return jVacioX;
    }

    public void setjVacioX(float jVacioX) {
        this.jVacioX = jVacioX;
    }

    public float getjVacioY() {
        return jVacioY;
    }

    public void setjVacioY(float jVacioY) {
        this.jVacioY = jVacioY;
    }

    public float getjFlechasX() {
        return jFlechasX;
    }

    public void setjFlechasX(float jFlechasX) {
        this.jFlechasX = jFlechasX;
    }

    public float getjFlechasY() {
        return jFlechasY;
    }

    public void setjFlechasY(float jFlechasY) {
        this.jFlechasY = jFlechasY;
    }
}

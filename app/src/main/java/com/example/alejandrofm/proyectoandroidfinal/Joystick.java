package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

public class Joystick {
    private Bitmap joyVacio, joyFlechas;
    private Utils utils;
    private float jVacioX, jVacioY, jFlechasX, jFlechasY;
    private boolean pulsado = false;
    private int idPuntero;
    private float radioJVacio, radioHipotenusa;
    private float constraintX, constraintY;
//    private int dx, dy;

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
        radioJVacio = joyVacio.getWidth() / 2;
    }

    public void dibujaJoystick(Canvas c) {
        c.drawBitmap(joyVacio, jVacioX - joyVacio.getWidth() / 2, jVacioY - joyVacio.getHeight() / 2, null);
        c.drawBitmap(joyFlechas, jFlechasX - joyFlechas.getWidth() / 2, jFlechasY - joyFlechas.getHeight() / 2, null);
    }

//    public boolean sale(){
//        dx = (int)Math.abs(jVacioX + joyVacio.getWidth() / 2 - jFlechasX - joyFlechas.getWidth() / 2);
//        dy = (int)Math.abs(jVacioY + joyVacio.getHeight() / 2 - jFlechasY - joyFlechas.getHeight() / 2);
//        Double hipo = Math.hypot(dx,dy);
//        if (hipo > joyVacio.getHeight() / 2 || hipo > joyVacio.getWidth() / 2 ) return true;
//        return false;
//    }
//
//    public boolean saleX() {
//        if (hipotenusa() > joyVacio.getHeight() / 2) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean saleY() {
//        if (hipotenusa() > joyVacio.getWidth() / 2) {
//            return true;
//        }
//        return false;
//    }

//    public double hipotenusa(){
//        dx = (int)Math.abs(jVacioX + joyVacio.getWidth() / 2 - jFlechasX - joyFlechas.getWidth() / 2);
//        dy = (int)Math.abs(jVacioY + joyVacio.getHeight() / 2 - jFlechasY - joyFlechas.getHeight() / 2);
//        Double hipo = Math.hypot(dx,dy);
//        return hipo;
//    }

    public void setCoordsJFlecas(float jFlechasX, float jFlechasY) {
        float desplazamiento = (float)Math.sqrt(Math.pow(jVacioX - jFlechasX, 2) + Math.pow(jVacioY - jFlechasY, 2));
        radioHipotenusa = radioJVacio / desplazamiento;
        constraintX = jVacioX + (jFlechasX - jVacioX) * radioHipotenusa;
        constraintY = jVacioY + (jFlechasY - jVacioY) * radioHipotenusa;
        if (desplazamiento < radioJVacio) {
            this.jFlechasX = jFlechasX;
            this.jFlechasY = jFlechasY;
        } else {
            this.jFlechasX = constraintX;
            this.jFlechasY = constraintY;
        }
    }

    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    public int getIdPuntero() {
        return idPuntero;
    }

    public void setIdPuntero(int idPuntero) {
        this.idPuntero = idPuntero;
    }
}

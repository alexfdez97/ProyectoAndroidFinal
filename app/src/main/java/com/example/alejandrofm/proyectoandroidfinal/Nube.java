package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Nube {

    public enum LadoInicio {
        IZQUIERDA(0), DERECHA(18);

        private final int value;
        private LadoInicio(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static LadoInicio intToNube(int valor) {
            LadoInicio lado;

            if (valor == 0) {
                lado = LadoInicio.IZQUIERDA;
            } else {
                lado = LadoInicio.DERECHA;
            }
            return lado;
        }
    }
    private LadoInicio ladoInicio;
    private float posX, posY;
    private int anchoPantalla, altoPantalla;
    private Bitmap nube;
    private float velocidad;

    public Nube(Bitmap nube, LadoInicio lado, float velocidad, int anchoPantalla, int altoPantalla) {
        this.nube = nube;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.ladoInicio = lado;
        if (ladoInicio == LadoInicio.IZQUIERDA) {
            posX = 0 - nube.getWidth();
        } else {
            posX = anchoPantalla;
        }
        Log.i("nube", "altoPantalla: " + altoPantalla);
        posY = (float)(Math.random() * (altoPantalla - 0));
        this.velocidad = velocidad;
    }

    public void dibujarNube(Canvas c) {
        c.drawBitmap(nube, posX, posY, null);
        moverNube();
    }

    private void moverNube() {
        if (ladoInicio == LadoInicio.IZQUIERDA) {
            posX += velocidad;
        } else {
            posX -= velocidad;
        }
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return nube.getWidth();
    }
}

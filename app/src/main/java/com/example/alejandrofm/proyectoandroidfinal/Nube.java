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

    /**
     * Inicializa las propiedades de la clase
     * @param nube el bitmap
     * @param lado el lado por el que aparece
     * @param altura la altura en la que aparece
     * @param velocidad la velocidad en la que aparece
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     */
    public Nube(Bitmap nube, LadoInicio lado, float altura, float velocidad, int anchoPantalla, int altoPantalla) {
        this.nube = nube;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.ladoInicio = lado;
        if (ladoInicio == LadoInicio.IZQUIERDA) {
            posX = 0 - nube.getWidth();
        } else {
            posX = anchoPantalla;
        }
        posY = altura;
        this.velocidad = velocidad;
    }

    /**
     * Dibuja la nube en el canvas
     * @param c el canvas
     */
    public void dibujarNube(Canvas c) {
        c.drawBitmap(nube, posX, posY, null);
        moverNube();
    }

    /**
     * Mueve la nube hacía el lado indicado
     */
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

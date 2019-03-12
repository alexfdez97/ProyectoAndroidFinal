package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Clase Nube
 */
public class Nube {

    /**
     * El Lado de inicio
     */
    public enum LadoInicio {
        IZQUIERDA(0), DERECHA(18);

        /**
         * El value
         */
        private final int value;

        /**
         * El value
         * @param value el value
         */
        private LadoInicio(int value) {
            this.value = value;
        }

        /**
         * Devuelve el value
         * @return el value
         */
        public int getValue() {
            return value;
        }

        /**
         * Construye el lado
         * @param valor el valor
         * @return el lado
         */
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

    /**
     * El lado de inicio
     */
    private LadoInicio ladoInicio;
    /**
     * Las posiciones X y Y
     */
    private float posX, posY;
    /**
     * El ancho y el alto de la pantalla
     */
    private int anchoPantalla, altoPantalla;
    /**
     * El bitmap de nube
     */
    private Bitmap nube;
    /**
     * La velocidad de la nube
     */
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
     * Mueve la nube hacia el lado indicado
     */
    private void moverNube() {
        if (ladoInicio == LadoInicio.IZQUIERDA) {
            posX += velocidad;
        } else {
            posX -= velocidad;
        }
    }

    /**
     * Devuelve la posicion X
     * @return la posicion X
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Establace la posicion Y
     * @param posX la posicion X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Devuelve la posicion Y
     * @return la posicion Y
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Establace Y
     * @param posY Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Devuelve el ancho de la nube
     * @return el ancho
     */
    public int getWidth() {
        return nube.getWidth();
    }
}

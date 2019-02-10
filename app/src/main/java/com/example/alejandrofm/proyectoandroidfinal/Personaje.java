
package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Personaje {
    protected int posX, posY, velocidad, vida;
    protected Bitmap[] sprite;
    protected Utils utils;
    private int anchoPantalla, altoPantalla;
    private int indice = 0;
    private int tmpCambioFrame = 200;
    private long tiempoActual;
    private Joystick.Direccion direccionAnterior;

    public Personaje(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        this.posX = x;
        this.posY = y;
        utils = new Utils(context);
        tiempoActual = System.currentTimeMillis();
        direccionAnterior = Joystick.Direccion.ESTE;
    }

    public void dibujarPersonaje(Canvas c) {
        c.drawBitmap(sprite[indice], posX, posY, null);
        cambiaFrame();
    }

    public void cambiaFrame() {
        if (sprite != null) {
            if (System.currentTimeMillis() - tiempoActual > tmpCambioFrame) {
                tiempoActual = System.currentTimeMillis();
                indice++;
                if (indice > sprite.length -1) {
                    indice = 0;
                }
            }
        }
    }

    public void caminar(Joystick.Direccion direccion) {
        switch (direccion) {
            case NORTE:
                posY--;
                break;
            case SUR:
                posY++;
                break;
            case ESTE:
                posX++;
                break;
            case OESTE:
                posX--;
                break;
        }
    }

    public void rotar(Joystick.Direccion direccion) {
        Matrix matrix = new Matrix();
        matrix.postRotate(calculoRotacion(direccion, direccionAnterior));
        rotarBitmap(matrix);
        direccionAnterior = direccion;
    }

    public void rotarBitmap(Matrix matrix) {
        Log.i("grados", "roto");
        for (int i = 0; i < sprite.length; i++) {
            sprite[i] = Bitmap.createBitmap(sprite[i], 0, 0, sprite[i].getWidth(), sprite[i].getHeight(), matrix, true);
        }
    }

    public int calculoRotacion(Joystick.Direccion direccionNueva, Joystick.Direccion direccionAnterior) {
            switch (direccionAnterior) {
                case NORTE:
                    switch (direccionNueva) {
                        case NORTE:
                            return 0;
                        case SUR:
                            return 180;
                        case ESTE:
                            return 90;
                        case OESTE:
                            return -90;
                    }
                    break;
                case SUR:
                    switch (direccionNueva) {
                        case NORTE:
                            return 180;
                        case SUR:
                            return 0;
                        case ESTE:
                            return -90;
                        case OESTE:
                            return 90;
                    }
                    break;
                case ESTE:
                    switch (direccionNueva) {
                        case NORTE:
                            return -90;
                        case SUR:
                            return 90;
                        case ESTE:
                            return 0;
                        case OESTE:
                            return 180;
                    }
                    break;
                case OESTE:
                    switch (direccionNueva) {
                        case NORTE:
                            return 90;
                        case SUR:
                            return -90;
                        case ESTE:
                            return 180;
                        case OESTE:
                            return 0;
                    }
                    break;
            }
            return 0;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Bitmap[] getSprite() {
        return sprite;
    }

    public void setSprite(Bitmap[] sprite) {
        this.sprite = sprite;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    public long getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }
}

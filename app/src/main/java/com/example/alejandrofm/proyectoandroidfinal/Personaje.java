
package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Personaje {
    private enum Direccion {
        NORTE, SUR, ESTE, OESTE, NORESTE, NOROESTE, SURESTE, SUROESTE
    }
    private int posX, posY, velocidad, vida;
    private Direccion direccion;
    protected Bitmap[] sprite;
    protected Utils utils;
    private boolean seMueve;
    private int anchoPantalla, altoPantalla;
    private int indice = 0;
    private int tmpCambioFrame = 200;
    private long tiempoActual;

    public Personaje(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        this.posX = x;
        this.posY = y;
        utils = new Utils(context);
        tiempoActual = System.currentTimeMillis();
    }

    public void dibujarPersonaje(Canvas c) {
        c.drawBitmap(sprite[indice], posX, posY, null);
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isSeMueve() {
        return seMueve;
    }

    public void setSeMueve(boolean seMueve) {
        this.seMueve = seMueve;
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

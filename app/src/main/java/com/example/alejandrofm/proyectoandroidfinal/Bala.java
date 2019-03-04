package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Bala {

    public enum TipoMunicion {
        RIFLE,
        PISTOLA,
        BIGPISTOLA
    }

    private Joystick.Direccion direccion;
    private Utils utils;
    private Bitmap bala;
    private int posX;
    private int posY;
    private int velocidadBala = 20;
    private int anchoPantalla, altoPantalla;

    public Bala(TipoMunicion tipo, Joystick.Direccion direccion, int posX, int posY, int anchoPantalla, int altoPantalla, Context context) {
        this.posX = posX;
        this.posY = posY;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        utils = new Utils(context);
        this.direccion = direccion;
        cargarBitmaps(tipo);
    }

    public void dibujarBala(Canvas c) {
        c.drawBitmap(bala, posX, posY, null);
    }

    public void mueveBala() {
        switch (direccion) {
            case NORTE:
                posY -= velocidadBala;
                break;
            case SUR:
                posY += velocidadBala;
                break;
            case ESTE:
                posX += velocidadBala;
                break;
            case OESTE:
                posX -= velocidadBala;
                break;
        }
    }

    private void cargarBitmaps(TipoMunicion tipo) {
        switch (tipo){
            case RIFLE:
                bala = utils.getBitmapFromAssets("balas/bullet1.png");
                break;
            case PISTOLA:
                bala = utils.getBitmapFromAssets("balas/bullet3.png");
                break;
            case BIGPISTOLA:
                bala = utils.getBitmapFromAssets("balas/bullet2.png");
                break;
        }

        Matrix matrix = new Matrix();
        switch (direccion) {
            case NORTE:
                matrix.postRotate(-90);
                break;
            case SUR:
                matrix.postRotate(90);
                break;
            case ESTE:
                matrix.postRotate(0);
                break;
            case OESTE:
                matrix.postRotate(180);
                break;
        }
        bala = Bitmap.createBitmap(bala, 0, 0, bala.getWidth(), bala.getHeight(), matrix, true);
        bala = Bitmap.createScaledBitmap(bala, altoPantalla * 1/80, altoPantalla * 1/80, false);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getWidth() {
        return bala.getWidth();
    }

    public int getHeight() {
        return bala.getHeight();
    }

    public int getVelocidadBala() {
        return velocidadBala;
    }

    public void setVelocidadBala(int velodidad) {
        this.velocidadBala = velodidad;
    }
}

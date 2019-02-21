package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class IconoBoton {

    public enum Tipo {
        RECORDS, AYUDA, CREDITOS, VOLVER
    }
    private Utils utils;
    private Bitmap botonPulsado;
    private Bitmap botonBase;
    private Bitmap icono;
    private Bitmap bmpFinal;
    private boolean pulsado;
    private int altoPantalla, anchoPantalla;
    private int posX, posY;

    public IconoBoton(Tipo tipo, int anchoPantalla, int altoPantalla, Context context) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        utils = new Utils(context);
        cargarBitmaps();
        cargarTipo(tipo);
    }

    public void dibujarIconoBoton(int posX, int posY, Canvas c) {
        this.posX = posX;
        this.posY = posY;
//        Bitmap boton = botonBase;
//        if (pulsado) {
//            boton = botonPulsado;
//        }
        if (pulsado) {
            c.drawBitmap(botonPulsado, posX, posY, null);
        } else {
            c.drawBitmap(botonBase, posX, posY, null);
        }
//        c.drawBitmap(boton, posX, posY, null);
        c.drawBitmap(icono, posX + botonBase.getWidth() / 2 - icono.getWidth() / 2, posY + botonBase.getHeight() / 2 - icono.getHeight() / 2, null);
    }

    public void cargarBitmaps() {
        Bitmap base = utils.getBitmapFromAssets("menu/iconButtons.png");
        botonPulsado = Bitmap.createBitmap(base, 5, 44, 34, 32);
        botonPulsado = Bitmap.createScaledBitmap(botonPulsado, altoPantalla * 1/6, altoPantalla * 1/6, false);
        botonBase = Bitmap.createBitmap(base, 5, 6, 34, 32);
        botonBase = Bitmap.createScaledBitmap(botonBase, altoPantalla * 1/6, altoPantalla * 1/6, false);
    }

    private void cargarTipo(Tipo tipo) {
        int proporcion = botonBase.getWidth() * 3/5;
        switch (tipo) {
            case RECORDS:
                icono = utils.getBitmapFromAssets("menu/trophy.png");
                break;
            case AYUDA:
                icono = utils.getBitmapFromAssets("menu/help.png");
                break;
            case CREDITOS:
                icono = utils.getBitmapFromAssets("menu/info.png");
                break;
            case VOLVER:
                icono = utils.getBitmapFromAssets("menu/rollback.png");
                break;
        }
        icono = Bitmap.createScaledBitmap(icono, proporcion, proporcion, false);
    }

    public boolean isPulsado(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        if ((eventX > posX && eventY > posY) && (eventX < posX + getWidth() && eventY < posY + getHeight())) {
            setPulsado(true);
        } else {
            setPulsado(false);
        }
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    public int getWidth() {
        return botonBase.getWidth();
    }

    public int getHeight() {
        return botonBase.getHeight();
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
}

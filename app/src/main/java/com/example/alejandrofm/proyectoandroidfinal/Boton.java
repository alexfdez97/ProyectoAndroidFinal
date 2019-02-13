package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Boton {

    private Utils utils;
    private Bitmap letras[] = new Bitmap[26];
    private Bitmap botonPulsado;
    private Bitmap botonBase;
    private int anchoPantalla, altoPantalla;

    /**
     * Inicializa Boton
     * @param texto
     * @param anchoPantalla
     * @param altoPantalla
     * @param context
     */
    public Boton(String texto, int anchoPantalla, int altoPantalla, Context context) {
        utils = new Utils(context);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        cargarBitmaps();
    }

    public void dibujarBoton(int x, int y, Canvas canvas) {
        canvas.drawBitmap(botonBase, x, y, null);
    }

    public void cargarBitmaps() {
        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
        botonBase = Bitmap.createBitmap(tileset, 16 , 16, 128, 32);
        botonBase = Bitmap.createScaledBitmap(botonBase, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        botonPulsado = Bitmap.createBitmap(tileset, 16, 64, 128, 32);
        botonPulsado = Bitmap.createScaledBitmap(botonPulsado, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        int tempX = 158;
        int tempY = 10;
        for (int i = 0; i < letras.length; i++) {
            if (i % 8 == 0) {
                tempY += 20;
                tempX = 158;
            }
            letras[i] = Bitmap.createBitmap(tileset, tempX, tempY, 16, 20);
            tempX += 16;
        }
    }

//    public Bitmap makeButton(String letras) {
//        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
//        Bitmap button = Bitmap.createBitmap(tileset, 0 ,0, 150, tileset.getHeight() / 2);
//        return null;
//    }
}

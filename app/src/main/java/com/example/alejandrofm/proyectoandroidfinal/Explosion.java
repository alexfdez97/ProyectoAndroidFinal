package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {

    private int ancho;
    private int alto;
    private boolean efectos;
    private Context context;
    private Bitmap explosions[] = new Bitmap[13];
    private int indiceFrame = 0;
    private int tmpCambioFrame = 40;
    private long tiempoActual;
    private Utils utils;

    public Explosion (int ancho, int alto, boolean effects, Context context) {
        this.ancho = ancho;
        this.alto = alto;
        this.efectos = effects;
        this.context = context;
        this.tiempoActual = System.currentTimeMillis();
        utils = new Utils(context);
        cargarBitmaps();
    }

    public void dibujaExplosion(int posX, int posY, Canvas c) {
        try {
            c.drawBitmap(explosions[indiceFrame], posX, posY, null);
            cambiaFrame();
        } catch (NullPointerException ex) {}
    }

    private void cambiaFrame() {
        if (explosions != null) {
            if (System.currentTimeMillis() - tiempoActual > tmpCambioFrame) {
                tiempoActual = System.currentTimeMillis();
                indiceFrame++;
                if (indiceFrame > explosions.length -1) {
                    indiceFrame = 0;
                }
            }
        }
    }

    private void cargarBitmaps() {
        Bitmap asset = utils.getBitmapFromAssets("explosions/explosions.png");
        int x = 0;
        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = Bitmap.createBitmap(asset, x, 0, 192, 102);
            explosions[i] = Bitmap.createScaledBitmap(explosions[i], ancho, alto, false);
            x += 192;
        }
    }
}

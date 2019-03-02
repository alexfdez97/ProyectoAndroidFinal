package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class HealthBar {

    private int vida;
    private Bitmap[] healthsBmps = new Bitmap[4];
    private Utils utils;
    private int anchoPantalla, altoPantalla;

    public HealthBar(int health, int anchoPantalla, int altoPantalla, Context context) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.vida = health;
        utils = new Utils(context);
        cargarBitmaps();
    }
    
    private void cargarBitmaps() {
        Bitmap temp = utils.getBitmapFromAssets("health.png");
        int anchoVariable = 0;
        for (int i = 0; i < healthsBmps.length; i++) {
            healthsBmps[i] = Bitmap.createBitmap(temp, anchoVariable, 0, 57, 20);
            healthsBmps[i] = Bitmap.createScaledBitmap(healthsBmps[i], (anchoPantalla * 1/4) * 2/3, (altoPantalla * 1/6) * 2/3, false);
            anchoVariable += 57;
        }
    }

    public void dibujaBar(int posX, int posY, Canvas c) {
        if (vida > 75) {
            c.drawBitmap(healthsBmps[3], posX, posY, null);
        } else if (vida > 50) {
            c.drawBitmap(healthsBmps[2], posX, posY, null);
        } else if (vida > 25) {
            c.drawBitmap(healthsBmps[1], posX, posY, null);
        } else {
            c.drawBitmap(healthsBmps[0], posX, posY, null);
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getWidth() {
        return healthsBmps[0].getWidth();
    }
}

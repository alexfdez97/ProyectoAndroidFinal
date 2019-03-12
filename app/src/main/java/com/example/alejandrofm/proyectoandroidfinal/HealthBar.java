package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Clase HealthBar
 */
public class HealthBar {

    /**
     * La vida
     */
    private int vida;
    /**
     * Los Bitmaps [ ]
     */
    private Bitmap[] healthsBmps = new Bitmap[4];
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El ancho de la pantalla y el alto
     */
    private int anchoPantalla, altoPantalla;
    /**
     * La posicion X y Y
     */
    private int posX, posY;

    /**
     * Inicializa las propiedades de la clase
     * @param health la vida
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     */
    public HealthBar(int health, int anchoPantalla, int altoPantalla, Context context) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.vida = health;
        utils = new Utils(context);
        cargarBitmaps();
    }

    /**
     * Carga los bitmaps para el funcionamiento de la clase
     */
    private void cargarBitmaps() {
        Bitmap temp = utils.getBitmapFromAssets("health.png");
        int anchoVariable = 0;
        for (int i = 0; i < healthsBmps.length; i++) {
            healthsBmps[i] = Bitmap.createBitmap(temp, anchoVariable, 0, 57, 20);
            healthsBmps[i] = Bitmap.createScaledBitmap(healthsBmps[i], (anchoPantalla * 1/4) * 2/3, (altoPantalla * 1/6) * 2/3, false);
            anchoVariable += 57;
        }
    }

    /**
     * Dibuja la barra
     * @param posX coordenada X
     * @param posY coordenada Y
     * @param c el Canvas donde se dibuja
     */
    public void dibujaBar(int posX, int posY, Canvas c) {
        this.posX = posX;
        this.posY = posX;
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

    /**
     * Devuelve la vida de la barra
     * @return la vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece la vida de la barra
     * @param vida la vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Devuelve el ancho de la barra
     * @return el ancho
     */
    public int getWidth() {
        return healthsBmps[0].getWidth();
    }

    /**
     * Devuelve la coordenada X de la barra
     * @return X
     */
    public int getX() {
        return posX;
    }

    /**
     * Devuelve la coordenada Y de la barra
     * @return Y
     */
    public int getY() {
        return posY;
    }
}

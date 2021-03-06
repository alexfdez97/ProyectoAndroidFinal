package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Clase Mapa
 */
public class Mapa {
    /**
     * Bitmaps del mapa
     */
    private Bitmap asfalto, bordilloBot, bordilloTop, mapa;
    /**
     * Bitmaps del mapa
     */
    private Bitmap[] superficies = new Bitmap[6];
    /**
     * Bitmaps del mapa
     */
    private Bitmap[] cristales = new Bitmap[3];
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El ancho y el alto de la pantalla
     */
    private int anchoPantalla, altoPantalla;

    /**
     * El mapa
     * @param context el contexto de la aplicación
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     */
    public Mapa(Context context, int anchoPantalla, int altoPantalla) {
        utils = new Utils(context);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        mapa = generarMapa();
    }

    /**
     * Dibuja el mapa
     * @param c el canvas
     */
    public void dibujaMapa(Canvas c) {
        c.drawBitmap(mapa, 0, 0 , null);
    }

    /**
     * Carga los bitmaps de la clase en memoria
     */
    private void cargarBitmaps() {
        int proporcion = altoPantalla * 1/6;
        superficies[0] = utils.getBitmapFromAssets("mapa/acera_3.png");
        superficies[0] = Bitmap.createScaledBitmap(superficies[0], proporcion, proporcion, false);
        superficies[1] = utils.getBitmapFromAssets("mapa/madera.png");
        superficies[1] = Bitmap.createScaledBitmap(superficies[1], proporcion, proporcion, false);
        superficies[2] = utils.getBitmapFromAssets("mapa/hierba.png");
        superficies[2] = Bitmap.createScaledBitmap(superficies[2], proporcion, proporcion, false);
        superficies[3] = utils.getBitmapFromAssets("mapa/tierra.png");
        superficies[3] = Bitmap.createScaledBitmap(superficies[3], proporcion, proporcion, false);
        superficies[4] = utils.getBitmapFromAssets("mapa/acera_1.png");
        superficies[4] = Bitmap.createScaledBitmap(superficies[4], proporcion, proporcion, false);
        superficies[5] = utils.getBitmapFromAssets("mapa/acera_2.png");
        superficies[5] = Bitmap.createScaledBitmap(superficies[5], proporcion, proporcion, false);
        asfalto = utils.getBitmapFromAssets("mapa/asfalto.png");
        asfalto = Bitmap.createScaledBitmap(asfalto, proporcion, proporcion, false);
        bordilloBot = utils.getBitmapFromAssets("mapa/bordilloBot.png");
        bordilloBot = Bitmap.createScaledBitmap(bordilloBot, proporcion, proporcion, false);
        bordilloTop = utils.getBitmapFromAssets("mapa/bordilloTop.png");
        bordilloTop = Bitmap.createScaledBitmap(bordilloTop, proporcion, proporcion, false);
        int proporcionCristales = altoPantalla * 1/16;
        cristales[0] = utils.getBitmapFromAssets("mapa/cristales_1.png");
        cristales[0] = Bitmap.createScaledBitmap(cristales[0], proporcionCristales, proporcionCristales, false);
        cristales[1] = utils.getBitmapFromAssets("mapa/cristales_2.png");
        cristales[1] = Bitmap.createScaledBitmap(cristales[1], proporcionCristales, proporcionCristales, false);
        cristales[2] = utils.getBitmapFromAssets("mapa/cristales_3.png");
        cristales[2] = Bitmap.createScaledBitmap(cristales[2], proporcionCristales, proporcionCristales, false);
    }

    /**
     * Genera un mapa aleatorio y lo guarda en un Bitmap según el alto y el ancho de la pantalla.
     * @return el Bitmap de el mapa
     */
    private Bitmap generarMapa() {
        cargarBitmaps();
        Bitmap bitmap =  Bitmap.createBitmap(anchoPantalla, altoPantalla, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Bitmap superior = superficies[(int)(Math.random() * (5))];
        Bitmap inferior = superficies[(int)(Math.random() * (5))];
        int alto = 0;
        int ancho = 0;
        while (alto < altoPantalla * 1/6) {
            c.drawBitmap(superior, ancho, alto, null);
            ancho += superior.getWidth();
            if (ancho >= anchoPantalla){
                alto += superior.getHeight();
                ancho = 0;
            }
        }
        while (ancho < anchoPantalla) {
            c.drawBitmap(bordilloTop, ancho, alto, null);
            ancho += bordilloTop.getWidth();
        }
        alto += bordilloTop.getHeight();
        ancho = 0;
        while (ancho < anchoPantalla) {
            c.drawBitmap(asfalto, ancho, alto, null);
            ancho += asfalto.getWidth();
        }
        alto += bordilloTop.getHeight();
        ancho = 0;
        while (ancho < anchoPantalla) {
            c.drawBitmap(bordilloBot, ancho, alto, null);
            ancho += bordilloTop.getWidth();
        }
        alto += bordilloBot.getHeight();
        ancho = 0;
        while (alto < altoPantalla) {
            c.drawBitmap(inferior, ancho, alto, null);
            ancho += inferior.getWidth();
            if (ancho >= anchoPantalla){
                alto += inferior.getHeight();
                ancho = 0;
            }
        }

        int cantidad = (int)(Math.random() * 10);
        for (int i = 0; i < cantidad; i++) {
            float x = (float)(Math.random() * anchoPantalla);
            float y = (float)(Math.random() * altoPantalla);
            int index = (int)(Math.random() * 3);
            c.drawBitmap(cristales[index], x, y, null);
        }
        return bitmap;
    }
}
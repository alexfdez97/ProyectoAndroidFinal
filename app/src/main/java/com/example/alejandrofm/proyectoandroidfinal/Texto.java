package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase Texto
 */
public class Texto {

    /**
     * Mapa de caractere y bitmaps
     */
    private HashMap<Character, Bitmap> letras = new HashMap<>();
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El bitmap del texto
     */
    private Bitmap bmpTexto;
    /**
     * Coordenadas X y Y
     */
    private int x, y;
    /**
     * El ancho y el alto de la pantalla
     */
    private int anchoPantalla, altoPantalla;

    /**
     * Inicializa las propiedades de la clase
     * @param texto el texto
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     */
    public Texto(String texto, int anchoPantalla, int altoPantalla, Context context) {
        utils = new Utils(context);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        cargarBitmaps();
        bmpTexto = textoAImagen(texto);
        bmpTexto = Bitmap.createScaledBitmap(bmpTexto, texto.length() * (anchoPantalla * 1/40), (altoPantalla * 1/10) * 2/3, false);
    }

    /**
     * Dibuja el texto en la coordenada XY del Canvas
     * @param posX coordenada X
     * @param posY coordenada Y
     * @param c el Canvas
     */
    public void dibujarTexto(int posX, int posY, Canvas c) {
        this.x = posX;
        this.y = posY;
        c.drawBitmap(bmpTexto, posX, posY, null);
    }

    /**
     * Transforma el texto a imagen
     * @param texto el texto
     * @return la imagen
     */
    private Bitmap textoAImagen(String texto) {
        texto = texto.toLowerCase();
        char letrasFromTexto[] = texto.toCharArray();
        ArrayList<Bitmap> cadena = new ArrayList<>();
        for (char ch:letrasFromTexto) {
            cadena.add(letras.get(ch));
        }
        Bitmap inicio = null;
        Bitmap temp;
        for (int i = 0; i < cadena.size(); i++) {
            if (i == 0) {
                inicio = cadena.get(i);
            } else {
                temp = cadena.get(i);
                inicio = combinarImagenes(inicio, temp);
            }
        }
        return inicio;
    }

    /**
     * Combina dos Bitmaps horizontalmente
     * @param bmp1 Bitmap1
     * @param bmp2 Bitmap2
     * @return Bitmap1+Bitmap2
     */
    private Bitmap combinarImagenes(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmpUnion = null;

        int ancho, alto = 0;

        if(bmp1.getWidth() > bmp2.getWidth()) {
            ancho = bmp1.getWidth() + bmp2.getWidth();
            alto = bmp1.getHeight();
        } else {
            ancho = bmp2.getWidth() + bmp2.getWidth();
            alto = bmp1.getHeight();
        }

        bmpUnion = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(bmpUnion);

        comboImage.drawBitmap(bmp1, 0f, 0f, null);
        comboImage.drawBitmap(bmp2, bmp1.getWidth(), 0f, null);

        return bmpUnion;
    }

    /**
     * Carga los Bitmaps necesarios
     */
    private void cargarBitmaps() {
        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
        Bitmap dosPuntos = utils.getBitmapFromAssets("menu/doblep.png");
        Bitmap guion = utils.getBitmapFromAssets("menu/guion.png");
        Bitmap punto = utils.getBitmapFromAssets("menu/punto.png");
        int tempX = 158;
        int tempY = 10;
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (i % 8 == 0 && i > 0) {
                tempY += 24;
                tempX = 158;
            }
            letras.put(c, Bitmap.createBitmap(tileset, tempX, tempY, 16, 24));
            tempX += 16;
            c++;
        }
        tempX = 302;
        tempY = 10;
        char num = '1';
        for (int j = 0; j < 10; j++) {
            if (j % 5 == 0 && j > 0) {
                tempY += 24;
                tempX = 302;
            }
            if (j < 9) {
                letras.put(num, Bitmap.createBitmap(tileset, tempX, tempY, 16, 24));
            } else {
                letras.put('0', Bitmap.createBitmap(tileset, tempX, tempY, 16, 24));
            }
            tempX += 16;
            num++;
        }
        letras.put(' ', Bitmap.createBitmap(16, 20, Bitmap.Config.ARGB_8888));
        letras.put(':', Bitmap.createBitmap(dosPuntos, 0, 0, 16, 24));
        letras.put('-', Bitmap.createBitmap(guion, 0, 0, 16, 24));
        letras.put('.', Bitmap.createBitmap(punto, 0, 0, 16, 24));
    }

    /**
     * Establece el texto
     * @param texto el texto
     */
    public void setTexto(String texto) {
        bmpTexto = textoAImagen(texto);
        bmpTexto = Bitmap.createScaledBitmap(bmpTexto, texto.length() * (anchoPantalla * 1/40), (altoPantalla * 1/10) * 2/3, false);
    }

    /**
     * Devuelve el ancho
     * @return el ancho
     */
    public int getWidth() {
        return bmpTexto.getWidth();
    }

    /**
     * Devuelve el alto
     * @return el alto
     */
    public int getHeight() {
        return bmpTexto.getHeight();
    }

    /**
     * Devuelve el X
     * @return el X
     */
    public int getX() {
        return this.x;
    }

    /**
     * Devuelve le Y
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * Establace el Y
     * @param y el Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Establece el X
     * @param x el X
     */
    public void setX(int x) {
        this.x = x;
    }
}

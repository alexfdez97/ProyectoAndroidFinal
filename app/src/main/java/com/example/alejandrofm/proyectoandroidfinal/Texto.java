package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Texto {

    private HashMap<Character, Bitmap> letras = new HashMap<>();
    private Utils utils;
    private Bitmap bmpTexto;
    private int x, y;
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
                Log.i("pasa", num + "");
            } else {
                letras.put('0', Bitmap.createBitmap(tileset, tempX, tempY, 16, 24));
            }
            tempX += 16;
            num++;
        }
        letras.put(' ', Bitmap.createBitmap(16, 20, Bitmap.Config.ARGB_8888));
    }

    public void setTexto(String texto) {
        bmpTexto = textoAImagen(texto);
        bmpTexto = Bitmap.createScaledBitmap(bmpTexto, texto.length() * (anchoPantalla * 1/40), (altoPantalla * 1/10) * 2/3, false);
    }

    public int getWidth() {
        return bmpTexto.getWidth();
    }

    public int getHeight() {
        return bmpTexto.getHeight();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

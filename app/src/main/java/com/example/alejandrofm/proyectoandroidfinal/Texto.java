package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;

public class Texto {

    private HashMap<Character, Bitmap> letras = new HashMap<>();
    private Utils utils;
    private Bitmap bmpTexto;
    private int x, y;

    public Texto(String texto, int anchoPantalla, int altoPantalla, Context context) {
        utils = new Utils(context);
        cargarBitmaps();
        bmpTexto = textoAImagen(texto);
        bmpTexto = Bitmap.createScaledBitmap(bmpTexto, (anchoPantalla * 1/4) * 2/3, (altoPantalla * 1/6) * 2/3, false);
    }

    public void dibujarTexto(int posX, int posY, Canvas c) {
        this.x = posX;
        this.y = posY;
        c.drawBitmap(bmpTexto, posX, posY, null);
    }

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

    private void cargarBitmaps() {
        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
        int tempX = 158;
        int tempY = 10;
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (i % 8 == 0 && i > 0) {
                tempY += 23;
                tempX = 158;
            }
            letras.put(c, Bitmap.createBitmap(tileset, tempX, tempY, 16, 20));
            tempX += 16;
            c++;
        }
        letras.put(' ', Bitmap.createBitmap(16, 20, Bitmap.Config.ARGB_8888));
    }

    public int getWidth() {
        return bmpTexto.getWidth();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

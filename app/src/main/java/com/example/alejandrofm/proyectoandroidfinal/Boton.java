package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Boton {

    private Utils utils;
//    private Bitmap letras[] = new Bitmap[26];
    private HashMap<Character, Bitmap> letras = new HashMap<>();
    private Bitmap botonPulsado;
    private Bitmap botonBase;
    private Bitmap imagenTexto;
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
        imagenTexto = textoAImagen(texto);
        imagenTexto = Bitmap.createScaledBitmap(imagenTexto, botonBase.getWidth() * 3/5, botonBase.getHeight() * 3/5, false);
    }

    public void dibujarBoton(int x, int y, Canvas canvas) {
        canvas.drawBitmap(botonBase, x, y, null);
        canvas.drawBitmap(imagenTexto, (x + (botonBase.getWidth() / 2)) - (imagenTexto.getWidth() / 2), (y + botonBase.getHeight() / 2) - (imagenTexto.getHeight() / 2), null);
    }

    public void cargarBitmaps() {
        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
        botonBase = Bitmap.createBitmap(tileset, 16 , 16, 128, 32);
        botonBase = Bitmap.createScaledBitmap(botonBase, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        botonPulsado = Bitmap.createBitmap(tileset, 16, 64, 128, 32);
        botonPulsado = Bitmap.createScaledBitmap(botonPulsado, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        int tempX = 158;
        int tempY = 10;
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (i % 8 == 0 && i > 0) {
                Log.i("test", "i: " + i);
                tempY += 23;
                tempX = 158;
            }
            letras.put(c, Bitmap.createBitmap(tileset, tempX, tempY, 16, 20));
            tempX += 16;
            c++;
        }
    }

    public Bitmap textoAImagen(String texto) {
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

    public Bitmap combinarImagenes(Bitmap c, Bitmap s) {
        Bitmap cs = null;

        int ancho, alto = 0;

        if(c.getWidth() > s.getWidth()) {
            ancho = c.getWidth() + s.getWidth();
            alto = c.getHeight();
        } else {
            ancho = s.getWidth() + s.getWidth();
            alto = c.getHeight();
        }

        cs = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        return cs;
    }
}

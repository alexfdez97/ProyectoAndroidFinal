package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Boton {

    private Utils utils;
    private HashMap<Character, Bitmap> letras = new HashMap<>();
    private Bitmap botonPulsado;
    private Bitmap botonBase;
    private Bitmap imagenTexto;
    private boolean pulsado = false;
    private int anchoPantalla, altoPantalla;
    private int x, y;

    /**
     * Inicializa Boton
     * @param texto el texto que aparece dentro del botón (Admite carácteres de la a-z dentro de la tabla ASCII)
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicación
     */
    public Boton(String texto, int anchoPantalla, int altoPantalla, Context context) {
        utils = new Utils(context);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        cargarBitmaps();
        imagenTexto = textoAImagen(texto);
        imagenTexto = Bitmap.createScaledBitmap(imagenTexto, botonBase.getWidth() * 3/5, botonBase.getHeight() * 3/5, false);
    }

    /**
     * Dibuja el Boton en el canvas
     * @param x la coordenada x del canvas
     * @param y la coordenada y del canvas
     * @param canvas el canvas donde se dibuja
     */
    public void dibujarBoton(int x, int y, Canvas canvas) {
        this.x = x;
        this.y = y;
        if (pulsado) {
            canvas.drawBitmap(botonPulsado, x, y, null);
        } else {
            canvas.drawBitmap(botonBase, x, y, null);
        }
        canvas.drawBitmap(imagenTexto, (x + (botonBase.getWidth() / 2)) - (imagenTexto.getWidth() / 2), (y + botonBase.getHeight() / 2) - (imagenTexto.getHeight() / 2), null);
    }

    /**
     * Carga en memoria, recorta y escala los bitmaps necesarios para el funcionamiento de la clase
     */
    private void cargarBitmaps() {
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

    /**
     * Recoge el parámetro texto y devuelve una imagen compuesta por ese texto (Admite carácteres de a-z de la tabla ASCII)
     * @param texto es el texto que se transforma
     * @return el bitmap con los carácteres del texto
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
     * Función que combina dos imagenes, se utiliza para unir las imágenes de los carácteres almacenados en la Hashtable
     * @param c el primer bitmap
     * @param s el segundo bitmap
     * @return la unión de 'c' y 's'
     */
    private Bitmap combinarImagenes(Bitmap c, Bitmap s) {
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

    /**
     * Comprueba si el Boton está pulsado y cambia el bitmap si es así
     * @param event el evento onTouch
     * @return TRUE si está pulsado, FALSE si no
     */
    public boolean isPulsado(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        if ((eventX > x && eventY > y) && (eventX < x + getWidth() && eventY < y + getHeight())) {
            setPulsado(true);
        } else {
            setPulsado(false);
        }
        return pulsado;
    }

    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return botonBase.getWidth();
    }

    public int getHeight() {
        return botonBase.getHeight();
    }
}
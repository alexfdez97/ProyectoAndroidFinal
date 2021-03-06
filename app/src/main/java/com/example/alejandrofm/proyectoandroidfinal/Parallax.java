package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Clase Parallax
 */
public class Parallax {

    /**
     * El bitmap de cielo
     */
    private Bitmap cielo;
    /**
     * El bitmap de graveyard
     */
    private Bitmap graveyard;
    /**
     * Los Bitmap [ ] de nubes
     */
    private Bitmap nubes[] = new Bitmap[2];
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El ancho de la pantalla y el alto
     */
    private int anchoPantalla, altoPantalla;
    /**
     * Lista de nubes
     */
    private ArrayList<Nube> nubesEnPantalla = new ArrayList<>();

    /**
     * Inicializa las propiedades de la clase
     * @param day si es dia o de noche
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     */
    public Parallax(boolean day, int anchoPantalla, int altoPantalla,Context context) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        utils = new Utils(context);
        if (day) {
            cielo = utils.getBitmapFromAssets("parallax/dia/sky.png");
        } else {
            cielo = utils.getBitmapFromAssets("parallax/noche/sky.png");
        }
        cielo = Bitmap.createScaledBitmap(cielo, anchoPantalla, altoPantalla, false);
        graveyard = utils.getBitmapFromAssets("parallax/graveyard.png");
        graveyard = Bitmap.createBitmap(graveyard, 33, 0, graveyard.getWidth() - 66, 517);
        graveyard = Bitmap.createScaledBitmap(graveyard, anchoPantalla, altoPantalla / 2, false);
        nubes[0] = utils.getBitmapFromAssets("parallax/clouds_1.png");
        nubes[0] = Bitmap.createScaledBitmap(nubes[0], anchoPantalla * 1/5, altoPantalla * 1/4, false);
        nubes[1] = utils.getBitmapFromAssets("parallax/clouds_2.png");
        nubes[1] = Bitmap.createScaledBitmap(nubes[1], anchoPantalla * 1/3, altoPantalla * 1/2, false);
        crearNubes();
    }

    /**
     * Dibuja el Parallax en el Canvas
     * @param c el canvas
     */
    public void dibujaParallax(Canvas c) {
        c.drawBitmap(cielo, 0 , 0, null);
        for (Nube nube:nubesEnPantalla) {
            nube.dibujarNube(c);
        }
        comprobarNubes();
        c.drawBitmap(graveyard, 0, altoPantalla / 2, null);
    }

    /**
     * Crea una nube
     */
    private void crearUnaNube() {
        int indexNube;
        if (Math.random() > 0.5) {
            indexNube = 1;
        } else {
            indexNube = 0;
        }
        int ladoNube;
        if (Math.random() > 0.5) {
            ladoNube = 1;
        } else {
            ladoNube = 0;
        }
        double ref = 0;
        for (int i = anchoPantalla; i > 0; i -= 360) {
            ref += 0.5;
        }
        float velNube = (float)Math.random() * (float)ref;

        float alturaNube = (float)(Math.random() * (altoPantalla * 3/4) - nubes[indexNube].getHeight());
        nubesEnPantalla.add(new Nube(nubes[indexNube], Nube.LadoInicio.intToNube(ladoNube), alturaNube, velNube, anchoPantalla, altoPantalla));
    }

    /**
     * Crea una cantidad aleatoria de nubes
     */
    private void crearNubes() {
        int cantidadNubes = (int)(Math.random() * (8 - 4)) + 4;
        for (int i = 0; i < cantidadNubes; i++) {
            crearUnaNube();
        }
    }

    /**
     * Comprueba si las nubes salieron de la pantalla, si es asi la elimina y crea una nueva
     */
    private void comprobarNubes() {
        for (int i = 0; i < nubesEnPantalla.size(); i++) {
            Nube nube = nubesEnPantalla.get(i);
            if (nube.getPosX() + nube.getWidth() < 0 || nube.getPosX() > anchoPantalla) {
                nubesEnPantalla.remove(i);
                crearUnaNube();
            }
        }
    }
}

package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Parallax {

    private Bitmap cielo;
    private Bitmap nubes[] = new Bitmap[2];
    private Utils utils;
    private int anchoPantalla, altoPantalla;
    private ArrayList<Nube> nubesEnPantalla = new ArrayList<>();

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
        nubes[0] = utils.getBitmapFromAssets("parallax/clouds_1.png");
        nubes[0] = Bitmap.createScaledBitmap(nubes[0], anchoPantalla * 1/5, altoPantalla * 1/4, false);
        nubes[1] = utils.getBitmapFromAssets("parallax/clouds_2.png");
        nubes[1] = Bitmap.createScaledBitmap(nubes[1], anchoPantalla * 1/5, altoPantalla * 1/4, false);
        nubes[1] = utils.getBitmapFromAssets("parallax/clouds_2.png");
        crearNubes();
    }

    public void dibujaParallax(Canvas c) {
        c.drawBitmap(cielo, 0 , 0, null);
        for (Nube nube:nubesEnPantalla) {
            nube.dibujarNube(c);
        }
        comprobarNubes();
    }

    private void crearUnaNube() {
        int indexNube = (int)(Math.random() * 1);
        int ladoNube;
        if (Math.random() > 0.5) {
            ladoNube = 1;
        } else {
            ladoNube = 0;
        }
        float velNube = (float)Math.random() * 2;
        nubesEnPantalla.add(new Nube(nubes[indexNube], Nube.LadoInicio.intToNube(ladoNube), velNube, anchoPantalla, altoPantalla));
    }

    private void crearNubes() {
        int cantidadNubes = (int)(Math.random() * (8 - 4)) + 4;
        for (int i = 0; i < cantidadNubes; i++) {
            crearUnaNube();
        }
    }

    private void comprobarNubes() {
        for (int i = 0; i < nubesEnPantalla.size(); i++) {
            Nube nube = nubesEnPantalla.get(i);
            if (nube.getPosX() + nube.getWidth() < 0 || nubesEnPantalla.get(i).getPosX() > anchoPantalla) {
                nubesEnPantalla.remove(i);
                crearUnaNube();
            }
        }
    }
}

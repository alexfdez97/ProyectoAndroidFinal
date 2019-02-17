package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;

public class Opciones extends Escena {

    private Parallax parallax;

    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        this.parallax = parallax;
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            parallax.dibujaParallax(c);
        } catch (NullPointerException ex) { }
    }
}

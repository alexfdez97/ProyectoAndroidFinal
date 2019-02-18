package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class Opciones extends Escena {

    private Parallax parallax;

    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            this.parallax.dibujaParallax(c);
        } catch (NullPointerException ex) { }
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    public Parallax getParallax() {
        return parallax;
    }
}

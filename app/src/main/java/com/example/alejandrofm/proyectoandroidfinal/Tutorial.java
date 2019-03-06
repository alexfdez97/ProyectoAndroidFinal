package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;

public class Tutorial extends Escena {

    private Mapa mapa;

    public Tutorial(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        mapa = new Mapa(context, anchoPantalla, altoPantalla);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            mapa.dibujaMapa(c);
        } catch (NullPointerException ex) {

        }
    }
}

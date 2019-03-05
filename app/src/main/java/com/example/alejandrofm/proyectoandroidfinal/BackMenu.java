package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class BackMenu extends Escena {
    private Boton btnVolver, btnMenu, btnSalir;
    private int anchoPantalla, altoPantalla;

    public BackMenu(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        btnMenu = new Boton(context.getResources().getString(R.string.strMenu), anchoPantalla, altoPantalla, efectos, context);
        btnVolver = new Boton(context.getResources().getString(R.string.strRollback), anchoPantalla, altoPantalla, efectos, context);
        btnSalir = new Boton(context.getResources().getString(R.string.strExit), anchoPantalla, altoPantalla, efectos, context);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            btnVolver.dibujarBoton(anchoPantalla * 2/3, altoPantalla * 2/7, c);
            btnMenu.dibujarBoton(anchoPantalla * 2/3, altoPantalla * 4/7, c);
            btnSalir.dibujarBoton(anchoPantalla * 2/3, altoPantalla * 6/7, c);
        } catch (NullPointerException ex) { }
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        return super.onTouchPersonalizado(event);
    }
}

package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class Opciones extends Escena {

    private Parallax parallax;
    private IconoBoton btnRollback;

    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, context);
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                btnRollback.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnRollback.isPulsado(event)) {
                    btnRollback.setPulsado(false);
                    return 0;
                }
                break;
        }
        return idEscena;
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            this.parallax.dibujaParallax(c);
            btnRollback.dibujarIconoBoton(0 + btnRollback.getWidth() / 2, 0 + btnRollback.getHeight() / 2, c);
        } catch (NullPointerException ex) { }
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    public Parallax getParallax() {
        return parallax;
    }
}

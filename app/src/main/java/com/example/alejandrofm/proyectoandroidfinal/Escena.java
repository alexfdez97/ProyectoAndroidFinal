package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Escena implements IEscena {

    protected int anchoPantalla, altoPantalla;
    private Context context;
    protected int idEscena;
    protected Bitmap fondo;
    protected Paint pntBotonMenu;

    public Escena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.idEscena = idEscena;
        if (idEscena == 0) {
            pntBotonMenu = new Paint();
            pntBotonMenu.setColor(Color.GREEN);
        }
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

    public int getIdEscena() {
        return idEscena;
    }

    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }

    public boolean esPulsado(Rect boton, MotionEvent event) {
        if (boton.contains((int)event.getX(), (int)event.getY())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actualizarFisica() {

    }

    @Override
    public void dibujar(Canvas c) {

    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
//        int indicePuntero = event.getActionIndex();
//        int punteroID = event.getPointerId(indicePuntero);
//        int accion = event.getActionMasked();
        return idEscena;
    }
}
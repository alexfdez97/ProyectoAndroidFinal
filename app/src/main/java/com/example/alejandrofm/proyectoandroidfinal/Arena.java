package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class Arena extends Escena {

    private Utils utils;
    private Joystick jIzquierdo, jDerecho;

    public Arena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        utils = new Utils(context);
        fondo = utils.getBitmapFromAssets("asphalt.png");
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        float x = event.getX(event.getActionIndex());
        float y = event.getY(event.getActionIndex());
        int id = event.getPointerId(event.getActionIndex());
        event.getX(event.getActionIndex());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                if (x < getAnchoPantalla() / 2) {
                    jIzquierdo = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                    jIzquierdo.setIdPuntero(id);
                    jIzquierdo.setPulsado(true);
                } else {
                    jDerecho = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                    jDerecho.setIdPuntero(id);
                    jDerecho.setPulsado(true);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (jIzquierdo != null && jIzquierdo.getIdPuntero() == id) {
                    jIzquierdo.setPulsado(false);
                    jIzquierdo = null;
                }
                if (jDerecho != null && jDerecho.getIdPuntero() == id) {
                    jDerecho.setPulsado(false);
                    jDerecho = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if (jIzquierdo != null && jIzquierdo.getIdPuntero() == i) {
//                        jIzquierdo.setjFlechasX(event.getX(i));
//                        jIzquierdo.setjFlechasY(event.getY(i));
                        jIzquierdo.setCoordsJFlecas(event.getX(i), event.getY(i));
                    }
                    if (jDerecho != null && jDerecho.getIdPuntero() == i) {
//                        jDerecho.setjFlechasX(event.getX(i));
//                        jDerecho.setjFlechasY(event.getY(i));
                        jDerecho.setCoordsJFlecas(event.getX(i), event.getY(i));
                    }
                }
                break;
        }
        return -1;
    }

    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(fondo, 0, 0, null);
        if (jIzquierdo != null) {
            if (jIzquierdo.isPulsado()) {
                jIzquierdo.dibujaJoystick(c);
            }
        }
        if (jDerecho != null) {
            if (jDerecho.isPulsado()) {
                jDerecho.dibujaJoystick(c);
            }
        }
    }
}

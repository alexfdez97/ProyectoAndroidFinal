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
        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {
            float x = event.getX(i);
            float y = event.getY(i);
            int action = event.getActionMasked();

            switch (action) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (x < getAnchoPantalla() / 2) {
                        jIzquierdo = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                        jIzquierdo.setPulsado(true);
                    } else {
                        jDerecho = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                        jDerecho.setPulsado(true);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if (jIzquierdo != null) {
                        jIzquierdo.setPulsado(false);
                    }
                    if (jDerecho != null) {
                        jDerecho.setPulsado(false);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (x < getAnchoPantalla() / 2 && jIzquierdo != null) {
                        jIzquierdo.setjFlechasX(event.getX(i));
                        jIzquierdo.setjFlechasY(event.getY(i));
                    } else if (jDerecho != null) {
                        jDerecho.setjFlechasX(event.getX(i));
                        jDerecho.setjFlechasY(event.getY(i));
                    }
                    break;
            }
        }
        return -1;
    }

    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(fondo, 0, 0, null);
        if (jIzquierdo != null && jDerecho != null) {
            if (jIzquierdo.isPulsado()) {
                jIzquierdo.dibujaJoystick(c);
            }
            if (jDerecho.isPulsado()) {
                jDerecho.dibujaJoystick(c);
            }
        }
    }
}

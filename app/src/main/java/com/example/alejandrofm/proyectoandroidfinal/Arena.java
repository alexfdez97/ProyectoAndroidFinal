package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Arena extends Escena {

    private Utils utils;
    private Joystick j;
    private boolean esPulsado = false;

    public Arena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        utils = new Utils(context);
        fondo = utils.getBitmapFromAssets("asphalt.png");
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);
//        j = new Joystick(getContext());
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            j = new Joystick(getContext(), event.getX(), event.getY(), getAnchoPantalla(), getAltoPantalla());
            esPulsado = true;
        }
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            esPulsado = false;
        }
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            j.setjFlechasX(event.getX());
            j.setjFlechasY(event.getY());
        }
        return 99;
    }

    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(fondo, 0, 0, null);
        if (esPulsado) {
            j.dibujaJoystick(c);
        }
    }
}

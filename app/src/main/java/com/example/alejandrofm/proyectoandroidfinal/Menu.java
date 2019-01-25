package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Escena {

    private Rect btnJugar, btnOpciones, btnAyuda, btnCreditos;
    private Utils utils;

    public Menu(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        utils = new Utils(context);
        fondo = utils.getBitmapFromAssets("asphalt.png");
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);
        btnJugar = new Rect(anchoPantalla * 2/6, altoPantalla * 3/12, anchoPantalla * 4/6, altoPantalla * 5/12);
        btnOpciones = new Rect(anchoPantalla * 2/6, altoPantalla * 7/12, anchoPantalla * 4/6, altoPantalla * 9/12);
        btnAyuda = new Rect(anchoPantalla * 1/12, altoPantalla * 21/26, anchoPantalla * 3/12, altoPantalla * 24/26);
        btnCreditos = new Rect(anchoPantalla * 9/12, altoPantalla * 21/26, anchoPantalla * 11/12, altoPantalla * 24/26);
    }

    @Override
    public void dibujar(Canvas c) {
        c.drawBitmap(fondo, 0, 0, null);
        c.drawRect(btnJugar, pntBotonMenu);
        c.drawRect(btnOpciones, pntBotonMenu);
        c.drawRect(btnAyuda, pntBotonMenu);
        c.drawRect(btnCreditos, pntBotonMenu);
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int indicePuntero = event.getActionIndex();
        int punteroID = event.getPointerId(indicePuntero);
        int accion = event.getActionMasked();
        if (accion == MotionEvent.ACTION_UP) {
            if (esPulsado(btnJugar, event)) {
                Log.i("pulsa", "pulsacion en boton");
            }
        }
        return idEscena;
    }
}

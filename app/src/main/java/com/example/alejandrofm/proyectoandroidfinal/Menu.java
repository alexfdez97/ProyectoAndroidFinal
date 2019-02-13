package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Escena {

    private Rect btnJugar, btnOpciones, btnAyuda, btnCreditos;
    private Boton botJugar, botOpciones, botAyuda, botCreditos;
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
        botJugar = new Boton("Jugar", anchoPantalla, altoPantalla, context);
        botOpciones = new Boton("Opciones", anchoPantalla, altoPantalla, context);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            botJugar.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 3/12, c);
//            c.drawRect(btnJugar, pntBotonMenu);
            botOpciones.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 7/12, c);
//            c.drawRect(btnOpciones, pntBotonMenu);
            c.drawRect(btnAyuda, pntBotonMenu);
            c.drawRect(btnCreditos, pntBotonMenu);
        } catch (NullPointerException ex) {}
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int indicePuntero = event.getActionIndex();
        int punteroID = event.getPointerId(indicePuntero);
        int accion = event.getActionMasked();
        if (accion == MotionEvent.ACTION_UP) {
            if (esPulsado(btnJugar, event)) {
                return 1;
            }
            if (esPulsado(btnOpciones, event)) {
                return 2;
            }
            if (esPulsado(btnAyuda, event)) {
                return 3;
            }
            if (esPulsado(btnCreditos, event)) {
                return 4;
            }
        }
        return idEscena;
    }
}
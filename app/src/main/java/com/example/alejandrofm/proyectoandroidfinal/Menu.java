package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Menu extends Escena {

    private Boton btnJugar, btnOpciones;
    private Bitmap btnAyuda, btnRecords, btnCreditos;
    private Utils utils;

    public Menu(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        utils = new Utils(context);
        fondo = utils.getBitmapFromAssets("parallax/dia/sky.png");
        fondo = Bitmap.createScaledBitmap(fondo, anchoPantalla, altoPantalla, false);
        btnJugar = new Boton("Jugar", anchoPantalla, altoPantalla, context);
        btnOpciones = new Boton("Opciones", anchoPantalla, altoPantalla, context);
        btnAyuda = utils.getBitmapFromAssets("menu/help.png");
        btnAyuda = Bitmap.createScaledBitmap(btnAyuda, altoPantalla * 1/9, altoPantalla * 1/9, false);
        btnRecords = utils.getBitmapFromAssets("menu/trophy.png");
        btnRecords = Bitmap.createScaledBitmap(btnRecords, altoPantalla * 1/9, altoPantalla * 1/9, false);
        btnCreditos = utils.getBitmapFromAssets("menu/info.png");
        btnCreditos = Bitmap.createScaledBitmap(btnCreditos, altoPantalla * 1/9, altoPantalla * 1/9, false);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            btnJugar.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 3/12, c);
            btnOpciones.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 7/12, c);
            int altoBotonesInferiores = (btnOpciones.getHeight() + btnOpciones.getY()) + (altoPantalla - (btnOpciones.getHeight() + btnOpciones.getY())) / 2 - btnAyuda.getHeight() / 2;
            c.drawBitmap(btnAyuda, btnOpciones.getX() / 2 - btnAyuda.getHeight() / 2, altoBotonesInferiores, null);
            c.drawBitmap(btnRecords, btnOpciones.getX() + btnOpciones.getWidth() / 2 - btnRecords.getWidth() / 2, altoBotonesInferiores, null);
            c.drawBitmap(btnCreditos, (btnOpciones.getX() + btnOpciones.getWidth()) + (anchoPantalla - (btnOpciones.getWidth() + btnOpciones.getX())) / 2 - btnCreditos.getWidth() / 2, altoBotonesInferiores, null);
        } catch (NullPointerException ex) { }
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int indicePuntero = event.getActionIndex();
        int punteroID = event.getPointerId(indicePuntero);
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                btnJugar.isPulsado(event);
                btnOpciones.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnJugar.isPulsado(event)) {
                    return 1;
                }
                if (btnOpciones.isPulsado(event)) {
                    return 2;
                }
                btnJugar.setPulsado(false);
                btnOpciones.setPulsado(false);
                break;
        }
        return idEscena;
    }
}
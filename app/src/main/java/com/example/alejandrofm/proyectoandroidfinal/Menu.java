package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Escena {

    private Boton btnJugar, btnOpciones;
    private IconoBoton btnAyuda, btnCreditos, btnRecords;
    private Utils utils;
    private Parallax parallax;
    private float luz;
    private MediaPlayer menuMusic;

    public Menu(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        utils = new Utils(context);
        parallax = new Parallax(true, anchoPantalla, altoPantalla, context);
        btnJugar = new Boton(context.getResources().getString(R.string.strPlay), anchoPantalla, altoPantalla, efectos, context);
        btnOpciones = new Boton(context.getResources().getString(R.string.strOptions), anchoPantalla, altoPantalla, efectos, context);
        btnAyuda = new IconoBoton(IconoBoton.Tipo.AYUDA, anchoPantalla, altoPantalla, efectos, context);
        btnRecords = new IconoBoton(IconoBoton.Tipo.RECORDS, anchoPantalla, altoPantalla, efectos, context);
        btnCreditos = new IconoBoton(IconoBoton.Tipo.CREDITOS, anchoPantalla, altoPantalla, efectos, context);
        menuMusic = MediaPlayer.create(context, R.raw.beethoven_moonlight_1st_movement);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            parallax.dibujaParallax(c);
            btnJugar.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 3/12, c);
            btnOpciones.dibujarBoton(anchoPantalla * 2/6, altoPantalla * 7/12, c);
            int altoBotonesInferiores = (btnOpciones.getHeight() + btnOpciones.getY()) + (altoPantalla - (btnOpciones.getHeight() + btnOpciones.getY())) / 2 - btnAyuda.getHeight() / 2;
            btnAyuda.dibujarIconoBoton(btnOpciones.getX() / 2 - btnAyuda.getHeight() / 2, altoBotonesInferiores, c);
            btnCreditos.dibujarIconoBoton((btnOpciones.getX() + btnOpciones.getWidth()) + (anchoPantalla - (btnOpciones.getWidth() + btnOpciones.getX())) / 2 - btnCreditos.getWidth() / 2, altoBotonesInferiores, c);
            btnRecords.dibujarIconoBoton(btnOpciones.getX() + btnOpciones.getWidth() / 2 - btnRecords.getWidth() / 2, altoBotonesInferiores, c);
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
                btnAyuda.isPulsado(event);
                btnRecords.isPulsado(event);
                btnCreditos.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnJugar.isPulsado() && btnJugar.isPulsado(event)) {
                    btnJugar.setPulsado(false);
                    return 1;
                }
                if (btnOpciones.isPulsado() && btnOpciones.isPulsado(event)) {
                    btnOpciones.setPulsado(false);
                    return 2;
                }
                if (btnAyuda.isPulsado() && btnAyuda.isPulsado(event)) {
                    btnAyuda.setPulsado(false);
                    return 0;
                }
                if (btnRecords.isPulsado() && btnRecords.isPulsado(event)) {
                    btnRecords.setPulsado(false);
                    return 0;
                }
                if (btnCreditos.isPulsado() && btnCreditos.isPulsado(event)) {
                    btnCreditos.setPulsado(false);
                    return 0;
                }
                break;
        }
        return idEscena;
    }

    @Override
    public void setEfectos(boolean efectos) {
        super.setEfectos(efectos);
        btnJugar.setEfectos(efectos);
        btnOpciones.setEfectos(efectos);
        btnAyuda.setEfectos(efectos);
        btnRecords.setEfectos(efectos);
        btnCreditos.setEfectos(efectos);
    }

    public void setLuz(float luz) {
        this.luz = luz;
        if (luz < 2) {
            parallax = new Parallax(false, anchoPantalla, altoPantalla, getContext());
        }
    }

    public Parallax getParallax() {
        return parallax;
    }
}
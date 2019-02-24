package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.view.MotionEvent;

public class Opciones extends Escena {

    private Parallax parallax;
    private IconoBoton btnRollback, btnMusic, btnEfect;
    private Boton btnResetRecords;
    private Texto txtMusica, txtEfectos;

    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, context);
        btnResetRecords = new Boton(context.getString(R.string.strReset), anchoPantalla, altoPantalla, context);
        txtMusica = new Texto(context.getString(R.string.strMusic), anchoPantalla, altoPantalla, context);
        txtEfectos = new Texto(context.getString(R.string.strEffects), anchoPantalla, altoPantalla, context);
        btnMusic = new IconoBoton(IconoBoton.Tipo.SPEAKERON, anchoPantalla, altoPantalla, context);
        btnEfect = new IconoBoton(IconoBoton.Tipo.SPEAKEROFF, anchoPantalla, altoPantalla, context);
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                btnRollback.isPulsado(event);
                btnMusic.isPulsado(event);
                btnEfect.isPulsado(event);
                btnResetRecords.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnRollback.isPulsado() && btnRollback.isPulsado(event)) {
                    btnRollback.setPulsado(false);
                    return 0;
                }
                if (btnMusic.isPulsado() && btnMusic.isPulsado(event)) {
                    btnMusic.setPulsado(false);
                    if (btnMusic.getTipo() == IconoBoton.Tipo.SPEAKERON) {
                        btnMusic.cambiarIcono(IconoBoton.Tipo.SPEAKEROFF);
                    } else {
                        btnMusic.cambiarIcono(IconoBoton.Tipo.SPEAKERON);
                    }
                }
                if (btnEfect.isPulsado() && btnEfect.isPulsado(event)) {
                    btnEfect.setPulsado(false);
                    if (btnEfect.getTipo() == IconoBoton.Tipo.SPEAKERON) {
                        btnEfect.cambiarIcono(IconoBoton.Tipo.SPEAKEROFF);
                    } else {
                        btnEfect.cambiarIcono(IconoBoton.Tipo.SPEAKERON);
                    }
                }
                if (btnResetRecords.isPulsado() && btnResetRecords.isPulsado(event)) {
                    btnResetRecords.setPulsado(false);
                    //TODO codigo reset records
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
            btnResetRecords.dibujarBoton(anchoPantalla * 1/3, altoPantalla * 3/4, c);
            txtEfectos.dibujarTexto(anchoPantalla * 1/3, altoPantalla * 1/4, c);
            txtMusica.dibujarTexto(anchoPantalla * 1/3, altoPantalla * 2/4, c);
            int xIconos = btnResetRecords.getX() + btnResetRecords.getWidth() - btnEfect.getWidth();
            btnEfect.dibujarIconoBoton(xIconos, txtEfectos.getY(), c);
            btnMusic.dibujarIconoBoton(xIconos, txtMusica.getY(), c);
        } catch (NullPointerException ex) { }
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    public Parallax getParallax() {
        return parallax;
    }
}

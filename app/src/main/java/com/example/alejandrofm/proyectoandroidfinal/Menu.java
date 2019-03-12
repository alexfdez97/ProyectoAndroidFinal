package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase Menu
 */
public class Menu extends Escena {

    /**
     * Los botones
     */
    private Boton btnJugar, btnOpciones;
    /**
     * Los botones
     */
    private Boton btnSi, btnNo;
    /**
     * Los iconoBoton
     */
    private IconoBoton btnAyuda, btnCreditos, btnRecords;
    /**
     * El texto de salir
     */
    private Texto txtSalir;
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El Parallax
     */
    private Parallax parallax;
    /**
     * El nivel de luz
     */
    private float luz;
    /**
     * El mediaplayer de menu
     */
    private MediaPlayer menuMusic;
    /**
     * Si se pulso onBackPressed
     */
    private boolean backPressed = false;

    /**
     * Inicializa las propiedades de la clase
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicación
     * @param idEscena el id de la Escena
     */
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
        btnSi = new Boton(context.getString(R.string.strYes), anchoPantalla / 2, altoPantalla, efectos, context);
        btnNo = new Boton(context.getString(R.string.strNo), anchoPantalla / 2, altoPantalla, efectos, context);
        txtSalir = new Texto(context.getString(R.string.strExit), anchoPantalla * 3, altoPantalla * 3, context);
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            parallax.dibujaParallax(c);
            if (!backPressed) {
                btnJugar.dibujarBoton(anchoPantalla * 2 / 6, altoPantalla * 3 / 12, c);
                btnOpciones.dibujarBoton(anchoPantalla * 2 / 6, altoPantalla * 7 / 12, c);
                int altoBotonesInferiores = (btnOpciones.getHeight() + btnOpciones.getY()) + (altoPantalla - (btnOpciones.getHeight() + btnOpciones.getY())) / 2 - btnAyuda.getHeight() / 2;
                btnAyuda.dibujarIconoBoton(btnOpciones.getX() / 2 - btnAyuda.getHeight() / 2, altoBotonesInferiores, c);
                btnCreditos.dibujarIconoBoton((btnOpciones.getX() + btnOpciones.getWidth()) + (anchoPantalla - (btnOpciones.getWidth() + btnOpciones.getX())) / 2 - btnCreditos.getWidth() / 2, altoBotonesInferiores, c);
                btnRecords.dibujarIconoBoton(btnOpciones.getX() + btnOpciones.getWidth() / 2 - btnRecords.getWidth() / 2, altoBotonesInferiores, c);
            } else {
                txtSalir.dibujarTexto(anchoPantalla / 2 - txtSalir.getWidth() / 2, altoPantalla * 1/3 - txtSalir.getHeight() / 2, c);
                btnSi.dibujarBoton(anchoPantalla * 2/8 - btnSi.getWidth() / 2, altoPantalla * 2/3 - btnSi.getHeight() / 2, c);
                btnNo.dibujarBoton(anchoPantalla * 6/8 - btnNo.getWidth() / 2, altoPantalla * 2/3 - btnNo.getHeight() / 2, c);
            }
        } catch (NullPointerException ex) { }
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        if (!backPressed) {
            switch (accion) {
                case MotionEvent.ACTION_DOWN:
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
                        return 3;
                    }
                    if (btnRecords.isPulsado() && btnRecords.isPulsado(event)) {
                        btnRecords.setPulsado(false);
                        return 4;
                    }
                    if (btnCreditos.isPulsado() && btnCreditos.isPulsado(event)) {
                        btnCreditos.setPulsado(false);
                        return 5;
                    }
                    break;
            }
        } else {
            switch (accion) {
                case MotionEvent.ACTION_DOWN:
                    btnSi.isPulsado(event);
                    btnNo.isPulsado(event);
                    break;
                case MotionEvent.ACTION_UP:
                    if (btnSi.isPulsado() && btnSi.isPulsado(event)) {
                        btnSi.setPulsado(false);
                        System.exit(0);
                    }
                    if (btnNo.isPulsado() && btnNo.isPulsado(event)) {
                        btnNo.setPulsado(false);
                        backPressed = !backPressed;
                    }
            }
        }
        return idEscena;
    }

    @Override
    public void onBackPressed() {
        backPressed = !backPressed;
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

    /**
     * Establece el nivel de luz
     * @param luz la luz en lux
     */
    public void setLuz(float luz) {
        this.luz = luz;
        if (luz < 2) {
            parallax = new Parallax(false, anchoPantalla, altoPantalla, getContext());
        }
    }

    /**
     * Devuelve el Parallax
     * @return el Parallax
     */
    public Parallax getParallax() {
        return parallax;
    }
}
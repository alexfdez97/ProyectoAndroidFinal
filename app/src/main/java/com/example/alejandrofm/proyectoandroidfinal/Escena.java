package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

public class Escena implements IEscena {

    protected int anchoPantalla, altoPantalla;
    protected Context context;
    protected int idEscena;
    protected Bitmap fondo;
    protected Utils utils;
    protected boolean efectos, musica;
    protected SoundPool soundPool;
    protected int efectoDisparo;
    final private int maxEfectos = 10;

    public Escena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.idEscena = idEscena;
        this.utils = new Utils(context);
        boolean prefs[] = utils.cargarPreferencias();
        musica = prefs[0];
        efectos = prefs[1];
        this.soundPool = new SoundPool(maxEfectos, AudioManager.STREAM_MUSIC, 0);
        this.efectoDisparo = this.soundPool.load(context, R.raw.gun_shot, 1);
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

    public boolean isEfectos() {
        return efectos;
    }

    public void setEfectos(boolean efectos) {
        this.efectos = efectos;
    }

    public boolean isMusica() {
        return musica;
    }

    public void setMusica(boolean musica) {
        this.musica = musica;
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
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

/**
 * Clase Escena
 */
public class Escena implements IEscena {

    /**
     * El ancho y el alto de la pantalla
     */
    protected int anchoPantalla, altoPantalla;
    /**
     * El contexto de la aplicacion
     */
    protected Context context;
    /**
     * El id de la escena
     */
    protected int idEscena;
    /**
     * El bitmap de fondo
     */
    protected Bitmap fondo;
    /**
     * Funciones utiles
     */
    protected Utils utils;
    /**
     * Indican si los efectos/musica estan activados
     */
    protected boolean efectos, musica;
    /**
     * El soundpool
     */
    protected SoundPool soundPool;
    /**
     * El efecto de disparo
     */
    protected int efectoDisparo;
    /**
     * El maximo de efectos
     */
    final private int maxEfectos = 10;

    /**
     * Inicializa las propiedades de la clase
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     * @param idEscena el id de la Escena
     */
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

    /**
     * Devuelve el ancho de la pantalla
     * @return el ancho
     */
    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    /**
     * Establece el ancho de la pantalla
     * @param anchoPantalla el ancho
     */
    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    /**
     * Devuelve el alto de la pantalla
     * @return el alto
     */
    public int getAltoPantalla() {
        return altoPantalla;
    }

    /**
     * Establece el alto de la pantalla
     * @param altoPantalla el alto
     */
    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    /**
     * Devuelve el contexto de la Escena
     * @return el contexto
     */
    public Context getContext() {
        return context;
    }

    /**
     * Establace el contexto
     * @param context el contexto
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Devuelve el fondo
     * @return el fondo
     */
    public Bitmap getFondo() {
        return fondo;
    }

    /**
     * Establece el fondo
     * @param fondo el fondo
     */
    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

    /**
     * Devuelve el id de la escena
     * @return el id
     */
    public int getIdEscena() {
        return idEscena;
    }

    /**
     * Establece el id de la Esceba
     * @param idEscena el id
     */
    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }

    /**
     * Devuelve si los efectos estan activados
     * @return los efectos
     */
    public boolean isEfectos() {
        return efectos;
    }

    /**
     * Establece si los efectos estan activados
     * @param efectos los efecots
     */
    public void setEfectos(boolean efectos) {
        this.efectos = efectos;
    }

    /**
     * Devuelve si la musica esta activada
     * @return la musica
     */
    public boolean isMusica() {
        return musica;
    }

    /**
     * Establece si la musica esta activada
     * @param musica la musica
     */
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
        return idEscena;
    }

    @Override
    public void onBackPressed() {

    }
}
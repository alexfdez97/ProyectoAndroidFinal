package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

public class IconoBoton {

    public enum Tipo {
        RECORDS, AYUDA, CREDITOS, VOLVER, SPEAKERON, SPEAKEROFF
    }
    Tipo tipo;
    private Utils utils;
    private Bitmap botonPulsado;
    private Bitmap botonBase;
    private Bitmap icono;
    private boolean pulsado;
    private int altoPantalla, anchoPantalla;
    private int posX, posY;
    private boolean efectos;
    private SoundPool sp;
    private int sonidoBoton;
    final private int maximoSonidos = 1;
    private boolean haSonado = false;

    /**
     * Inicializa las propiedades de la clase
     * @param tipo el tipo de IconoBoton
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos si los efectos están activados
     * @param context el contexto de la aplicación
     */
    public IconoBoton(Tipo tipo, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        utils = new Utils(context);
        this.efectos = efectos;
        this.sp = new SoundPool(maximoSonidos, AudioManager.STREAM_MUSIC, 0);
        sonidoBoton = this.sp.load(context, R.raw.button_press, 1);
        cargarBitmaps();
        cargarTipo(tipo);
    }

    /**
     * Dibuja el IconoBoton
     * @param posX coordenada X donde dibuja
     * @param posY coordenada Y donde dibuja
     * @param c el canvas donde dibuja
     */
    public void dibujarIconoBoton(int posX, int posY, Canvas c) {
        this.posX = posX;
        this.posY = posY;
        if (pulsado) {
            c.drawBitmap(botonPulsado, posX, posY, null);
            if (efectos && !haSonado) {
                sp.play(sonidoBoton, 0.3f, 0.3f, 1, 0, 1);
                haSonado = true;
            }
        } else {
            c.drawBitmap(botonBase, posX, posY, null);
            haSonado = false;
        }
        c.drawBitmap(icono, posX + botonBase.getWidth() / 2 - icono.getWidth() / 2, posY + botonBase.getHeight() / 2 - icono.getHeight() / 2, null);
    }

    /**
     * Carga los Bitmaps necesarios
     */
    public void cargarBitmaps() {
        Bitmap base = utils.getBitmapFromAssets("menu/iconButtons.png");
        botonPulsado = Bitmap.createBitmap(base, 5, 44, 34, 32);
        botonPulsado = Bitmap.createScaledBitmap(botonPulsado, altoPantalla * 1/6, altoPantalla * 1/6, false);
        botonBase = Bitmap.createBitmap(base, 5, 6, 34, 32);
        botonBase = Bitmap.createScaledBitmap(botonBase, altoPantalla * 1/6, altoPantalla * 1/6, false);
    }

    /**
     * Carga el bitmap del tipo de IconoBoton
     * @param tipo el tipo
     */
    private void cargarTipo(Tipo tipo) {
        int proporcion = botonBase.getWidth() * 3/5;
        switch (tipo) {
            case RECORDS:
                icono = utils.getBitmapFromAssets("menu/trophy.png");
                break;
            case AYUDA:
                icono = utils.getBitmapFromAssets("menu/help.png");
                break;
            case CREDITOS:
                icono = utils.getBitmapFromAssets("menu/info.png");
                break;
            case VOLVER:
                icono = utils.getBitmapFromAssets("menu/rollback.png");
                break;
            case SPEAKERON:
                icono = utils.getBitmapFromAssets("menu/speaker.png");
                break;
            case SPEAKEROFF:
                icono = utils.getBitmapFromAssets("menu/speaker-off.png");
                break;
        }
        this.tipo = tipo;
        icono = Bitmap.createScaledBitmap(icono, proporcion, proporcion, false);
    }

    /**
     * Comprueba si el icono está pulsado
     * @param event el evento que se usa para detectar la pulsación
     * @return si el icono está pulsado
     */
    public boolean isPulsado(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        if ((eventX > posX && eventY > posY) && (eventX < posX + getWidth() && eventY < posY + getHeight())) {
            setPulsado(true);
        } else {
            setPulsado(false);
        }
        return pulsado;
    }

    /**
     * Comprueba si el icono está pulsado
     * @return si el icono está pulsado
     */
    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    public int getWidth() {
        return botonBase.getWidth();
    }

    public int getHeight() {
        return botonBase.getHeight();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void cambiarIcono(Tipo tipo) {
        cargarTipo(tipo);
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public boolean isEfectos() {
        return efectos;
    }

    public void setEfectos(boolean efectos) {
        this.efectos = efectos;
    }
}

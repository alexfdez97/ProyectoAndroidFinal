package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * Clase IconoBoton
 */
public class IconoBoton {

    /**
     * El tipo de icono
     */
    public enum Tipo {
        RECORDS, AYUDA, CREDITOS, VOLVER, SPEAKERON, SPEAKEROFF
    }

    /**
     * El tipo
     */
    private Tipo tipo;
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * El Bitmap del boton pulsado
     */
    private Bitmap botonPulsado;
    /**
     * El Bitmap del boton base
     */
    private Bitmap botonBase;
    /**
     * El Bitmap del icono
     */
    private Bitmap icono;
    /**
     * Indica si el boton es pulsado
     */
    private boolean pulsado;
    /**
     * El alto y el ancho de la pantalla
     */
    private int altoPantalla, anchoPantalla;
    /**
     * La posicionX y la posicionY
     */
    private int posX, posY;
    /**
     * Indica si los efectos de sonido estan activados
     */
    private boolean efectos;
    /**
     * El soundpool
     */
    private SoundPool sp;
    /**
     * El sonido del boton
     */
    private int sonidoBoton;
    /**
     * El maximo de sonidos
     */
    final private int maximoSonidos = 1;
    /**
     * Indica si ha sonado
     */
    private boolean haSonado = false;

    /**
     * Inicializa las propiedades de la clase
     * @param tipo el tipo de IconoBoton
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos si los efectos estan activados
     * @param context el contexto de la aplicacion
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
     * Comprueba si el icono esta pulsado
     * @param event el evento que se usa para detectar la pulsacion
     * @return si el icono esta pulsado
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
     * Comprueba si el icono esta pulsado
     * @return si el icono esta pulsado
     */
    public boolean isPulsado() {
        return pulsado;
    }

    /**
     * Establace si el boton es pulsado
     * @param pulsado es pulsado
     */
    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    /**
     * Devuelve el ancho del boton
     * @return el ancho
     */
    public int getWidth() {
        return botonBase.getWidth();
    }

    /**
     * Devuelve el alto del boton
     * @return el alto
     */
    public int getHeight() {
        return botonBase.getHeight();
    }

    /**
     * Devuelve la posicion X del boton
     * @return la X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Establece la posicion X del boton
     * @param posX la X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Devuelve la posicion Y del boton
     * @return la Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Establece la posicion Y
     * @param posY la Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Cambia el tipo de icono
     * @param tipo el tipo
     */
    public void cambiarIcono(Tipo tipo) {
        cargarTipo(tipo);
    }

    /**
     * Devuelve el tipo de icono
     * @return el icono
     */
    public Tipo getTipo() {
        return this.tipo;
    }

    /**
     * Devuelve si los efectos estan activos
     * @return los efectos
     */
    public boolean isEfectos() {
        return efectos;
    }

    /**
     * Establace los efectos
     * @param efectos los efectos
     */
    public void setEfectos(boolean efectos) {
        this.efectos = efectos;
    }
}

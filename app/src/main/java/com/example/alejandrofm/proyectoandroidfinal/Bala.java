package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bala {

    public enum TipoMunicion {
        RIFLE,
        PISTOLA,
        BIGPISTOLA
    }

    private Joystick.Direccion direccion;
    private Utils utils;
    private Bitmap bala;
    private int posX;
    private int posY;
    private int velocidadBala = 20;
    private int anchoPantalla, altoPantalla;
    private Rect hitbox;

    /**
     * Inicializa las propiedades de la clase
     * @param tipo la munición que se va a usar
     * @param direccion la dirección hacia la que se mueve la Bala
     * @param posX coordenada X inicial de la Bala
     * @param posY coordenada Y inicial de la Bala
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicación
     */
    public Bala(TipoMunicion tipo, Joystick.Direccion direccion, int posX, int posY, int anchoPantalla, int altoPantalla, Context context) {
        this.posX = posX;
        this.posY = posY;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        utils = new Utils(context);
        this.direccion = direccion;
        cargarBitmaps(tipo);
        actualizaHitbox();
    }

    /**
     * Dibuja la Bala en el Canvas
     * @param c el Canvas donde lo dibuja
     */
    public void dibujarBala(Canvas c) {
        c.drawBitmap(bala, posX, posY, null);
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);
        c.drawRect(hitbox, p);
    }

    /**
     * Mueve la bala en la dirección específicada
     */
    public void mueveBala() {
        switch (direccion) {
            case NORTE:
                posY -= velocidadBala;
                break;
            case SUR:
                posY += velocidadBala;
                break;
            case ESTE:
                posX += velocidadBala;
                break;
            case OESTE:
                posX -= velocidadBala;
                break;
        }
        actualizaHitbox();
    }

    /**
     * Actualiza la hitbox de la bala
     */
    private void actualizaHitbox() {
        hitbox = new Rect((int)(posX + 0.2 * bala.getWidth()), (int)(posY + 0.2 * bala.getHeight()), (int)(posX + 0.8 * bala.getWidth()), (int)(posY + 0.8 * bala.getHeight()));
    }

    /**
     * Carga el Bitmap de la bala según el tipo que se le haya indicado
     * @param tipo la municion que se le pasa
     */
    private void cargarBitmaps(TipoMunicion tipo) {
        switch (tipo){
            case RIFLE:
                bala = utils.getBitmapFromAssets("balas/bullet1.png");
                break;
            case PISTOLA:
                bala = utils.getBitmapFromAssets("balas/bullet3.png");
                break;
            case BIGPISTOLA:
                bala = utils.getBitmapFromAssets("balas/bullet2.png");
                break;
        }

        Matrix matrix = new Matrix();
        switch (direccion) {
            case NORTE:
                matrix.postRotate(-90);
                break;
            case SUR:
                matrix.postRotate(90);
                break;
            case ESTE:
                matrix.postRotate(0);
                break;
            case OESTE:
                matrix.postRotate(180);
                break;
        }
        bala = Bitmap.createBitmap(bala, 0, 0, bala.getWidth(), bala.getHeight(), matrix, true);
        bala = Bitmap.createScaledBitmap(bala, altoPantalla * 1/80, altoPantalla * 1/80, false);
    }

    /**
     * Devuelve la posición X de la Bala
     * @return la posición X
     */
    public int getX() {
        return posX;
    }

    /**
     * Devuelve la posición Y de la Bala
     * @return la posición Y
     */
    public int getY() {
        return posY;
    }

    /**
     * Deuvelve el ancho del Bitmap de la Bala
     * @return el ancho del Bitmap de la Bala
     */
    public int getWidth() {
        return bala.getWidth();
    }

    /**
     * Deuvelve el alto del Bitmap de la Bala
     * @return el alto del Bitmap de la Bala
     */
    public int getHeight() {
        return bala.getHeight();
    }

    /**
     * Devuelve la velocidad de la Bala
     * @return la velocidad de la Bala
     */
    public int getVelocidadBala() {
        return velocidadBala;
    }

    /**
     * Establece la velocidad de la Bala
     * @param velodidad la velocidad de la Bala
     */
    public void setVelocidadBala(int velodidad) {
        this.velocidadBala = velodidad;
    }

    /**
     * Devuelve la hitbox de la Bala
     * @return la hitbox de la Bala
     */
    public Rect getHitbox() {
        return hitbox;
    }
}

package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;

/**
 * Clase Protagonista
 */
public class Protagonista extends Personaje {

    /**
     * Los Joystick
     */
    private Joystick jIzquierdo, jDerecho;
    /**
     * El vibrador
     */
    private Vibrator vibrator;

    /**
     * Inicializa las propiedades de la clase y de su clase padre
     * @param x la coordenada X del Protagonista
     * @param y la coordenada Y del Protagonista
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos sonoros estan activados
     * @param context el contexto de la aplicacion
     */
    public Protagonista(int x, int y, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        super(x, y, anchoPantalla, altoPantalla, efectos, context);
        vida = 100;
        sprite = new Bitmap[20];
        idleRight = new Bitmap[20];
        idleLeft = new Bitmap[20];
        idleDown = new Bitmap[20];
        idleUp = new Bitmap[20];
        moveUp = new Bitmap[20];
        moveLeft = new Bitmap[20];
        moveDown = new Bitmap[20];
        moveRight = new Bitmap[20];
        idleRight = cargarSpriteProtagonista("idle", "handgun");
        idleUp = rotarSprite(idleRight, -90);
        idleDown = rotarSprite(idleRight, 90);
        idleLeft = rotarSprite(idleRight, 180);
        moveRight = cargarSpriteProtagonista("move", "handgun");
        moveUp = rotarSprite(moveRight, -90);
        moveDown = rotarSprite(moveRight, 90);
        moveLeft = rotarSprite(moveRight, 180);
        sprite = idleRight;
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        super.actualizaHitBox();
    }

    @Override
    public void dibujarPersonaje(Canvas c) {
        super.dibujarPersonaje(c);
        if (jIzquierdo != null) {
            if (jIzquierdo.isPulsado()) {
                caminar(jIzquierdo.getDireccion());
            } else {
                move = false;
            }
        }
        if (jDerecho != null) {
            if (jDerecho.isPulsado()) {
                rotar(jDerecho.getDireccion());
            }
        }
    }

    /**
     * Hace que el personaje camine en la direccion que se le pasa
     * @param direccion la direccion hacia la que se mueve
     */
    public void caminar(Joystick.Direccion direccion) {
        float velocidad = jIzquierdo.getDesplazamiento() / 40;
        move = true;
        if (velocidad > (altoPantalla * 1/4 / 40)) {
            velocidad = (altoPantalla * 1/4 / 40);
        }
        switch (direccion) {
            case NORTE:
                if (posY > 0) {
                    posY -= velocidad;
                }
                break;
            case SUR:
                if (posY < altoPantalla - this.getHeight()) {
                    posY += velocidad;
                }
                break;
            case ESTE:
                if (posX < anchoPantalla - this.getWidth()) {
                    posX += velocidad;
                }
                break;
            case OESTE:
                if (posX > 0) {
                    posX -= velocidad;
                }
                break;
        }
        if (blEfectos) {
            if (direccion != Joystick.Direccion.NINGUNA) {
                sonidoCaminar();
            }
        }
        super.actualizaHitBox();
    }

    /**
     * Carga los sprites del protagonista segun el tipo que se le pase
     * @param tipo el tipo de sprite
     * @param arma el tipo de arma
     * @return el array de Bitmap correspondiente
     */
    protected Bitmap[] cargarSpriteProtagonista(String tipo, String arma) {
        Bitmap bitmap;
        Bitmap[] asset = new Bitmap[20];
        for (int i = 0; i < 20; i++) {
            bitmap = utils.getBitmapFromAssets("protagonista/" + arma +"/" + tipo +"/survivor-" + tipo + "_" + arma + "_" + i + ".png");
            asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
        }
        return asset;
    }

    /**
     * Devuelve joystick izquierdo
     * @return el joystick
     */
    public Joystick getjIzquierdo() {
        return jIzquierdo;
    }

    /**
     * Establece el joystick izquierdo
     * @param jIzquierdo el joystick
     */
    public void setjIzquierdo(Joystick jIzquierdo) {
        this.jIzquierdo = jIzquierdo;
    }

    /**
     * Devuelve el joystick derecho
     * @return el joystick
     */
    public Joystick getjDerecho() {
        return jDerecho;
    }

    /**
     * Establece el joystick derecho
     * @param jDerecho el joystick
     */
    public void setjDerecho(Joystick jDerecho) {
        this.jDerecho = jDerecho;
    }

    /**
     * Devuelve la coordenada X de la boquilla del arma
     * @return la coordenada
     */
    public int getArmaX() {
        switch (jDerecho.getDireccion()) {
            case ESTE:
                return this.getPosX() + this.getWidth() - this.getWidth() * 2/20;
            case NORTE:
                return this.getPosX() + this.getWidth() - this.getWidth() * 3/11;
            case SUR:
                return this.getPosX() + this.getWidth() * 2/10;
            case OESTE:
                return this.getPosX() + this.getWidth() * 2/20;
        }
        return this.getPosX();
    }

    /**
     * Devuelve la coordenada Y de la boquilla del arma
     * @return la coordenada
     */
    public int getArmaY() {
        switch (jDerecho.getDireccion()) {
            case ESTE:
                return this.getPosY() + this.getHeight() - this.getHeight() * 5/18;
            case NORTE:
                return this.getPosY() + this.getHeight() * 1/10;
            case SUR:
                return this.getPosY() + this.getHeight() - this.getHeight() * 1/10;
            case OESTE:
                return this.getPosY() + this.getHeight() * 3/13;
        }
        return this.getPosY();
    }

    /**
     * Llama al padre y vibra
     */
    @Override
    public void damaged() {
        super.damaged(5);
        vibrator.vibrate(300);
    }
}

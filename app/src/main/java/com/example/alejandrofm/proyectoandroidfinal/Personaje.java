
package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;

public class Personaje {
    protected int posX, posY, velocidad, vida;
    protected Bitmap[] sprite = new Bitmap[20];
    protected Bitmap[] moveUp = new Bitmap[20];
    protected Bitmap[] moveDown = new Bitmap[20];
    protected Bitmap[] moveLeft = new Bitmap[20];
    protected Bitmap[] moveRight = new Bitmap[20];
    protected Bitmap[] idleUp = new Bitmap[20];
    protected Bitmap[] idleDown = new Bitmap[20];
    protected Bitmap[] idleLeft = new Bitmap[20];
    protected Bitmap[] idleRight = new Bitmap[20];
    protected Utils utils;
    protected Joystick.Direccion direccionAnterior;
    protected boolean move;
    protected boolean blEfectos;
    protected int anchoPantalla, altoPantalla;
    private int indiceFrame = 0;
    private int tmpCambioFrame = 60;
    private long tiempoActual;
    protected Context context;
    private long ultimaReprod;
    protected SoundPool efectos;
    protected int sonidoCaminar;
    final private int maximoSonidos = 1;

    public Personaje(int x, int y, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        this.posX = x;
        this.posY = y;
        this.context = context;
        this.blEfectos = efectos;
        utils = new Utils(context);
        tiempoActual = System.currentTimeMillis();
        direccionAnterior = Joystick.Direccion.ESTE;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        ultimaReprod = tiempoActual + 500;
        this.efectos = new SoundPool(maximoSonidos, AudioManager.STREAM_MUSIC, 0);
        sonidoCaminar = this.efectos.load(context, R.raw.caminando, 1);
    }

    public void dibujarPersonaje(Canvas c) {
        c.drawBitmap(sprite[indiceFrame], posX, posY, null);
        cambiaFrame();
    }

    public void cambiaFrame() {
        if (sprite != null) {
            if (System.currentTimeMillis() - tiempoActual > tmpCambioFrame) {
                tiempoActual = System.currentTimeMillis();
                indiceFrame++;
                if (indiceFrame > sprite.length -1) {
                    indiceFrame = 0;
                }
            }
        }
    }

    protected void sonidoCaminar() {
        if (Math.abs(tiempoActual - ultimaReprod) >= 500) {
            efectos.play(sonidoCaminar,0.2f,0.2f,1,0,1);
            ultimaReprod = System.currentTimeMillis();
        }
    }

    public void caminar(Joystick.Direccion direccion) {
        switch (direccion) {
            case NORTE:
                posY--;
                break;
            case SUR:
                posY++;
                break;
            case ESTE:
                posX++;
                break;
            case OESTE:
                posX--;
                break;
        }
    }

    public void rotar(Joystick.Direccion direccion) {
        rotarPersonaje(direccion);
        if (direccion != Joystick.Direccion.NINGUNA) {
            direccionAnterior = direccion;
        }
    }

//    protected Bitmap[] cargarSpriteProtagonista(String tipo, String arma) {
//        Bitmap bitmap;
//        Bitmap[] asset = new Bitmap[20];
//        for (int i = 0; i < 20; i++) {
//            bitmap = utils.getBitmapFromAssets("protagonista/" + arma +"/" + tipo +"/survivor-" + tipo + "_" + arma + "_" + i + ".png");
//            asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
//        }
//        return asset;
//    }

    protected Bitmap[] rotarSprite(Bitmap[] base, int grados) {
        Bitmap bitmap[] = new Bitmap[20];
        Matrix matrix = new Matrix();
        matrix.postRotate(grados);
        for (int i = 0; i < base.length; i++) {
            bitmap[i] = Bitmap.createBitmap(base[i], 0, 0, base[i].getWidth(), base[i].getHeight(), matrix, true);
        }
        return bitmap;
    }

    protected void rotarPersonaje(Joystick.Direccion direccion) {
        if (move) {
            switch (direccion) {
                case NORTE:
                    sprite = moveUp;
                    break;
                case SUR:
                    sprite = moveDown;
                    break;
                case OESTE:
                    sprite = moveLeft;
                    break;
                case ESTE:
                    sprite = moveRight;
                    break;
            }
        } else {
            switch (direccion) {
                case NORTE:
                    sprite = idleUp;
                    break;
                case SUR:
                    sprite = idleDown;
                    break;
                case OESTE:
                    sprite = idleLeft;
                    break;
                case ESTE:
                    sprite = idleRight;
                    break;
            }
        }
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

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Bitmap[] getSprite() {
        return sprite;
    }

    public void setSprite(Bitmap[] sprite) {
        this.sprite = sprite;
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

    public long getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public int getWidth() {
        return sprite[0].getWidth();
    }

    public int getHeight() {
        return sprite[0].getHeight();
    }
}

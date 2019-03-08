
package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;

public class Personaje {
    protected int posX, posY, velocidad, vida;
    protected Bitmap[] sprite;
    protected Bitmap[] moveUp;
    protected Bitmap[] moveDown;
    protected Bitmap[] moveLeft;
    protected Bitmap[] moveRight;
    protected Bitmap[] idleUp;
    protected Bitmap[] idleDown;
    protected Bitmap[] idleLeft;
    protected Bitmap[] idleRight;
    protected Rect hitbox;
    protected Utils utils;
    protected Joystick.Direccion direccionAnterior;
    protected boolean move;
    protected boolean blEfectos;
    protected int anchoPantalla, altoPantalla;
    protected int indiceFrame = 0;
    private int tmpCambioFrame = 60;
    protected long tiempoActual;
    protected Context context;
    private long ultimaReprod;
    protected SoundPool efectos;
    protected int sonidoCaminar;
    protected int sonidoPunch;
    final private int maximoSonidos = 10;

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
        sonidoCaminar = this.efectos.load(context, R.raw.caminando, 2);
        sonidoPunch = this.efectos.load(context, R.raw.punch, 1);
    }

    protected void actualizaHitBox() {
        if (sprite != null) {
            hitbox = new Rect((int)(getPosX() + 0.2 * sprite[0].getWidth()), (int)(getPosY() + 0.2 * sprite[0].getHeight()), (int)(getPosX() + 0.8 * sprite[0].getWidth()), (int)(getPosY() + 0.8 * sprite[0].getHeight()));
        }
    }

    public void dibujarPersonaje(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        c.drawBitmap(sprite[indiceFrame], posX, posY, null);
        if (hitbox != null) {
            c.drawRect(hitbox, p);
        }
        cambiaFrame();
    }

    public void cambiaFrame() {
//        if (velocidad > 0) {
//            tmpCambioFrame = tmpCambioFrame * velocidad;
//        }
//        if (tmpCambioFrame == 0) {
//            tmpCambioFrame = 60;
//        }
//        Log.i("cambio", tmpCambioFrame+"");
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
        if (blEfectos) {
            if (Math.abs(tiempoActual - ultimaReprod) >= 500) {
                efectos.play(sonidoCaminar,0.2f,0.2f,1,0,1);
                ultimaReprod = System.currentTimeMillis();
            }
        }
    }

    public void caminar(Joystick.Direccion direccion) {
//        move = true;
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
//            case NINGUNA:
//                move = false;
//                break;
        }
    }

    public void rotar(Joystick.Direccion direccion) {
        rotarPersonaje(direccion);
        if (direccion != Joystick.Direccion.NINGUNA) {
            direccionAnterior = direccion;
        }
    }

    protected Bitmap[] rotarSprite(Bitmap[] base, int grados) {
        Bitmap bitmap[] = new Bitmap[base.length];
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

    public void damaged() {
        vida--;
    }
}

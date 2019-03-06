package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class Protagonista extends Personaje {

    private Joystick jIzquierdo, jDerecho;
    private Vibrator vibrator;

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
    }

    protected Bitmap[] cargarSpriteProtagonista(String tipo, String arma) {
        Bitmap bitmap;
        Bitmap[] asset = new Bitmap[20];
        for (int i = 0; i < 20; i++) {
            bitmap = utils.getBitmapFromAssets("protagonista/" + arma +"/" + tipo +"/survivor-" + tipo + "_" + arma + "_" + i + ".png");
            asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
        }
        return asset;
    }

    public Joystick getjIzquierdo() {
        return jIzquierdo;
    }

    public void setjIzquierdo(Joystick jIzquierdo) {
        this.jIzquierdo = jIzquierdo;
    }

    public Joystick getjDerecho() {
        return jDerecho;
    }

    public void setjDerecho(Joystick jDerecho) {
        this.jDerecho = jDerecho;
    }

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

    @Override
    public void damaged() {
        super.damaged();
        vibrator.vibrate(300);
        Log.i("vida", this.vida+"");
    }
}

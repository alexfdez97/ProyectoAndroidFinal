package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Protagonista extends Personaje {

    private Joystick jIzquierdo, jDerecho;

    public Protagonista(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        super(x, y, anchoPantalla, altoPantalla, context);
        this.jIzquierdo = jIzquierdo;
        this.jDerecho = jDerecho;
        idleRight = cargarSpriteProtagonista("idle", "handgun");
        sprite = idleRight;
        idleUp = rotarSprite(idleRight, -90);
        idleDown = rotarSprite(idleRight, 90);
        idleLeft = rotarSprite(idleRight, 180);
        moveRight = cargarSpriteProtagonista("move", "handgun");
        moveUp = rotarSprite(moveRight, -90);
        moveDown = rotarSprite(moveRight, 90);
        moveLeft = rotarSprite(moveRight, 180);
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
                posY -= velocidad;
                break;
            case SUR:
                posY += velocidad;
                break;
            case ESTE:
                posX += velocidad;
                break;
            case OESTE:
                posX -= velocidad;
                break;
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
}

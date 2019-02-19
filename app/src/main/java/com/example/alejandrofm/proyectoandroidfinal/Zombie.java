package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Zombie extends Personaje {

    public Zombie(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        super(x, y, anchoPantalla, altoPantalla, context);
        idleRight = cargarSpriteZombie("idle");
        idleUp = rotarSprite(idleRight, -90);
        idleDown = rotarSprite(idleRight, 90);
        idleLeft = rotarSprite(idleRight, 180);
        moveRight = cargarSpriteZombie("move");
        moveUp = rotarSprite(moveRight, -90);
        moveDown = rotarSprite(moveRight, 90);
        moveLeft = rotarSprite(moveRight, 180);
    }

    @Override
    public void dibujarPersonaje(Canvas c) {
        super.dibujarPersonaje(c);
    }

    protected Bitmap[] cargarSpriteZombie(String tipo) {
        Bitmap bitmap;
        int cantidadBmps = 17;

        if (tipo.equals("attack")) {
            cantidadBmps = 9;
        }

        Bitmap[] asset = new Bitmap[cantidadBmps];

        for (int i = 0; i < cantidadBmps; i++) {
            bitmap = utils.getBitmapFromAssets("zombie/" + tipo +"/skeleton-" + tipo + "_" + i + ".png");
            asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
        }
        return asset;
    }
}

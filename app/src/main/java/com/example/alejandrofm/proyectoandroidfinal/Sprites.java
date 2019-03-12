package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * El tipo Sprites.
 */
public class Sprites {

    /**
     * El enum Tipo.
     */
    public enum Tipo {
        /**
         * Zombie tipo.
         */
        ZOMBIE
    }

    /**
     * Sprites
     */
    private Bitmap[] atackRigth = new Bitmap[9];
    /**
     * Sprites
     */
    private Bitmap[] atackLeft = new Bitmap[9];
    /**
     * Sprites
     */
    private Bitmap[] atackDown = new Bitmap[9];
    /**
     * Sprites
     */
    private Bitmap[] atackUp = new Bitmap[9];
    /**
     * Sprites
     */
    private Bitmap[] moveUp;
    /**
     * Sprites
     */
    private Bitmap[] moveDown;
    /**
     * Sprites
     */
    private Bitmap[] moveLeft;
    /**
     * Sprites
     */
    private Bitmap[] moveRight;
    /**
     * El ancho y el alto de la pantalla
     */
    private int anchoPantalla, altoPantalla;
    /**
     * Funciones utiles
     */
    private Utils utils;

    /**
     * Instancia un nuevo Sprite.
     *
     * @param tipo          el tipo
     * @param anchopantalla el anchopantalla
     * @param altopantalla  el altopantalla
     * @param context       el context
     */
    public Sprites(Tipo tipo, int anchopantalla, int altopantalla, Context context) {
        utils = new Utils(context);
        this.anchoPantalla = anchopantalla;
        this.altoPantalla = altopantalla;
        switch (tipo) {
            case ZOMBIE:
                cargarTipoZombie();
                break;
        }
    }

    /**
     * Carga los sprites del zombie
     */
    private void cargarTipoZombie() {
        moveLeft = new Bitmap[17];
        moveDown = new Bitmap[17];
        moveRight = new Bitmap[17];
        moveUp = new Bitmap[17];
        moveRight = cargarSpriteZombie("move");
        moveUp = rotarSprite(moveRight, -90);
        moveDown = rotarSprite(moveRight, 90);
        moveLeft = rotarSprite(moveRight, 180);
        atackRigth = cargarSpriteZombie("attack");
        atackUp = rotarSprite(atackRigth, -90);
        atackDown = rotarSprite(atackRigth, 90);
        atackLeft = rotarSprite(atackRigth, 180);
    }

    /**
     * Carga los sprites segun el tipo
     * @param tipo el tipo
     * @return los sprites [ ]
     */
    private Bitmap[] cargarSpriteZombie(String tipo) {
        Bitmap bitmap;
        int cantidadBmps = 17;

        if (tipo.equals("attack")) {
            cantidadBmps = 9;
        }

        Bitmap[] asset = new Bitmap[cantidadBmps];

        for (int i = 0; i < cantidadBmps; i++) {
            bitmap = utils.getBitmapFromAssets("zombie/" + tipo + "/skeleton-" + tipo + "_" + i + ".png");
            if (!tipo.equals("idle")) {
                asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1 / 8, altoPantalla * 1 / 4, false);
            } else {
                asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1 / 10, altoPantalla * 1 / 6, false);
            }
        }
        return asset;
    }

    /**
     * Rota los sprite
     * @param base el sprite que se rota
     * @param grados los grados que se rota
     * @return devuelve el sprite rotado
     */
    private Bitmap[] rotarSprite(Bitmap[] base, int grados) {
        Bitmap bitmap[] = new Bitmap[base.length];
        Matrix matrix = new Matrix();
        matrix.postRotate(grados);
        for (int i = 0; i < base.length; i++) {
            bitmap[i] = Bitmap.createBitmap(base[i], 0, 0, base[i].getWidth(), base[i].getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * Devuelve  el atack rigth bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getAtackRigth() {
        return atackRigth;
    }

    /**
     * Establece atack rigth.
     *
     * @param atackRigth el atack rigth
     */
    public void setAtackRigth(Bitmap[] atackRigth) {
        this.atackRigth = atackRigth;
    }

    /**
     * Devuelve atack left bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getAtackLeft() {
        return atackLeft;
    }

    /**
     * Establece atack left.
     *
     * @param atackLeft el atack left
     */
    public void setAtackLeft(Bitmap[] atackLeft) {
        this.atackLeft = atackLeft;
    }

    /**
     * Devuelve atack down bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getAtackDown() {
        return atackDown;
    }

    /**
     * Establece atack down.
     *
     * @param atackDown el atack down
     */
    public void setAtackDown(Bitmap[] atackDown) {
        this.atackDown = atackDown;
    }

    /**
     * Devuelve atack up bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getAtackUp() {
        return atackUp;
    }

    /**
     * Establece atack up.
     *
     * @param atackUp el atack up
     */
    public void setAtackUp(Bitmap[] atackUp) {
        this.atackUp = atackUp;
    }

    /**
     * Devuelve move up bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getMoveUp() {
        return moveUp;
    }

    /**
     * Establece move up.
     *
     * @param moveUp el move up
     */
    public void setMoveUp(Bitmap[] moveUp) {
        this.moveUp = moveUp;
    }

    /**
     * Devuelve move down bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getMoveDown() {
        return moveDown;
    }

    /**
     * Establece move down.
     *
     * @param moveDown el move down
     */
    public void setMoveDown(Bitmap[] moveDown) {
        this.moveDown = moveDown;
    }

    /**
     * Devuelve move left bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getMoveLeft() {
        return moveLeft;
    }

    /**
     * Establece move left.
     *
     * @param moveLeft el move left
     */
    public void setMoveLeft(Bitmap[] moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Devuelve move right bitmap [ ].
     *
     * @return el bitmap [ ]
     */
    public Bitmap[] getMoveRight() {
        return moveRight;
    }

    /**
     * Establece move right.
     *
     * @param moveRight el move right
     */
    public void setMoveRight(Bitmap[] moveRight) {
        this.moveRight = moveRight;
    }
}

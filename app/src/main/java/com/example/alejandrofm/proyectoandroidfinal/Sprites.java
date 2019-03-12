package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * The type Sprites.
 */
public class Sprites {

    /**
     * The enum Tipo.
     */
    public enum Tipo {
        /**
         * Zombie tipo.
         */
        ZOMBIE
    }

    private Bitmap[] atackRigth = new Bitmap[9];
    private Bitmap[] atackLeft = new Bitmap[9];
    private Bitmap[] atackDown = new Bitmap[9];
    private Bitmap[] atackUp = new Bitmap[9];
    private Bitmap[] moveUp;
    private Bitmap[] moveDown;
    private Bitmap[] moveLeft;
    private Bitmap[] moveRight;
    private int anchoPantalla, altoPantalla;
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
     * Get atack rigth bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getAtackRigth() {
        return atackRigth;
    }

    /**
     * Sets atack rigth.
     *
     * @param atackRigth the atack rigth
     */
    public void setAtackRigth(Bitmap[] atackRigth) {
        this.atackRigth = atackRigth;
    }

    /**
     * Get atack left bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getAtackLeft() {
        return atackLeft;
    }

    /**
     * Sets atack left.
     *
     * @param atackLeft the atack left
     */
    public void setAtackLeft(Bitmap[] atackLeft) {
        this.atackLeft = atackLeft;
    }

    /**
     * Get atack down bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getAtackDown() {
        return atackDown;
    }

    /**
     * Sets atack down.
     *
     * @param atackDown the atack down
     */
    public void setAtackDown(Bitmap[] atackDown) {
        this.atackDown = atackDown;
    }

    /**
     * Get atack up bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getAtackUp() {
        return atackUp;
    }

    /**
     * Sets atack up.
     *
     * @param atackUp the atack up
     */
    public void setAtackUp(Bitmap[] atackUp) {
        this.atackUp = atackUp;
    }

    /**
     * Get move up bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getMoveUp() {
        return moveUp;
    }

    /**
     * Sets move up.
     *
     * @param moveUp the move up
     */
    public void setMoveUp(Bitmap[] moveUp) {
        this.moveUp = moveUp;
    }

    /**
     * Get move down bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getMoveDown() {
        return moveDown;
    }

    /**
     * Sets move down.
     *
     * @param moveDown the move down
     */
    public void setMoveDown(Bitmap[] moveDown) {
        this.moveDown = moveDown;
    }

    /**
     * Get move left bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getMoveLeft() {
        return moveLeft;
    }

    /**
     * Sets move left.
     *
     * @param moveLeft the move left
     */
    public void setMoveLeft(Bitmap[] moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Get move right bitmap [ ].
     *
     * @return the bitmap [ ]
     */
    public Bitmap[] getMoveRight() {
        return moveRight;
    }

    /**
     * Sets move right.
     *
     * @param moveRight the move right
     */
    public void setMoveRight(Bitmap[] moveRight) {
        this.moveRight = moveRight;
    }
}

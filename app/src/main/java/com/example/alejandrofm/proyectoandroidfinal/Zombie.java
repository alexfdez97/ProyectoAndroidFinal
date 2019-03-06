package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Zombie extends Personaje {

    private int velocidadInicial;
    private int pasos = 0;
    private long ultimoGolpe;
    private Bitmap[] atackRigth = new Bitmap[9];
    private Bitmap[] atackLeft = new Bitmap[9];
    private Bitmap[] atackDown = new Bitmap[9];
    private Bitmap[] atackUp = new Bitmap[9];

    public Zombie(int x, int y, int velocidad, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        super(x, y, anchoPantalla, altoPantalla, efectos, context);
        this.velocidad = velocidad;
        this.velocidadInicial = velocidad;
        sprite = new Bitmap[17];
        idleRight = new Bitmap[17];
        idleDown = new Bitmap[17];
        idleLeft = new Bitmap[17];
        idleUp = new Bitmap[17];
        moveLeft = new Bitmap[17];
        moveDown = new Bitmap[17];
        moveRight = new Bitmap[17];
        moveUp = new Bitmap[17];
        idleRight = cargarSpriteZombie("idle");
        idleUp = rotarSprite(idleRight, -90);
        idleDown = rotarSprite(idleRight, 90);
        idleLeft = rotarSprite(idleRight, 180);
        moveRight = cargarSpriteZombie("move");
        moveUp = rotarSprite(moveRight, -90);
        moveDown = rotarSprite(moveRight, 90);
        moveLeft = rotarSprite(moveRight, 180);
        atackRigth = cargarSpriteZombie("attack");
        atackUp = rotarSprite(atackRigth, -90);
        atackDown = rotarSprite(atackRigth, 90);
        atackLeft = rotarSprite(atackRigth, 180);
        sprite = moveDown;
        ultimoGolpe = tiempoActual + 500;
    }

    @Override
    public void dibujarPersonaje(Canvas c) {
        super.dibujarPersonaje(c);
    }

    public void caminar(Protagonista protagonista) {
        if ((Math.abs(this.getPosX() - protagonista.getPosX()) < velocidad) || (Math.abs(this.getPosY() - protagonista.getPosY()) < velocidad)) {
            velocidad = 1;
        } else {
            velocidad = velocidadInicial;
        }

        pasos++;

        if (pasos < 100 && (this.getPosX() != protagonista.getPosX())) {
            if (this.getPosX() != protagonista.getPosX()) {
                if (this.getPosX() > protagonista.getPosX()) {
                    decrementaX();
                } else if (this.getPosX() < protagonista.getPosX()) {
                    incrementaX();
                }
            }
        } else if (this.getPosY() != protagonista.getPosY()) {
            if (this.getPosY() != protagonista.getPosY()) {
                if (this.getPosY() > protagonista.getPosY()) {
                    decrementaY();
                } else if (this.getPosY() < protagonista.getPosY()) {
                    incrementaY();
                }
            }
            if (pasos > 200) {
                pasos = 0;
            }
        } else if (this.getPosX() != protagonista.getPosX() || this.getPosY() != protagonista.getPosY()) {
            if (this.getPosX() > protagonista.getPosX()) {
                decrementaX();
            } else if (this.getPosX() < protagonista.getPosX()) {
                incrementaX();
            } else if (this.getPosY() > protagonista.getPosY()) {
                decrementaY();
            } else if (this.getPosY() < protagonista.getPosY()) {
                incrementaY();
            }
        } else {
            if (indiceFrame > 8) {
                indiceFrame = 0;
            }
            sprite = atackRigth;
            if (Math.abs(tiempoActual - ultimoGolpe) >= 500) {
                protagonista.damaged();
                if (blEfectos) {
                    efectos.play(sonidoPunch, 0.3f, 0.3f, 1, 0, 1);
                }
                ultimoGolpe = System.currentTimeMillis();
            }
        }
    }

    private void incrementaX() {
        posX += velocidad;
        sprite = moveRight;
        sonidoCaminar();
    }

    private void decrementaX() {
        posX -= velocidad;
        sprite = moveLeft;
        sonidoCaminar();
    }

    private void incrementaY() {
        posY += velocidad;
        sprite = moveDown;
        sonidoCaminar();
    }

    private void decrementaY() {
        posY -= velocidad;
        sprite = moveUp;
        sonidoCaminar();
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
            if (!tipo.equals("idle")) {
                asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/8, altoPantalla * 1/4, false);
            } else {
                asset[i] = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
            }
        }
        return asset;
    }
}

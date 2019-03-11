package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Zombie extends Personaje {

    private int velocidadInicial;
    private int pasos = 0;
    private long ultimoGolpe;
    private Bitmap[] atackRigth = new Bitmap[9];
    private Bitmap[] atackLeft = new Bitmap[9];
    private Bitmap[] atackDown = new Bitmap[9];
    private Bitmap[] atackUp = new Bitmap[9];
    private boolean cambioVelocidad = false;
    private boolean muriendo = false;
    private boolean murio = false;
    private int contParpadeo = 0;
    private long lastParpadeo;
    private Paint alphaPaint = new Paint();
    private boolean parpadeo = true;

    /**
     * Inicializa las propiedades de la clase y de su clase padre
     * @param x la coordenada X del Zombie
     * @param y la coordenada Y del Zombie
     * @param velocidad la velocidad del Zombie
     * @param vida la vida del Zombie
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos están activos
     * @param context el contexto de la aplicación
     */
    public Zombie(int x, int y, int velocidad, int vida, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        super(x, y, anchoPantalla, altoPantalla, efectos, context);
        this.velocidad = velocidad;
        this.velocidadInicial = velocidad;
        this.vida = vida;
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
        lastParpadeo = System.currentTimeMillis();
        alphaPaint.setAlpha(42);
        super.actualizaHitBox();
    }

    @Override
    public void dibujarPersonaje(Canvas c) {
        if (!muriendo) {
            super.dibujarPersonaje(c);
        } else {

            if (System.currentTimeMillis() - lastParpadeo > 500) {
                parpadeo = !parpadeo;
                lastParpadeo = System.currentTimeMillis();
                contParpadeo++;
                if (contParpadeo == 6) {
                    murio = true;
                }
            }
            if (parpadeo) {
                c.drawBitmap(sprite[indiceFrame], posX, posY, alphaPaint);
            } else {
                c.drawBitmap(sprite[indiceFrame], posX, posY, null);
            }
        }
    }

    /**
     * Hace que el zombie se mueva hacia el Protagonista
     * @see Protagonista
     * @param protagonista el Protagonista hacia el que se mueve
     */
    public void caminar(Protagonista protagonista) {
        if (blEfectos) {
            int rand = (int)Math.random() * 10;
            if (rand == 5) {
                efectos.play(sonidoZRising, 0.6f, 0.6f, 1, 0, 1);
            }
        }
        if (!this.muriendo) {
            Point pProta = new Point(protagonista.getPosX(), protagonista.getPosY());
            Point pZombie = new Point(this.getPosX(), this.getPosY());
//        if ((Math.abs(this.getPosX() - protagonista.getPosX()) < velocidad) || (Math.abs(this.getPosY() - protagonista.getPosY()) < velocidad)) {
//            velocidad = 1;
//        } else {
//            velocidad = velocidadInicial;
//        }

//        if () {
//
//        }

            if ((utils.dist(pProta, pZombie) - 1) < velocidad) {
                velocidad = 1;
            } else {
                velocidad = velocidadInicial;
            }
            pasos++;
//        if (pasos > 100) {
//            velocidad = 1;
//        } else if (pasos == 2) {
//            velocidad = 1;
//        } else {
//            velocidad = velocidadInicial;
//        }
            if (!Rect.intersects(this.hitbox, protagonista.getHitbox())) {
                if (pasos < 100) {
                    if (this.getPosX() != protagonista.getPosX() && this.getPosY() != protagonista.getPosY()) {
                        if (this.getPosX() < protagonista.getPosX()) {
                            incrementaX();
                        } else if (this.getPosX() > protagonista.getPosX()) {
                            decrementaX();
                        } else if (this.getPosY() < protagonista.getPosY()) {
                            incrementaY();
                        } else if (this.getPosY() > protagonista.getPosY()) {
                            decrementaY();
                        }
                    } else {
                        if (this.getPosX() != protagonista.getPosX()) {
                            if (this.getPosX() < protagonista.getPosX()) {
                                incrementaX();
                            } else if (this.getPosX() > protagonista.getPosX()) {
                                decrementaX();
                            }
                        } else if (this.getPosY() != protagonista.getPosY()) {
                            if (this.getPosY() < protagonista.getPosY()) {
                                incrementaY();
                            } else if (this.getPosY() > protagonista.getPosY()) {
                                decrementaY();
                            }
                        } else if (this.getPosX() == protagonista.getPosX() && this.getPosY() == protagonista.getPosY()) {
                            ataca(protagonista);
                        }
                    }
                } else {
                    if (this.getPosY() != protagonista.getPosY() && this.getPosX() != protagonista.getPosX()) {
                        if (this.getPosY() < protagonista.getPosY()) {
                            incrementaY();
                        } else if (this.getPosY() > protagonista.getPosY()) {
                            decrementaY();
                        } else if (this.getPosX() < protagonista.getPosX()) {
                            incrementaX();
                        } else if (this.getPosX() > protagonista.getPosX()) {
                            decrementaX();
                        }
                    } else {
                        if (this.getPosY() != protagonista.getPosY()) {
                            if (this.getPosY() < protagonista.getPosY()) {
                                incrementaY();
                            } else if (this.getPosY() > protagonista.getPosY()) {
                                decrementaY();
                            }
                        } else if (this.getPosX() != protagonista.getPosX()) {
                            if (this.getPosX() < protagonista.getPosX()) {
                                incrementaX();
                            } else if (this.getPosX() > protagonista.getPosX()) {
                                decrementaX();
                            }
                        } else if (this.getPosX() == protagonista.getPosX() && this.getPosY() == protagonista.getPosY()) {
                            ataca(protagonista);
                        }
                    }
                    if (pasos > 200) {
                        pasos = 0;
                    }
                }
            } else {
                ataca(protagonista);
            }
            super.actualizaHitBox();
        }
        Log.i("velocidad", this.velocidad+"");
    }

    /**
     * Hace que el Zombie ataque al Protagonista
     * @see Protagonista
     * @param protagonista el Protagonista al que ataca
     */
    private void ataca(Protagonista protagonista) {
        if (indiceFrame > 8) {
                indiceFrame = 0;
            }
            if (sprite == moveRight) {
                sprite = atackRigth;
            } else if (sprite == moveUp) {
                sprite = atackUp;
            } else if (sprite == moveDown) {
                sprite = atackDown;
            } else if (sprite == moveLeft) {
                sprite = atackLeft;
            }
            if (Math.abs(tiempoActual - ultimoGolpe) >= 500) {
                protagonista.damaged();
                if (blEfectos) {
                    efectos.play(sonidoPunch, 0.3f, 0.3f, 1, 0, 1);
                }
                ultimoGolpe = System.currentTimeMillis();
            }
    }

    /**
     * Incrementa la X del Zombie y cambia el sprite
     */
    private void incrementaX() {
        posX += velocidad;
        sprite = moveRight;
        sonidoCaminar();
    }

    /**
     * Decrementa la X del Zombie y cambia el sprite
     */
    private void decrementaX() {
        posX -= velocidad;
        sprite = moveLeft;
        sonidoCaminar();
    }

    /**
     * Incrementa la Y del Zombie y cambia el sprite
     */
    private void incrementaY() {
        posY += velocidad;
        sprite = moveDown;
        sonidoCaminar();
    }

    /**
     * Decrementa la Y del Zombie y cambia el sprite
     */
    private void decrementaY() {
        posY -= velocidad;
        sprite = moveUp;
        sonidoCaminar();
    }

    /**
     * Carga el bitmap según el tipo específicado
     * @param tipo el tipo de imagen a cargar
     * @return el array de Bitmaps de ese tipo
     */
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

    @Override
    public void damaged() {
        super.damaged();
        efectos.play(sonidoZPain,0.2f,0.2f,1,0,1);
    }

    public boolean isMuriendo() {
        return muriendo;
    }

    public void setMuriendo(boolean muriendo) {
        this.muriendo = muriendo;
    }

    public boolean isMurio() {
        return murio;
    }

    public void setMurio(boolean murio) {
        this.murio = murio;
    }
}

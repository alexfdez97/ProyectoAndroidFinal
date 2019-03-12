package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/**
 * Clase zombie
 */
public class Zombie extends Personaje {

    /**
     * La velocidad inicial
     */
    private int velocidadInicial;
    /**
     * Cantida de pasos
     */
    private int pasos = 0;
    /**
     * Tiempo del ultimo golpe
     */
    private long ultimoGolpe;
    /**
     * Bitmap de ataque derecho
     */
    private Bitmap[] atackRigth = new Bitmap[9];
    /**
     * Bitmap de ataque izquierdo
     */
    private Bitmap[] atackLeft = new Bitmap[9];
    /**
     * Bitmap de ataque abajo
     */
    private Bitmap[] atackDown = new Bitmap[9];
    /**
     * Bitmap de ataque arriba
     */
    private Bitmap[] atackUp = new Bitmap[9];
    /**
     * Si esta muriendo
     */
    private boolean muriendo = false;
    /**
     * Si se murio
     */
    private boolean murio = false;
    /**
     * Cuenta las veces que parpadea
     */
    private int contParpadeo = 0;
    /**
     * Tiempo de la ultima vez que parpadeo
     */
    private long lastParpadeo;
    /**
     * Tiempo del ultimo gole
     */
    private long lastHit = 0;
    /**
     * Tiempo del ultimo rugido
     */
    private long lastRise = 0;
    /**
     * El alpha
     */
    private Paint alphaPaint = new Paint();
    /**
     * Si parpadea
     */
    private boolean parpadeo = true;

    /**
     * Inicializa las propiedades de la clase y de su clase padre
     * @param x la coordenada X del Zombie
     * @param y la coordenada Y del Zombie
     * @param velocidad la velocidad del Zombie
     * @param vida la vida del Zombie
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos estan activos
     * @param context el contexto de la aplicacion
     */
    public Zombie(int x, int y, int velocidad, int vida, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        super(x, y, anchoPantalla, altoPantalla, efectos, context);
        this.velocidad = velocidad;
        this.velocidadInicial = velocidad;
        this.vida = vida;
        sprite = new Bitmap[17];
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
        inicializadorComun();
    }

    /**
     * Inicializa Zombie
     * @param x la posicion X
     * @param y las posicion Y
     * @param velocidad la velocidad
     * @param vida la vida
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos los efectos
     * @param context el contexto
     * @param sprites los sprites
     */
    public Zombie(int x, int y, int velocidad, int vida, int anchoPantalla, int altoPantalla, boolean efectos, Context context, Sprites sprites) {
        super(x, y, anchoPantalla, altoPantalla, efectos, context);
        this.velocidad = velocidad;
        this.velocidadInicial = velocidad;
        this.vida = vida;
        moveLeft = sprites.getMoveLeft();
        moveRight = sprites.getMoveRight();
        moveUp = sprites.getMoveUp();
        moveDown = sprites.getMoveDown();
        atackLeft = sprites.getAtackLeft();
        atackDown = sprites.getAtackDown();
        atackRigth = sprites.getAtackRigth();
        atackUp = sprites.getAtackUp();
        inicializadorComun();
    }

    /**
     * Se llama en ambos constructores, inicializa
     */
    private void inicializadorComun() {
        sprite = moveRight;
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
            if (Math.abs(tiempoActual - lastRise) > 3000) {
                if (Math.random() > 0.8) {
                    efectos.play(sonidoZRising, 0.6f, 0.6f, 1, 0, 1);
                }
                lastRise = System.currentTimeMillis();
            }
        }
        if (!this.muriendo) {
            Point pProta = new Point(protagonista.getPosX(), protagonista.getPosY());
            Point pZombie = new Point(this.getPosX(), this.getPosY());

            if ((utils.dist(pProta, pZombie) - 1) < velocidad) {
                velocidad = 1;
            } else {
                velocidad = velocidadInicial;
            }
            boolean diferenciaX = Math.abs(this.getPosX() - protagonista.getPosX()) < velocidadInicial;
            boolean diferenciaY = Math.abs(this.getPosY() - protagonista.getPosY()) < velocidadInicial;
            pasos++;
            if (!Rect.intersects(this.hitbox, protagonista.getHitbox())) {
                if (diferenciaX ) {
                    if (this.getPosY() < protagonista.getPosY()) {
                        incrementaY();
                    } else if (this.getPosY() > protagonista.getPosY()) {
                        decrementaY();
                    }
                } else if (diferenciaY) {
                    if (this.getPosX() < protagonista.getPosX()) {
                        incrementaX();
                    } else if (this.getPosX() > protagonista.getPosX()) {
                        decrementaX();
                    }
                }
                else if (pasos < 100) {
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
     * Carga el bitmap segun el tipo especificado
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

    /**
     * Devuelve si esta muriendo
     * @return si esta muriendo
     */
    public boolean isMuriendo() {
        return muriendo;
    }

    /**
     * Establece si esta muriendo
     * @param muriendo si esta muriendo
     */
    public void setMuriendo(boolean muriendo) {
        this.muriendo = muriendo;
    }

    /**
     * Devuelve si se murio
     * @return si se murio
     */
    public boolean isMurio() {
        return murio;
    }

    /**
     * Establace si se murio
     * @param murio si se murio
     */
    public void setMurio(boolean murio) {
        this.murio = murio;
    }

    /**
     * Devuelve el ultimo golpe
     * @return el ultimo golpe
     */
    public long getLastHit() {
        return lastHit;
    }

    /**
     * Establace el ultimo golpe
     * @param lastHit el ultimo golpe
     */
    public void setLastHit(long lastHit) {
        this.lastHit = lastHit;
    }
}

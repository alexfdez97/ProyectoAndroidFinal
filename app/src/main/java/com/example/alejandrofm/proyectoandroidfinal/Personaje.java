
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

/**
 * Clase Personaje
 */
public class Personaje {
    /**
     * Posicion X, Y y velocidad y vida
     */
    protected int posX, posY, velocidad, vida;
    /**
     * Los sprites
     */
    protected Bitmap[] sprite;
    /**
     * Los sprites
     */
    protected Bitmap[] moveUp;
    /**
     * Los sprites
     */
    protected Bitmap[] moveDown;
    /**
     * Los sprites
     */
    protected Bitmap[] moveLeft;
    /**
     * Los sprites
     */
    protected Bitmap[] moveRight;
    /**
     * Los sprites
     */
    protected Bitmap[] idleUp;
    /**
     * Los sprites
     */
    protected Bitmap[] idleDown;
    /**
     * Los sprites
     */
    protected Bitmap[] idleLeft;
    /**
     * Los sprites
     */
    protected Bitmap[] idleRight;
    /**
     * La hitbox
     */
    protected Rect hitbox;
    /**
     * Funciones utiles
     */
    protected Utils utils;
    /**
     * La direccion anterior
     */
    protected Joystick.Direccion direccionAnterior;
    /**
     * Indica si se esta moviendo
     */
    protected boolean move;
    /**
     * Indica si los efectos estan activos
     */
    protected boolean blEfectos;
    /**
     * El ancho y el alto de la pantalla
     */
    protected int anchoPantalla, altoPantalla;
    /**
     * El indice del frame
     */
    protected int indiceFrame = 0;
    /**
     * El tiempo de cambio frame
     */
    private int tmpCambioFrame = 60;
    /**
     * El tiempo actual
     */
    protected long tiempoActual;
    /**
     * El ultimo golpe
     */
    protected long lastGolpe;
    /**
     * El contexto de la aplicacion
     */
    protected Context context;
    /**
     * La ultima reproduccion
     */
    private long ultimaReprod;
    /**
     * El SoundPool
     */
    protected SoundPool efectos;
    /**
     * Sonido de caminar
     */
    protected int sonidoCaminar;
    /**
     * Sonido de puñetazo
     */
    protected int sonidoPunch;
    /**
     * Sonido de dolor zombie
     */
    protected int sonidoZPain;
    /**
     * Sonido de rugido zombie
     */
    protected int sonidoZRising;
    /**
     * Maximo de sonidos
     */
    final private int maximoSonidos = 20;

    /**
     * Inicializa las propiedades de la clase
     * @param x la coordenada X
     * @param y la coordenada Y
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos estan activados o no
     * @param context el contexto de la aplicacion
     */
    public Personaje(int x, int y, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        this.posX = x;
        this.posY = y;
        this.context = context;
        this.blEfectos = efectos;
        utils = new Utils(context);
        tiempoActual = System.currentTimeMillis();
        lastGolpe = tiempoActual + 200;
        direccionAnterior = Joystick.Direccion.ESTE;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        ultimaReprod = tiempoActual + 500;
        this.efectos = new SoundPool(maximoSonidos, AudioManager.STREAM_MUSIC, 0);
        sonidoCaminar = this.efectos.load(context, R.raw.caminando, 1);
        sonidoPunch = this.efectos.load(context, R.raw.punch, 1);
        sonidoZPain = this.efectos.load(context, R.raw.zombie_pain, 1);
        sonidoZRising = this.efectos.load(context, R.raw.zombie_rising, 1);
    }

    /**
     * Actualiza la hitbox del personaje
     */
    protected void actualizaHitBox() {
        if (sprite != null) {
            hitbox = new Rect((int)(getPosX() + 0.2 * sprite[0].getWidth()), (int)(getPosY() + 0.2 * sprite[0].getHeight()), (int)(getPosX() + 0.8 * sprite[0].getWidth()), (int)(getPosY() + 0.8 * sprite[0].getHeight()));
        }
    }

    /**
     * Dibuja el Personaje en el Canvas
     * @param c el Canvas
     */
    public void dibujarPersonaje(Canvas c) {
        c.drawBitmap(sprite[indiceFrame], posX, posY, null);
        cambiaFrame();
    }

    /**
     * Cambia el frame del Personaje
     */
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

    /**
     * Reproduce el efecto de sonido de caminar si los efectos estan activados
     */
    protected void sonidoCaminar() {
        if (blEfectos) {
            if (Math.abs(tiempoActual - ultimaReprod) >= 500) {
                efectos.play(sonidoCaminar,0.2f,0.2f,1,0,1);
                ultimaReprod = System.currentTimeMillis();
            }
        }
    }

    /**
     * Caminar basico en una direccion
     * @param direccion la direccion
     */
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

    /**
     * Rota el personaje en la direccion indicada
     * Realiza mas comprobaciones que 'rotarPersonaje'
     * @param direccion la direccion
     */
    public void rotar(Joystick.Direccion direccion) {
        rotarPersonaje(direccion);
        if (direccion != Joystick.Direccion.NINGUNA) {
            direccionAnterior = direccion;
        }
    }

    /**
     * Rota un array de bitmap los grados indicados y lo devuelve
     * @param base el Bitmap a rotar
     * @param grados los grados que se rota
     * @return el Bitmap rotado
     */
    protected Bitmap[] rotarSprite(Bitmap[] base, int grados) {
        Bitmap bitmap[] = new Bitmap[base.length];
        Matrix matrix = new Matrix();
        matrix.postRotate(grados);
        for (int i = 0; i < base.length; i++) {
            bitmap[i] = Bitmap.createBitmap(base[i], 0, 0, base[i].getWidth(), base[i].getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * Rota el personaje en la direccion indicada
     * @param direccion la direccion
     */
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

    /**
     * Devuelve la coordenada X del Personaje
     * @return la coordenada X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Establece la coordenada X del Personaje
     * @param posX la coordenada X
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Devuelve la coordenada Y del Personaje
     * @return la coordenada Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Establece la coordenada Y del Personaje
     * @param posY la coordenada Y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Devuelve la velocidad a la que se mueve el Personaje
     * @return la velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad a la que se mueve
     * @param velocidad la velocidad
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Devulve la vida del Personaje
     * @return la vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece la vida del Personaje
     * @param vida la vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Devuelve el ancho de la pantalla
     * @return el ancho de la pantalla
     */
    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    /**
     * Establece el ancho de la pantalla
     * @param anchoPantalla el ancho de la pantalla
     */
    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    /**
     * Devuelve el alto de la pantalla
     * @return el alto de la pantalla
     */
    public int getAltoPantalla() {
        return altoPantalla;
    }

    /**
     * Establece el alto de la pantalla
     * @param altoPantalla el alto
     */
    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    /**
     * Devuelve el ancho del sprite
     * @return
     */
    public int getWidth() {
        return sprite[0].getWidth();
    }

    /**
     * Devuelve el alto del sprite
     * @return
     */
    public int getHeight() {
        return sprite[0].getHeight();
    }

    /**
     * Devuelve la hitbox
     * @return
     */
    public Rect getHitbox() {
        return hitbox;
    }

    /**
     * Si el ultimo golpe a sido hace mas de 200ms daña al Personaje
     */
    public void damaged() {
        if (Math.abs(tiempoActual - lastGolpe) >= 200) {
            vida--;
            lastGolpe = System.currentTimeMillis();
        }
    }

    /**
     * Igual que el anterior pero daña una cantidad
     * @param daño la cantidad
     */
    public void damaged(int daño) {
        if (Math.abs(tiempoActual - lastGolpe) >= 200) {
            vida -= daño;
            lastGolpe = System.currentTimeMillis();
        }
    }

    /**
     * Devuelve el contexto
     * @return el contexto
     */
    public Context getContext() {
        return context;
    }
}

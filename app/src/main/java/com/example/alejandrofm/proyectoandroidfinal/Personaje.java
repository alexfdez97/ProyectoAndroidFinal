
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
    protected long lastGolpe;
    protected Context context;
    private long ultimaReprod;
    protected SoundPool efectos;
    protected int sonidoCaminar;
    protected int sonidoPunch;
    protected int sonidoZPain;
    protected int sonidoZRising;
    final private int maximoSonidos = 10;

    /**
     * Inicializa las propiedades de la clase
     * @param x la coordenada X
     * @param y la coordenada Y
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos están activados o no
     * @param context el contexto de la aplicación
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
        sonidoCaminar = this.efectos.load(context, R.raw.caminando, 2);
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
//        Paint p = new Paint();
//        p.setColor(Color.RED);
//        p.setStyle(Paint.Style.STROKE);
//        p.setStrokeWidth(5);
        c.drawBitmap(sprite[indiceFrame], posX, posY, null);
//        if (hitbox != null) {
//            c.drawRect(hitbox, p);
//        }
        cambiaFrame();
    }

    /**
     * Cambia el frame del Personaje
     */
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

    /**
     * Reproduce el efecto de sonido de caminar si los efectos están activados
     */
    protected void sonidoCaminar() {
        if (blEfectos) {
            if (Math.abs(tiempoActual - ultimaReprod) >= 500) {
                efectos.play(sonidoCaminar,0.2f,0.2f,1,0,1);
                ultimaReprod = System.currentTimeMillis();
            }
        }
    }

    @Deprecated
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

    /**
     * Rota el personaje en la direccion indicada
     * Realiza más comprobaciones que 'rotarPersonaje'
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
     * Si el último golpe a sido hace más de 200ms daña al Personaje
     */
    public void damaged() {
        if (Math.abs(tiempoActual - lastGolpe) >= 200) {
            vida--;
            lastGolpe = System.currentTimeMillis();
        }
    }

    public Context getContext() {
        return context;
    }
}

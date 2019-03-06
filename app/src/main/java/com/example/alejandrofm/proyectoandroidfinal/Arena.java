package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class Arena extends Escena {

    private ArrayList<Puntero> punteros = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Utils utils;
    private Joystick jIzquierdo, jDerecho;
    private Protagonista protagonista;
    private Mapa mapa;
    private Texto txtHealth, txtPuntuation, txtPointsCounter;
    private HealthBar hBar;
    private Bitmap fogonazos[] = new Bitmap[4];
    private int puntuation = 0;
    private ArrayList<Bala> balas = new ArrayList<>();
    private long currentTime, lastBala;
    private Context context;
    private boolean fuego = false;
    private Matrix matrix = new Matrix();

    /**
     * Constructor de Arena, declara el Mapa y el Protagonista
     * @param anchoPantalla es el ancho de la pantalla del dispositivo
     * @param altoPantalla es el alto de la pantalla del dispositivo
     * @param context es el contexto de la aplicación
     * @param idEscena es el id de la Escena actual
     */
    public Arena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        this.context = context;
        utils = new Utils(context);
        mapa = new Mapa(context, anchoPantalla, altoPantalla);
        protagonista = new Protagonista(anchoPantalla / 2, altoPantalla / 2, anchoPantalla, altoPantalla, efectos, context);
        txtHealth = new Texto(context.getString(R.string.strHealth), anchoPantalla, altoPantalla, context);
        txtPuntuation = new Texto(context.getString(R.string.strPuntuation), anchoPantalla, altoPantalla, context);
        txtPointsCounter = new Texto(String.format("%06d", puntuation), anchoPantalla, altoPantalla, context);
        hBar = new HealthBar(100, anchoPantalla * 2/4, altoPantalla * 2/4, context);
        generarZombies(protagonista, 1);
        cargaFogonazos();
        currentTime = System.currentTimeMillis();
        lastBala = currentTime + 2000;
    }

    /**
     * Gestiona los eventos OnTouch en esta escena, declara y manda parámetros a los joystick en los que se basan los controles del juego.
     * @param event el evento que se le pasa desde la clase principal
     * @return Siempre devuelve -1 porque no cambia la escena
     */
    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        float x = event.getX(event.getActionIndex());
        float y = event.getY(event.getActionIndex());
        int id = event.getPointerId(event.getActionIndex());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                if (x < getAnchoPantalla() / 2) {
                    jIzquierdo = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                    jIzquierdo.setIdPuntero(id);
                    jIzquierdo.setPulsado(true);
                    protagonista.setjIzquierdo(jIzquierdo);
                } else {
                    jDerecho = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                    jDerecho.setIdPuntero(id);
                    jDerecho.setPulsado(true);
                    protagonista.setjDerecho(jDerecho);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (jIzquierdo != null && jIzquierdo.getIdPuntero() == id) {
                    jIzquierdo.setPulsado(false);
                    jIzquierdo = null;
                }
                if (jDerecho != null && jDerecho.getIdPuntero() == id) {
                    jDerecho.setPulsado(false);
                    jDerecho = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if (jIzquierdo != null && jIzquierdo.getIdPuntero() == i) {
                        jIzquierdo.setCoordsJFlechas(event.getX(i), event.getY(i));
                    }
                    if (jDerecho != null && jDerecho.getIdPuntero() == i) {
                        jDerecho.setCoordsJFlechas(event.getX(i), event.getY(i));
                        if (jDerecho.getDireccion() != Joystick.Direccion.NINGUNA) {
                            if (Math.abs(lastBala - currentTime) >= 1000) {
                                balas.add(new Bala(Bala.TipoMunicion.PISTOLA, jDerecho.getDireccion(), protagonista.getArmaX(), protagonista.getArmaY(), anchoPantalla, altoPantalla, context));
                                if (efectos) {
                                    soundPool.play(efectoDisparo, 1, 1, 1, 0 ,1);
                                }
                                lastBala = System.currentTimeMillis();
                                fuego = true;
                            }
                        }
                        currentTime = System.currentTimeMillis();
                    }
                }
            break;
        }
        return -1;
    }

    private void inicioRonda(Protagonista protagonista) {

    }

    private void generarZombies(Protagonista protagonista, int cantidad) {
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int velocidad = random.nextInt(4 + 1 - 1) + 1;
            int posX = random.nextInt(anchoPantalla + 500 - anchoPantalla) + anchoPantalla;
            int posY = random.nextInt(altoPantalla + 500 - altoPantalla) + altoPantalla;
            zombies.add(new Zombie(10, 10, velocidad, anchoPantalla, altoPantalla, efectos, context));
        }
    }

    /**
     * Dibuja en el Canvas
     * @param c es el canvas
     */
    @Override
    public void dibujar(Canvas c) {
        try  {
            c.drawColor(Color.BLACK);
            mapa.dibujaMapa(c);
            dibujaBalas(c);
            if (fuego) {
                dibujaFogonazo(c);
                fuego = false;
            }
            protagonista.dibujarPersonaje(c);
            txtHealth.dibujarTexto(anchoPantalla * 1/80, altoPantalla * 1/80, c);
            hBar.dibujaBar(txtHealth.getWidth() + txtHealth.getWidth() * 1/4, altoPantalla * 1/80 , c);
            txtPuntuation.dibujarTexto(hBar.getX() + hBar.getWidth() + anchoPantalla * 1/20, altoPantalla * 1/80, c);
            txtPointsCounter.dibujarTexto(txtPuntuation.getX() + txtPuntuation.getWidth() + anchoPantalla * 1/80, altoPantalla * 1/80, c);
            dibujaZombies(c);
            if (jIzquierdo != null) {
                if (jIzquierdo.isPulsado()) {
                    jIzquierdo.dibujaJoystick(c);
                }
            }
            if (jDerecho != null) {
                if (jDerecho.isPulsado()) {
                    jDerecho.dibujaJoystick(c);
                }
            }
        } catch (NullPointerException ex) { }
    }

    @Override
    public void actualizarFisica() {
        try  {
//            puntuation++;
//            txtPointsCounter.setTexto(String.format("%06d", puntuation));
            for (Bala bala:balas) {
                bala.mueveBala();
            }
            mueveZombies(protagonista);
            hBar.setVida(protagonista.getVida());
//            compruebaBalas();
        } catch (NullPointerException ex) { }
    }

    private void dibujaZombies(Canvas c) {
        for (Zombie zombie:zombies) {
            zombie.dibujarPersonaje(c);
        }
    }

    private void mueveZombies(Protagonista protagonista) {
        for (Zombie zombie:zombies) {
            zombie.caminar(protagonista);
        }
    }

    private void dibujaFogonazo(Canvas c) {
        if (jDerecho != null) {
            switch (jDerecho.getDireccion()) {
                case ESTE:
                    c.drawBitmap(fogonazos[0], protagonista.getArmaX(), protagonista.getArmaY() - fogonazos[0].getHeight() / 2, null);
                    break;
                case OESTE:
                    c.drawBitmap(fogonazos[3], protagonista.getArmaX() - fogonazos[3].getWidth(), protagonista.getArmaY() - fogonazos[3].getHeight() / 2, null);
                    break;
                case NORTE:
                    c.drawBitmap(fogonazos[2], protagonista.getArmaX() - fogonazos[2].getWidth() / 2, protagonista.getArmaY() - fogonazos[2].getHeight(), null);
                    break;
                case SUR:
                    c.drawBitmap(fogonazos[1], protagonista.getArmaX() - fogonazos[1].getWidth() /  2, protagonista.getArmaY(), null);
                    break;
            }
        }
    }

    private void cargaFogonazos() {
        int grados = 0;
        for (int i = 0; i < fogonazos.length; i++) {
            fogonazos[i] = utils.getBitmapFromAssets("efectos/fogonazo.png");
            fogonazos[i] = Bitmap.createScaledBitmap(fogonazos[i], altoPantalla * 1/10, altoPantalla * 1/10, false);
            matrix.postRotate(grados);
            fogonazos[i] = Bitmap.createBitmap(fogonazos[i], 0, 0, fogonazos[i].getWidth(), fogonazos[i].getHeight(), matrix, true);
            grados += 90;
        }
    }

    private void dibujaBalas(Canvas c) {
        for (Bala bala:balas) {
            bala.dibujarBala(c);
        }
    }

    private void compruebaBalas() {
        for (Bala bala : balas) {
            if (bala.getX() > anchoPantalla) {
                balas.remove(bala);
            } else if (bala.getX() < anchoPantalla + bala.getWidth()) {
                balas.remove(bala);
            } else if (bala.getY() > altoPantalla) {
                balas.remove(bala);
            } else if (bala.getY() < altoPantalla + bala.getHeight()) {
                balas.remove(bala);
            }
        }
    }
}

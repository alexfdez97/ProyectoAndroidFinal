package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Arena extends Escena {

    private ArrayList<Joystick> joysticksActivos = new ArrayList<>();
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Utils utils;
    private Joystick jIzquierdo, jDerecho;
    private Protagonista protagonista;
    private Mapa mapa;
    private Texto txtHealth, txtPuntuation;
    private HealthBar hBar;

    /**
     * Constructor de Arena, declara el Mapa y el Protagonista
     * @param anchoPantalla es el ancho de la pantalla del dispositivo
     * @param altoPantalla es el alto de la pantalla del dispositivo
     * @param context es el contexto de la aplicación
     * @param idEscena es el id de la Escena actual
     */
    public Arena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        mapa = new Mapa(context, anchoPantalla, altoPantalla);
        protagonista = new Protagonista(anchoPantalla / 2, altoPantalla / 2, anchoPantalla, altoPantalla, context);
        txtHealth = new Texto(context.getString(R.string.strHealth), anchoPantalla * 2/4, altoPantalla * 2/4, context);
        txtPuntuation = new Texto(context.getString(R.string.strPuntuation), anchoPantalla * 2/4, altoPantalla * 2/4, context);
        hBar = new HealthBar(100, anchoPantalla * 2/4, altoPantalla * 2/4, context);

        zombies.add(new Zombie(30 , 30, anchoPantalla, altoPantalla, context));
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
                    joysticksActivos.add(jIzquierdo);
                    protagonista.setjIzquierdo(jIzquierdo);
                } else {
                    jDerecho = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                    jDerecho.setIdPuntero(id);
                    jDerecho.setPulsado(true);
                    joysticksActivos.add(jDerecho);
                    protagonista.setjDerecho(jDerecho);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (jIzquierdo != null && jIzquierdo.getIdPuntero() == id) {
                    jIzquierdo.setPulsado(false);
                    joysticksActivos.remove(jDerecho);
                    jIzquierdo = null;
                }
                if (jDerecho != null && jDerecho.getIdPuntero() == id) {
                    jDerecho.setPulsado(false);
                    joysticksActivos.remove(jDerecho);
                    jDerecho = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                if (event.getPointerCount() == 1) {
//                    if (jIzquierdo != null) {
//                        if (event.getPointerId(event.getActionIndex()) == jIzquierdo.getIdPuntero()) {
//                            jIzquierdo.setCoordsJFlechas(event.getX(jIzquierdo.getIdPuntero()), event.getY(jIzquierdo.getIdPuntero()));
//                        }
//                    }
//
//                    if (jDerecho != null) {
//                        if (event.getPointerId(event.getActionIndex()) == jDerecho.getIdPuntero()) {
//                            jDerecho.setCoordsJFlechas(event.getX(jDerecho.getIdPuntero()), event.getY(jDerecho.getIdPuntero()));
//                        }
//                    }
//                } else {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (jIzquierdo != null && jIzquierdo.getIdPuntero() == i) {
                            jIzquierdo.setCoordsJFlechas(event.getX(i), event.getY(i));
                        }
                        if (jDerecho != null && jDerecho.getIdPuntero() == i) {
                            jDerecho.setCoordsJFlechas(event.getX(i), event.getY(i));
                        }
                    }
//                }
                break;
        }
        return -1;
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
            protagonista.dibujarPersonaje(c);
            txtHealth.dibujarTexto(anchoPantalla * 1/80, altoPantalla * 1/80, c);
            hBar.dibujaBar(txtHealth.getWidth() + txtHealth.getWidth() * 1/4, altoPantalla * 1/80 , c);
//            txtPuntuation.dibujarTexto();
//            dibujaZombies(c);
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

    private void dibujaZombies(Canvas c) {
        for (Zombie zombie:zombies) {
            zombie.dibujarPersonaje(c);
        }
    }
}

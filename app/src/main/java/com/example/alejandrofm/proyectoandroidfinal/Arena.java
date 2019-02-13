package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

public class Arena extends Escena {

    private Utils utils;
    private Joystick jIzquierdo, jDerecho;
    private Protagonista protagonista;
    private Mapa mapa;

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
        event.getX(event.getActionIndex());
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
                    }
                }
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
}

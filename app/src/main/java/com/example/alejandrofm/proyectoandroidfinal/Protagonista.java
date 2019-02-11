package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Protagonista extends Personaje {

    private Joystick jIzquierdo, jDerecho;

    public Protagonista(int x, int y, int anchoPantalla, int altoPantalla, Context context) {
        super(x, y, anchoPantalla, altoPantalla, context);
        this.jIzquierdo = jIzquierdo;
        this.jDerecho = jDerecho;
        sprite = new Bitmap[20];
        for (int i = 0; i < 20; i++) {
            Bitmap bitmap = utils.getBitmapFromAssets("protagonista/handgun/idle/survivor-idle_handgun_" + i + ".png");
            bitmap = Bitmap.createScaledBitmap(bitmap, anchoPantalla * 1/10, altoPantalla * 1/6, false);
            sprite[i] = bitmap;
        }
    }

    @Override
    public void dibujarPersonaje(Canvas c) {
        super.dibujarPersonaje(c);
        if (jIzquierdo != null) {
            if (jIzquierdo.isPulsado()) {
                caminar(jIzquierdo.getDireccion());
            }
        }
        if (jDerecho != null) {
            if (jDerecho.isPulsado()) {
                rotar(jDerecho.getDireccion());
            }
        }
    }

    public void caminar(Joystick.Direccion direccion) {
        float velocidad = jIzquierdo.getDesplazamiento() / 20;
        Log.i("velocidad", "velocidad: " + velocidad);
        if (velocidad > 4.5) {
            velocidad = (float)4.5;
        }
        Log.i("velocidad", "velocidad: " + velocidad);
        switch (direccion) {
            case NORTE:
                posY -= velocidad;
                break;
            case SUR:
                posY += velocidad;
                break;
            case ESTE:
                posX += velocidad;
                break;
            case OESTE:
                posX -= velocidad;
                break;
        }
    }

    public Joystick getjIzquierdo() {
        return jIzquierdo;
    }

    public void setjIzquierdo(Joystick jIzquierdo) {
        this.jIzquierdo = jIzquierdo;
    }

    public Joystick getjDerecho() {
        return jDerecho;
    }

    public void setjDerecho(Joystick jDerecho) {
        this.jDerecho = jDerecho;
    }
}

package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Joystick {
    private Paint paint;

    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    private boolean pulsado = false;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    private float posX, posY;

    public Joystick(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.paint = new Paint();
    }

    public void dibujaJoystick(Canvas c) {
        if (pulsado) {
            c.drawCircle(posX, posY, 20, paint);
        }
    }
}

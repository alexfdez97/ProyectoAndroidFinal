package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IEscena {
    public void actualizarFisica();
    public void dibujar(Canvas c);
    public int onTouchEvent(MotionEvent event);
}

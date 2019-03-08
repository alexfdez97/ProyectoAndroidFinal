package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IEscena {
    /**
     * Actualiza las físicas, posiciones, propiedades...
     */
    public void actualizarFisica();

    /**
     * Dibuja en el canvas
     * @param c el canvas
     */
    public void dibujar(Canvas c);

    /**
     * Evento onTouch
     * @param event el evento
     * @return el id de la escena
     */
    public int onTouchPersonalizado(MotionEvent event);
}


package com.example.alejandrofm.proyectoandroidfinal;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Personaje {
    private enum Direccion {
        NORTE, SUR, ESTE, OESTE, NORESTE, NOROESTE, SURESTE, SUROESTE
    }
    private int posX, posY, velocidad, vida;
    private Direccion direccion;
    private Bitmap[] sprite;

    public void dibujarPersonaje(Canvas c) {

    }
}

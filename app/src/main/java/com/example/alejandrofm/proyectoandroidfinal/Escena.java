package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;

public class Escena {

    int anchoPantalla, altoPantalla;
    Context context;
    Bitmap fondo;
    int idEscena;

    public Escena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.context = context;
        this.idEscena = idEscena;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

    public int getIdEscena() {
        return idEscena;
    }

    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }
}
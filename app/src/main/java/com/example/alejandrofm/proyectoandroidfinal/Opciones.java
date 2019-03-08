package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.view.MotionEvent;

public class Opciones extends Escena {

    private Parallax parallax;
    private IconoBoton btnRollback, btnMusic, btnEfect;
    private Boton btnResetRecords;
    private Texto txtMusica, txtEfectos;

    /**
     * Inicializa las propiedades de la clase
     * @param anchoPantalla el ancho de la pantala
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicación
     * @param idEscena el id de la escena
     */
    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, efectos, context);
        btnResetRecords = new Boton(context.getString(R.string.strReset), anchoPantalla, altoPantalla, efectos, context);
        txtMusica = new Texto(context.getString(R.string.strMusic), anchoPantalla, altoPantalla, context);
        txtEfectos = new Texto(context.getString(R.string.strEffects), anchoPantalla, altoPantalla, context);
        if (musica) {
            btnMusic = new IconoBoton(IconoBoton.Tipo.SPEAKERON, anchoPantalla, altoPantalla, efectos, context);
        } else {
            btnMusic = new IconoBoton(IconoBoton.Tipo.SPEAKEROFF, anchoPantalla, altoPantalla, efectos, context);
        }
        if (efectos) {
            btnEfect = new IconoBoton(IconoBoton.Tipo.SPEAKERON, anchoPantalla, altoPantalla, efectos, context);
        } else {
            btnEfect = new IconoBoton(IconoBoton.Tipo.SPEAKEROFF, anchoPantalla, altoPantalla, efectos, context);
        }
    }

    /**
     * Guarda las preferencias en el fichero de preferencias
     * @see SharedPreferences
     */
    private void guardarPreferencias() {
        SharedPreferences preferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (btnMusic.getTipo() == IconoBoton.Tipo.SPEAKERON) {
            editor.putBoolean("musica", true);
        } else {
            editor.putBoolean("musica", false);
        }

        if (btnEfect.getTipo() == IconoBoton.Tipo.SPEAKERON) {
            editor.putBoolean("efectos", true);
        } else {
            editor.putBoolean("efectos", false);
        }

        editor.commit();
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                btnRollback.isPulsado(event);
                btnMusic.isPulsado(event);
                btnEfect.isPulsado(event);
                btnResetRecords.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnRollback.isPulsado() && btnRollback.isPulsado(event)) {
                    btnRollback.setPulsado(false);
                    return 0;
                }
                if (btnMusic.isPulsado() && btnMusic.isPulsado(event)) {
                    btnMusic.setPulsado(false);
                    if (btnMusic.getTipo() == IconoBoton.Tipo.SPEAKERON) {
                        btnMusic.cambiarIcono(IconoBoton.Tipo.SPEAKEROFF);
                        guardarPreferencias();
                        return 50;
                    } else {
                        btnMusic.cambiarIcono(IconoBoton.Tipo.SPEAKERON);
                        guardarPreferencias();
                        return 51;
                    }
                }
                if (btnEfect.isPulsado() && btnEfect.isPulsado(event)) {
                    btnEfect.setPulsado(false);
                    if (btnEfect.getTipo() == IconoBoton.Tipo.SPEAKERON) {
                        btnEfect.cambiarIcono(IconoBoton.Tipo.SPEAKEROFF);
                        guardarPreferencias();
                        return 52;
                    } else {
                        btnEfect.cambiarIcono(IconoBoton.Tipo.SPEAKERON);
                        guardarPreferencias();
                        return 53;
                    }
                }
                if (btnResetRecords.isPulsado() && btnResetRecords.isPulsado(event)) {
                    btnResetRecords.setPulsado(false);
                    //TODO codigo reset records
                }
                break;
        }
        return idEscena;
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            this.parallax.dibujaParallax(c);
            btnRollback.dibujarIconoBoton(0 + btnRollback.getWidth() / 2, 0 + btnRollback.getHeight() / 2, c);
            btnResetRecords.dibujarBoton(anchoPantalla * 1/3, altoPantalla * 3/4, c);
            txtEfectos.dibujarTexto(anchoPantalla * 1/3, altoPantalla * 1/4 + (btnEfect.getHeight() / 2), c);
            txtMusica.dibujarTexto(anchoPantalla * 1/3, altoPantalla * 2/4 + (btnMusic.getHeight() / 2), c);
            int xIconos = btnResetRecords.getX() + btnResetRecords.getWidth() - btnEfect.getWidth();
            btnEfect.dibujarIconoBoton(xIconos, txtEfectos.getY() - (btnEfect.getHeight() / 2), c);
            btnMusic.dibujarIconoBoton(xIconos, txtMusica.getY() - (btnMusic.getHeight() / 2), c);
        } catch (NullPointerException ex) { }
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    public Parallax getParallax() {
        return parallax;
    }

    @Override
    public void setEfectos(boolean efectos) {
        super.setEfectos(efectos);
        btnEfect.setEfectos(efectos);
        btnMusic.setEfectos(efectos);
        btnResetRecords.setEfectos(efectos);
        btnRollback.setEfectos(efectos);
    }
}

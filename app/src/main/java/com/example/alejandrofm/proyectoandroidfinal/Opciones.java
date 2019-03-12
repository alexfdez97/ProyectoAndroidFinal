package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.view.MotionEvent;

/**
 * Clase Opciones
 */
public class Opciones extends Escena {

    /**
     * El parallax
     */
    private Parallax parallax;
    /**
     * Los icono boton
     */
    private IconoBoton btnRollback, btnMusic, btnEfect;
    /**
     * Los botones
     */
    private Boton btnResetRecords, btnSi, btnNo;
    /**
     * Los textos
     */
    private Texto txtMusica, txtEfectos, txtConfirmacion;
    /**
     * Indica si se esta pidiendo confirmacion
     */
    private boolean inConfirmation = false;

    /**
     * Inicializa las propiedades de la clase
     * @param anchoPantalla el ancho de la pantala
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     * @param idEscena el id de la escena
     */
    public Opciones(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, efectos, context);
        btnResetRecords = new Boton(context.getString(R.string.strReset), anchoPantalla, altoPantalla, efectos, context);
        btnSi = new Boton(context.getString(R.string.strYes), anchoPantalla / 2, altoPantalla, efectos, context);
        btnNo = new Boton(context.getString(R.string.strNo), anchoPantalla / 2, altoPantalla, efectos, context);
        txtMusica = new Texto(context.getString(R.string.strMusic), anchoPantalla, altoPantalla * 2, context);
        txtEfectos = new Texto(context.getString(R.string.strEffects), anchoPantalla, altoPantalla * 2, context);
        txtConfirmacion = new Texto(context.getString(R.string.strConfirmation), anchoPantalla * 2, altoPantalla * 2, context);
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
        if (!inConfirmation) {
            switch (accion) {
                case MotionEvent.ACTION_DOWN:
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
                            btnNo.setEfectos(false);
                            btnSi.setEfectos(false);
                            guardarPreferencias();
                            return 52;
                        } else {
                            btnEfect.cambiarIcono(IconoBoton.Tipo.SPEAKERON);
                            btnSi.setEfectos(true);
                            btnNo.setEfectos(true);
                            guardarPreferencias();
                            return 53;
                        }
                    }
                    if (btnResetRecords.isPulsado() && btnResetRecords.isPulsado(event)) {
                        btnResetRecords.setPulsado(false);
                        inConfirmation = true;
                    }
                    break;
            }
        } else {
            switch (accion) {
                case MotionEvent.ACTION_DOWN:
                    btnSi.isPulsado(event);
                    btnNo.isPulsado(event);
                    break;
                case MotionEvent.ACTION_UP:
                    if (btnSi.isPulsado() && btnSi.isPulsado(event)) {
                        btnSi.setPulsado(false);
                        context.deleteDatabase("records");
                        inConfirmation = false;
                    }
                    if (btnNo.isPulsado() && btnNo.isPulsado(event)) {
                        btnNo.setPulsado(false);
                        inConfirmation = false;
                    }
                    break;
            }
        }
        return idEscena;
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            this.parallax.dibujaParallax(c);
            if (!inConfirmation) {
                btnRollback.dibujarIconoBoton(0 + btnRollback.getWidth() / 2, 0 + btnRollback.getHeight() / 2, c);
                btnResetRecords.dibujarBoton(anchoPantalla * 1 / 3, altoPantalla * 3 / 4, c);
                int xIconos = btnResetRecords.getX() + btnResetRecords.getWidth() - btnEfect.getWidth();
                txtEfectos.dibujarTexto(btnResetRecords.getX(), altoPantalla * 1 / 4 + txtEfectos.getHeight() / 2, c);
                txtMusica.dibujarTexto(btnResetRecords.getX(), altoPantalla * 2 / 4 + txtMusica.getHeight() / 2, c);
                btnEfect.dibujarIconoBoton(xIconos, txtEfectos.getY(), c);
                btnMusic.dibujarIconoBoton(xIconos, txtMusica.getY(), c);
            } else {
                txtConfirmacion.dibujarTexto(anchoPantalla / 2 - txtConfirmacion.getWidth() / 2, altoPantalla * 1/3 - txtConfirmacion.getHeight() / 2, c);
                btnSi.dibujarBoton(anchoPantalla * 2 / 6 - btnSi.getWidth() / 2, altoPantalla * 2/3 - btnSi.getHeight() / 2, c);
                btnNo.dibujarBoton(anchoPantalla * 4 / 6 - btnNo.getWidth() / 2, altoPantalla * 2/3 - btnNo.getHeight() / 2, c);
            }
        } catch (NullPointerException ex) { }
    }

    /**
     * Establece el Paralax
     * @param parallax el parallax
     */
    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    /**
     * Devuelve le parallax
     * @return el parallax
     */
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
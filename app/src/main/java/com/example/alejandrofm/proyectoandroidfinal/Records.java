package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Date;

/**
 * Clase Records
 */
public class Records extends Escena {

    /**
     * El parallax
     */
    private Parallax parallax;
    /**
     * El icono de volver
     */
    private IconoBoton btnRollback;
    /**
     * El texto de records
     */
    private Texto txtRecords;
    /**
     * El texto de puntuacion
     */
    private Texto txtPuntuacion;
    /**
     * El texto de fecha
     */
    private Texto txtFecha;
    /**
     * Textos de rankings
     */
    private Texto rankings[] = new Texto[4];
    /**
     * Textos de puntos
     */
    private Texto point[] = new Texto[4];
    /**
     * Textos de fechas
     */
    private Texto fechas[] = new Texto[4];

    /**
     * Inicializa Records
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto
     * @param idEscena el id de la escena
     */
    public Records(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, efectos, context);
        txtRecords = new Texto("Records", anchoPantalla * 2, altoPantalla * 2, context);
        for (int i = 0; i < rankings.length; i++) {
            rankings[i] = new Texto(String.valueOf(i+1), anchoPantalla, altoPantalla, context);
        }
        txtPuntuacion = new Texto(context.getString(R.string.strPuntuation), anchoPantalla, altoPantalla, context);
        txtFecha = new Texto(context.getString(R.string.strDate), anchoPantalla, altoPantalla, context);
        cargarRecords();
    }

    /**
     * Carga los records llamando a la base de datos
     */
    private void cargarRecords () {
        BaseDatos bd = null;
        SQLiteDatabase lite = null;
        try {
            bd = new BaseDatos(context, "records", null, 1);
            lite = bd.getReadableDatabase();
            String query = "SELECT points, date FROM records ORDER BY points DESC LIMIT 4";
            Cursor cursor = lite.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                int puntos;
                String fecha;
                int i = 0;
                do {
                    puntos = cursor.getInt(0);
                    fecha = cursor.getString(1);
                    fecha = fecha.substring(0, 10);
                    point[i] = new Texto(String.format("%06d", puntos), anchoPantalla, altoPantalla, context);
                    fechas[i] = new Texto(fecha, anchoPantalla, altoPantalla, context);
                    i++;
                } while (cursor.moveToNext());
            }
        } finally {
            if (lite != null) {
                lite.close();
            }
            if (bd != null) {
                bd.close();
            }
        }
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            this.parallax.dibujaParallax(c);
            btnRollback.dibujarIconoBoton(btnRollback.getWidth() / 2, btnRollback.getHeight() / 2, c);
            txtRecords.dibujarTexto(anchoPantalla * 2/6, btnRollback.getPosY(), c);
            txtPuntuacion.dibujarTexto(anchoPantalla * 2/6, altoPantalla * 2/8, c);
            txtFecha.dibujarTexto(anchoPantalla * 4/6, altoPantalla * 2/8, c);
            rankings[0].dibujarTexto(anchoPantalla * 1/6, altoPantalla * 3/8, c);
            rankings[1].dibujarTexto(anchoPantalla * 1/6, altoPantalla * 4/8, c);
            rankings[2].dibujarTexto(anchoPantalla * 1/6, altoPantalla * 5/8, c);
            rankings[3].dibujarTexto(anchoPantalla * 1/6, altoPantalla * 6/8, c);
            for (int i = 0; i < point.length; i++) {
                if (point[i] != null) {
                    point[i].dibujarTexto(anchoPantalla * 2/6, altoPantalla * (i + 3)/8, c);
                }
                if (fechas[i] != null) {
                    fechas[i].dibujarTexto(anchoPantalla * 4/6, altoPantalla * (i + 3)/8, c);
                }
            }
        } catch (NullPointerException ex) { }
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                btnRollback.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnRollback.isPulsado() && btnRollback.isPulsado(event)) {
                    btnRollback.setPulsado(false);
                    return 0;
                }
                break;
        }
        return idEscena;
    }

    /**
     * Establece el parallax
     * @param parallax el parallax
     */
    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    @Override
    public void setEfectos(boolean efectos) {
        super.setEfectos(efectos);
        btnRollback.setEfectos(efectos);
    }
}

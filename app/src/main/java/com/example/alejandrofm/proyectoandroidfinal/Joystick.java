package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Clase Joystick
 */
public class Joystick {
    /**
     * La direccion del joystick
     */
    public enum Direccion {
        NORTE, SUR, ESTE, OESTE, NINGUNA
    }

    /**
     * La direccion
     */
    private Direccion direccion = Direccion.NINGUNA;
    /**
     * Los bitmaps del joystick
     */
    private Bitmap joyVacio, joyFlechas;
    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * Las coordenadas de los joystick
     */
    private float jVacioX, jVacioY, jFlechasX, jFlechasY;
    /**
     * Si el joystick es pulsado
     */
    private boolean pulsado = false;
    /**
     * El id del puntero
     */
    private int idPuntero;
    /**
     * Los radios necesarios para hacer calculos
     */
    private float radioJVacio, radioHipotenusa;
    /**
     * Distancia entre la pulsacion y el centro del joystick
     */
    private float desplazamiento;
    /**
     * Coordenadas calculadas del circulo exterior
     */
    private float constraintX, constraintY;
    /**
     * Grados en los que se esta moviendo
     */
    private float grados;

    /**
     * Inicializa las propiedades de la clase
     * @param context el contexto de la aplicacion
     * @param jVacioX la coordenada X
     * @param jVacioY la coordenada Y
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     */
    public Joystick(Context context, float jVacioX, float jVacioY, int anchoPantalla, int altoPantalla) {
        utils = new Utils(context);
        joyVacio = utils.getBitmapFromAssets("joystick/flatDark06.png");
        joyVacio = Bitmap.createScaledBitmap(joyVacio, altoPantalla * 1/4, altoPantalla * 1/4, false);
        joyFlechas = utils.getBitmapFromAssets("joystick/flatDark00.png");
        joyFlechas = Bitmap.createScaledBitmap(joyFlechas, altoPantalla * 1/10, altoPantalla * 1/10, false);
        this.jVacioX = jVacioX;
        this.jVacioY = jVacioY;
        this.jFlechasX = jVacioX;
        this.jFlechasY = jVacioY;
        radioJVacio = joyVacio.getWidth() / 2;
    }

    /**
     * Dibuja el Joystick en el Canvas
     * @param c el canvas
     */
    public void dibujaJoystick(Canvas c) {
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(200);
        c.drawBitmap(joyVacio, jVacioX - joyVacio.getWidth() / 2, jVacioY - joyVacio.getHeight() / 2, alphaPaint);
        c.drawBitmap(joyFlechas, jFlechasX - joyFlechas.getWidth() / 2, jFlechasY - joyFlechas.getHeight() / 2, alphaPaint);
    }

    /**
     * Establece las coordenadas
     * @param jFlechasX coordenada X
     * @param jFlechasY coordenada Y
     */
    public void setCoordsJFlechas(float jFlechasX, float jFlechasY) {
        desplazamiento = (float) Math.sqrt(Math.pow(jVacioX - jFlechasX, 2) + Math.pow(jVacioY - jFlechasY, 2));
        radioHipotenusa = radioJVacio / desplazamiento;

        constraintX = jVacioX + (jFlechasX - jVacioX) * radioHipotenusa;
        constraintY = jVacioY + (jFlechasY - jVacioY) * radioHipotenusa;
        grados = (float)Math.abs(Math.toDegrees(Math.atan2(constraintY - jVacioY, constraintX - jVacioX)));

        if (desplazamiento < radioJVacio) {
            this.jFlechasX = jFlechasX;
            this.jFlechasY = jFlechasY;
        } else {
            this.jFlechasX = constraintX;
            this.jFlechasY = constraintY;
        }

        if (desplazamiento > 0) {
            if (grados < 45) {
                direccion = Direccion.ESTE;
            } else if (grados > 135) {
                direccion = Direccion.OESTE;
            } else if (grados > 45 && grados < 135) {
                if (jFlechasY < jVacioY) {
                    direccion = Direccion.NORTE;
                } else {
                    direccion = Direccion.SUR;
                }
            }
        }
    }

    /**
     * Devuelve si es pulsado
     * @return es pulsado
     */
    public boolean isPulsado() {
        return pulsado;
    }

    /**
     * Establece si es pulsado
     * @param pulsado es pulsado
     */
    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    /**
     * Devuelve le id del puntero
     * @return el id
     */
    public int getIdPuntero() {
        return idPuntero;
    }

    /**
     * Establece el id del puntero
     * @param idPuntero el id
     */
    public void setIdPuntero(int idPuntero) {
        this.idPuntero = idPuntero;
    }

    /**
     * Devuelve la direccion
     * @return la direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la direccion
     * @param direccion la direccion
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve el desplazamiento
     * @return el desplazamiento
     */
    public float getDesplazamiento() {
        return desplazamiento;
    }
}

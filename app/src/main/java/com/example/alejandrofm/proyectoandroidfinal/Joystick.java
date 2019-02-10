package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;

public class Joystick {
    public enum Direccion {
        NORTE, SUR, ESTE, OESTE, NORESTE, NOROESTE, SURESTE, SUROESTE, NINGUNA
    }
    private Direccion direccion = Direccion.NINGUNA;
    private Bitmap joyVacio, joyFlechas;
    private Utils utils;
    private float jVacioX, jVacioY, jFlechasX, jFlechasY;
    private boolean pulsado = false;
    private int idPuntero;
    private float radioJVacio, radioHipotenusa;
    private float desplazamiento;
    private float constraintX, constraintY;
    private float grados;

    public Joystick(Context context, float jVacioX, float jVacioY, int anchoPantalla, int altoPantalla) {
        utils = new Utils(context);
        joyVacio = utils.getBitmapFromAssets("joystick/flatDark06.png");
        joyVacio = Bitmap.createScaledBitmap(joyVacio, altoPantalla * 1/6, altoPantalla * 1/6, false);
        joyFlechas = utils.getBitmapFromAssets("joystick/flatDark00.png");
        joyFlechas = Bitmap.createScaledBitmap(joyFlechas, altoPantalla * 1/12, altoPantalla * 1/12, false);
        this.jVacioX = jVacioX;
        this.jVacioY = jVacioY;
        this.jFlechasX = jVacioX;
        this.jFlechasY = jVacioY;
        radioJVacio = joyVacio.getWidth() / 2;
    }

//    public float calcularDistancia(float x1, float y1, float x2, float y2)
//    {
//        float dLat = (float) Math.toRadians(x2 - x1);
//        float dLon = (float) Math.toRadians(y2 - y1);
//        float a =
//                (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(x1))
//                        * Math.cos(Math.toRadians(x2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
//        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
////        float d = earthRadius * c;
//        return d;
//    }

    public void dibujaJoystick(Canvas c) {
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(200);
        c.drawBitmap(joyVacio, jVacioX - joyVacio.getWidth() / 2, jVacioY - joyVacio.getHeight() / 2, alphaPaint);
        c.drawBitmap(joyFlechas, jFlechasX - joyFlechas.getWidth() / 2, jFlechasY - joyFlechas.getHeight() / 2, alphaPaint);
    }

    public void setCoordsJFlechas(float jFlechasX, float jFlechasY) {
        desplazamiento = (float) Math.sqrt(Math.pow(jVacioX - jFlechasX, 2) + Math.pow(jVacioY - jFlechasY, 2));
        radioHipotenusa = radioJVacio / desplazamiento;

        constraintX = jVacioX + (jFlechasX - jVacioX) * radioHipotenusa;
        constraintY = jVacioY + (jFlechasY - jVacioY) * radioHipotenusa;
        grados = (float)Math.abs(Math.toDegrees(Math.atan2(constraintY - jVacioY, constraintX - jVacioX)));
//        grados = (float)(Math.toDegrees(Math.atan2(constraintY - 360, 360 - constraintY)) + 360) % 360;
        Log.i("grados", "Grados: " + grados);
//        Log.i("grados", "Desplazamiento: " + desplazamiento);
//        Log.i("grados", "Hipotenusa: " + radioHipotenusa);

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


    public boolean isPulsado() {
        return pulsado;
    }

    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    public int getIdPuntero() {
        return idPuntero;
    }

    public void setIdPuntero(int idPuntero) {
        this.idPuntero = idPuntero;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public float getDesplazamiento() {
        return desplazamiento;
    }
}

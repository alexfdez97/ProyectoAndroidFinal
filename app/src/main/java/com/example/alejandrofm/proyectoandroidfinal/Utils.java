package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase Utils
 */
public class Utils {

    /**
     * El contexto
     */
    private Context context;

    /**
     * Inicializa las propiedades de la clase
     * @param context el contexto de la aplicación
     */
    public Utils(Context context){
        this.context = context;
    }

    /**
     * Recoge una imagen de la carpeta assets
     * @param fichero la ruta del archivo (entendiendo como raiz la propia carpeta assets)
     * @return el Bitmap correspondiente
     */
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Carga las preferencias del fichero de preferencias y devuelve un array de booleanos
     * [0] --> Indica si la musica esta activada
     * [1] --> Indica si los efectos sonoros estan activados
     * @see SharedPreferences
     * @return el array de booleanos
     */
    public boolean[] cargarPreferencias() {
        boolean prefs[] = new boolean[2];
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        prefs[0] = sharedPreferences.getBoolean("musica", true);
        prefs[1] = sharedPreferences.getBoolean("efectos", true);
        return prefs;
    }

    /**
     * Calcula la distancia entre dos puntos
     * @param p1 el punto1
     * @param p2 el punto2
     * @return la distancia entre los dos puntos
     */
    public int dist(Point p1, Point p2) {
        int dist = (int)Math.sqrt(Math.pow(p2.x - p1.x, 2)
                + Math.pow(p2.y - p1.y, 2));
        return dist;
    }
}

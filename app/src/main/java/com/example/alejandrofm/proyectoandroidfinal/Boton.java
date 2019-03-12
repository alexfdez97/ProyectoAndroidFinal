package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase boton
 */
public class Boton {

    /**
     * Funciones utiles
     */
    private Utils utils;
    /**
     * Mapa de caracteres y bitmaps
     */
    private HashMap<Character, Bitmap> letras = new HashMap<>();
    /**
     * Bitmap de boton pulsado
     */
    private Bitmap botonPulsado;
    /**
     * Bitmap de boton base
     */
    private Bitmap botonBase;
    /**
     * Bitmap final
     */
    private Bitmap imagenTexto;
    /**
     * Indica si esta pulsado o no
     */
    private boolean pulsado = false;
    /**
     * El ancho y el alto de la pantalla
     */
    private int anchoPantalla, altoPantalla;
    /**
     * Las coordenadas X y Y
     */
    private int x, y;
    /**
     * El SoundPool
     */
    private SoundPool sp;
    /**
     * El sonido del boton
     */
    private int sonidoBoton;
    /**
     * El numero maximo de sonidos
     */
    final private int maximoSonidos = 1;
    /**
     * Indica si ha sonado
     */
    private boolean haSonado = false;
    /**
     * Indica si los efectos estan activos
     */
    private boolean efectos;

    /**
     * Inicializa Boton
     * @param texto el texto que aparece dentro del boton (Admite caracteres de la a-z dentro de la tabla ASCII)
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param efectos indica si los efectos sonoros estan activados
     * @param context el contexto de la aplicacion
     */
    public Boton(String texto, int anchoPantalla, int altoPantalla, boolean efectos, Context context) {
        utils = new Utils(context);
        this.efectos = efectos;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        cargarBitmaps();
        imagenTexto = textoAImagen(texto);
        imagenTexto = Bitmap.createScaledBitmap(imagenTexto, botonBase.getWidth() * 3/5, botonBase.getHeight() * 3/5, false);
        this.sp = new SoundPool(maximoSonidos, AudioManager.STREAM_MUSIC, 0);
        sonidoBoton = this.sp.load(context, R.raw.button_press, 1);
    }

    /**
     * Dibuja el Boton en el canvas
     * @param x la coordenada x del canvas
     * @param y la coordenada y del canvas
     * @param canvas el canvas donde se dibuja
     */
    public void dibujarBoton(int x, int y, Canvas canvas) {
        this.x = x;
        this.y = y;
        if (pulsado) {
            canvas.drawBitmap(botonPulsado, x, y, null);
            if (efectos && !haSonado) {
                sp.play(sonidoBoton, 0.3f, 0.3f, 1, 0, 1);
                haSonado = true;
            }
        } else {
            canvas.drawBitmap(botonBase, x, y, null);
            haSonado = false;
        }
        canvas.drawBitmap(imagenTexto, (x + (botonBase.getWidth() / 2)) - (imagenTexto.getWidth() / 2), (y + botonBase.getHeight() / 2) - (imagenTexto.getHeight() / 2), null);
    }

    /**
     * Carga en memoria, recorta y escala los bitmaps necesarios para el funcionamiento de la clase
     */
    private void cargarBitmaps() {
        Bitmap tileset = utils.getBitmapFromAssets("menu/ui.png");
        botonBase = Bitmap.createBitmap(tileset, 16 , 16, 128, 32);
        botonBase = Bitmap.createScaledBitmap(botonBase, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        botonPulsado = Bitmap.createBitmap(tileset, 16, 64, 128, 32);
        botonPulsado = Bitmap.createScaledBitmap(botonPulsado, anchoPantalla * 1/3, altoPantalla * 1/6, false);
        int tempX = 158;
        int tempY = 10;
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (i % 8 == 0 && i > 0) {
                tempY += 23;
                tempX = 158;
            }
            letras.put(c, Bitmap.createBitmap(tileset, tempX, tempY, 16, 20));
            tempX += 16;
            c++;
        }
        letras.put(' ', Bitmap.createBitmap(16, 20, Bitmap.Config.ARGB_8888));
    }

    /**
     * Recoge el parametro texto y devuelve una imagen compuesta por ese texto (Admite caracteres de a-z de la tabla ASCII)
     * @param texto es el texto que se transforma
     * @return el bitmap con los caracteres del texto
     */
    private Bitmap textoAImagen(String texto) {
        texto = texto.toLowerCase();
        char letrasFromTexto[] = texto.toCharArray();
        ArrayList<Bitmap> cadena = new ArrayList<>();
        for (char ch:letrasFromTexto) {
            cadena.add(letras.get(ch));
        }
        Bitmap inicio = null;
        Bitmap temp;
        for (int i = 0; i < cadena.size(); i++) {
            if (i == 0) {
                inicio = cadena.get(i);
            } else {
                temp = cadena.get(i);
                inicio = combinarImagenes(inicio, temp);
            }
        }
        return inicio;
    }

    /**
     * Funcion que combina dos imagenes, se utiliza para unir las imagenes de los caracteres almacenados en la Hashtable
     * @param bmp1 el primer bitmap
     * @param bmp2 el segundo bitmap
     * @return bmpUnion la unión de 'bmp1' y 'bmp2'
     */
    private Bitmap combinarImagenes(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmpUnion = null;

        int ancho, alto = 0;

        if(bmp1.getWidth() > bmp2.getWidth()) {
            ancho = bmp1.getWidth() + bmp2.getWidth();
            alto = bmp1.getHeight();
        } else {
            ancho = bmp2.getWidth() + bmp2.getWidth();
            alto = bmp1.getHeight();
        }

        bmpUnion = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(bmpUnion);

        comboImage.drawBitmap(bmp1, 0f, 0f, null);
        comboImage.drawBitmap(bmp2, bmp1.getWidth(), 0f, null);

        return bmpUnion;
    }

    /**
     * Comprueba si el Boton esta pulsado y cambia el bitmap si es asi
     * @param event el evento onTouch
     * @return TRUE si está pulsado, FALSE si no
     */
    public boolean isPulsado(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        if ((eventX > x && eventY > y) && (eventX < x + getWidth() && eventY < y + getHeight())) {
            setPulsado(true);
        } else {
            setPulsado(false);
        }
        return pulsado;
    }

    /**
     * Devuelve si el boton esta pulsado
     * @return es pulsado
     */
    public boolean isPulsado() {
        return pulsado;
    }

    /**
     * Establece si el boton es pulsado
     * @param pulsado es pulsado
     */
    public void setPulsado(boolean pulsado) {
        this.pulsado = pulsado;
    }

    /**
     * Devuelve la posicion X del boton
     * @return la x
     */
    public int getX() {
        return x;
    }

    /**
     * Establace la posicion X del boton
     * @param x la x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Devuelve la posicion Y del boton
     * @return la y
     */
    public int getY() {
        return y;
    }

    /**
     * Establace Y
     * @param y la y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Devuelve el ancho del boton
     * @return el ancho
     */
    public int getWidth() {
        return botonBase.getWidth();
    }

    /**
     * Devuelve le alto del boton
     * @return el alto
     */
    public int getHeight() {
        return botonBase.getHeight();
    }

    /**
     * Devuelve si los efectos estan activos
     * @return los efectos
     */
    public boolean isEfectos() {
        return efectos;
    }

    /**
     * Establece si los efectos estan activos
     * @param efectos los efectos
     */
    public void setEfectos(boolean efectos) {
        this.efectos = efectos;
    }
}

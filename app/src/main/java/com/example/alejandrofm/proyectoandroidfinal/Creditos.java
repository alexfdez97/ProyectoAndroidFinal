package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase Creditos
 */
public class Creditos extends Escena {

    /**
     * Los textos [ ]
     */
    private Texto textos[] = new Texto[30];
    /**
     * El parallax
     */
    private Parallax parallax;
    /**
     * La posicion Y
     */
    private int posY;
    /**
     * La separacion entre textos
     */
    private int gap;
    /**
     * El movimiento
     */
    private int mov = 0;

    /**
     * Inicializa Creditos
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicacion
     * @param idEscena el id de la escena
     */
    public Creditos(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        posY = altoPantalla * 1/3;
        gap = altoPantalla / 5;
        int[] cadenas = {
                R.string.strCredits,
                R.string.strGameBy,
                R.string.strImageAssets,
                R.string.strGameIcon,
                R.string.strParallax,
                R.string.strGraveyard,
                R.string.strSurvivor,
                R.string.strZombie,
                R.string.strArrow,
                R.string.strHealthBar,
                R.string.strBullet,
                R.string.strJoystick,
                R.string.strMap,
                R.string.strHelpIcon,
                R.string.strInfoIcon,
                R.string.strTrophy,
                R.string.strGui,
                R.string.strAlejandromodified,
                R.string.strSounds,
                R.string.strMenuMusic,
                R.string.strSonata,
                R.string.strBy,
                R.string.strBethoven,
                R.string.strWikimedia,
                R.string.strgamemusic,
                R.string.strDarkF,
                R.string.strSoundible,
                R.string.strWalkingsound,
                R.string.strRest,
                R.string.strScreenExit
        };
        for (int i = 0; i < cadenas.length ; i++) {
            int ancho = anchoPantalla;
            int alto = altoPantalla;
            if (i == 14 || i == 17 || i == 3) {
                ancho = anchoPantalla * 3/4;
            } else if (i == 0 || i == 2 || i == 18) {
                ancho = anchoPantalla * 2;
                alto = altoPantalla * 2;
            }
            textos[i] = new Texto(context.getString(cadenas[i]), ancho, alto, context);
        }
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            parallax.dibujaParallax(c);
            for (int i = 0; i < textos.length; i++) {
                textos[i].dibujarTexto(anchoPantalla / 2 - textos[i].getWidth() / 2, posY + mov, c);
                if (i <= 1 || i == 17) {
                    posY += (gap * 2);
                } else if (i == 28) {
                    posY += (gap * 4);
                } else {
                    posY += gap;
                }
            }
            mov -= 1;
            posY = altoPantalla * 1/3;
        } catch (NullPointerException ex) { }
    }

    @Override
    public void actualizarFisica() {
        try {
            if (textos[29].getY() + textos[29].getHeight() < 0) {
                reiniciarCreditos();
            }
        } catch (NullPointerException ex) { }
    }

    /**
     * Reinicia los creditos
     */
    private void reiniciarCreditos() {
        mov = altoPantalla;
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        return 0;
    }

    /**
     * Establece el parallax
     * @param parallax el parallax
     */
    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }
}

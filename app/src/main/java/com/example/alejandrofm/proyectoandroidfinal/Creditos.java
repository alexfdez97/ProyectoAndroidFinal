package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;


public class Creditos extends Escena {

    private Texto textos[] = new Texto[27];
    private Parallax parallax;
    private int posY = 0;
    private int gap = 100;
    private int mov = 0;

    public Creditos(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        int[] cadenas={ R.string.strCredits, R.string.strGameBy, R.string.strBullet, R.string.strJoystick, R.string.strMap, R.string.strHelpIcon, R.string.strInfoIcon, R.string.strTrophy, R.string.strGui, R.string.strAlejandromodified, R.string.strImageAssets, R.string.strParallax, R.string.strGraveyard, R.string.strSurvivor, R.string.strZombie, R.string.strArrow, R.string.strHealthBar, R.string.strMenuMusic, R.string.strWikimedia, R.string.strBethoven, R.string.strSonata, R.string.strgamemusic, R.string.strDarkF, R.string.strSoundible, R.string.strWalkingsound, R.string.strRest, R.string.strGameIcon};
        for (int i = 0; i <cadenas.length ; i++) {
            textos[i] = new Texto(context.getString(cadenas[i]), anchoPantalla, altoPantalla, context);
        }
    }

    @Override
    public void dibujar(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            parallax.dibujaParallax(c);
            for(Texto t:textos){
                t.dibujarTexto(0, posY + mov,c);
                posY += gap;
            }
            mov-=1;
            posY=0;
        } catch (NullPointerException ex) { }
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        try {

        } catch (NullPointerException ex) { }
        return 0;
    }

    @Override
    public void actualizarFisica() {
        super.actualizarFisica();
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }
}

package com.example.alejandrofm.proyectoandroidfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Clase Main
 */
public class MainActivity extends AppCompatActivity {

    /**
     * El juego
     */
    private Juego juego;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarOpciones();
        juego = new Juego(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarOpciones();
    }

    /**
     * Carga las opciones predeterminadas
     */
    private void cargarOpciones() {
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
    }

    @Override
    public void onBackPressed() {
        juego.onBackPressed();
    }
}

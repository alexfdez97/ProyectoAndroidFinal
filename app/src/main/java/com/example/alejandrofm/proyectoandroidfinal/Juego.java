package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private SurfaceHolder surfaceHolder;
    private Context context;
    private int anchoPantalla;
    private int altoPantalla;
    private boolean funcionando = false;
    private Escena escenaActual;
    private Menu menu = null;
    private Opciones opciones;
    private Hilo hilo;
    private SensorManager sensorManager;
    private Sensor sensorLuz;
    private float luz = -1;
    private MediaPlayer menuMusic;
    private Utils utils;
    private boolean music = true, effects = true;

    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        utils = new Utils(context);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorLuz == null) {
            luz = (float)Math.random() * 4;
        }
        hilo = new Hilo();
        setFocusable(true);
        menuMusic = MediaPlayer.create(context, R.raw.beethoven_moonlight_1st_movement);
        menuMusic.start();
        boolean prefs[] = utils.cargarPreferencias();
        music = prefs[0];
        effects = prefs[1];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (escenaActual != null) {
            if (music && escenaActual.getIdEscena() != 1) {
                menuMusic.start();
            } else {
                menuMusic.pause();
            }
        } else {
            if (music) {
                menuMusic.start();
            } else {
                menuMusic.pause();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;
        if (sensorLuz != null) {
            sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (menu == null) {
            menu = new Menu(width, height, context, 0);
            if (luz != -1) {
                menu.setLuz(luz);
            }
            escenaActual = menu;
        }

        if (opciones == null) {
            opciones = new Opciones(width, height, context, 2);
        }

//        hilo.setSurfaceSize(width, height);
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW) {
            hilo.start();
        } else if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hilo.setFuncionando(false);
        if (music) {
            menuMusic.pause();
        }
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int nuevaEscena = escenaActual.onTouchPersonalizado(event);
            if (nuevaEscena != escenaActual.idEscena) {
                switch (nuevaEscena) {
                    case 0:
                        escenaActual = menu;
                        if (music) {
                            menuMusic.start();
                        }
                        break;
                    case 1:
                        escenaActual = new Arena(anchoPantalla, altoPantalla, context, 1);
                        menuMusic.pause();
                        break;
                    case 2:
                        opciones.setParallax(menu.getParallax());
                        escenaActual = opciones;
                        break;
                    case 50:
                        menuMusic.pause();
                        music = false;
                        break;
                    case 51:
                        Log.i("media", "" + (menuMusic == null));
                        menuMusic.start();
                        music = true;
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (luz == -1) {
            luz = event.values[0];
            menu.setLuz(luz);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    class Hilo extends Thread {
        public Hilo() {

        }

        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid()) {
                        continue;
                    }
                    c = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        escenaActual.actualizarFisica();
                        escenaActual.dibujar(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        public void setSurfaceSize () {
            synchronized (surfaceHolder) {

            }
        }
    }
}

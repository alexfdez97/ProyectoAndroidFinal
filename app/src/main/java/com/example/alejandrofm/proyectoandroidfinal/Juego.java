package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
    private Arena arena;
    private Hilo hilo;
    private SensorManager sensorManager;
    private Sensor sensorLuz;
    private float luz = -1;

    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        hilo = new Hilo();
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;
        if (sensorLuz != null) {
            sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }
//        if (menu != null) {
//            escenaActual = menu;
//        } else {
//            menu = new Menu(width, height, context, 0);
//            escenaActual = menu;
//        }

        if (menu == null) {
            menu = new Menu(width, height, context, 0);
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
                        escenaActual = new Menu(anchoPantalla, altoPantalla, context, 0);
                        break;
                    case 1:
                        escenaActual = new Arena(anchoPantalla, altoPantalla, context, 1);
                        break;
                    case 2:
                        opciones.setParallax(menu.getParallax());
                        escenaActual = opciones;
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
//                        escenaActual.actualizarFisica();
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

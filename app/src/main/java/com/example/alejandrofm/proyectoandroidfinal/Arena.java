package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Arena extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder; // Interfaz abstracta para manejar la superficie de dibujado
    private Context context; // Contexto de la aplicación
    private Bitmap bitmapFondo; // Imagen de fondo
    private int anchoPantalla=1; // Ancho de la pantalla, su valor se actualiza en el método surfaceChanged
    private int altoPantalla=1; // Alto de la pantalla, su valor se actualiza en el método surfaceChanged
//    private Hilo hilo; // Hilo encargado de dibujar y actualizar la física
    private boolean funcionando = false; // Control del hilo

    public Arena(Context context) {
        super(context);

        this.surfaceHolder = getHolder();
    }
//    private static int anchoPantalla, altoPantalla;
//    Context context;
//    Bitmap background;
//    Utils utils;
//    Canvas canvas;
//    Joystick j;
//
//    public Arena (Context context) {
//        super(context);
//        this.context = context;
//        utils = new Utils(context);
//        background = utils.getBitmapFromAssets("asphalt.png");
//        j = new Joystick(100, 100);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        this.canvas = canvas;
//        this.canvas.drawBitmap(background, 0, 0, null);
//        j.dibujaJoystick(canvas);
//        invalidate();
//    }
//
//    @Override
//    public boolean onTouchPersonalizado(MotionEvent event) {
//        int accion = event.getAction();
//        float x = event.getX(), y = event.getY();
//        switch (accion) {
//            case MotionEvent.ACTION_DOWN:
//                j.setPosX(x);
//                j.setPosY(y);
//                j.setPulsado(true);
//                break;
//            case MotionEvent.ACTION_UP:
//                j.setPulsado(false);
//                break;
//        }
//        return true;
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

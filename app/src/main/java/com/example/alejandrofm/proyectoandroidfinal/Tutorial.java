package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Tutorial extends Arena {

    private IconoBoton btnRollback;
    private Texto txtDeslizaIzquierda;
    private Texto txtDeslizaDerecha;
    private Texto txtInfoPuntuacion;
    private Texto txtInfoVida;
    private Texto txtInfoVida2;
    private Texto txtPressScreen;
    private Texto txtObjective;
    private Texto txtVibration;
    private Texto txtFun;
    private Bitmap arrowTop;
    private int tap = 0;

    public Tutorial(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        protagonista.setVida(100);
        btnRollback = new IconoBoton(IconoBoton.Tipo.VOLVER, anchoPantalla, altoPantalla, efectos, context);
        arrowTop = utils.getBitmapFromAssets("tutorial/arrow_top.png");
        arrowTop = Bitmap.createScaledBitmap(arrowTop, altoPantalla * 1/8, altoPantalla * 1/8, false);
        txtDeslizaIzquierda = new Texto(context.getString(R.string.strSwipeL), anchoPantalla * 3/4, altoPantalla, context);
        txtDeslizaDerecha = new Texto(context.getString(R.string.strSwipeR), anchoPantalla * 3/4, altoPantalla, context);
        txtInfoPuntuacion = new Texto(context.getString(R.string.strInfoPuntuation), anchoPantalla, altoPantalla, context);
        txtInfoVida = new Texto(context.getString(R.string.strInfoHealth), anchoPantalla, altoPantalla, context);
        txtInfoVida2 = new Texto(context.getString(R.string.strInfoHealth2), anchoPantalla, altoPantalla, context);
        txtPressScreen = new Texto(context.getString(R.string.strPressContinue), anchoPantalla, altoPantalla, context);
        txtObjective = new Texto(context.getString(R.string.strObjetive), anchoPantalla, altoPantalla, context);
        txtVibration = new Texto(context.getString(R.string.strDamage), anchoPantalla * 7/8, altoPantalla, context);
        txtFun = new Texto(context.getString(R.string.strEnjoy), anchoPantalla, altoPantalla, context);
    }

    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        int accion = event.getActionMasked();
        if (accion == MotionEvent.ACTION_DOWN) {
            if (tap > 1) {
                tap++;
            }
        }
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                btnRollback.isPulsado(event);
                break;
            case MotionEvent.ACTION_UP:
                if (btnRollback.isPulsado() && btnRollback.isPulsado(event)) {
                    btnRollback.setPulsado(false);
                    return 0;
                }
                break;
        }
        return super.onTouchPersonalizado(event);
    }

    @Override
    public void dibujar(Canvas c) {
        super.dibujar(c);
        try {
            if (!partidaFinalizada) {
                btnRollback.dibujarIconoBoton(anchoPantalla - btnRollback.getWidth() - btnRollback.getWidth()/4, btnRollback.getWidth()/4, c);
                if (tap < 7) {
                    switch (tap) {
                        case 0:
                            txtDeslizaIzquierda.dibujarTexto(anchoPantalla / 2 - txtDeslizaIzquierda.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                        case 1:
                            txtDeslizaDerecha.dibujarTexto(anchoPantalla / 2 - txtDeslizaDerecha.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                        case 2:
                            c.drawBitmap(arrowTop, anchoPantalla * 2 / 10, btnRollback.getHeight() / 2, null);
                            txtInfoVida.dibujarTexto(0, altoPantalla * 1 / 4, c);
                            break;
                        case 3:
                            txtInfoVida2.dibujarTexto(anchoPantalla / 2 - txtInfoVida2.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                        case 4:
                            c.drawBitmap(arrowTop, anchoPantalla * 6 / 10, btnRollback.getHeight() / 2, null);
                            txtInfoPuntuacion.dibujarTexto(anchoPantalla / 2 - txtInfoPuntuacion.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                        case 5:
                            txtObjective.dibujarTexto(anchoPantalla / 2 - txtObjective.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                        case 6:
                            txtVibration.dibujarTexto(anchoPantalla / 2 - txtVibration.getWidth() / 2, altoPantalla * 1 / 4, c);
                            break;
                    }
                    if (tap > 1) {
                        txtPressScreen.dibujarTexto(anchoPantalla / 2 - txtPressScreen.getWidth() / 2, altoPantalla * 3 / 4, c);
                    }
                }
                if (tap > 6) {
                        txtFun.dibujarTexto(anchoPantalla / 2 - txtFun.getWidth() / 2, altoPantalla * 3/4, c);
                }
            }
        } catch (NullPointerException ex) {}
    }


    @Override
    public void actualizarFisica() {
        super.actualizarFisica();
        if (tap == 0) {
            if (jIzquierdo != null) {
                tap = 1;
            }
        }

        if (tap == 1) {
            if (jDerecho != null) {
                tap = 2;
            }
        }
    }

    @Override
    protected void inicioRonda(Protagonista protagonista) {
        numeroRonda++;
        protagonista.setVida(100);
        generarZombies(protagonista, 1);
    }

    @Override
    protected void guardaRecords() {
    }
}

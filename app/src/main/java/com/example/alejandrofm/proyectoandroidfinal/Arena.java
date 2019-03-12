package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Arena extends Escena {

    private ArrayList<Zombie> zombies = new ArrayList<>();
    protected Utils utils;
    protected Joystick jIzquierdo, jDerecho;
    protected Protagonista protagonista;
    protected Mapa mapa;
    private Texto txtHealth, txtPuntuation, txtPointsCounter;
    private HealthBar hBar;
    private Bitmap fogonazos[] = new Bitmap[4];
    private int puntuation = 0;
    private ArrayList<Bala> balas = new ArrayList<>();
    private long currentTime, lastBala;
    private Context context;
    private boolean fuego = false;
    private Matrix matrix = new Matrix();
    protected int numeroRonda = 0;
    private boolean inicioRonda = true;
    private HashMap<Integer, PointF> pulsaciones = new HashMap<>();
    private Boton btnMenu = null;
    private Boton btnSalir = null;
    private Texto txtPartidaEnd = null;
    private Sprites spritesZombie;
    protected boolean partidaFinalizada = false;

    /**
     * Inicializa las propiedades de la clase
     * @param anchoPantalla el ancho de la pantalla
     * @param altoPantalla el alto de la pantalla
     * @param context el contexto de la aplicación
     * @param idEscena el id de la escena
     */
    public Arena(int anchoPantalla, int altoPantalla, Context context, int idEscena) {
        super(anchoPantalla, altoPantalla, context, idEscena);
        this.context = context;
        utils = new Utils(context);
        mapa = new Mapa(context, anchoPantalla, altoPantalla);
        protagonista = new Protagonista(anchoPantalla / 2, altoPantalla / 2, anchoPantalla, altoPantalla, efectos, context);
        txtHealth = new Texto(context.getString(R.string.strHealth), anchoPantalla, altoPantalla, context);
        txtPuntuation = new Texto(context.getString(R.string.strPuntuation), anchoPantalla, altoPantalla, context);
        txtPointsCounter = new Texto(String.format("%06d", puntuation), anchoPantalla, altoPantalla, context);
        hBar = new HealthBar(100, anchoPantalla * 2/4, altoPantalla * 2/4, context);
        spritesZombie = new Sprites(Sprites.Tipo.ZOMBIE, anchoPantalla, altoPantalla, context);
        cargaFogonazos();
        currentTime = System.currentTimeMillis();
        lastBala = currentTime + 2000;
    }

    /**
     * Gestiona los eventos OnTouch en esta escena, declara y manda parámetros a los joystick en los que se basan los controles del juego.
     * @param event el evento que se le pasa desde la clase principal
     * @return Siempre devuelve -1 porque no cambia la escena
     */
    @Override
    public int onTouchPersonalizado(MotionEvent event) {
        float x = event.getX(event.getActionIndex());
        float y = event.getY(event.getActionIndex());
        int id = event.getPointerId(event.getActionIndex());
        if (!partidaFinalizada) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:
                    pulsaciones.put(id, new PointF(x, y));
                    if (x < getAnchoPantalla() / 2) {
                        jIzquierdo = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                        jIzquierdo.setIdPuntero(id);
                        jIzquierdo.setPulsado(true);
                        protagonista.setjIzquierdo(jIzquierdo);
                    } else {
                        jDerecho = new Joystick(getContext(), x, y, getAnchoPantalla(), getAltoPantalla());
                        jDerecho.setIdPuntero(id);
                        jDerecho.setPulsado(true);
                        protagonista.setjDerecho(jDerecho);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    pulsaciones.remove(id);
                    if (jIzquierdo != null && jIzquierdo.getIdPuntero() == id) {
                        jIzquierdo.setPulsado(false);
                        jIzquierdo = null;
                    }
                    if (jDerecho != null && jDerecho.getIdPuntero() == id) {
                        jDerecho.setPulsado(false);
                        jDerecho = null;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (int index : pulsaciones.keySet()) {
                        float xx, yy;
                        if (event.getPointerCount() > 1) {
                            xx = event.getX(index);
                            yy = event.getY(index);
                        } else {
                            xx = event.getX(0);
                            yy = event.getY(0);
                        }

                        if (pulsaciones.containsKey(index)) {
                            if (jIzquierdo != null && jIzquierdo.getIdPuntero() == index) {

                                jIzquierdo.setCoordsJFlechas(xx, yy);
                            }
                        }
                        if (pulsaciones.containsKey(index)) {
                            if (jDerecho != null && jDerecho.getIdPuntero() == index) {
                                jDerecho.setCoordsJFlechas(xx, yy);
                                if (jDerecho.getDireccion() != Joystick.Direccion.NINGUNA) {
                                    if (Math.abs(lastBala - currentTime) >= 1000) {
                                        balas.add(new Bala(Bala.TipoMunicion.PISTOLA, jDerecho.getDireccion(), protagonista.getArmaX(), protagonista.getArmaY(), anchoPantalla, altoPantalla, context));
                                        if (efectos) {
                                            soundPool.play(efectoDisparo, 1, 1, 1, 0, 1);
                                        }
                                        lastBala = System.currentTimeMillis();
                                        fuego = true;
                                    }
                                }
                                currentTime = System.currentTimeMillis();
                            }
                        }
                    }
                    break;
            }
        } else {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_DOWN:
                    btnMenu.isPulsado(event);
                    btnSalir.isPulsado(event);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
                    if (btnMenu.isPulsado() && btnMenu.isPulsado(event)) {
                        btnMenu.setPulsado(false);
                        return 0;
                    }
                    if (btnSalir.isPulsado() && btnSalir.isPulsado(event)) {
                        btnSalir.setPulsado(false);
                        System.exit(0);
                    }
            }
        }
        return -1;
    }

    /**
     * Avanza a la siguiente ronda y genera sus zombies correspondientes
     * @param protagonista el protagonista del juego
     */
    protected void inicioRonda(Protagonista protagonista) {
        numeroRonda++;
        generarZombies(protagonista, numeroRonda);
    }

    /**
     * Inicializa y dibuja el texto en el que se cuenta la ronda actual de la partida
     * @param canvas el canvas en el que se dibuja
     */
    private void textoInicioRonda(Canvas canvas) {
        try {
            Texto textoRonda = new Texto(context.getString(R.string.strRound) + " " + numeroRonda, anchoPantalla , altoPantalla, context);
            textoRonda.dibujarTexto(anchoPantalla / 2 - textoRonda.getWidth() / 2, altoPantalla / 2 - textoRonda.getHeight() / 2, canvas);
        } catch (NullPointerException ex) { }
    }

    /**
     * Genera los zombies que se le indican
     * @param protagonista persiguen a este Protagonista
     * @param cantidad numero de zombies en partida
     * @see Zombie
     * @see Protagonista
     */
    protected void generarZombies(Protagonista protagonista, int cantidad) {
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int velocidad = random.nextInt(4 + 1 - 1) + 1;
            double lado = Math.random();
            int posX = -500;
            int posY = -500;
            if (lado < 0.25) {
                posX = -500;
                posY = random.nextInt(altoPantalla + 500 - 0 + (-500)) + (-500);
            } else if (lado < 0.5) {
                posX = random.nextInt(anchoPantalla + 500 - 0 + (-500) + (-500));
                posY = -500;
            } else if (lado < 0.75) {
                posX = anchoPantalla + 500;
                posY = random.nextInt(altoPantalla + 500 - 0 + (-500)) + (-500);
            } else if (lado < 1) {
                posX = random.nextInt(anchoPantalla + 500 - 0 + (-500) + (-500));
                posY = altoPantalla + 500;
            }
            int vida = cantidad;
            zombies.add(new Zombie(posX, posY, velocidad, vida, anchoPantalla, altoPantalla, efectos, context, spritesZombie));
        }
    }

    /**
     * Dibuja en el canvas lo relacionado con la partida
     * @param c es el canvas
     */
    @Override
    public void dibujar(Canvas c) {
        try  {
            c.drawColor(Color.BLACK);
            mapa.dibujaMapa(c);
            if (!partidaFinalizada) {
                if (inicioRonda) {
                    textoInicioRonda(c);
                }
                dibujaBalas(c);
                if (fuego) {
                    dibujaFogonazo(c);
                    fuego = false;
                }
                protagonista.dibujarPersonaje(c);
                dibujaZombies(c);
                txtPointsCounter.dibujarTexto(txtPuntuation.getX() + txtPuntuation.getWidth() + anchoPantalla * 1 / 80, altoPantalla * 1 / 80, c);
                txtHealth.dibujarTexto(anchoPantalla * 1 / 80, altoPantalla * 1 / 80, c);
                hBar.dibujaBar(txtHealth.getWidth() + txtHealth.getWidth() * 1 / 4, altoPantalla * 1 / 80, c);
                txtPuntuation.dibujarTexto(hBar.getX() + hBar.getWidth() + anchoPantalla * 1 / 20, altoPantalla * 1 / 80, c);
                if (jIzquierdo != null) {
                    if (jIzquierdo.isPulsado()) {
                        jIzquierdo.dibujaJoystick(c);
                    }
                }
                if (jDerecho != null) {
                    if (jDerecho.isPulsado()) {
                        jDerecho.dibujaJoystick(c);
                    }
                }
            } else {
                btnMenu.dibujarBoton(anchoPantalla / 2 - btnMenu.getWidth() / 2, altoPantalla * 3/5 - btnMenu.getHeight() / 2, c);
                btnSalir.dibujarBoton(anchoPantalla / 2 - btnSalir.getWidth() / 2, altoPantalla * 4/5 - btnSalir.getHeight() / 2, c);
                txtPuntuation.dibujarTexto(anchoPantalla / 2 - txtPuntuation.getWidth() / 2 - txtPointsCounter.getWidth() / 2, altoPantalla * 2/5 - txtPuntuation.getHeight(), c);
                txtPointsCounter.dibujarTexto(txtPuntuation.getX() + txtPuntuation.getWidth() + anchoPantalla * 1/80, txtPuntuation.getY(), c);
                txtPartidaEnd.dibujarTexto(anchoPantalla / 2 - txtPartidaEnd.getWidth() / 2, altoPantalla * 1/5 - btnMenu.getHeight() / 2, c);
            }
        } catch (NullPointerException ex) { }
    }

    /**
     * Actualiza las propiedades y físicas, comprueba colisiones...
     */
    @Override
    public void actualizarFisica() {
        try  {
            if (!partidaFinalizada) {
                compruebaZombies();
                for (Bala bala : balas) {
                    bala.mueveBala();
                }
                mueveZombies(protagonista);
                hBar.setVida(protagonista.getVida());
                compruebaColisionBalas();
                compruebaFinalPartida();
                compruebaBalas();
            } else {
                balas.clear();
                zombies.clear();
                protagonista = null;
                jIzquierdo = null;
                jDerecho = null;
            }
        } catch (NullPointerException ex) { }
    }

    /**
     * Dibuja todos los zombies de la lista en sus posiciones XY
     * @param c el canvas en el que los dibuja
     */
    private void dibujaZombies(Canvas c) {
        for (Zombie zombie:zombies) {
            zombie.dibujarPersonaje(c);
        }
    }

    /**
     * Mueve los zombies por la Arena
     * @param protagonista el Protagonista al que persiguen
     * @see Protagonista
     * @see Zombie
     */
    private void mueveZombies(Protagonista protagonista) {
        for (Zombie zombie:zombies) {
            zombie.caminar(protagonista);
        }
    }

    /**
     * Dibuja el efecto de fogonazo del arma
     * @param c el Canvas donde lo dibuja
     */
    private void dibujaFogonazo(Canvas c) {
        if (jDerecho != null) {
            switch (jDerecho.getDireccion()) {
                case ESTE:
                    c.drawBitmap(fogonazos[0], protagonista.getArmaX(), protagonista.getArmaY() - fogonazos[0].getHeight() / 2, null);
                    break;
                case OESTE:
                    c.drawBitmap(fogonazos[3], protagonista.getArmaX() - fogonazos[3].getWidth(), protagonista.getArmaY() - fogonazos[3].getHeight() / 2, null);
                    break;
                case NORTE:
                    c.drawBitmap(fogonazos[2], protagonista.getArmaX() - fogonazos[2].getWidth() / 2, protagonista.getArmaY() - fogonazos[2].getHeight(), null);
                    break;
                case SUR:
                    c.drawBitmap(fogonazos[1], protagonista.getArmaX() - fogonazos[1].getWidth() /  2, protagonista.getArmaY(), null);
                    break;
            }
        }
    }

    /**
     * Carga los Bitmap del fogonazo
     */
    private void cargaFogonazos() {
        int grados = 0;
        for (int i = 0; i < fogonazos.length; i++) {
            fogonazos[i] = utils.getBitmapFromAssets("efectos/fogonazo.png");
            fogonazos[i] = Bitmap.createScaledBitmap(fogonazos[i], altoPantalla * 1/10, altoPantalla * 1/10, false);
            matrix.postRotate(grados);
            fogonazos[i] = Bitmap.createBitmap(fogonazos[i], 0, 0, fogonazos[i].getWidth(), fogonazos[i].getHeight(), matrix, true);
            grados += 90;
        }
    }

    /**
     * Dibuja las balas en el Canvas
     * @param c el canvas donde las dibuja
     */
    private void dibujaBalas(Canvas c) {
        for (Bala bala:balas) {
            bala.dibujarBala(c);
        }
    }

    /**
     * Comprueba si alguna bala colisiona con algún zombie
     */
    private void compruebaColisionBalas() {
        Iterator<Zombie> zIteratos = zombies.iterator();
        while (zIteratos.hasNext()) {
            Zombie zombie = zIteratos.next();
            if (zombie.isMurio()) {
                zIteratos.remove();
            } else if (!zombie.isMuriendo()) {
                for (Bala bala : balas) {
                    if (Rect.intersects(bala.getHitbox(), zombie.getHitbox()) && currentTime - zombie.getLastHit() > 900) {
                        zombie.damaged();
                        puntuation += 100;
                        txtPointsCounter.setTexto(String.format("%06d", puntuation));
                        if (zombie.getVida() <= 0) {
                            zombie.setMuriendo(true);
                        }
                        zombie.setLastHit(currentTime);
                    }
                }
            }
        }
    }

    /**
     * Comprueba si queda algún zombie vivo, si no es así inicia una nueva ronda
     */
    private void compruebaZombies() {
        if (zombies.size() == 0) {
            inicioRonda(protagonista);
        }
    }

    /**
     * Comprueba si la partida debe acabar
     */
    private void compruebaFinalPartida() {
        if (protagonista.getVida() <= 0) {
            if (btnMenu == null || btnSalir == null || txtPartidaEnd == null) {
                btnMenu = new Boton(context.getString(R.string.strMenu), anchoPantalla, altoPantalla, efectos, context);
                btnSalir = new Boton(context.getString(R.string.strExit), anchoPantalla, altoPantalla, efectos, context);
                txtPartidaEnd = new Texto(context.getString(R.string.strGameOver), anchoPantalla * 2, altoPantalla * 2, context);
            }
            guardaRecords();
            partidaFinalizada = true;
        }
    }

    protected void guardaRecords() {
        BaseDatos bd = null;
        SQLiteDatabase lite = null;
        try {
            bd = new BaseDatos(context, "records", null, 1);
            lite = bd.getWritableDatabase();
            lite.execSQL("INSERT INTO records (points) VALUES (" + puntuation +")");
        } finally {
            if (lite != null) {
                lite.close();
            }
            if (bd != null) {
                bd.close();
            }
        }
    }

    private void compruebaBalas() {
        Iterator<Bala> iteratorBalas = balas.iterator();
        while (iteratorBalas.hasNext()) {
            Bala bala = iteratorBalas.next();
            if (bala.getX() > anchoPantalla || bala.getY() > altoPantalla || bala.getX() < (bala.getWidth() * -1) || bala.getY() < (bala.getHeight() * -1)) {
                iteratorBalas.remove();
            }
        }
    }
}

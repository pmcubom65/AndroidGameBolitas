package com.example.juegopedromanuelcubomedina;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.media.MediaPlayer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Personaje extends View implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    private GestureDetectorCompat migesture;
    MediaPlayer mp;

    private Bitmap[] mono=new Bitmap[2];
    private Bitmap nino;
    private Bitmap backgroundfondo;
    private Paint score=new Paint();
    private Bitmap[] vida=new Bitmap[2];
    private int monox=10;

    private int monoy;
    private int velocidad=3;
    private int canvasancho, canvasalto;
    public boolean tocar;
    private int resultado;
    private int cocox, cocoy, cocov=20;
    private Paint cocopaint=new Paint();
    private int contador;

    private int coco2x, coco2y, coco2v=20;
    private Paint cocopaint2=new Paint();

    private int rojox, rojoy, rojov=40;
    private Paint rojopaint=new Paint();

    private int ninox, ninoy, ninov=0;

    private int verdex, verdey, verdev=22;
    private Paint paint=new Paint();
    private Paint verdepaint=new Paint();
    private boolean elnino=false;
    long tiempo;
    long tiempofinal;

    public Personaje(Context context)

    {
        super(context);

        this.migesture=new GestureDetectorCompat(getContext(),this);
        migesture.setOnDoubleTapListener(this);

        mono[0]= BitmapFactory.decodeResource(getResources(),R.drawable.mono2);
        mono[1]= BitmapFactory.decodeResource(getResources(),R.drawable.mono1);

        resultado=0;
        backgroundfondo= BitmapFactory.decodeResource(getResources(),R.drawable.fondo2);
        nino= BitmapFactory.decodeResource(getResources(),R.drawable.ninodesnudo);
        cocopaint.setColor(Color.GRAY);
        cocopaint.setAntiAlias(false);

        cocopaint2.setColor(Color.GRAY);
        cocopaint2.setAntiAlias(false);

        rojopaint.setColor(Color.RED);
        rojopaint.setAntiAlias(false);
        verdepaint.setColor(Color.GREEN);
        verdepaint.setAntiAlias(false);



        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);
        vida[0]=BitmapFactory.decodeResource(getResources(),R.drawable.red);
        vida[1]=BitmapFactory.decodeResource(getResources(),R.drawable.white);


        contador=0;
        tiempo=System.currentTimeMillis();
        tiempofinal=0;


        verdev=(MenuActividad.nd.getNivel()==2) ? verdev*2 : verdev;
        rojov=(MenuActividad.nd.getNivel()==2) ? rojov*2 : rojov;
        coco2v=cocov=(MenuActividad.nd.getNivel()==2) ? cocov*2 : cocov;
        ninov=(MenuActividad.nd.getNivel()==2) ? ninov*2 : ninov;
    }

    /**
     *
     *     *
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvasancho=canvas.getWidth();
        canvasalto=canvas.getHeight();

        int minimo=mono[0].getHeight();
        int maximo=canvasalto-minimo;


        int minimon=nino.getHeight();
        int maximon=canvasalto-minimon*3;



        monoy=monoy+velocidad;
        ninox=ninox-ninov;

        canvas.drawBitmap(backgroundfondo,0,0,null);



        if (monoy<minimo) {
            monoy=minimo;
        }
        if (monoy>maximo) {
            monoy=maximo;
        }

        velocidad=velocidad+4;
        ninov=ninov+1;
        if (tocar) {
            canvas.drawBitmap(mono[0],monox,monoy, null);
            tocar=false;

        }else {
            canvas.drawBitmap(mono[1],monox,monoy,null);
        }

//````fs

        cocox=cocox-cocov;
        coco2x=coco2x-coco2v;
        verdex=verdex-verdev;
        ninox=ninox-ninov;
        rojox=rojox-rojov;
        if (haTocado(cocox,cocoy)) {
            resultado++;
            suena();
            cocox=-10;

        }else if (haTocado(coco2x,coco2y)) {
            resultado++;
            suena();
            coco2x=-10;
        } else if (haTocado(verdex,verdey)) {
            resultado=resultado+10;
            suena();
            verdex=-10;
        } else if (haTocado(ninox,ninoy)) {
        resultado=resultado+50;
            suena();
        ninov=0;
        ninox=-10;
    } else if (haTocado(rojox,rojoy)) {
            contador++;
            mp = MediaPlayer.create(getContext(), R.raw.metallic);
            mp.start();
            rojox=-10;
        }


        if (rojox<0) {
            rojox=canvasancho+21;
            rojoy=(int) Math.floor(Math.random()*(maximo-minimo))+minimo;

        }


        if (cocox<0) {
            cocox=canvasancho+21;
            cocoy=(int) Math.floor(Math.random()*(maximo-minimo))+minimo;

        }else if (verdex<0) {
            verdex=canvasancho+21;
            verdey=(int) Math.floor(Math.random()*(maximo-minimo))+minimo;
        }


        if (coco2x<0) {
            coco2x=canvasancho+21;
            coco2y=(int) Math.floor(Math.random()*(maximo-minimo))+minimo;

        }

        if (ninox<0) {
            ninox=canvasancho+21;
            ninoy=(int) Math.floor(Math.random()*(maximon-minimon))+minimon;
        }




        canvas.drawText("Score: "+resultado, 20, 60, score);
        canvas.drawCircle(cocox,cocoy,24,cocopaint);
        canvas.drawCircle(coco2x,coco2y,24,cocopaint2);
        canvas.drawCircle(verdex,verdey,24,verdepaint);
        canvas.drawCircle(rojox,rojoy, 24,rojopaint);



        switch (contador) {
            case 0:
                canvas.drawBitmap(vida[0],580,10,null);
                canvas.drawBitmap(vida[0],680,10,null);
                canvas.drawBitmap(vida[0],780,10,null);
                break;
            case 1:
                canvas.drawBitmap(vida[1],580,10,null);
                canvas.drawBitmap(vida[0],680,10,null);
                canvas.drawBitmap(vida[0],780,10,null);

                break;
            case 2:
                canvas.drawBitmap(vida[1],580,10,null);
                canvas.drawBitmap(vida[1],680,10,null);
                canvas.drawBitmap(vida[0],780,10,null);
                break;
            case 3:
                canvas.drawBitmap(vida[1],580,10,null);
                canvas.drawBitmap(vida[1],680,10,null);
                canvas.drawBitmap(vida[1],780,10,null);


                int puntuacionfinal=resultado;
                    //tiempofinal=System.currentTimeMillis()-tiempo;

                String segundos=String.valueOf((int) Math.round(Math.ceil((System.currentTimeMillis()-tiempo)/1000)));

                    LocalDate ld=LocalDate.now();
                mp.release();
                    DateTimeFormatter dt=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String fecha=ld.format(dt).toString();



                    System.out.println(segundos+" "+fecha);


                    Intent intent=new Intent(getContext(), Final.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("puntuacion",String.valueOf(puntuacionfinal));
                intent.putExtra("segundos",segundos);
                intent.putExtra("fecha", fecha);


                    getContext().startActivity(intent);

                break;

        }



    if (elnino) {
      canvas.drawText("Double tap "+"\n"+"Rescatalo", 40,canvasalto-60,score);
        ninox=canvasancho+21;

        canvas.drawBitmap(nino,ninox,ninoy,null);
        mp = MediaPlayer.create(getContext(), R.raw.din);
        mp.start();

        elnino=false;
    }else {
        canvas.drawBitmap(nino,ninox,ninoy,null);

    }
    if (ninov>300) {
        ninov=300;
    }



    }
//*

    //**


    public void suena() {
        mp = MediaPlayer.create(getContext(), R.raw.coin);
        mp.start();
    }



    public boolean haTocado(int x, int y) {
        if (monox<x && x<(monox+mono[0].getWidth()) && monoy<y && y<(monoy+mono[0].getHeight())) {

            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.migesture.onTouchEvent(event);

        if (event.getAction()==MotionEvent.ACTION_DOWN) {

            tocar=true;
            velocidad=-30;
            ninov=ninov-4;
        }
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        elnino=true;
        ninov=ninov+3;
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    public int getContador() {
        return contador;
    }
}

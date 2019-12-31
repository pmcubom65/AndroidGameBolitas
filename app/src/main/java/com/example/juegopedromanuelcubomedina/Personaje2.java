package com.example.juegopedromanuelcubomedina;

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

public class Personaje2 extends View {


    MediaPlayer mp;

    private Bitmap mono;
    private Bitmap nino;

    private Bitmap backgroundfondo;
    private Bitmap gota;
    private Paint score=new Paint();
    private Bitmap[] vida=new Bitmap[2];
    private int monoy=0;

    private int monox=0;
    private int velocidad=0;
    private int canvasancho, canvasalto;
    public boolean tocar;
    private int resultado;
    private int gotax=30, gotay, gotav=-40;
    private Paint gotapaint=new Paint();
    private int contador;
    long tiempo;
    long tiempofinal;
    Paint paintrect=new Paint();


    private Paint paint=new Paint();


    public Personaje2(Context context) {
        super(context);

        mono= BitmapFactory.decodeResource(getResources(),R.drawable.cup);
        gota=BitmapFactory.decodeResource(getResources(),R.drawable.drop);

        nino= BitmapFactory.decodeResource(getResources(),R.drawable.ninodesnudo);


        resultado=0;
        backgroundfondo= BitmapFactory.decodeResource(getResources(),R.drawable.lluvia);

        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);
        vida[0]=BitmapFactory.decodeResource(getResources(),R.drawable.red);
        vida[1]=BitmapFactory.decodeResource(getResources(),R.drawable.white);

        contador=0;
        tiempo=System.currentTimeMillis();
        tiempofinal=0;



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundfondo,0,0,null);
        canvasancho=canvas.getWidth();
        canvasalto=canvas.getHeight();

        int minimo=mono.getWidth();
        velocidad=4;
        int maximo=canvasancho-mono.getWidth();
        monoy=canvasalto-600;

        monox=monox+velocidad;
        if (monox<minimo) {
            monox=monox+velocidad;
        }
        if (monox>maximo) {
            monox=maximo-velocidad;

        }
        velocidad=velocidad+1;


        if (tocar) {
            canvas.drawBitmap(mono,monox,monoy, null);
            tocar=false;

        }else {
            canvas.drawBitmap(mono,monox,monoy,null);
        }

        gotay=gotay-gotav;
        if (haTocado(gotax,gotay)) {
            resultado++;
            suena();
            gotay=-10;

        } else if (gotay>canvasalto) {
            contador++;
            gotay=-10;
            mp = MediaPlayer.create(getContext(), R.raw.metallic);
            mp.start();

        }


        if (gotay<0) {
            gotay=-10;
            gotax=(int) Math.floor(Math.random()*(maximo-(minimo+7)))+(minimo+7);


        }


        canvas.drawText("Score: "+resultado, 20, 60, score);

      canvas.drawBitmap(gota,gotax,gotay,null);







    paintrect.setColor(Color.BLUE);
    canvas.drawRect(0,canvasalto-300,canvasancho,canvasalto,paintrect);
    canvas.drawBitmap(nino, canvasancho-200, canvasalto-100, null);



        switch (contador) {
            case 0:
                canvas.drawBitmap(vida[0], 580, 10, null);
                canvas.drawBitmap(vida[0], 680, 10, null);
                canvas.drawBitmap(vida[0], 780, 10, null);
                break;
            case 1:
                canvas.drawBitmap(vida[1], 580, 10, null);
                canvas.drawBitmap(vida[0], 680, 10, null);
                canvas.drawBitmap(vida[0], 780, 10, null);

                break;
            case 2:
                canvas.drawBitmap(vida[1], 580, 10, null);
                canvas.drawBitmap(vida[1], 680, 10, null);
                canvas.drawBitmap(vida[0], 780, 10, null);
                break;
            case 3:
                canvas.drawBitmap(vida[1], 580, 10, null);
                canvas.drawBitmap(vida[1], 680, 10, null);
                canvas.drawBitmap(vida[1], 780, 10, null);
                int puntuacionfinal=resultado;

          //      tiempofinal=System.currentTimeMillis()-tiempo;
                String segundos=String.valueOf((int) Math.round(Math.ceil((System.currentTimeMillis()-tiempo)/1000)));
                LocalDate ld=LocalDate.now();
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







    }

    public void suena() {
        mp = MediaPlayer.create(getContext(), R.raw.coin);
        mp.start();
    }



    public boolean haTocado(int x, int y) {
        if (monoy<y && y<(monoy+mono.getHeight()) && monox<x && x<(monox+mono.getWidth())) {

            return true;
        }
        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction()==MotionEvent.ACTION_DOWN) {

            tocar=true;
            velocidad=10;
          monox=(monox<event.getX()) ? (int) event.getX()+velocidad: (int) event.getX()-velocidad;
        }
        return true;
    }











}

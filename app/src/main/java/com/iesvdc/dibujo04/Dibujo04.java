package com.iesvdc.dibujo04;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dibujo04 extends AppCompatActivity /*implements SensorEventListener*/ {
    SensorManager sensorManager;
    Sensor acelerometro;
    Grafico grafico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        grafico = new Grafico(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                grafico.onSensorEvent(event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, acelerometro, SensorManager.SENSOR_DELAY_GAME);



        setContentView(grafico);



    }

    private class Grafico extends View {

        int x = 25;
        int y = 25;
        static final int RADIO_CIRCULO = 25;
        int ancho;
        int alto;
        Paint pincel;


        public Grafico(Context context) {
            super(context);
            pincel = new Paint();
            pincel.setColor(Color.BLUE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            ancho = canvas.getWidth();
            alto = canvas.getHeight();

            canvas.drawCircle(x, y , RADIO_CIRCULO, pincel);
            invalidate();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            ancho = w;
            alto = h;
        }

        public void onSensorEvent (SensorEvent event){
            if (x <=0 + RADIO_CIRCULO){
                x = 0 + RADIO_CIRCULO;
            }
            if(x >= ancho - RADIO_CIRCULO){
                x = ancho - RADIO_CIRCULO;
            }
            if (y <= 0 + RADIO_CIRCULO){
                y = 0 + RADIO_CIRCULO;
            }
            if (y >= alto - RADIO_CIRCULO){
                y = alto - RADIO_CIRCULO;
            }
            x = x - (int) event.values[0];
            y = y + (int) event.values[1];
        }
    }

}

package com.discovry.safe;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Handler;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView speedOfVibration, routeState, lastVibrationSpeed, vibrationsCount;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0,vibrations = 0;
    private float last_x, last_y, last_z;
    Button start = findViewById(R.id.Start);
    private static final int SHAKE_THRESHOLD = 550;
    private void check(){
        final int x = 10;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init for the sensors services
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        speedOfVibration = findViewById(R.id.speedOfVibration);
        routeState = findViewById(R.id.routeState);
        lastVibrationSpeed = findViewById(R.id.lastVibrationSpeed);
        vibrationsCount = findViewById(R.id.vibrationsCounter);
    }

    //most of the coding goes in here
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                    speedOfVibration.setText("Speed of vibration : "+String.valueOf(speed));
                    routeState.setText("Unstable Route");
                    lastVibrationSpeed.setText("Last Vibration Speed : "+String.valueOf(speed));
                    vibrations ++;
                    vibrationsCount.setText("Vibrations Counter : " + vibrations);
                }
                else{
                    speedOfVibration.setText("Speed of vibration : 0");
                    routeState.setText("Stable Route");
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
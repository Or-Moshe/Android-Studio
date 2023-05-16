package com.example.obscalesraceapp.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.obscalesraceapp.Interfaces.StepCallback;

public class StepDetector {

    private Sensor sensor;

    private SensorManager sensorManager;

    private StepCallback stepCallback;

    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateStep(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateStep(float y) {
        if (System.currentTimeMillis() - timestamp > 1000) {
            timestamp = System.currentTimeMillis();
            if (y > 10.0) {
                if (stepCallback != null)
                    stepCallback.stepYRight();
            }
            if (y < 10.0) {
                if (stepCallback != null)
                    stepCallback.stepYLeft();
            }
        }
    }


    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}

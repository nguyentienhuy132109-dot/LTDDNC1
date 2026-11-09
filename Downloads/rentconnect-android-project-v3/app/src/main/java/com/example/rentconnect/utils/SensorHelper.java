package com.example.rentconnect.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorHelper implements SensorEventListener {

    public interface OnShakeListener {
        void onShakeDetected();
    }

    public interface OnLightChangeListener {
        void onLightModeChanged(boolean isDark, float luxValue);
    }

    private final SensorManager sensorManager;
    private final Sensor lightSensor;
    private final Sensor accelerometer;
    private OnShakeListener shakeListener;
    private OnLightChangeListener lightChangeListener;

    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private long mShakeTimestamp;
    private boolean lastIsDarkState = false;

    public SensorHelper(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) : null;
        accelerometer = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }

    public void setOnLightChangeListener(OnLightChangeListener listener) {
        this.lightChangeListener = listener;
    }

    public void register() {
        if (sensorManager != null) {
            if (lightSensor != null) sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            if (accelerometer != null) sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void unregister() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            boolean isDark = lux < 3000.0f;
            if (isDark != lastIsDarkState) {
                lastIsDarkState = isDark;
                if (lightChangeListener != null) {
                    lightChangeListener.onLightModeChanged(isDark, lux);
                }
            }
        }

        if (type == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gForce = (float) Math.sqrt((x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH));

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                if (mShakeTimestamp + 800 > now) {
                    return;
                }
                mShakeTimestamp = now;
                if (shakeListener != null) {
                    shakeListener.onShakeDetected();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}

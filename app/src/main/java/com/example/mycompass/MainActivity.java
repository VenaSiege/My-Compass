package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private ImageView imageView;

    private TextView textViewDegree;
    private TextView textViewDirection;

    float degree;

    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.Compass);
        textViewDegree = findViewById(R.id.CurrentDegree);
        textViewDirection = findViewById(R.id.Direction);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            degree = sensorEvent.values[0];
            CharSequence current_degree = String.valueOf((int)degree);

            imageView.setRotation(degree);
            textViewDegree.setText(current_degree + "°");

            if((int)degree == 0) {
                textViewDirection.setText("北");
            }

            if((int)degree < 360 && (int)degree > 270) {
                textViewDirection.setText("东北");
            }

            if((int)degree == 270) {
                textViewDirection.setText("东");
            }

            if((int)degree < 270 && (int)degree > 180) {
                textViewDirection.setText("东南");
            }

            if((int)degree == 180) {
                textViewDirection.setText("南");
            }

            if((int)degree < 180 && (int)degree > 90) {
                textViewDirection.setText("西南");
            }

            if((int)degree == 90) {
                textViewDirection.setText("西");
            }

            if((int)degree < 90 && (int)degree > 0) {
                textViewDirection.setText("西北");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}

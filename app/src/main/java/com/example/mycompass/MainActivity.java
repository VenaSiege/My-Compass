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

    /**
     * Called when the activity is starting. Initializes the UI components and sets up the sensor manager
     * to listen for orientation sensor events. Binds the compass image, degree text, and direction text
     * views to their respective layout elements.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                          this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
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

    /**
     * SensorEventListener that handles compass sensor changes and updates the UI with
     * the current degree reading and directional information.
     *
     * This listener processes magnetic sensor events to:
     * - Extract the current compass degree value (0-360°)
     * - Rotate the compass image view based on the degree
     * - Display the numeric degree value with the degree symbol
     * - Update the direction text view with Chinese cardinal/intercardinal directions
     *
     * Direction mapping:
     * - 北 (North): 345-360° or 0-15°
     * - 东北 (Northeast): 285-345°
     * - 东 (East): 255-285°
     * - 东南 (Southeast): 195-255°
     * - 南 (South): 165-195°
     * - 西南 (Southwest): 105-165°
     * - 西 (West): 75-105°
     * - 西北 (Northwest): 15-75°
     */
    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            degree = sensorEvent.values[0];
            CharSequence current_degree = String.valueOf((int)degree);

            imageView.setRotation(degree);
            textViewDegree.setText(current_degree + "°");

            if((int)degree >= 345 && (int)degree <= 360 || (int)degree >= 0 && (int)degree <= 15) {
                textViewDirection.setText("北");
            }

            else if((int)degree < 345 && (int)degree > 285) {
                textViewDirection.setText("东北");
            }

            else if((int)degree <= 285 && (int)degree >= 255) {
                textViewDirection.setText("东");
            }

            else if((int)degree < 255 && (int)degree > 195) {
                textViewDirection.setText("东南");
            }

            else if((int)degree <= 195 && (int)degree >= 165) {
                textViewDirection.setText("南");
            }

            else if((int)degree < 165 && (int)degree > 105) {
                textViewDirection.setText("西南");
            }

            else if((int)degree <= 105 && (int)degree >= 75) {
                textViewDirection.setText("西");
            }

            else if((int)degree < 75 && (int)degree > 15) {
                textViewDirection.setText("西北");
            }
        }

        /**
         * Called when the accuracy of a sensor has changed.
         *
         * @param sensor The sensor whose accuracy has changed.
         * @param i The new accuracy of the sensor. This is one of
         *          {@link SensorManager#SENSOR_STATUS_UNRELIABLE},
         *          {@link SensorManager#SENSOR_STATUS_ACCURACY_LOW},
         *          {@link SensorManager#SENSOR_STATUS_ACCURACY_MEDIUM}, or
         *          {@link SensorManager#SENSOR_STATUS_ACCURACY_HIGH}.
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}

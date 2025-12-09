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


    private class CompassSensorEventListener implements SensorEventListener {
        /**
         * Handles sensor events when the device's orientation changes.
         *
         * Updates the compass UI with the current device orientation in degrees and displays
         * the corresponding cardinal/intercardinal direction in Chinese characters.
         *
         * @param sensorEvent the sensor event containing orientation data. Must not be null
         *                    and must be of type Sensor.TYPE_ORIENTATION with at least one value
         *
         * The method performs the following operations:
         * - Extracts the degree value from the sensor event (azimuth angle from 0-360)
         * - Rotates the compass imageView to match the current orientation
         * - Displays the current degree value in the textViewDegree UI element
         * - Determines and displays the cardinal direction (N, NW, W, SW, S, SE, E, NE)
         *   in Chinese in the textViewDirection UI element based on the degree ranges
         *
         * Direction mappings:
         * - 北 (North): 345-360° or 0-15°
         * - 东北 (NE): 15-75°
         * - 东 (East): 75-105°
         * - 东南 (SE): 105-165°
         * - 南 (South): 165-195°
         * - 西南 (SW): 195-255°
         * - 西 (West): 255-285°
         * - 西北 (NW): 285-345°
         */
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if (sensorEvent != null && sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION && sensorEvent.values != null && sensorEvent.values.length > 0) {
                degree = sensorEvent.values[0];
                CharSequence current_degree = String.valueOf((int)degree);

                imageView.setRotation(degree);
                textViewDegree.setText(current_degree + "°");
            }

            int d = ((int) degree + 360) % 360; // normalize and cast once

            if ((d >= 345 && d <= 360) || (d >= 0 && d <= 15)) {
                textViewDirection.setText("北");
            }
            else if (d < 345 && d > 285) {
                textViewDirection.setText("西北");
            }
            else if (d <= 285 && d >= 255) {
                textViewDirection.setText("西");
            }
            else if (d < 255 && d > 195) {
                textViewDirection.setText("西南");
            }
            else if (d <= 195 && d >= 165) {
                textViewDirection.setText("南");
            }
            else if (d < 165 && d > 105) { 
                textViewDirection.setText("东南");
            }
            else if (d <= 105 && d >= 75) {
                textViewDirection.setText("东");
            }
            else if (d < 75 && d > 15) {
                textViewDirection.setText("东北");
            }
            else {
                textViewDirection.setText("未知"); // Fallback
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
    }

    private CompassSensorEventListener sensorEventListener = new CompassSensorEventListener();
}

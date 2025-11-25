package com.example.compasslevel

import android.content.Context
import android.hardware.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.*

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    // Raw sensor arrays
    private val accelData = FloatArray(3)
    private val magnetData = FloatArray(3)

    // Exposed to Compose via mutable states
    private val _azimuth = mutableStateOf(0f)
    private val _roll = mutableStateOf(0f)
    private val _pitch = mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Register accelerometer, magnetometer, gyroscope
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }

        setContent {
            CompassLevelApp(
                azimuth = _azimuth.value,
                roll = _roll.value,
                pitch = _pitch.value
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {

            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, accelData, 0, event.values.size)
                calculateOrientation()
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, magnetData, 0, event.values.size)
                calculateOrientation()
            }

            Sensor.TYPE_GYROSCOPE -> {
                // Gyroscope gives rotation speed — integrate small shifts
                _roll.value += event.values[1] * 2      // small multiplier for visibility
                _pitch.value += event.values[0] * 2
            }
        }
    }

    private fun calculateOrientation() {
        val rotationMatrix = FloatArray(9)
        val orientationVals = FloatArray(3)

        val success = SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelData,
            magnetData
        )

        if (success) {
            SensorManager.getOrientation(rotationMatrix, orientationVals)
            val azimuthRadians = orientationVals[0]
            val azimuthDegrees = ((Math.toDegrees(azimuthRadians.toDouble()) + 360) % 360).toFloat()
            _azimuth.value = azimuthDegrees
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun CompassLevelApp(azimuth: Float, roll: Float, pitch: Float) {

    val animatedHeading = animateFloatAsState(targetValue = azimuth).value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF101820)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                "Compass & Digital Level",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            /* ---------------- Compass ---------------- */
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .background(Color(0xFF1E2A38), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(200.dp)) {
                    rotate(animatedHeading) {
                        drawLine(
                            color = Color.Red,
                            start = center,
                            end = center.copy(y = center.y - 80),
                            strokeWidth = 10f
                        )
                        drawLine(
                            color = Color.White,
                            start = center,
                            end = center.copy(y = center.y + 80),
                            strokeWidth = 8f
                        )
                    }
                }
                Text(
                    "${animatedHeading.roundToInt()}°",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            /* ---------------- Digital Level ---------------- */
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Digital Level", color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Text("Roll:  ${roll.roundToInt()}°", color = Color.White)
                Text("Pitch: ${pitch.roundToInt()}°", color = Color.White)
            }
        }
    }
}

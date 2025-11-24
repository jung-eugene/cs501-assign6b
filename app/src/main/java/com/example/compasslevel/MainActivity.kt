package com.example.compasslevel

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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CompassLevelApp() }
    }
}

@Composable
fun CompassLevelApp() {
    // Simulated values (instead of real sensors)
    var heading by remember { mutableStateOf(0f) }   // Compass heading
    var roll by remember { mutableStateOf(0f) }      // Gyroscope roll
    var pitch by remember { mutableStateOf(0f) }     // Gyroscope pitch

    // Rotation animation for fun
    val animatedHeading = animateFloatAsState(targetValue = heading).value

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

            Text("Compass & Digital Level",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            /* ------------------- Compass Section ------------------- */
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(Color(0xFF1E2A38), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(200.dp)) {
                    rotate(animatedHeading) {
                        // Compass arrow
                        drawLine(
                            color = Color.Red,
                            start = center,
                            end = center.copy(y = center.y - 80),
                            strokeWidth = 8f
                        )
                        drawLine(
                            color = Color.White,
                            start = center,
                            end = center.copy(y = center.y + 80),
                            strokeWidth = 6f
                        )
                    }
                }
                Text("${animatedHeading.roundToInt()}°",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            /* ----------------- Digital Level Section ---------------- */
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Digital Level", color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Text("Roll:  ${roll.roundToInt()}°", color = Color.White)
                Text("Pitch: ${pitch.roundToInt()}°", color = Color.White)
            }

            /* ------------------ Simulation Controls ------------------ */
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Simulate Compass Heading", color = Color.White)
                Slider(
                    value = heading,
                    onValueChange = { heading = it },
                    valueRange = 0f..360f
                )

                Spacer(Modifier.height(24.dp))
                Text("Simulate Tilt (Gyroscope)", color = Color.White)
                Text("Roll")
                Slider(
                    value = roll,
                    onValueChange = { roll = it },
                    valueRange = -45f..45f
                )
                Text("Pitch")
                Slider(
                    value = pitch,
                    onValueChange = { pitch = it },
                    valueRange = -45f..45f
                )
            }
        }
    }
}

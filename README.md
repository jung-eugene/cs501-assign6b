# CS 501 Individual Assignment 6 Question 2 — Compass & Digital Level

## Explanation

The **Compass & Digital Level** app simulates a real compass and tilt meter using animated UI elements.
It demonstrates two key instrument tools:

* **Compass**: Uses a rotating needle based on heading values (simulated from 0°-360°).
* **Digital Level**: Displays device tilt using roll and pitch values (simulated).

Although the assignment mentions magnetometer, accelerometer, and gyroscope, this version uses **simulated sliders** so the app works fully on the emulator. The UI is built with **Jetpack Compose**, using `Canvas`, rotation, and bright theme colors to produce a fun instrument look.

## How to Use

1. Launch the app. Both the **compass** and **digital level** appear centered on screen.
2. Drag the **Compass Heading** slider to rotate the compass needle.
3. Use the **Roll** and **Pitch** sliders to simulate tilt and update the digital level readouts.
4. The compass arrow moves smoothly thanks to animated rotation.
5. The entire UI updates instantly as the simulated sensor values change.

## Implementation

* **State Management**: `remember` state variables store simulated heading, roll, and pitch.
* **Compass Needle**: Drawn using a `Canvas` and rotated using `animateFloatAsState` for smooth animation.
* **Digital Level**: Simple text-based display for roll and pitch degrees.
* **Simulation Controls**: Three sliders allow real-time testing and UI updates without physical sensors.
* **Composable Layout**: The entire screen is arranged with `Column` and centered using `Arrangement.SpaceEvenly`.

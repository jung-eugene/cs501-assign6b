# CS 501 Individual Assignment 6 Question 2 — Compass & Digital Level

## Explanation

The **Compass & Digital Level** app uses actual device sensors to act like a real navigation instrument. It combines data from three sensors:
* **Magnetometer + Accelerometer** → calculates the compass heading in degrees.
* **Gyroscope** → tracks roll and pitch to show a live digital level.

The compass needle rotates smoothly using Compose animations, and the digital level updates in real time as the device is tilted. When running on the emulator, values can be controlled using **Extended Controls → Virtual Sensors**.

## How to Use

### **Using an Emulator**

Android Studio includes a Virtual Sensor panel that lets you simulate real device movement.

1. Run the app on an Android Emulator.
2. Open **Extended Controls** → **Virtual Sensors**
3. Use the rotation sliders:
   * **Z-Rot** → rotates compass heading (turn left/right)
   * **X-Rot** → tilts forward/backward (pitch)
   * **Y-Rot** → tilts left/right (roll)
4. Watch the UI update instantly:
   * The **compass needle rotates** as Z-Rot changes.
   * **Roll and pitch numbers change** as X- and Y-Rot change.

### **Using a Physical Device**

1. Install the app on a real phone with magnetometer, accelerometer, and gyroscope.
2. Rotate the phone:
   * Turning left/right changes compass heading.
   * Tilting the phone adjusts roll and pitch values.
3. The compass needle and digital level update automatically.

## Implementation

* **SensorManager** registers listeners for:
  * `TYPE_ACCELEROMETER`
  * `TYPE_MAGNETIC_FIELD`
  * `TYPE_GYROSCOPE`
* **Compass Heading** is calculated using:
  * `getRotationMatrix()`
  * `getOrientation()`
  * Converted from radians → degrees and normalized to 0°–360°.
* **Digital Level** integrates gyroscope rotation values to produce roll and pitch updates.
* **UI** uses:
  * A **Canvas** to draw the compass needle
  * `animateFloatAsState()` for smooth rotation
  * Centered layout with simple dark theme styling.

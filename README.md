# LightRing

Device is a 3D printed cylinder holding a 24-LED NeoPixel ring.
Inside is an Arduino Nano connected to an HC05 bluetooth module, a button and a piezo buzzer.
The device acts as a display, as it only responds to commands based on bluetooth input.

The Android application is the driver for the device.
It has several mini-games and applications which send pixel data to the device for display.
The two-way communication allows for real-time interaction with both phone and device.
Because the device only recieves pixel information, only the Android app needs to be updated to add new games.

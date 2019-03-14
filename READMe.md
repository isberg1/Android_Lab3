# Lab 03: Throwing a virtual ball upwards

## using the app

This app reads and calculates the mobile devices acceleration when moved rapidly, the values
retrived are then used to throw a ball into the air. note that the height is based on
max acceleration, not max force applied.

The maximum height of the ball is recorded as a highscore.
when the ball movement animation is playing the toolbar will turn red-pink to indicate it is
not recording any new acceleration values. when the ball has landed, the toolbar will turn
green, indicating that you may throw the ball again.

at the start and end of a throw, the mobile device will vibrate. and when the ball is at it's
top position å sound is played.

to the left of the balls starting position is a current height display field it updates in
realtime.

in the top right corner you may go to the settings screen and change settings for:
* MIN_ACC value the minimum acceleration needed to start a throw
* SLIDING_WINDOW size the number of measurements taken before a throw can happen
* SELECT BACKGROUND the background image in MainActivity
* RESET HIGHSCORE button resets the ball throw height highscore


## Technical implementation

In order to make the app I have made/modified the following files:
* app/java/com.e.android_lab3/MainActivity
* app/java/com.e.android_lab3/Settings
* app/java/com.e.android_lab3/Utilities
* app/res/drawable/ball.ping
* app/res/drawable/ball.png
* app/res/drawable/height.PNG
* app/res/drawable/italy.jpg
* app/res/drawable/nature.jpg
* app/res/layout/activity_main.xml
* app/res/layout/activity_settings.xml
* app/res/raw/ping.mp3
* app/res/values/strings.xml
* app/manifests/AndroidManifests.xml





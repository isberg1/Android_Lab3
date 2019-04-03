# Lab 03: Throwing a virtual ball upwards

this app was made using a Samsung galaxy S5 with android 6.0.1

## using the app

This app reads and calculates the mobile devices acceleration when moved rapidly, the values
retrieved are then used to throw a ball into the air. note that the height is based on
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


## Bonus Feature

i have added support for localization by making all strings displayed on screen in
English(default) and Norwegian. in order to test this. you must first force stop the app. then go to 
android settings and change language to/from English or Norwegian. then you must restart the 
app(changing language at runtime may at time cause weird things happen)


Made allot of comments in javaDoc format. to test this open the 'Tools' tab in Android Studio. 
and click 'Generate JaveDoc...'. select our output directory, move the slider under to 
'private' (top position) and click 'OK'. the JavaDoc HTML files
can then be opened in a web browser. 


the app provides haptic feedback(vibrates) in the person hand, 
when the ball leaves the hand (the highest acceleration reached),
and then, again, when the ball comes back down to the same location 
from where it has been thrown.


the ability to change background image



## Technical implementation

i have modularized as much as i can by placing anything nor UI related into a 
separate 'Utilities' Class. 

i made som tests in app/java/com.e.android_lab3(androidTest)/MainActivityTest


In order to make the app I have made/modified the following files:
(set the project structure in android studio to Android mode)
* app/java/com.e.android_lab3/MainActivity
* app/java/com.e.android_lab3/Settings
* app/java/com.e.android_lab3/Utilities
* app/java/com.e.android_lab3(androidTest)/MainActivityTest
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





# visual-toolbox
An open-source Android application, that provides vibration feedback based on the level of ambient light detected.

Visual toolbox was developed at Queen's University, as a design project for the second-year computer engineering course, APSC-200. The application interface was designed with the needs of the visually impaired in mind, and can be used in in conjunction with Talk Back.

#### Application

Visual Toolbox presents two features that can be used by visually impaired individuals to better understand their environment. For example, the Light Sensor can be used to guide a visually impaired person to a light they may have left on, while the colour sensor can help them sort their white and dark clothes before doing laundry.

### Light Sensor
The ambient light value detected by the device light sensor is displayed in the Light Sensor tab, and is updated in real time. When the light value changes, the user receives physical feedback via a vibration beat. When the ambient light value begins to increase, the delay between vibrations becomes shorter, and the beat becomes faster. Vice-versa, when the light value begins to drop, the beat becomes slower. 

### Colour Analysis
The average colour of an item is detected by the rear camera. A camera preview is available in the Colour Analysis tab fragment. Upon tapping the 'Analyze' button, a Bitmap of the camera preview is generated, and the average colour of is detected via a colour analysis function, that scans through the Bitmap. The resulting colour and name are then displayed below the camera preview 'Analyze' button.

**Note:** As of December 4th, 2015, the colour analysis feature has not been enabled.

# Team

Developed by Zachary Yale.

ECE Section 205, Team 8: Zachary Yale, Henry Li, Wyatt Wood, Pascal Michaud.

#References

https://gist.github.com/tskulbru/12ab15e81b87a34a4c48

http://stackoverflow.com/questions/8471236/finding-the-dominant-color-of-an-image-in-an-android-drawable

http://tech.thecoolblogs.com/2013/02/get-bitmap-image-from-yuv-in-android.html#ixzz3t95j6hYc

# Screenshots
<img src="http://i.imgur.com/mqwv6w8.png" width="432" height="768" />
<img src="http://i.imgur.com/VGNGWhN.jpg" width="432" height="768" />

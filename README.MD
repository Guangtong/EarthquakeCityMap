# UCSDEarthquakeCityMap
## Background
* This is the homework of Coursera Course: Object Oriented Programming in Java from UCSD
* Module 1 to 5 are solutions to homework assignment 
  * Earthquakes and cities are drawn aas markers on the map
  * The applet extends "PApplet" class provided by "Processing" 
  * The map and markers are drawn using UnfoldingMaps package
  * The earthquake and city markers respond to hover event by showing names and information of the marker
  * The earthquake and city markers respond to click event by showing influenced cities of the clicked earthquake or earthquakes influencing the clicked city, with all other markers hidden
* Module 6 is my own improvement to make it more practical in use.

## Improvement
  * Draw influence circle of every earthquake when it is clicked or when the influencing city is clicked
  * The text label of a marker is displayed on top in hovering event
  * Keyborad filter for earthquakes in different ages (With indicators under the key area)
  * Heatmap of earthquakes
  * Map canvas size can change according to the window, but fix when zooming the map. 

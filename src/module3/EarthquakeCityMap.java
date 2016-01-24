package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    ArrayList<Marker> markers = new ArrayList<Marker>(326);
	    

	    
	    

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");			//every unknown class object can use Object at first
	    	float mag = Float.parseFloat(magObj.toString());	//every object has a toString. 
	    														//In this way you don't need to know the absolute class
	    	
	    	// PointFeatures also have a getLocation method	
	    }
	    
	    
	    System.out.println(markers.size());// print result: 0
	    markers.ensureCapacity(earthquakes.size());
	    System.out.println(markers.size()); // print result: 0
	    
	    
	    
	    
	    
	    for(PointFeature earthquake : earthquakes) {
	    
	    	Object magObj = earthquake.getProperty("magnitude");			//every unknown class object can use Object at first
	    	float mag = Float.parseFloat(magObj.toString());	//every object has a toString. 
	    	
	    	int radius;
	    	int colorLevel;
	    	
	    	if(mag<4.0) {
	    		radius = 10;
	    		colorLevel = color(0,0,250);
	    	}else if(mag>=5.0) {
	    		radius = 30;
	    		colorLevel = color(250,0,0);
	    	}else {
	    		radius = 20;
	    		colorLevel = color(250,250,0);
	    	}
	    	
	    	SimplePointMarker earthquakePoint = createMarker(earthquake);
	    	earthquakePoint.setRadius(radius);
	    	earthquakePoint.setColor(colorLevel);
	    	
	    	markers.add(earthquakePoint);
		    System.out.println(markers.size());	    	

	    }
	    
	    map.addMarkers(markers);
	    
	    
	    //TODO: Add code here as appropriate
	    
	    
	    
	    
	    
	    
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(color(250,250,250));
		rect(25,50,150,250);
		fill(color(0,0,0));
		textSize(15);
		text("Earthquake key", 50,90);
		
		textSize(12);
		text("5.0+ Magnitude", 75, 140);
		text("4.0+ Magnitude", 75, 190);
		text("Below 4.0", 75,240);
		
		fill(color(250,0,0));
		ellipse(50,135,30,30);
		fill(color(250,250,0));
		ellipse(50,185,20,20);
		fill(color(0,0,250));
		ellipse(50,235,10,10);
		
	}
}

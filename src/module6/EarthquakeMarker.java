package module6;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.GeoUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;;


/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 *
 */
// TODO: Implement the comparable interface
public abstract class EarthquakeMarker extends CommonMarker implements Comparable<EarthquakeMarker>
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;
	
	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	//protected float radius;
	
	
	// constants for distance
	protected static final float kmPerMile = 1.6f;
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors

	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
		
	
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = (float) properties.get("magnitude");
		//float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 2f*getMagnitude();
	}
	
	// TODO: Add the method:
	public int compareTo(EarthquakeMarker other)
	{
		if(this.getMagnitude()>other.getMagnitude())
			return -1;
		else if(this.getMagnitude()<other.getMagnitude())
			return 1;
		else
			return 0;
		
	}
	
	
	// calls abstract method drawEarthquake and then checks age and draws X if needed
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		colorDetermine(pg);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		if(getClicked()||isSelected())
		{
			float d = 2 * threatCircleRadius();
			pg.noFill();
			pg.ellipse(x,y,d,d);
		}
		
		// IMPLEMENT: add X over marker if within past day		
		String age = getStringProperty("age");
		if ("Past Hour".equals(age) || "Past Day".equals(age)) {
			
			pg.strokeWeight(2);
			int buffer = 2;
			pg.line(x-(radius+buffer), 
					y-(radius+buffer), 
					x+radius+buffer, 
					y+radius+buffer);
			pg.line(x-(radius+buffer), 
					y+(radius+buffer), 
					x+radius+buffer, 
					y-(radius+buffer));
			
		}
		
		// reset to previous styling
		pg.popStyle();
		
	}

	/** Show the title of the earthquake if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		String title = getTitle();
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(title) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3 , y +18);
		
		
		pg.popStyle();
		
	}

	
	/**
	 * Return the "threat circle" radius, or distance up to 
	 * which this earthquake can affect things, for this earthquake.   
	 * DISCLAIMER: this formula is for illustration purposes
	 *  only and is not intended to be used for safety-critical 
	 *  or predictive applications.
	 */
	public float threatCircle() {	
		float miles = (float) (20.0f * Math.pow(1.8, 2*getMagnitude()-5));
		float km = (miles * kmPerMile);
		return km;
	}
	

	
	// determine color of marker from depth
	// We use: Deep = red, intermediate = blue, shallow = yellow
	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
		
		if (depth < THRESHOLD_INTERMEDIATE) {
			pg.fill(255, 255, 0);
		}
		else if (depth < THRESHOLD_DEEP) {
			pg.fill(0, 0, 255);
		}
		else {
			pg.fill(255, 0, 0);
		}
	}
	
	
	/** toString
	 * Returns an earthquake marker's string representation
	 * @return the string representation of an earthquake marker.
	 */
	public String toString()
	{
		return getTitle();
	}
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	

	public float threatCircleRadius() {
		
		float radius = 0;
		
		Location centerLocation = this.getLocation();
		Location upperLocation = GeoUtils.getDestinationLocation(centerLocation, 0, threatCircle());
//		SimplePointMarker upperMarker = new SimplePointMarker(upperLocation);
//		map.addMarker(upperMarker);
		
		ScreenPosition center = map.getScreenPosition(centerLocation);
		ScreenPosition upper = map.getScreenPosition(upperLocation);
		radius = PApplet.dist(center.x, center.y, upper.x, upper.y);		
		
		return radius;
	}


	
}

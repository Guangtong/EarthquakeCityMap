package module5;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	

	/** Draw the earthquake as a square */
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.rect(x-radius, y-radius, 2*radius, 2*radius);
		if(this.getClicked())
		{
			pg.pushStyle();
			for(CommonMarker marker : affectedMarkers) 
			{
				//Even if I import affected city markers into EarthquakeMarker, how can I get there ScreenPosition
				//The map is private in EarthquakeCityMap PApplet, who to refer to the map?
				// I have to record the screen position of every affectedMarkers!
				float xx = marker.getScreenPosition().x;
				float yy = marker.getScreenPosition().y;
				
				pg.strokeWeight(2);
				pg.line(x,y,xx,yy);			// the line is not right...
			}
			pg.popStyle();
		}
	}
	

	

}


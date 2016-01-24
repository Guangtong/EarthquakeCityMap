package MyPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.*;


public class LifeExpectancy extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UnfoldingMap map;
	
	private Map<String, Float> lifeExpMap;		
	//Map is a interface, implemented by other subclasses, such as Hashmap, Linked bindings, Treemap...
	
	List<Feature> countries = new ArrayList<Feature>();
	List<Marker> countryMarkers = new ArrayList<Marker>(); // type is needed?
	
	public void setup() {
		
		//1 setup map which is an UnfoldingMap Object
		size(800,600,OPENGL);
		map = new UnfoldingMap(this, 50,50,700,500,new Google.GoogleMapProvider()); // x,y, width, length
		MapUtils.createDefaultEventDispatcher(this, map);  // used for zoom and go around
		
		//2 Read Data from csv to a Map object, which is implemented as HashMap
		lifeExpMap = loadLifeExpentancyFromCSV("../../data/LifeExpectancyWorldBankModule3.csv");
		
		//3 Display the data on the map: 
		
		//graphical information of each country
		
		//Class Feature: stores location's type and properties
		//Feature has only one field, which is : HashMap properties
		//Methods include: getId, setId, getProperty, setProperty
		
		//geographical description of countries, 
		countries = GeoJSONReader.loadData(this, "../../data/countries.geo.json");


		
		
		
		//Interface Marker: Markers are what is drawn on to maps.
		// created by countries geographical properties
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		
		map.addMarkers(countryMarkers);
		shadeCountries();
		
		
		
		
		
	}
	
	
	
	
	private void shadeCountries() {
		// TODO Auto-generated method stub
		for (Marker marker : countryMarkers) {			//class instance variable is global in the whole class
			String countryId = marker.getId();

			if (lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				
				marker.setColor(color(255-colorLevel,100,colorLevel));
				
				
			}
			else {
				marker.setColor(color(150,150,150));
				
			}
		}
		
	}




	private Map<String, Float> loadLifeExpentancyFromCSV(String filename) {
		//this is called a helper method, used locally so it's private

		Map<String, Float> Country_LifeExpectancy = new HashMap<String,Float>();	
		// Use subclass constructor
		//no need to know its inside
		
		String[] rows = loadStrings(filename);
		//PApplet method: loadStrings -> Reads the contents of a file and creates a String array of its individual lines
		
		for(String row : rows) {
		// for each syntax, build a local variable row
			
			String[] columns = row.split(",");
			//split is a String method, divide the string to a string by the marker
			
			if (columns.length == 6 && !columns[5].equals("..")) {
				
				//"Life expectancy at birth, total (years)",SP.DYN.LE00.IN,Austria,AUT,80.890243902439
				
				float value = Float.parseFloat(columns[5]); 	
				// columns[5] is life expectancy, need to change to float
				
				Country_LifeExpectancy.put(columns[4], value);
				// columns[4] is CountryID, which is a string, needn't change type
				
				
				
			}
		}

		return Country_LifeExpectancy;
	}

	public void draw() {
		map.draw();
	}
	
	
}

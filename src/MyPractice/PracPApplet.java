package MyPractice;
import processing.core.*;


// draw a sun, changing color with seconds

public class PracPApplet extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String picname = "palmTrees.jpg";
	private PImage backgroundImg;
	private int yellow = 200;
	
	public void setup() {   // configure the canvas
		size(400, 400);
		backgroundImg = loadImage(picname,"jpg");
		backgroundImg.resize(0, height);
		
	}
	public void draw() {
		
		
		image(backgroundImg, 0, 0);
		int[] color = sunColorSec(second());
		fill(color[0], color[1], color[2]);
		ellipse(width/4,height/5,width/5,height/5);
		fill(0,0,0);
		ellipse(width/4-width/20, height/5-height/30, width/30,height/20);
		ellipse(width/4+width/20, height/5-height/30, width/30,height/20);
		noFill();
		arc(width/4,height/5+height/30,width/20,height/20, 0, PI);
		

		
		
		
		
	}   //it loops by PApplet
	
	
	public int[] sunColorSec(float seconds) {
	
		int[] rgb = new int[3];
		float diffFrom30 = Math.abs(30-seconds);
		float ratio = diffFrom30/30;
		rgb[0] = (int) (255*ratio);
		rgb[1] = (int) (255*ratio); //30 as the noon, ratio=1
		rgb[2] = 0;
		return rgb;
		
	}
	
	
	
	
	

}


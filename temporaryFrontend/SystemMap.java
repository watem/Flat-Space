package FlatSpace.temporaryFrontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import FlatSpace.Controller.SpatialController;
import FlatSpace.Controller.TO.Coordinates;
import FlatSpace.Controller.TO.TOBody;
import FlatSpace.Controller.TO.TOSystem;

public class SystemMap extends JPanel {

	

	private TOSystem system;
	private List<TOBody> bodies;
	private Map<TOBody, Coordinates> bodyLocations;
	private String focus = "coords";
	private TOBody focusedBody;
	private Coordinates focusedCoords = new Coordinates(0,0);
//	private TOFleet focusedFleet;
	
	private List<Ellipse2D.Double> drawnBodies;
	
	private double zoomLevel = 3;
	private int minShownSize = 5;
	
	
	
	public SystemMap() {
	}

	public SystemMap(TOSystem system, List<TOBody> bodies) {
		this.system = system;
		this.bodies = bodies;
	}

	private void doDrawing(Graphics g){
		bodyLocations = SpatialController.getAllLocations(system.getId());
		Graphics2D background = (Graphics2D) g.create();
		background.setColor(Color.WHITE);
		Rectangle2D background_1 = new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight());
		background.draw(background_1);
		background.fill(background_1);
		
	    Graphics2D g2d = (Graphics2D) g.create();
	    BasicStroke aStroke = new BasicStroke(2);
	    g2d.setStroke(aStroke);
	    g2d.setColor(Color.BLACK);
	    Rectangle2D boundingBox = new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight());
	    g2d.draw(boundingBox);
	    for(TOBody body:bodies) {
	    	Color c = body.getC();
	    	if (c==null) {
	    		
	    	}
	    	g2d.setColor(c);
	    	double d = 2*zoomScale(body.getRadius());
	    	Coordinates co = bodyLocations.get(body);
	    	Pixels pos = toPixel(bodyLocations.get(body));
	    	if (pos.x+d/2>0 && pos.y+d/2>0 && pos.x-d/2<this.getWidth() && pos.y-d/2<this.getHeight()) {
	    		g2d.fill(new Ellipse2D.Double(pos.x-d/2, pos.y-d/2, d, d));
	    	}
	    }
	    
	    
	}
	
	public void refresh() {
		
		repaint();
	}
	
	
	
	
	
	
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
			doDrawing(g);
	  }
	
	public Pixels toPixel(Coordinates coords) {
		Dimension mapSize = this.getSize();
		Coordinates centreCoords;
		if (focus.equals("coords")) {
			centreCoords = focusedCoords;
		} else if (focus.equals("body")) {
			centreCoords = bodyLocations.get(focusedBody);
		} else if (focus.equals("fleet")) {
//			focusCoords = fleetLocations.get(FocusedFleet);
			throw new RuntimeException("not (yet) a valid focus point");
		} else {
			throw new RuntimeException("not a valid focus point");
		}
		int distanceFromFocusX =(int) (zoomScale(coords.getX()-centreCoords.getX())), distanceFromFocusY =(int) -(zoomScale(coords.getY()-centreCoords.getY()));
		Pixels focusLocation = new Pixels(mapSize.width/2, mapSize.height/2);
		Pixels targetLocation = new Pixels(focusLocation.x+distanceFromFocusX,(focusLocation.y+distanceFromFocusY));
		return targetLocation;
		
	}
	public double zoomScale(double distance) {
		double size = distance*zoomLevel;
//		if (size<minShownSize) {
//			return minShownSize;
//		}
		return distance*zoomLevel;
		
	}

	public class Pixels {
		int x, y;
		public Pixels(int a, int b) {
			x=a;
			y=b;
		}

	}

	public double getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
		refresh();
	}

	public void setSystem(TOSystem system) {
		this.system = system;
		this.bodies = SpatialController.getAllBodies(system.getId());
		refresh();
	}
}

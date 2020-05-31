package flatSpace.backendData.planarSpace;

import flatSpace.backendData.game.GameSettings;
import flatSpace.backendData.stellarBodies.PlanarEntrance;

public class PlanarSidePortal {
	private PlanarEntrance entrance;
	private PlanarLocation location;
	private int id;
	private PlanarSpace plane;
	
	public PlanarSidePortal(double x, double y, PlanarSpace plane) {
		this.plane = plane;
		setXY(x,y);
	}
	public PlanarSidePortal(double angle, int ring, PlanarSpace plane) {
		this.plane = plane;
		GameSettings settings = plane.getGame().getGameSettings();
		double rad = settings.getFlatMinRadialDistance()+(ring-1)*settings.getFlatRadialDistance();
		double x = rad*Math.cos(angle);
		double y = rad*Math.sin(angle);
		setXY(x,y);
	}
	
	private void setXY(double x, double y) {
		location = new PlanarLocation();
		location.setX(x);
		location.setY(y);
	}
	public PlanarEntrance getEntrance() {
		return entrance;
	}
	public void setEntrance(PlanarEntrance entrance) {
		this.entrance = entrance;
	}
	public PlanarLocation getLocation() {
		return location;
	}
	public int getId() {
		return id;
	}
	
	
	
}

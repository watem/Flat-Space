package flatSpace.backendData.game;

import java.awt.Color;
import java.util.List;

import flatSpace.backendData.stellarBodies.Body;
import flatSpace.backendData.stellarBodies.StellarSystem;

public class SystemFactory {
	private FlatSpaceGame game;
	SystemBuilder builder = new SystemBuilder();
	private int nextSystemId = 0;
	
	protected SystemFactory(FlatSpaceGame game) {
		this.game = game;
	}

	protected StellarSystem create() {
		List<String> nameList = game.getNames().getSystemNames();
		if (nameList.size()<1) {
			throw new OutOfNamesException("no system names left to select from");
		}
		int index =(int)Math.floor(Math.random()*nameList.size());
		String name = nameList.get(index);
		nameList.remove(index);
		StellarSystem system = new StellarSystem(name);
		system.setId(nextSystemId);
		++nextSystemId;
		game.getSystems().add(system);
		create(system);
		return system;
	}
	protected StellarSystem create(String systemName) {
		List<String> nameList = game.getNames().getSystemNames();
		nameList.remove(systemName);
		StellarSystem system = new StellarSystem(systemName);
		system.setId(nextSystemId);
		++nextSystemId;
		game.getSystems().add(system);
		create(system);
		return system;
	}
	private void create(StellarSystem sys) {
		double r , v;
		if (sys.getName().equals("Sol")) {
			Body sun = sys.createStar("Sol", 1.989E30, 696E3); 
			sys.setPrincipleBody(sun);
			sun.getVectors().setOmega(0).setR(0).setTheta(0);
			r = 5.79E7; v = 47.4;
			Body mercury = sys.createPlanetoid("Mercury", 3.285E23, 2439).setParentBody(sun);
			mercury.getVectors().setOmega(VToW(v,r)).setTheta(randAngle()).setR(r);
			r = 1.082E8; v = 35.0;
			Body venus = sys.createPlanetoid("Venus", 4.87E24, 6052).setParentBody(sun);
			venus.getVectors().setOmega(VToW(v,r)).setTheta(randAngle()).setR(r);
			r = 1.496E8; v = 29.8;
			Body earth = sys.createPlanetoid("Earth", 5.976E24, 6378).setParentBody(sun);
			earth.getVectors().setOmega(VToW(v,r)).setTheta(randAngle()).setR(r);
			r = 385E3; v = 1.022;
			Body luna = sys.createPlanetoid("Luna", 7.342E22, 1737.4).setParentBody(earth); 
			luna.getVectors().setOmega(VToW(v,r)).setTheta(randAngle()).setR(r);
			r = 2.279E8; v = 24.1;
			Body mars = sys.createPlanetoid("Mars", 6.42E23, 3397).setParentBody(sun);
			mars.getVectors().setOmega(VToW(v,r)).setTheta(randAngle()).setR(r);
			
			
			
			sun.setColor(Color.yellow);
			mercury.setColor(Color.orange);
			venus.setColor(Color.magenta);
			earth.setColor(Color.blue);
			luna.setColor(Color.gray);
			mars.setColor(Color.red);
			
		} else if (sys.getName().equals("Proxima Centauri")) {
			
		} else {
			builder.build(sys);
		}
	}
	
	private double randAngle() {
		return Math.random()*360;
	}
	private double VToW(double velocity, double radius) {
		return velocity/radius;
		
	}
}

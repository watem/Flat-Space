package FlatSpace.testing.frontend;

import FlatSpace.application.FlatSpaceApplication;
import FlatSpace.backendData.game.FlatSpace;
import FlatSpace.backendData.game.FlatSpaceGame;
import FlatSpace.backendData.stellarBodies.Body;
import FlatSpace.backendData.stellarBodies.StellarSystem;
import FlatSpace.temporaryFrontend.SystemView;

public class systemTesting {

	public static void main(String[] args) {
		
		
		FlatSpace flatSpace = new FlatSpace();
		FlatSpaceGame game = flatSpace.newGame("testGame");
		FlatSpace.setCurrentGame(game);
		StellarSystem system = game.createEmptySystem();
		Body star = system.createStar(system.getName()+" A", 10, 10);
		star.getVectors().setOmega(0).setR(0).setTheta(0);
		system.setPrincipleBody(star);
		Body planet = system.createPlanetoid(system.getName()+" II", 3, 3).setParentBody(star);
		planet.getVectors().setOmega(0.0001).setR(200).setTheta(0);
		Body planet2 = system.createPlanetoid(system.getName()+" I", 3, 3).setParentBody(star);
		planet2.getVectors().setOmega(0.001).setR(50).setTheta(0);
		Body moon = system.createPlanetoid(system.getName()+" IIa", 2, 2).setParentBody(planet);
		moon.getVectors().setOmega(0.002).setR(20).setTheta(0);
		
		StellarSystem system2 = game.createEmptySystem();
		Body star2 = system.createStar(system2.getName()+" A", 30, 30);
		star2.getVectors().setOmega(0).setR(0).setTheta(0);
		system2.setPrincipleBody(star2);
		Body planet3 = system.createPlanetoid(system.getName()+" II", 3, 3).setParentBody(star2);
		planet3.getVectors().setOmega(0.001).setR(70).setTheta(90);

		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	SystemView view = new SystemView(); 
	        	FlatSpaceApplication.setSystemView(view);
	            view.setVisible(true);
	        }
	    });

	}

	
}
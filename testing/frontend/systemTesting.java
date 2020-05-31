package flatSpace.testing.frontend;

import flatSpace.Controller.TO.TOGame;
import flatSpace.application.FlatSpaceApplication;
import flatSpace.backendData.game.FlatSpace;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.NameLists;
import flatSpace.backendData.game.Profile;
import flatSpace.backendData.stellarBodies.Body;
import flatSpace.backendData.stellarBodies.StellarSystem;
import flatSpace.temporaryFrontend.GameMenuView;
import flatSpace.temporaryFrontend.SystemView;

public class systemTesting {

	public static void main(String[] args) {
		
		Profile user = new Profile("test username");
		FlatSpace flatSpace = user.getFlatSpace();
		FlatSpaceGame game = flatSpace.newGame("testGame");
		game.getGameSettings().setStartingNPEs(0);
		Profile.setCurrentProfile(user);
		flatSpace.setCurrentGame(game);
		StellarSystem system = game.createSystem();
		Body star = system.createStar(system.getName()+" A", 10, 10);
		star.getVectors().setOmega(0).setR(0).setTheta(0);
		system.setPrincipleBody(star);
		Body planet = system.createPlanetoid(system.getName()+" "+NameLists.toRoman(2), 3, 3).setParentBody(star);
		planet.getVectors().setOmega(0.0001).setR(200).setTheta(0);
		Body planet2 = system.createPlanetoid(system.getName()+" "+NameLists.toRoman(1), 3, 3).setParentBody(star);
		planet2.getVectors().setOmega(0.001).setR(50).setTheta(0);
		Body moon = system.createPlanetoid(system.getName()+" "+NameLists.toRoman(2)+"a", 2, 2).setParentBody(planet);
		moon.getVectors().setOmega(0.002).setR(20).setTheta(0);
		
		StellarSystem system2 = game.createSystem();
		Body star2 = system.createStar(system2.getName()+" A", 30, 30);
		star2.getVectors().setOmega(0).setR(0).setTheta(0);
		system2.setPrincipleBody(star2);
		Body planet3 = system.createPlanetoid(system.getName()+" "+NameLists.toRoman(1), 3, 3).setParentBody(star2);
		planet3.getVectors().setOmega(0.001).setR(70).setTheta(90);

		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	GameMenuView g = new GameMenuView(new TOGame(game));
	        	FlatSpaceApplication.setGameMenuView(g);
	        	SystemView view = new SystemView(g); 
	            view.setVisible(true);
	            g.getSystemViews().add(view);
	        }
	    });

	}

	
}
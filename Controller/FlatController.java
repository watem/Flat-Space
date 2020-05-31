package flatSpace.Controller;

import java.util.ArrayList;
import java.util.List;

import flatSpace.Controller.TO.Coordinates;
import flatSpace.Controller.TO.TOFlatPortal;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.planarSpace.PlanarSidePortal;

public class FlatController {
	public static List<TOFlatPortal> getPortals() {
		List<TOFlatPortal> portals = new ArrayList<>();
		FlatSpaceGame currentGame = GameController.getCurrentGame();
		for(PlanarSidePortal p:currentGame.getPlaneSpace().getPortals()) {
			portals.add(new TOFlatPortal(p));
		}
		return portals;
	}
	public static void getWind(Coordinates c) {
		
	}
}

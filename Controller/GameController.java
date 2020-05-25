package FlatSpace.Controller;


import java.util.ArrayList;
import java.util.List;

import FlatSpace.Controller.TO.TOSystem;
import FlatSpace.backendData.game.FlatSpace;
import FlatSpace.backendData.stellarBodies.StellarSystem;

public class GameController {

	public GameController() {
	
	
	}
	
	public static List<TOSystem> getSystems() {
		List<TOSystem> list = new ArrayList<>();
		for(StellarSystem s:FlatSpace.getCurrentGame().getSystems()) {
			list.add(new TOSystem(s));
		}
		return list;
	}

}

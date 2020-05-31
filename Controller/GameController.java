package flatSpace.Controller;


import java.util.ArrayList;
import java.util.List;

import flatSpace.Controller.TO.TOSystem;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.Profile;
import flatSpace.backendData.stellarBodies.StellarSystem;

public class GameController {
	
	public static List<TOSystem> getSystems() {
		List<TOSystem> list = new ArrayList<>();
		Profile p = Profile.getCurrentProfile();
		if (p==null) {
			return list;
		}
		FlatSpaceGame g;
		try {
			g=getCurrentGame();
		} catch(InvalidInputException e) {
			return list;
		}
		for(StellarSystem s:Profile.getCurrentProfile().getFlatSpace().getCurrentGame().getSystems()) {
			list.add(new TOSystem(s));
		}
		return list;
	}
	
	public static FlatSpaceGame getCurrentGame() {
		Profile p = Profile.getCurrentProfile();
		if (p==null) {
			throw new InvalidInputException("no user logged in");
		}
		FlatSpaceGame g = p.getFlatSpace().getCurrentGame();
		if (g==null) {
			throw new InvalidInputException("no currently played game");
		}
		return g;
	}

}

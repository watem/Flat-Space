package flatSpace.backendData.planarSpace;

import java.util.ArrayList;
import java.util.List;

import flatSpace.Controller.InvalidInputException;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.GameSettings;
import flatSpace.backendData.stellarBodies.PlanarEntrance;

public class PlanarSpace {
	FlatSpaceGame game;
	List<PlanarSidePortal> portals;
	List<PlanarSidePortal> unusedPortals;
	
	public PlanarSpace(FlatSpaceGame game) {
		this.game = game;
	}

	public List<PlanarSidePortal> getPortals() {
		if (portals==null) {
			portals = new ArrayList<>();
			GameSettings settings = game.getGameSettings();
			for(int i=0;i<settings.getFlatRings();++i) {
				double angle = randRadAngle();
				int num = settings.numPortalsOnRing(i+1);
				for(int j=0;j<num;++j) {
					portals.add(new PlanarSidePortal(angle, i+1, this));
					angle+=radSeparating(num);
					angle%=(2*Math.PI);
				}
			}
			unusedPortals = new ArrayList<>();
			for(PlanarSidePortal p:portals) {
				unusedPortals.add(p);
			}
		}
		return portals;
	}
	private double randRadAngle() {
		return Math.random()*2*Math.PI;
	}
	private double radSeparating(int portalsInRing) {
		return 2*Math.PI/portalsInRing;
	}
	public PlanarSidePortal useUnoccupiedPortal(PlanarEntrance e) {
		PlanarSidePortal p = getUnoccupiedPortal();
		if (p==null) {
			throw new InvalidInputException("out of unused portals");
		}
		p.setEntrance(e);
		e.setPlanarSideEntrance(p);
		unusedPortals.remove(p);
		
		return null;
	}

	private PlanarSidePortal getUnoccupiedPortal() {
		getPortals();
		int i =(int) Math.floor((Math.random()*unusedPortals.size()));
		return unusedPortals.get(i);
	}
	
	protected FlatSpaceGame getGame() {
		return game;
	}
}

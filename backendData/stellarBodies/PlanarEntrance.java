package flatSpace.backendData.stellarBodies;

import flatSpace.backendData.planarSpace.PlanarLocation;
import flatSpace.backendData.planarSpace.PlanarSidePortal;

public class PlanarEntrance extends Body {
	
  public PlanarEntrance(String aName, double aRadius, StellarSystem sys) {
		super(aName, 0, aRadius,sys);
		
	}

private PlanarSidePortal planarSideEntrance;

public PlanarSidePortal getPlanarSideEntrance() {
	return planarSideEntrance;
}

public void setPlanarSideEntrance(PlanarSidePortal planarSideEntrance) {
	this.planarSideEntrance = planarSideEntrance;
}
}

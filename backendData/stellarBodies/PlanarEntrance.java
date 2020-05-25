package FlatSpace.backendData.stellarBodies;

import FlatSpace.backendData.planarSpace.PlanarLocation;

public class PlanarEntrance extends Body {
	
  public PlanarEntrance(String aName, double aRadius, StellarSystem sys) {
		super(aName, 0, aRadius,sys);
		
	}

private PlanarLocation planarSideEntrance;

public PlanarLocation getPlanarSideEntrance() {
	return planarSideEntrance;
}

public void setPlanarSideEntrance(PlanarLocation planarSideEntrance) {
	this.planarSideEntrance = planarSideEntrance;
}
}

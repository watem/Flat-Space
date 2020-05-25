package FlatSpace.backendData.stellarBodies;

import FlatSpace.backendData.mechanics.Location;

public class SystemLocation extends Location {
  StellarSystem currentSystem;

public StellarSystem getCurrentSystem() {
	return currentSystem;
}

public void setCurrentSystem(StellarSystem currentSystem) {
	this.currentSystem = currentSystem;
}


}

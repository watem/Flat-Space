package flatSpace.backendData.stellarBodies;

import flatSpace.backendData.mechanics.Location;

public class SystemLocation extends Location {
  StellarSystem currentSystem;

public StellarSystem getCurrentSystem() {
	return currentSystem;
}

public void setCurrentSystem(StellarSystem currentSystem) {
	this.currentSystem = currentSystem;
}


}

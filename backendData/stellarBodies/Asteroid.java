package FlatSpace.backendData.stellarBodies;

import FlatSpace.backendData.mechanics.Availability;
import FlatSpace.backendData.mechanics.ResourceSet;

public class Asteroid extends Body {

  public Asteroid() {
	  this("", 0, new ResourceSet(), new Availability(), (StellarSystem)null);
      }

  public Asteroid(String aName, double aRadius, ResourceSet aResources, Availability avail, StellarSystem sys) {
    super(aName, 0, aRadius, aResources, avail, sys);
  }
}

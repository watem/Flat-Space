package flatSpace.backendData.stellarBodies;

import flatSpace.backendData.mechanics.Availability;
import flatSpace.backendData.mechanics.ResourceSet;

public class Comet extends Body {

  public Comet() {
	  this("",0,new ResourceSet(), new Availability(), null);
  }

  public Comet(String aName, double aRadius, ResourceSet aResources, Availability avail, StellarSystem sys) {
    super(aName, 0, aRadius, aResources, avail, sys);
  }
}

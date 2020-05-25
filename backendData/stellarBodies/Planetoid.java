package FlatSpace.backendData.stellarBodies;

import java.util.List;

public class Planetoid extends Body {
  public Planetoid(String aName, double aMass, double aRadius, StellarSystem sys) {
		super(aName, aMass, aRadius, sys);
		if (aMass>=1000) {
			sys.getMajorBodies().add(this);
		} else {
			sys.getMinorBodies().add(this);
		}
	}
}

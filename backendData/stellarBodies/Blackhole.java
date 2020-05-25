package FlatSpace.backendData.stellarBodies;

import FlatSpace.backendData.mechanics.Constants;

public class Blackhole extends Body{

  public Blackhole(String name, double mass, StellarSystem sys) {
    super(name, mass, 2*mass*Constants.G/Constants.C/Constants.C, sys);
  }

  @Override
  public void setMass(double mass) {
    super.setMass(mass);
    double r = 2*mass*Constants.G/Constants.C/Constants.C;
    super.setRadius(r);
  }

  @Override
  public double getRadius() {
    return 2*this.getMass()*Constants.G/Constants.C/Constants.C;
  }

  @Override
  public void setRadius(double radius) {
    super.setRadius(this.getRadius());
  }
}

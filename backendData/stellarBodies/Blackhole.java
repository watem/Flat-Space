public class Blackhole extends Body{

  public Blackhole(String name, double mass) {
    double r = 2*mass*Constants.G/Constants.C/Constants.C;
    super(name, mass, r);
  }

  @override
  public void setMass(double mass) {
    super.setMass(mass);
    double r = 2*mass*Constants.G/Constants.C/Constants.C;
    super.setRadius(r);
  }

  @override
  public double getRadius() {
    return 2*this.getMass()*Constants.G/Constants.C/Constants.C;
  }

  @override
  public void setRadius(double radius) {
    super.setRadius(this.getRadius());
  }
}

public class Asteroid extends Body {

  public Asteroid() {
    ResourceSet r = new ResourceSet();
    String aName;
    double rad;
    Availability a;
    Asteroid(aName, rad, r, a)
  }

  public Asteroid(String aName, double aRadius, ResourceSet aResources, Availability avail) {
    super(aName, 0, aRadius, aResources, avail);
  }
}

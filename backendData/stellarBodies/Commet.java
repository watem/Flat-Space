public class Comet extends Body {

  public Comet() {
    ResourceSet r = new ResourceSet();
    String aName;
    double rad;
    Availability a;
    Comet(aName, rad, r, a);
  }

  public Comet(String aName, double aRadius, ResourceSet aResources, Availability avail) {
    super(aName, 0, aRadius, aResources, avail);
  }
}

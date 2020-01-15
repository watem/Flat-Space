public abstract class Body {
  private Colony colony;

  private System system;
  private String name;
  private double mass;
  private double radius;
  private ResourceSet resources, initialResources;
  private Availability resourceAvail;
  private List<Body> Satellites;
  private Body parentBody;


  public Body(String aName, double aMass, double aRadius, ResourceSet aResources, Availability avail) {
    this.name = aName;
    this.mass = aMass;
    this.radius = aRadius;
    this.resources = aResources;
    this.initialResources = aResources;
    this.resourceAvail = avail;
  }
  public Body(String aName, double aMass, double aRadius) {
    this.name = aName;
    this.mass = aMass;
    this.radius = aRadius;
  }

  public ResourceSet getResources() {
    return resources;
  }

  public void updateAvail() {

  }
}

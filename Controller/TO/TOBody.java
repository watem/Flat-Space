public class TOBody {
  private String system;
  private String name;
  private double mass;
  private double radius;
  private Color c;

  public TOBody() {}
  public TOBody(Body b) {
    system = b.getSystem().getName();
    name = b.getName();
    mass = b.getMass();
    radius = b.getRadius();
  }

  
}

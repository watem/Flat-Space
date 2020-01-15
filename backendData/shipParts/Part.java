
public class Part {
  private Shape shape;
  private int mass;
  private boolean military;
  private ResourceSet resourceCost;
  private boolean obsolete;

  public void setMass(int aMass) {
    this.mass = aMass;
  }
  public void setShape(Shape aShape) {
    this.shape = aShape;
  }
  public int getMass() {
    return this.mass;
  }
  public Shape getShape() {
    return this.shape;
  }
}

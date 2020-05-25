package FlatSpace.backendData.shipParts;

import FlatSpace.backendData.mechanics.ResourceSet;

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
public boolean isMilitary() {
	return military;
}
public void setMilitary(boolean military) {
	this.military = military;
}
public boolean isObsolete() {
	return obsolete;
}
public void setObsolete(boolean obsolete) {
	this.obsolete = obsolete;
}
public ResourceSet getResourceCost() {
	return resourceCost;
}
}

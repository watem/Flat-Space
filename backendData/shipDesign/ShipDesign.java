package flatSpace.backendData.shipDesign;

import java.util.List;

import flatSpace.backendData.mechanics.ResourceSet;
import flatSpace.backendData.shipParts.Part;

public class ShipDesign implements Cloneable{

  List<Part> parts;
  ResourceSet resourceCost;
  int buildCost;
  boolean military;
  int size; //TODO
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}
public ResourceSet getResourceCost() {
	return resourceCost;
}
public void setResourceCost(ResourceSet resourceCost) {
	this.resourceCost = resourceCost;
}
public int getBuildCost() {
	return buildCost;
}
public void setBuildCost(int buildCost) {
	this.buildCost = buildCost;
}
public boolean isMilitary() {
	return military;
}
public void setMilitary(boolean military) {
	this.military = military;
}
public List<Part> getParts() {
	return parts;
}
public ShipDesign clone() {
	  return new ShipDesign();
}
}

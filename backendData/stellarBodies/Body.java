package flatSpace.backendData.stellarBodies;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import flatSpace.backendData.mechanics.Availability;
import flatSpace.backendData.mechanics.ResourceSet;
import flatSpace.backendData.nations.Colony;

public abstract class Body {
  private Colony colony;
  
  private int id;
  private StellarSystem system;
  private String name;
  private double mass;
  private double radius;
  private ResourceSet resources, initialResources;
  private Availability resourceAvail;
  private List<Body> satellites;
  private Body parentBody;
  private int depth=0;
  private RotationalVectors vectors;
  private List<PlanetaryRing> rings;
  private Color color;

  public Body(String aName, double aMass, double aRadius, ResourceSet aResources, Availability avail, StellarSystem sys) {
    this(aName, aMass, aRadius, sys);
    this.resources = aResources;
    this.initialResources = aResources;
    this.resourceAvail = avail;
  }
  public Body(String aName, double aMass, double aRadius, StellarSystem sys) {
    this.name = aName;
    this.mass = aMass;
    this.radius = aRadius;
    this.system = sys;
  }

  public ResourceSet getResources() {
    return resources;
  }

  public void updateAvail() {

  }

  public RotationalVectors getVectors() {
	  if (vectors==null) {
		  vectors = new RotationalVectors();
	  }
    return vectors;
  }
public Colony getColony() {
	return colony;
}
public void setColony(Colony colony) {
	this.colony = colony;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public double getMass() {
	return mass;
}
public void setMass(double mass) {
	this.mass = mass;
}
public Availability getResourceAvail() {
	return resourceAvail;
}
public void setResourceAvail(Availability resourceAvail) {
	this.resourceAvail = resourceAvail;
}
public StellarSystem getSystem() {
	return system;
}
public double getRadius() {
	return radius;
}
public List<Body> getSatellites() {
	if (satellites == null) {
		satellites = new ArrayList<>();
	}
	return satellites;
}
public Body getParentBody() {
	return parentBody;
}
public Body setParentBody(Body parentBody) {
	if (parentBody != null) {
		parentBody.getSatellites().remove(this);
	}
	this.parentBody = parentBody;
	parentBody.getSatellites().add(this);
	depth = parentBody.getDepth()+1;
	return this;
}

public int getDepth() {
	return depth;
}
public void setResources(ResourceSet resources) {
	this.resources = resources;
}
public void setRadius(double radius) {
	this.radius = radius;
	
}
public Color getColor() {
	return color;
}
public void setColor(Color color) {
	this.color = color;
}

}

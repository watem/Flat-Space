package FlatSpace.backendData.stellarBodies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StellarSystem {
  private String name;
  private int id;
  private int nextBodyId = 0;
List<Star> stars;
  List<Planetoid> majorBodies;
  List<Planetoid> minorBodies;
  Set<Body> subBodies;
  List<Asteroid> asteroids;
  List<Comet> comets;
  List<PlanarEntrance> planarEntrances;
  Body principleBody;
  
  public StellarSystem(String name) {
		this.name = name;
	}
  
  
  public Body getPrincipleBody() {
	  return principleBody;
  }
  public void setPrincipleBody(Body principleBody) {
		this.principleBody = principleBody;
	}
public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}





public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<Star> getStars() {
	if (stars == null) {
		stars = new ArrayList<>();
	}
	return stars;
}
public List<Planetoid> getMajorBodies() {
	if (majorBodies == null) {
		majorBodies = new ArrayList<>();
	}
	return majorBodies;
}
public List<Planetoid> getMinorBodies() {
	if (majorBodies == null) {
		minorBodies = new ArrayList<>();
	}
	return minorBodies;
}
public Set<Body> getSubBodies() {
	if (subBodies == null) {
		subBodies = new HashSet<>();
	}
	return subBodies;
}
public List<Asteroid> getAsteroids() {
	if (asteroids == null) {
		asteroids = new ArrayList<>();
	}
	return asteroids;
}
public List<Comet> getComets() {
	if (comets == null) {
		comets = new ArrayList<>();
	}
	return comets;
}
public List<PlanarEntrance> getPlanarEntrances() {
	if (planarEntrances == null) {
		planarEntrances = new ArrayList<>();
	}
	return planarEntrances;
}
public Star createStar(String aName, double aMass, double aRadius) {
	Star s = new Star(aName, aMass, aRadius, this);
	s.setId(nextBodyId);
	nextBodyId++;
	return s;
}
public Planetoid createPlanetoid(String aName, double aMass, double aRadius) {
	Planetoid p = new Planetoid(aName, aMass, aRadius, this);
	p.setId(nextBodyId);
	nextBodyId++;
	return p;
}
}

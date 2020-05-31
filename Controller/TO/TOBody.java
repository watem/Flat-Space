package flatSpace.Controller.TO;

import java.awt.Color;
import java.util.Random;

import flatSpace.backendData.stellarBodies.Body;

public class TOBody {
  private TOSystem system;
  private int id;
  private String name;
  private double mass;
  private double radius;
  private Color c;
  private int depth;

  public TOBody() {}
  public TOBody(Body b) {
	if (b==null) {
		return;  
	}
    system = new TOSystem(b.getSystem());
    name = b.getName();
    mass = b.getMass();
    radius = b.getRadius();
    id = b.getId();
    c = b.getColor();
    depth = b.getDepth();
  }
public TOSystem getSystem() {
	return system;
}
public String getName() {
	return name;
}
public double getMass() {
	return mass;
}
public double getRadius() {
	return radius;
}
public Color getC() {
	if (c==null) {
		Random r = new Random();
		int i = r.nextInt();
		c = new Color(i);
	}
	return c;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((system == null) ? 0 : system.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (obj == null) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	TOBody other = (TOBody) obj;
	if (id != other.id) {
		return false;
	}
	if (system == null) {
		if (other.system != null) {
			return false;
		}
	} else if (!system.equals(other.system)) {
		return false;
	}
	return true;
}
public int getDepth() {
	return depth;
}
@Override
public String toString() {
	return name;
}

  
}

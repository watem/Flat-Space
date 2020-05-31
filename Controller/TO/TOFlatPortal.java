package flatSpace.Controller.TO;

import flatSpace.backendData.planarSpace.PlanarSidePortal;

public class TOFlatPortal {
	int id;
	String name;
	Coordinates location;
	
	public TOFlatPortal(PlanarSidePortal p) {
		// TODO Auto-generated constructor stub
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
	public Coordinates getLocation() {
		return location;
	}

}

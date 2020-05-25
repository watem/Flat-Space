package FlatSpace.Controller.TO;

import FlatSpace.backendData.stellarBodies.StellarSystem;

public class TOSystem {
	private String name;
	private int id;
	
	
	@Override
	public String toString() {
		return name;
	}


	public TOSystem() {
		
	}
	public TOSystem(String name, int id) {
		this.name = name;
		this.setId(id);
	}
	public TOSystem(StellarSystem system) {
		this.name = system.getName();
		this.setId(system.getId());
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TOSystem other = (TOSystem) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}

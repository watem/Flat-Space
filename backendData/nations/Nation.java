package FlatSpace.backendData.nations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import FlatSpace.backendData.nations.technologies.Technology;
import FlatSpace.backendData.nations.technologies.Unlockable;

public class Nation {
  List<Technology> discoveredTechs;
  List<Colony> colonies;
  private GovernmentType government;
  private String name;

  private HashMap<String, Integer> repeatableTechs;
  private Set<String> unlockedTechs;
  private List<Technology> availableTechnologies;

  public void updateRate(String type) {

  }

  public HashMap getRepeatableTechs() {
    if (repeatableTechs==null) {
      repeatableTechs = new HashMap<String, Integer>();
    }
    return repeatableTechs;
  }
  public Set getUnlockedTechs() {
    if (unlockedTechs==null) {
      unlockedTechs = new HashSet<String>();
    }
    return unlockedTechs;
  }
  public List getAvailableTechnologies() {
    if (availableTechnologies==null) {
      availableTechnologies = new ArrayList<Technology>();
    }
    return availableTechnologies;
  }

public int getMineMultiplier() {
	// TODO Auto-generated method stub
	return 0;
}

public void removeAvailableTechnology(Technology tech) {
	getAvailableTechnologies().remove(tech);
	
}
}

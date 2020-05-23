import java.util.HashMap;
import java.util.HashSet;

public class Nation {
  List<Technology> discoveredTechs;
  List<Colony> colonies;
  private GovernmentType government;
  private String name;
  private NationTech ownedTechs;

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
  public HashMap getUnlockedTechs() {
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
}

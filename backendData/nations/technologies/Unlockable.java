package FlatSpace.backendData.nations.technologies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import FlatSpace.backendData.nations.Nation;

public class Unlockable extends Technology {
  private static List<Unlockable> allUnlockables;
  public static List<Unlockable> tierZero;
  Set<Unlockable> dependancies, dependants;
  String name;
  int researchCost;

  public Unlockable() {

    allUnlockables.add(this);
  }

  public void unlock(Nation nation) {
//    nation.getUnlockedTechs().add(type);
    this.getNextTechs(nation);
    nation.removeAvailableTechnology(this);
  }
  private void getNextTechs(Nation nation) {
    //check dependants
    for (Unlockable u:dependants) {                 //check each dependant if they're eligable to be researched next
      boolean allDependanciesUnlocked = true;

      for (Unlockable v:dependancies) {             //check if all dependancies are now met
        boolean unlocked = false;
//        for (Unlockable w:nation.getUnlockedTechs()) {
//          if (w.equals(v.name)) {
//            unlocked = true;
//          }
//        }
        if (!unlocked) {
          allDependanciesUnlocked = false;
        }
      }
      if (allDependanciesUnlocked) {
        nation.getAvailableTechnologies().add(u);
      }
    }
    //generate other effects
  }



  public void addDependancy(Unlockable unlockable) {
    if (dependancies==null) {
      dependancies = new HashSet<Unlockable>();
    }
    dependancies.add(unlockable);
    unlockable.addDependant(this);
  }
  private void addDependant(Unlockable unlockable) {
    if (dependants==null) {
      dependants = new HashSet<Unlockable>();
    }
    dependants.add(unlockable);
  }
}

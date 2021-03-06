package flatSpace.backendData.nations.technologies;

import flatSpace.backendData.nations.Nation;

public class ResearchInstance {
  Nation nation;
  Technology tech;
  int remainingCost;


  public void update(int progress) {
    if (progress<remainingCost) {
      remainingCost-=progress;
    } else {
      int extra = progress - remainingCost;
      tech.unlock(nation);
    }

  }
}

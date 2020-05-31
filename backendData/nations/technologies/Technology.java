package flatSpace.backendData.nations.technologies;

import flatSpace.backendData.nations.Nation;

public abstract class Technology {
  String name;
  int cost;


  public abstract void unlock(Nation nation);
}

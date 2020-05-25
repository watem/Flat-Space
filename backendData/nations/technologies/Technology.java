package FlatSpace.backendData.nations.technologies;

import FlatSpace.backendData.nations.Nation;

public abstract class Technology {
  String name;
  int cost;


  public abstract void unlock(Nation nation);
}

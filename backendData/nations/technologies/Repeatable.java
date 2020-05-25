package FlatSpace.backendData.nations.technologies;

import FlatSpace.backendData.nations.Nation;

public class Repeatable extends Technology{
  private int amount;

  public Repeatable(String name, int amount, int cost, Nation nation) {
    this(name);
    this.amount = amount;
    this.cost = cost;
//    this.nation = nation;
  }

  private Repeatable(String name) {
    this.name = name;
  }




  public void unlock(Nation nation) {
    nation.getRepeatableTechs().put(name, amount);
    this.getNextTech(name);
//    nation.removeRepeatable(this);
  }



  private void getNextTech(String type) {
    Repeatable repeatable = new Repeatable(type);
//    if () {
//      repeatable.amount +=
//    } else if () {
//
//    } else if () {
//
//    } else if () {
//
//    }
  }



  //getters
}

public class Repeatable extends Technology{
  private int amount;

  public Repeatable(String name, int amount, int cost, Nation nation) {
    this(name);
    this.amount = amount;
    this.researchCost = researchCost;
    this.nation = nation;
  }

  private Repeatable(String name) {
    this.type = type;
  }




  public void unlock(Nation nation) {
    nation.getRepeatableTechs().put(type, amount);
    this.getNextTech();
    nation.removeRepeatable(this);
  }



  private void getNextTech() {
    repeatable = new Repeatable(type);
    if () {
      repeatable.amount +=
    } else if () {

    } else if () {

    } else if () {

    }
  }



  //getters
}

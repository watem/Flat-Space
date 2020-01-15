public class Nation {
  List<Technology> discoveredTechs;
  List<Colony> colonies;
  private GovernmentType government;
  private String name;
  private NationTech ownedTechs;

  private double mineRate;

  public void updateMineRate(int rate) {

    for (Colony c:colonies) {
      c.updateMineRate();
    }
  }
}

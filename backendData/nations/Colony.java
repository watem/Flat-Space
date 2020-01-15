public class Colony {
  private Body body;
  private Nation owner;
  private Governer governer;

  private int automatedMines;
  private int mines;
  private int factories;
  private int fighterFactories;
  private int fuelRefineries;
  private int ordinanceFactories;
  private int maintanceFacilities;
  private int massDrivers;
  private int researchLabs;
  private int spacePorts;
  private int terraformingInstallations;
  private int trackingStations;

  private List<NavalShipyard> navalShipyards;
  private List<CommercialShipyard> CommercialShipyards;
  private ShipyardFleet shipyardFleet;

  private ResourceSet stockpile;
  private List<SpecificPart> partsStockpile;

  private int mineRate = updateMineRate();

  public mine() {
    ResourceSet resources = body.getResources();
    if (mineRate <= 0 || resources == null) {
      return;
    }
    resources.mine(stockpile, mineRate, body.getAvailabilty());
    body.updateAvail();
  }

  public int updateMineRate() {
    mineRate = (int) (owner.getMineMultiplier()*(automatedMines+mines)*governor.getMineMultiplier());
    return mineRate;
  }

  public void addMines(int numMines) {
    mines += numMines;
    updateMineRate();

  }
  public void addAutoMines(int numMines) {
    automatedMines += numMines;
    updateMineRate();
  }
  /**
  * @return number of mines actually removed
  **/
  public int removeMines(int numMines) {
    if(numMines>mines) {
      numMines = mines;
    }
    mines -= numMines;
    updateMineRate();
    return numMines;
  }
  /**
  * @return number of automated mines actually removed
  **/
  public int removeAutoMines(int numMines) {
    if(numMines>mines) {
      numMines = mines;
    }
    automatedMines -= numMines;
    updateMineRate();
    return numMines;
  }
  public void setGovernor(Governer aGovernor) {
    this.governer = aGovernor;
    updateMineRate();
  }


  public void scrapPart(SpecificPart part) {
    if (partsStockpile.contains(part)) {
      part.scrap(this);
      partsStockpile.remove(part);
    } else {
      //error message
    }
  }
}

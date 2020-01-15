public abstract class Shipyard {
  Colony home;
  String name;
  int size;
  int slipways;
  int slipwaysInUse;
  int buildCost;
  ResourceSet resourceCost;
  int costDivisor;


  public boolean increaseSize(int amount) {
    multiplier = slipways/costDivisor*;

    ResourceSet increaseCost = resourceCost.clone();
    increaseCost.multiply(multiplier);
    if (home.getStockPile().subtract(increaseCost)) {
      new ShipyardTask();
      return true;
    } else {
      return false;
    }
  }

  public Shipyard(String aName, Colony colony) {
    home = colony;
    name = aName;
    slipways = 1;
    slipwaysInUse = 0;


  }

  public IntContainer getBuildRate(ShipDesign design) {
    //TODO get normal build rate of colony
    int normalBuildRate = 1000;

    int rate = normalBuildRate*(1+(((design.getSize()/Constants.baseShipSize)-1)/2));
    return new IntContainer(rate);
  }
}

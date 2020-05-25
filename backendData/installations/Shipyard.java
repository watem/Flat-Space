package FlatSpace.backendData.installations;

import FlatSpace.backendData.mechanics.Constants;
import FlatSpace.backendData.mechanics.IntContainer;
import FlatSpace.backendData.mechanics.ResourceSet;
import FlatSpace.backendData.mechanics.ShipyardTask;
import FlatSpace.backendData.nations.Colony;
import FlatSpace.backendData.shipDesign.ShipDesign;

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
	  //TODO
    int multiplier = slipways/costDivisor; //* something

    ResourceSet increaseCost = resourceCost.clone();
    increaseCost.multiplyAll(multiplier);
    if (home.getStockpile().subtract(increaseCost)) {
      new ShipyardTask(multiplier, multiplier, null, home, increaseCost); //TODO
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

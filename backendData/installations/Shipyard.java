package flatSpace.backendData.installations;

import flatSpace.backendData.mechanics.Constants;
import flatSpace.backendData.mechanics.IntContainer;
import flatSpace.backendData.mechanics.ResourceSet;
import flatSpace.backendData.mechanics.ShipyardTask;
import flatSpace.backendData.nations.Colony;
import flatSpace.backendData.shipDesign.ShipDesign;

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

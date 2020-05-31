package flatSpace.backendData.installations;

import flatSpace.backendData.nations.Colony;

public class NavalShipyard extends Shipyard {

  public NavalShipyard(String aName, Colony colony) {
    super(aName, colony);
    size = 1000;
    costDivisor = 1;
  }
}

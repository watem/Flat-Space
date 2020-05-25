package FlatSpace.backendData.installations;

import FlatSpace.backendData.nations.Colony;

public class CommercialShipyard extends Shipyard {

  public CommercialShipyard(String aName, Colony colony) {
    super(aName, colony);
    size = 10000;
    costDivisor = 10;
  }
}

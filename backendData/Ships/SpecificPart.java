package FlatSpace.backendData.Ships;

import FlatSpace.backendData.nations.Colony;
import FlatSpace.backendData.shipParts.Part;

public class SpecificPart {
  Part design;
  int damage;
  boolean destroyed;
  //location on a Ship

  public void scrap(Colony colony) {
    colony.getStockpile().add(design.getResourceCost().multiplyAll(0.5));
    
    //delete part
  }
}

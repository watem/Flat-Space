package flatSpace.backendData.Ships;

import flatSpace.backendData.nations.Colony;
import flatSpace.backendData.shipParts.Part;

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

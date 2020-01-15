public class SpecificPart {
  Part design;
  int damage;
  boolean destroyed;
  //location on a Ship

  public void scrap(Colony colony) {
    colony.getStockPile().add(design.getResouceCost().multiplyAll(0.5));
    
    //delete part
  }
}

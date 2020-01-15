public class Ship {
  List<SpecificPart> parts;
  double mass; //? or int? or long?
  int size;

  String name;
  String class;
  boolean planarCapable;
  Fleet fleet;
  boolean military;

  public Ship(ShipDesign design, Shipyard yard) {

  }

  public void setFleet(Fleet masterFleet) {
    fleet = masterFleet;
  }

  public void retrofit(ShipDesign design, Shipyard yard) {

  }
  public void scrap(Shipyard yard) {

  }
}

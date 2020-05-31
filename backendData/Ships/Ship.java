package flatSpace.backendData.Ships;

import java.util.List;

import flatSpace.backendData.installations.Shipyard;
import flatSpace.backendData.shipDesign.ShipDesign;

public class Ship implements Cloneable{
  List<SpecificPart> parts;
  double mass; //? or int? or long?
  int size;

  String name;
  String shipClass;
  boolean planarCapable;
  Fleet fleet;
  boolean military;
  
  ShipDesign design;
  Shipyard yard;

  public Ship(ShipDesign design, Shipyard yard) {
	  this.design = design;
	  this.yard = yard;
  }

  public void setFleet(Fleet masterFleet) {
    fleet = masterFleet;
  }

  public void retrofit(ShipDesign design, Shipyard yard) {

  }
  public void scrap(Shipyard yard) {

  }
  
  
}

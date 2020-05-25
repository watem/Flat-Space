package FlatSpace.backendData.mechanics;

import FlatSpace.backendData.Ships.Ship;
import FlatSpace.backendData.installations.Shipyard;
import FlatSpace.backendData.nations.Colony;
import FlatSpace.backendData.shipDesign.ShipDesign;

public class ShipTask extends Task{
  ShipDesign ship;
  Shipyard shipyard;

  public ShipTask(int cost, IntContainer progress, Colony colony, ShipDesign ship, Shipyard shipyard) {
    super(cost, progress, colony, ship.getResourceCost());
    this.ship = ship.clone();
    this.shipyard = shipyard;
  }

  public void finish() {
    new Ship(ship, shipyard);
  }


}

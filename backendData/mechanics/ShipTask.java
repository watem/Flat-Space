package flatSpace.backendData.mechanics;

import flatSpace.backendData.Ships.Ship;
import flatSpace.backendData.installations.Shipyard;
import flatSpace.backendData.nations.Colony;
import flatSpace.backendData.shipDesign.ShipDesign;

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

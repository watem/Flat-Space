public class ShipTask {
  ShipDesign ship;
  Shipyard shipyard;

  public ShipTask(int cost, IntContainer progress, Colony colony, ShipDesign ship, Shipyard shipyard) {
    super(cost, progress, colony, ship.getResouceCost());
    this.ship = ship.clone();
    this.shipyard = shipyard;
  }

  private void finish() {
    new Ship(ship, shipyard);
  }


}

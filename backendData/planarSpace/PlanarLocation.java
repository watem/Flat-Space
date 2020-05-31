package flatSpace.backendData.planarSpace;

import flatSpace.backendData.mechanics.Location;

public class PlanarLocation extends Location {
  public int getDistance(PlanarLocation location) {
    int dx = (int) (getX()-location.getX()), dy = (int) (getY()-location.getY());
    return (int) Math.sqrt(dx*dx+dy*dy);
  }
}

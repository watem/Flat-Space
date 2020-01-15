public class PlanarLocation extends Location {
  public int getDistance(PlanarLocation location) {
    int dx = getX()-location.getX(), dy = getY()-location.getY();
    return (int) Math.sqrt(dx*dx+dy*dy);
  }
}

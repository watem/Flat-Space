public class SpatialController {

  public HashMap<String, Coordinates> getAllLocations(String system) {
    return getAllLocations(getSystem(system));
  }
  public HashMap<String, Coordinates> getAllLocations(System s) {
    HashMap<String, Coordinates> map = new HashMap<>();
    getBodyLocation(map, s.getPrincipleBody(), new Coordinates(0,0));
    return map;
  }

  public void getBodyLocation(HashMap<String, Coordinates> map, Body b, Coordinates parentCoords) {
    Coordinates localPosition = b.getVectors().transform();
    Coordinates systemLocation = new Coordinates(parentCoords.getX()+localPosition.getX(), parentCoords.getY()+localPosition.getY());
    map.put(b.getName(), systemLocation);
    for (Body s:b.getSatellites()) {
      getBodyLocation(map, s, systemLocation);
    }
  }

  public List<TOBody> getAllBodies(String system) {
    System s = getSystem(system);
    List<TOBody> list = new ArrayList<>();
    getBodies(list, s.getPrincipleBody());
    return list;
  }
  public void getBodies(List<TOBody> list, Body b) {
    list.add(new TOBody(b));
    for (Body s:b.getSatellites()) {
      getBodies(list, s);
    }
  }
}

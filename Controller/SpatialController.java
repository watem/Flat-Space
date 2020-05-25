package FlatSpace.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import FlatSpace.Controller.TO.Coordinates;
import FlatSpace.Controller.TO.TOBody;
import FlatSpace.backendData.game.FlatSpace;
import FlatSpace.backendData.stellarBodies.Body;
import FlatSpace.backendData.stellarBodies.StellarSystem;

public class SpatialController {

  public static HashMap<TOBody, Coordinates> getAllLocations(int systemId) {
    return getAllLocations(getSystem(systemId));
  }
  
public static HashMap<TOBody, Coordinates> getAllLocations(StellarSystem s) {
    HashMap<TOBody, Coordinates> map = new HashMap<>();
    getBodyLocation(map, s.getPrincipleBody(), new Coordinates(0,0));
    return map;
  }

  public static void getBodyLocation(HashMap<TOBody, Coordinates> map, Body b, Coordinates parentCoords) {
    Coordinates localPosition = b.getVectors().transform();
    Coordinates systemLocation = new Coordinates(parentCoords.getX()+localPosition.getX(), parentCoords.getY()+localPosition.getY());
    map.put(new TOBody(b), systemLocation);
    for (Body s:b.getSatellites()) {
      getBodyLocation(map, s, systemLocation);
    }
  }

  public static List<TOBody> getAllBodies(int systemId) {
    StellarSystem s = getSystem(systemId);
    List<TOBody> list = new ArrayList<>();
    getBodies(list, s.getPrincipleBody());
    return list;
  }
  public static void getBodies(List<TOBody> list, Body b) {
    list.add(new TOBody(b));
    for (Body s:b.getSatellites()) {
      getBodies(list, s);
    }
  }
  
  
  
  
  
  private static StellarSystem getSystem(int systemId) {
	  for (StellarSystem s:FlatSpace.getCurrentGame().getSystems()) {
		  if (s.getId()==systemId) {
			  return s;
		  }
	  }
	  return null;
  }
  
  public static void update() {
	  updateBodies();
  }
  
  public static void updateBodies() {
	  Stack<Body> bodiesToUpdate = new Stack<>();
	  for(StellarSystem s:FlatSpace.getCurrentGame().getSystems()) {
		  bodiesToUpdate.push(s.getPrincipleBody());
	  }
	  while(!bodiesToUpdate.isEmpty()) {
		  Body b = bodiesToUpdate.pop();
		  b.getVectors().update(TimeController.timePerTick());
		  for(Body s:b.getSatellites()) {
			  bodiesToUpdate.push(s);
		  }
	  }

  }
  
}

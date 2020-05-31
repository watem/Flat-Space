package flatSpace.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import flatSpace.Controller.TO.Coordinates;
import flatSpace.Controller.TO.TOBody;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.Profile;
import flatSpace.backendData.stellarBodies.Body;
import flatSpace.backendData.stellarBodies.StellarSystem;

public class SpatialController {

  public static HashMap<TOBody, Coordinates> getAllLocations(int systemId) {
	  StellarSystem s = getSystem(systemId);
	  if (s==null) {
		  throw new InvalidInputException("System not found");
	  }
    return getAllLocations(s);
  }
  
private static HashMap<TOBody, Coordinates> getAllLocations(StellarSystem s) {
    HashMap<TOBody, Coordinates> map = new HashMap<>();
    getBodyLocation(map, s.getPrincipleBody(), new Coordinates(0,0));
    return map;
  }

  private static void getBodyLocation(HashMap<TOBody, Coordinates> map, Body b, Coordinates parentCoords) {
	if (b==null) {
	  return;
	}
    Coordinates localPosition = b.getVectors().transform();
    Coordinates systemLocation = new Coordinates(parentCoords.getX()+localPosition.getX(), parentCoords.getY()+localPosition.getY());
    map.put(new TOBody(b), systemLocation);
    for (Body s:b.getSatellites()) {
      getBodyLocation(map, s, systemLocation);
    }
  }

  public static List<TOBody> getAllBodies(int systemId) {
    StellarSystem s = getSystem(systemId);
    if (s==null) {
		  throw new InvalidInputException("System not found");
	}
    List<TOBody> list = new ArrayList<>();
    getBodies(list, s.getPrincipleBody());
    return list;
  }
  private static void getBodies(List<TOBody> list, Body b) {
	if (b==null) {
		return;
	}
    list.add(new TOBody(b));
    for (Body s:b.getSatellites()) {
      getBodies(list, s);
    }
  }
  
  
  
  
  
  private static StellarSystem getSystem(int systemId) {
	  for (StellarSystem s:Profile.getCurrentProfile().getFlatSpace().getCurrentGame().getSystems()) {
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
	  Queue<Body> bodiesToUpdate = new LinkedList<>();
	  try {
		  FlatSpaceGame g = GameController.getCurrentGame();
		  for(StellarSystem s:Profile.getCurrentProfile().getFlatSpace().getCurrentGame().getSystems()) {
			  if (s.getPrincipleBody()!=null) {
				  bodiesToUpdate.add(s.getPrincipleBody());
			  }
		  }
		  while(!bodiesToUpdate.isEmpty()) {
			  Body b = bodiesToUpdate.remove();
			  b.getVectors().update(TimeController.timePerTick());
			  for(Body s:b.getSatellites()) {
				  bodiesToUpdate.add(s);
			  }
		  }
	  } catch(InvalidInputException e) {
		  
	  }
  }
}

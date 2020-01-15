public class Fleet {
  Location currentLocation;
  double mass;
  List<Ship> ships;
  Fleet masterFleet;
  Fleet subFleet;
  Velocity v;
  boolean joinedWithMaster;
  boolean inPlane;
  boolean autoPlane;
  Body landed;                    //null if in motion, body

  public int getMaxPlanarMass() {

  }
  public void updatePosition() {

  }

  public int getMaxSpeed() {
    if(inPlane) {

    } else {

    }
  }

  public void enterPlanarSpace(PlanarEntrance portal) {
    //
  }
  public void exitPlanarSpace(PlanarEntrance portal) {

  }

  public boolean goToBody(Body target) {
    if (currentLocation instanceof PlanarLocation) { //currently in planar space
      if (target instanceof PlanarEntrance) { //going to an exit portal
        planarMoveTo(target);
      } else if (autoPlane) {                 //not going to an exit portal, but allowed to auto pathfind
        return false; //TODO
      } else {                                //not allowed to travel to target location, abort
        return false;
      }
    } else if (currentLocation instanceof SystemLocation) {
      if (((SystemLocation)currentLocation).getSystem().equals(target.getSystem())) {
        //TODO
        //determine current primary body
        //determine if they share common spheres, determine smallest common spheres
        //estimate apogee and periapse
        //if body is gravityless, move to it's orbit then rendezvouz. be within certain distance of target, and below certain relative velocity, then land
        //when in body's sphere, get to appogee if possible, then slow down periapse until it's within body's landing radius, then move to perigee, then slow down apogee.
        //when moving to sphere that contains target, estimate apogee and periapse
        // if target is lower than perigee, go to apogee if possible, then lower apogee to match, then get to periapse, and circularize, then rendezVouz
        // if target is higher than perigee, go to perigee, then raise apogee to match target, then get to apogee, and circularize, then rendezVouz

      } else if (autoPlane) {                 //different system, but can auto transit
        PlanarEntrance closestE1, closestE2;
        int minDistance = -1;
        for (PlanarEntrance e1: ((SystemLocation)currentLocation).getSystem().getPlanarEntrances()) {
          for (PlanarEntrance e2: target.getSystem().getPlanarEntrances()) {
            int dis = e1.getPlanarSideEntrance().getDistance(e2.getPlanarSideEntrance());
            if (minDistance == -1) {
              minDistance = dis;
              closestE1 = e1;
              closestE2 = e2;
            } else if (dis<minDistance) {
              minDistance = dis;
              closestE1 = e1;
              closestE2 = e2;
            }
          }
        }
        if (minDistance = -1) {               //no detected path
          return false;
        }
        goToBody(closestE1);
        enterPlanarSpace(closestE1);
        goToBody(closestE2);
        exitPlanarSpace(closestE2);
        goToBody(target);
      } else {                                //different system, cannot auto transit
        return false;
      }
    } else { //invalid location type
      return false;
    }
  }

  public boolean matchOrbit(Body target) {

  }
  public boolean rendezVouz(Body target) {

  }
  public boolean land(Body target) {

  }


  public class Landed {
    boolean landed;
    Body landedLocation;
  }
}

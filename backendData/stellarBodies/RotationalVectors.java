package flatSpace.backendData.stellarBodies;

import flatSpace.Controller.TO.Coordinates;

public class RotationalVectors {
  private double theta, omega, r;

  public double getR() {
    return r;
  }
  public double getOmega() {
    return omega;
  }
  public double getTheta() {
    return theta;
  }
  public void update(double dt) {
    theta +=omega*dt;
    theta %= 360;
  }
  public RotationalVectors setR(double distance){
    r = distance;
    return this;
  }
  public RotationalVectors setOmega(double angularV) {
    omega = angularV;
    return this;
  }
  public RotationalVectors setTheta(double angle) {
    theta = angle;
    return this;
  }

  public Coordinates transform() {
    return new Coordinates(getX(), getY());
  }
  public double getX() {
    return r*Math.cos(Math.toRadians(theta));
  }
  public double getY() {
    return r*Math.sin(Math.toRadians(theta));
  }

}

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
  public void setR(double distance){
    r = distance;
  }
  public void setOmega(double angularV) {
    omega = angularV;
  }
  public void setTheta(double angle) {
    theta = angle;
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

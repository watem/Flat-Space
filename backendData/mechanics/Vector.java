package flatSpace.backendData.mechanics;

public interface Vector {
  public static Vector add(Vector a, Vector b) {
	  return a.add(b);
  }
  public static Vector subtract(Vector a, Vector b) {
	  return a.subtract(b);
  }
  public static Vector multiply(Vector a, double b) {
	  return a.multiply(b);
  }
  public static Vector transpose(Vector a) {
	  return a.transpose();
  }
  public Vector add(Vector a);
  public Vector subtract(Vector a);
  public Vector multiply(double c);
  public Vector transpose();
  public Vector2d unitVector();
  public double length();
  public Number x();
  public Number y();
}

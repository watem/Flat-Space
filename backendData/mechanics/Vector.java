public interface Vector {
  public static Vector add(Vector a, Vector b);
  public static Vector subtract(Vector a, Vector b);
  public static Vector multiply(Vector a, double b);
  public static Vector transpose(Vector a);
  public Vector add(Vector a);
  public Vector subtract(Vector a);
  public Vector multiply(double c);
  public Vector transpose();
  public Vector2d unitVector();
  public double length();
}

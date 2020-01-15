public class Vector2d implements D2Vector {
  private double x, y;
  public Vector2d(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public Vector2d(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public Vector2d(D2Vector a) {
    this(a.x(), a.y());
  }
  public static Vector add(Vector a, Vector b) {
    return new Vector2d(a.x()+b.x(), a.y()+b.y());
  }
  public static Vector subtract(Vector a, Vector b) {
    return new Vector2d(a.x()-b.x(), a.y()-b.y());
  }
  public static Vector multiply(Vector a, double b) {
    return new Vector2d(a.x()*b, a.y()*b);
  }
  public Vector add(Vector a) {
    this.x+=a.x();
    this.y+=a.y();
    return this;
  }
  public Vector subtract(Vector a) {
    this.x-=a.x();
    this.y-=a.y();
    return this;
  }
  public Vector multiply(double c) {
    x *= c;
    y *= c;
    return this;
  }
  public Vector2d unitVector() {
    double l = length();
    return new Vector2d(x/l, y/l);
  }
  public double length() {
    return Math.sqrt(x*x+y*y);
  }
  public Number x() {
    return this.x;
  }
  public Number y() {
    return this.y;
  }
}

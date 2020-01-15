public class Vector2i implements D2Vector {
  private int x, y;
  public Vector2i(int x, int y) {
    this.x  = x;
    this.y = y;
  }
  public Vector2i(double x, double y) {
    this((int)x, (int)y);
  }
  public Vector2i(D2Vector a) {
    this(a.x(), a.y());
  }
  public static Vector add(Vector a, Vector b) {
    return new Vector2i(a.x()+b.x(), a.y()+b.y());
  }
  public static Vector subtract(Vector a, Vector b) {
    return new Vector2i(a.x()-b.x(), a.y()-b.y());
  }
  public static Vector multiply(Vector a, double b) {
    return new Vector2i(a.x()*b, a.y()*b);
  }
  public Vector add(Vector a) {
    this.x+=(int)a.x();
    this.y+=(int)a.y();
    return this;
  }
  public Vector subtract(Vector a) {
    this.x-=(int)a.x();
    this.y-=(int)a.y();
    return this;
  }
  public Vector multiply(double c) {
    x = (int)(x*c);
    y = (int)(y*c);
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

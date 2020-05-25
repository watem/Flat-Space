package FlatSpace.backendData.mechanics;

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
    this(a.x().intValue(), a.y().intValue());
  }
  public static Vector add(Vector a, Vector b) {
    return new Vector2i(a.x().intValue()+b.x().intValue(), a.y().intValue()+b.y().intValue());
  }
  public static Vector subtract(Vector a, Vector b) {
    return new Vector2i(a.x().intValue()-b.x().intValue(), a.y().intValue()-b.y().intValue());
  }
  public static Vector multiply(Vector a, double b) {
    return new Vector2i(a.x().intValue()*b, a.y().intValue()*b);
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
@Override
public Vector transpose() {
	// TODO Auto-generated method stub
	return null;
}
}

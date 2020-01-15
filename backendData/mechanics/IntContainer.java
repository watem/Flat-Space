//Exists so that everywhere that needs to use a common int that is shared doesn't need to constantly grab from the source as it's changed since the source is not nessecarily known.
public class IntContainer {
  public int i;
  public IntContainer(int x) {
    i=x;
  }
  public IntContainer() {}
}

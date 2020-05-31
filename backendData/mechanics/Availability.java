package flatSpace.backendData.mechanics;

public class Availability {
  private int[] availability; //from 0 to 100 representing 0 to 1.0

  public int[] getAll() {
    if (availability == null) {
      availability = new int[ResourceSet.numResources];
    }
    return availability;
  }
}

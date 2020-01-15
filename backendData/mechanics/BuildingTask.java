public class BuildingTask extends Task {
  IntContainer building;
  int number;
  IndividualBuildingTask childTask;
  private boolean cancelled = false;

  public BuildingTask(int cost, IntContainer progress, Colony colony, ResourceSet materialCost, IntContainer building, int number) {
    super(cost, progress, colony, materialCost.clone().multiplyAll(number));
    this.building = building;
    this.number = number;
    if (number > 0) {
      task = new individualBuildingTask(cost, cost, progress, building, number, colony, this, materialCost);
    }
  }
  private void finish() {}
  public void cancel() {
    cancelled = true;
    childTask.cancel();
  }
  public boolean isCancelled() {
    return cancelled;
  }

  public class IndividualBuildingTask extends Task {
    IntContainer building;
    int number;
    BuildingTask parentTask;
    ResourceSet individualCost;

    public IndividualBuildingTask(int totalCost, int remainingCost, IntContainer progress, IntContainer building, int number, Colony colony, BuildingTask parentTask, ResourceSet materialCost) {
      super(totalCost, remainingCost, progress, colony, null);
      this.building = building;
      this.number = number;
      this.parentTask = parentTask;
      this.individualCost = materialCost;
    }
    private void finish() {
      int buildingsMade = 1+(progress.i-cost)/totalCost;
      if (buildingsMade>number) {
        buildingsMade = number
      }
      building.i += buildingsMade;

      number-=buildingsMade;

      if (number>0) {
        int remainingCost = (progress.i-cost)%totalCost;
        new individualBuildingTask(totalCost, remainingCost, progress, building, number);
      }
    }
    public void cancel() {
      if(!parentTask.isCancelled()) {
        parentTask.cancel()
      } else {
        if (materialCost!=null) {
          if ((double)cost/totalCost>=0.9) {
            colony.getResources().add(materialCost);
          } else {
            colony.getResources().add(materialCost.multiplyAll(0.5));
          }
        }
        --number;
        if (number > 0 ) {
          colony.getResources().add(materialCost.multiplyAll(number));
        }
      }
    }
  }
}

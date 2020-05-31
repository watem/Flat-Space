package flatSpace.backendData.mechanics;

import flatSpace.backendData.nations.Colony;

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
    	childTask = new IndividualBuildingTask(cost, cost, progress, building, number, colony, this, materialCost);
    }
  }
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
    public void finish() {
      int buildingsMade = 1+(getProgress().i-getCost())/getTotalCost();
      if (buildingsMade>number) {
        buildingsMade = number;
      }
      building.i += buildingsMade;

      number-=buildingsMade;

      if (number>0) {
        int remainingCost = (getProgress().i-getCost())%getTotalCost();
        new IndividualBuildingTask(getTotalCost(), remainingCost, getProgress(), building, number, getColony(), parentTask, getMaterialCost());
      }
    }
    public void cancel() {
      if(!parentTask.isCancelled()) {
        parentTask.cancel();
      } else {
        if (getMaterialCost()!=null) {
          if ((double)getCost()/getTotalCost()>=0.9) {
            getColony().getStockpile().add(getMaterialCost());
          } else {
            getColony().getStockpile().add(getMaterialCost().multiplyAll(0.5));
          }
        }
        --number;
        if (number > 0 ) {
          getColony().getStockpile().add(getMaterialCost().multiplyAll(number));
        }
      }
    }
  }

@Override
public void finish() {
	// TODO Auto-generated method stub
	
}
}

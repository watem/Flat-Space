package FlatSpace.backendData.mechanics;

import java.util.Set;

import FlatSpace.backendData.nations.Colony;

public abstract class Task {
  private static Set<Task> allTasks;
  private int cost;
  private int totalCost;
  private IntContainer progress;
  private boolean finished = false;
  private Colony colony;
  private ResourceSet materialCost;

  public Task(int cost, IntContainer progress, Colony colony, ResourceSet materialCost) {
    this.setCost(cost);
    this.setProgress(progress);
    this.setTotalCost(cost);
    this.setColony(colony);
    if (colony.getStockpile().subtract(materialCost)) {
      allTasks.add(this);
    } else {
      //error
    }
  }
  public Task(int cost, int remainingCost, IntContainer progress, Colony colony, ResourceSet materialCost) {
    this(cost, progress, colony, materialCost);
    this.setCost(remainingCost);
  }

  public void work() {
    if (finished) {
      return;
    }
    if (getCost()<getProgress().i) {
      finish();
      finished = true;
      allTasks.remove(this);
    } else {
      setCost(getCost() - getProgress().i);
    }
  }
  public void cancel() {
    if (getMaterialCost()!=null) {
      if ((double)getCost()/getTotalCost()>=0.9) {
        getColony().getStockpile().add(getMaterialCost());
      } else {
        getColony().getStockpile().add(getMaterialCost().multiplyAll(0.5));
      }
    }
    finished = true;
    allTasks.remove(this);
  }

  public abstract void finish();
//public IntContainer getProgress() {
//	// TODO Auto-generated method stub
//	return null;
//}
public int getCost() {
	return cost;
}
public void setCost(int cost) {
	this.cost = cost;
}
public int getTotalCost() {
	return totalCost;
}
public void setTotalCost(int totalCost) {
	this.totalCost = totalCost;
}
public ResourceSet getMaterialCost() {
	return materialCost;
}
public void setMaterialCost(ResourceSet materialCost) {
	this.materialCost = materialCost;
}
public IntContainer getProgress() {
	return progress;
}
public void setProgress(IntContainer progress) {
	this.progress = progress;
}
public Colony getColony() {
	return colony;
}
public void setColony(Colony colony) {
	this.colony = colony;
}
}

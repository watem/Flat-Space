public abstract class Task {
  private static Set<Task> allTasks;
  private int cost;
  private int totalCost;
  private IntContainer progress;
  private boolean finished = false;
  private Colony colony;
  private ResourceSet materialCost;

  public Task(int cost, IntContainer progress, Colony colony, ResourceSet materialCost) {
    this.cost = cost;
    this.progress = progress;
    this.totalCost = cost;
    this.colony = colony;
    if (colony.getResources().subtract(materialCost)) {
      allTasks.add(this);
    } else {
      //error
    }
  }
  public Task(int cost, int remainingCost, IntContainer progress, Colony colony, ResourceSet materialCost) {
    this(cost, progress, colony, materialCost);
    this.cost = remainingCost;
  }

  public void work() {
    if (finished) {
      return;
    }
    if (cost<progress.i) {
      finish();
      finished = true;
      allTasks.remove(this);
    } else {
      cost -= progress.i;
    }
  }
  public void cancel() {
    if (materialCost!=null) {
      if ((double)cost/totalCost>=0.9) {
        colony.getResources().add(materialCost);
      } else {
        colony.getResources().add(materialCost.multiplyAll(0.5));
      }
    }
    finished = true;
    allTasks.remove(this);
  }

  private abstract void finish();
}

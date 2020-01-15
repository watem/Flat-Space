public class ResearchTask extends Task {
  Technology tech;

  public ResearchTask(int cost, IntContainer progress, Technology tech, Colony colony) {
    super(cost, progress, colony, null);
    this.tech = tech;
  }
  private void finish() {}
}

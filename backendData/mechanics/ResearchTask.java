package FlatSpace.backendData.mechanics;

import FlatSpace.backendData.nations.Colony;
import FlatSpace.backendData.nations.technologies.Technology;

public class ResearchTask extends Task {
  Technology tech;

  public ResearchTask(int cost, IntContainer progress, Technology tech, Colony colony) {
    super(cost, progress, colony, null);
    this.tech = tech;
  }
  public void finish() {}
}

public class Star extends Body {
  private String classification;
  public Star generateStar() {
    int totalWeight = 0;
    Map starWeightingMap = UniverseData.getWeightByStarType();
    for (int weight:starWeightingMap.values()) {
      totalWeight += weight;
    }
  }

}

public class UniverseData {

  private static Map<String, StarType> detailsByStarType;


  public static Map<String, int> getWeightByStarType() {
    if (detailsByStarType == null || detailsByStarType.size()==0) {
      generateDetails();
    }

    for ( : ) {

    }
    return weightByStarType;
  }


  private static void generateDetails() {
    if (detailsByStarType == null) {
      detailsByStarType = new HashMap<>();
    } else {
      detailsByStarType.clear();
    }
    List<StarType> types = new LinkedList();
    types.add(new StarType("Main sequence: O", 30, 16, 40, 6.6, 18)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: B", 130000, 2.1, 16, 1.8, 6.6)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: A", 600000, 1.4, 2.1, 1.4, 1.8)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: F", 3000000, 1.04, 1.4, 1.15, 1.4)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: G", 7600000, 0.8, 1.04, 0.96, 1.15)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: K", 12100000, 0.45, 0.8, 0.7, 0.96)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Main sequence: M", 76450000, 0.08, 0.45, 0.11, 0.7)); //TODO, mass currently in solar masses, rad in solar rads

    types.add(new StarType("Blue Giant", 1, 2, 16, 5, 10)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Blue Supergiant", 1, 11, 56, 12, 86.3)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Yellow Supergiant", 1, 0.4, 25, 30, 420)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Red Supergiant", , 0.3, 8, 9, 737)); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Red Giant", , 8, 40, 600, 2850)); //TODO, mass currently in solar masses, rad in solar rads

    types.add(new StarType("White Dwarf", , 0.17, 1.41, , )); //TODO, mass currently in solar masses, rad in solar rads
    types.add(new StarType("Neutron Star", , 1.1, 2.16, 10.8, 15)); //TODO, mass currently in solar masses, rad in km
    types.add(new StarType("Red Dwarf", , 0.075, 0.6, 0.09, 0.62); //TODO, mass currently in solar masses, rad in solar rads


    for (StarType StarType:types) {
      detailsByStarType.put(starType.classification, starType);
    }
  }

  public class StarType {
    public final String classification;
    public final int weighting;
    public final double minMass, maxMass;
    private final double minRad, maxRad;
    public StarType(String classification, int weighting, double minMass, double maxMass, double minRad, double maxRad) {
      this.classification = classification;
      this.weighting = weighting;
      this.minMass=minMass;
      this.maxMass=maxMass;
      this.massToRadRatio=massToRadRatio;
    }
    public double getRadius(double mass) {
      if (classification.equals("White Dwarf")) {
        return
      } else {
        return -1;
      }
    }
  }
}

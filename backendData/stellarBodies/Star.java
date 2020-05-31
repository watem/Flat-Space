package flatSpace.backendData.stellarBodies;

import java.util.Map;

public class Star extends Body {
  
	/**
	 *  
	 * @param aName
	 * @param aMass
	 * @param aRadius
	 * @param sys
	 */
public Star(String aName, double aMass, double aRadius, StellarSystem sys) {
		super(aName, aMass, aRadius, sys);
		sys.getStars().add(this);
	}
private String classification;
  public static Star generateStar(StellarSystem system) {
    int totalWeight = 0;
    Map<String, Integer> starWeightingMap = UniverseData.getWeightByStarType();
    for (int weight:starWeightingMap.values()) {
      totalWeight += weight;
    }
    
    
    
    return null;
  }

}

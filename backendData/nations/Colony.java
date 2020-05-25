package FlatSpace.backendData.nations;

import java.util.List;

import FlatSpace.backendData.Ships.ShipyardFleet;
import FlatSpace.backendData.Ships.SpecificPart;
import FlatSpace.backendData.installations.CommercialShipyard;
import FlatSpace.backendData.installations.NavalShipyard;
import FlatSpace.backendData.mechanics.ResourceSet;
import FlatSpace.backendData.stellarBodies.Body;

public class Colony {
  private Body body;
  private Nation owner;
  private Governor governor;

  private int automatedMines;
  private int mines;
  private int factories;
  private int fighterFactories;
  private int fuelRefineries;
  private int ordinanceFactories;
  private int maintanceFacilities;
  private int massDrivers;
  private int researchLabs;
  private int spacePorts;
  private int terraformingInstallations;
  private int trackingStations;

  private List<NavalShipyard> navalShipyards;
  private List<CommercialShipyard> CommercialShipyards;
  private ShipyardFleet shipyardFleet;

  private ResourceSet stockpile;
  private List<SpecificPart> partsStockpile;

  private int mineRate = updateMineRate();

  public void mine() {
    ResourceSet resources = body.getResources();
    if (mineRate <= 0 || resources == null) {
      return;
    }
    resources.mine(stockpile, mineRate, body.getResourceAvail());
    
    body.updateAvail();
  }

  public int updateMineRate() {
    mineRate = (int) (owner.getMineMultiplier()*(automatedMines+mines)*governor.getMineMultiplier());
    return mineRate;
  }

  public void addMines(int numMines) {
    mines += numMines;
    updateMineRate();

  }
  public void addAutoMines(int numMines) {
    automatedMines += numMines;
    updateMineRate();
  }
  /**
  * @return number of mines actually removed
  **/
  public int removeMines(int numMines) {
    if(numMines>mines) {
      numMines = mines;
    }
    mines -= numMines;
    updateMineRate();
    return numMines;
  }
  /**
  * @return number of automated mines actually removed
  **/
  public int removeAutoMines(int numMines) {
    if(numMines>mines) {
      numMines = mines;
    }
    automatedMines -= numMines;
    updateMineRate();
    return numMines;
  }
  public void setGovernor(Governor aGovernor) {
    this.governor = aGovernor;
    updateMineRate();
  }


  public void scrapPart(SpecificPart part) {
    if (partsStockpile.contains(part)) {
      part.scrap(this);
      partsStockpile.remove(part);
    } else {
      //error message
    }
  }

public Nation getOwner() {
	return owner;
}

public void setOwner(Nation owner) {
	this.owner = owner;
}

public int getAutomatedMines() {
	return automatedMines;
}

public void setAutomatedMines(int automatedMines) {
	this.automatedMines = automatedMines;
}

public int getMines() {
	return mines;
}

public void setMines(int mines) {
	this.mines = mines;
}

public int getFactories() {
	return factories;
}

public void setFactories(int factories) {
	this.factories = factories;
}

public int getFighterFactories() {
	return fighterFactories;
}

public void setFighterFactories(int fighterFactories) {
	this.fighterFactories = fighterFactories;
}

public int getFuelRefineries() {
	return fuelRefineries;
}

public void setFuelRefineries(int fuelRefineries) {
	this.fuelRefineries = fuelRefineries;
}

public int getOrdinanceFactories() {
	return ordinanceFactories;
}

public void setOrdinanceFactories(int ordinanceFactories) {
	this.ordinanceFactories = ordinanceFactories;
}

public int getMaintanceFacilities() {
	return maintanceFacilities;
}

public void setMaintanceFacilities(int maintanceFacilities) {
	this.maintanceFacilities = maintanceFacilities;
}

public int getMassDrivers() {
	return massDrivers;
}

public void setMassDrivers(int massDrivers) {
	this.massDrivers = massDrivers;
}

public int getResearchLabs() {
	return researchLabs;
}

public void setResearchLabs(int researchLabs) {
	this.researchLabs = researchLabs;
}

public int getSpacePorts() {
	return spacePorts;
}

public void setSpacePorts(int spacePorts) {
	this.spacePorts = spacePorts;
}

public int getTerraformingInstallations() {
	return terraformingInstallations;
}

public void setTerraformingInstallations(int terraformingInstallations) {
	this.terraformingInstallations = terraformingInstallations;
}

public int getTrackingStations() {
	return trackingStations;
}

public void setTrackingStations(int trackingStations) {
	this.trackingStations = trackingStations;
}

public Body getBody() {
	return body;
}

public Governor getGovernor() {
	return governor;
}

public List<NavalShipyard> getNavalShipyards() {
	return navalShipyards;
}

public List<CommercialShipyard> getCommercialShipyards() {
	return CommercialShipyards;
}

public ShipyardFleet getShipyardFleet() {
	return shipyardFleet;
}

public ResourceSet getStockpile() {
	return stockpile;
}

public List<SpecificPart> getPartsStockpile() {
	return partsStockpile;
}

public int getMineRate() {
	return mineRate;
}
}

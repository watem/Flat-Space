package flatSpace.backendData.nations;

import flatSpace.Controller.TO.TONationSettings;

public class NationSettings {
	private String name;
	private String homeSys;
	private int sysId;
	
	public NationSettings() {}
	public NationSettings(TONationSettings settings) {
		name = settings.getName();
		homeSys = settings.getHomeSys();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomeSys() {
		return homeSys;
	}
	public void setHomeSys(String homeSys) {
		this.homeSys = homeSys;
	}
}

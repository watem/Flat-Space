package flatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

import flatSpace.backendData.nations.Nation;
import flatSpace.backendData.nations.NationSettings;
import flatSpace.backendData.planarSpace.PlanarSpace;
import flatSpace.backendData.stellarBodies.StellarSystem;

public class FlatSpaceGame {
	private String gameName;
	private int id;
	private List<StellarSystem> systems;
	private List<Nation> nations;
	private NameLists names;
	private GameSettings gameSettings;
	private NationSettings playerNation;
	private SystemFactory factory = new SystemFactory(this);
	private PlanarSpace planeSpace;
	
	public List<StellarSystem> getSystems() {
		if (systems == null) {
			systems = new ArrayList<>();
		}
		return systems;
	}
	public FlatSpaceGame(String name, int id) {
		this(name, id, new GameSettings());
	}
	public FlatSpaceGame(String name, int id, GameSettings gameSettings) {
		gameName = name;
		this.id = id;
		names = new NameLists();
		this.gameSettings = gameSettings;
	}
	public List<Nation> getNations() {
		if (nations == null) {
			nations = new ArrayList<>();
		}
		return nations;
	}
	public void generate() {
		String sysName = playerNation.getHomeSys();
		if (sysName.equals("RAND")) {
			factory.create();
		} else {
			factory.create(sysName);
		}
		for(int i = 0;i<gameSettings.getStartingNPEs();++i) {
			createSystem();
		}
		
	}
	
	
	public StellarSystem createSystem() throws OutOfNamesException {
		return factory.create();
	}
	public StellarSystem createSystem(String name) {		
		return factory.create(name);
	}
	
	public String getGameName() {
		return gameName;
	}
	public int getId() {
		return id;
	}
	public NameLists getNames() {
		return names;
	}
	public GameSettings getGameSettings() {
		return gameSettings;
	}
	public void setGameSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}
	public NationSettings getPlayerNation() {
		return playerNation;
	}
	public void setPlayerNation(NationSettings playerNation) {
		this.playerNation = playerNation;
	}
	public PlanarSpace getPlaneSpace() {
		return planeSpace;
	}
	public void setPlaneSpace(PlanarSpace planeSpace) {
		this.planeSpace = planeSpace;
	}
}

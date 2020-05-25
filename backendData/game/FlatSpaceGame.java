package FlatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

import FlatSpace.backendData.game.OutOfNamesException;
import FlatSpace.backendData.nations.Nation;
import FlatSpace.backendData.stellarBodies.StellarSystem;

public class FlatSpaceGame {
	private String gameName;
	private int id;
	private List<StellarSystem> systems;
	private List<Nation> nations;
	private NameLists names;
	private GameSettings gameSetting = new GameSettings();
	private int nextSystemId = 0;
	
	public List<StellarSystem> getSystems() {
		if (systems == null) {
			systems = new ArrayList<>();
		}
		return systems;
	}
	public FlatSpaceGame(String name, int id) {
		gameName = name;
		this.id = id;
		names = new NameLists();
	}
	public List<Nation> getNations() {
		if (nations == null) {
			nations = new ArrayList<>();
		}
		return nations;
	}
	
	
	public StellarSystem createEmptySystem() throws OutOfNamesException {
		List<String> nameList = names.getSystemNames();
		if (nameList.size()<1) {
			throw new OutOfNamesException("no names left to select from");
		}
		int index =(int)Math.floor(Math.random()*nameList.size());
		String name = nameList.get(index);
		nameList.remove(index);
		StellarSystem system = new StellarSystem(name);
		system.setId(nextSystemId);
		++nextSystemId;
		getSystems().add(system);
		return system;
	}
	public StellarSystem createSystem() throws OutOfNamesException {
		StellarSystem system = createEmptySystem();
		
		
		return system;
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
		return gameSetting;
	}
}

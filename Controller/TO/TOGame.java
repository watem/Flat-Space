package flatSpace.Controller.TO;

import flatSpace.backendData.game.FlatSpaceGame;

public class TOGame {
	private String gameName;
	private int id;
	private TONationSettings natSet;
	private TOGameSettings gameSet;
	
	public TOGame() {
		// TODO Auto-generated constructor stub
	}
	public TOGame(FlatSpaceGame g) {
		gameName = g.getGameName();
		id = g.getId();
	}
	@Override
	public String toString() {
		return gameName;
	}
	public String getGameName() {
		return gameName;
	}
	public TOGame setGameName(String gameName) {
		this.gameName = gameName;
		return this;
	}
	public int getId() {
		return id;
	}
	public TONationSettings getNatSet() {
		return natSet;
	}
	public void setNatSet(TONationSettings natSet) {
		this.natSet = natSet;
	}
	public TOGameSettings getGameSet() {
		return gameSet;
	}
	public void setGameSet(TOGameSettings gameSet) {
		this.gameSet = gameSet;
	}

}

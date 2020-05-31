package flatSpace.Controller.TO;

import java.util.ArrayList;
import java.util.List;

import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.Profile;

public class TOProfile {

	String username;
	int id;
	List <String> games;
	
	public TOProfile() {
		
	}
	public TOProfile(String name, int id) {
		
	}
	public TOProfile(Profile p) {
		if (p==null) {
			return;
		}
		username = p.getUsername();
		id = p.getId();
		games = new ArrayList<>();
		for(FlatSpaceGame g:p.getFlatSpace().getGames()) {
			games.add(g.getGameName());
		}
	}

	@Override
	public String toString() {
		return username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public List<String> getGames() {
		return games;
	}

}

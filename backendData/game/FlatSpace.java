package flatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

public class FlatSpace {
	List<FlatSpaceGame> games;
	FlatSpaceGame currentGame;
	private int nextGameId = 0;
	Profile user;
	public static final String[] startingSys = {"Sol", "Proxima Centauri", "RAND"};
	
	public FlatSpace(Profile user) {
		this.user = user;
	}
	
	public FlatSpaceGame newGame(String name) {
		FlatSpaceGame newGame = new FlatSpaceGame(name, nextGameId);
		++nextGameId;
		getGames().add(newGame);
		return newGame;
	}


	public void setCurrentGame(FlatSpaceGame game) {
		currentGame = game;
	}


	public List<FlatSpaceGame> getGames() {
		if (games == null) {
			games = new ArrayList<>();
		}
		return games;
	}


	public FlatSpaceGame getCurrentGame() {
		return currentGame;
	}
	
	
}

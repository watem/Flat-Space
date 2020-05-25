package FlatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

public class FlatSpace {
	List<FlatSpaceGame> games;
	static FlatSpaceGame currentGame;
	private int nextId = 0;
	
	
	public FlatSpaceGame newGame(String name) {
		FlatSpaceGame newGame = new FlatSpaceGame(name, nextId);
		++nextId;
		getGames().add(newGame);
		return newGame;
	}


	public static void setCurrentGame(FlatSpaceGame game) {
		currentGame = game;
	}


	public List<FlatSpaceGame> getGames() {
		if (games == null) {
			games = new ArrayList<>();
		}
		return games;
	}


	public static FlatSpaceGame getCurrentGame() {
		return currentGame;
	}
	
	
}

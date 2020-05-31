package flatSpace.Controller;

import java.util.ArrayList;
import java.util.List;

import flatSpace.Controller.TO.TOGame;
import flatSpace.Controller.TO.TOProfile;
import flatSpace.backendData.game.FlatSpaceGame;
import flatSpace.backendData.game.GameSettings;
import flatSpace.backendData.game.Profile;
import flatSpace.backendData.nations.NationSettings;

public class StartMenuController {
	public static TOProfile createNewProfile(String username, char[] cs) {
		return new TOProfile(new Profile(username));
	}

	public static TOProfile getCurrentProfile() {
		
		return new TOProfile(Profile.getCurrentProfile());
	}
	public static Profile getProfile(TOProfile toP) {
		for(Profile p: Profile.getProfiles()) {
			if (p.getId()==toP.getId())  {
				return p;
			}
		}
		return null;
	}
	public static List<TOProfile> getAllProfiles() {
		List<TOProfile> list = new ArrayList<>();
		for (Profile p:Profile.getProfiles()) {
			list.add(new TOProfile(p));
		}
		return list;
	}

	public static boolean login(TOProfile user, char[] passphrase) {
		for(Profile p: Profile.getProfiles()) {
			if (p.getUsername().equals(user.getUsername())) {
				Profile.setCurrentProfile(p);
				return true;
			}
		}
		return false;		
	}
	
	public static List<TOGame> getProfileGames(TOProfile user) {
		List<TOGame> list = new ArrayList<>();
		if(user==null||getProfile(user)==null) {
			return list;
		}
		for(FlatSpaceGame g:getProfile(user).getFlatSpace().getGames()) {
			list.add(new TOGame(g));
		}
		return list;
	}
	public static void logout() {
		Profile.setCurrentProfile(null);
	}

	public static void createNewGame(TOGame newGame) {
		FlatSpaceGame game = Profile.getCurrentProfile().getFlatSpace().newGame(newGame.getGameName());
		game.setGameSettings(new GameSettings(newGame.getGameSet()));
		game.setPlayerNation(new NationSettings(newGame.getNatSet()));
		game.generate();	
	}

	public static void startGame(TOGame game) {
		FlatSpaceGame g = getGame(game);
		if (g==null) {
			throw new InvalidInputException("No game selected.");
		}
		Profile.getCurrentProfile().getFlatSpace().setCurrentGame(g);
		
	}
	public static FlatSpaceGame getGame(TOGame game) {
		for(FlatSpaceGame g:Profile.getCurrentProfile().getFlatSpace().getGames()) {
			if (g.getId()==game.getId() && g.getGameName().equals(game.getGameName())) {
				return g;
			}
		}
		return null;
	}
}

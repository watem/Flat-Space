package flatSpace.application;

import flatSpace.temporaryFrontend.GameMenuView;
import flatSpace.temporaryFrontend.StartMenu;

/**
 * 
 * @author Matthew
 *
 */
public class FlatSpaceApplication {
	static GameMenuView gameView;
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	          new StartMenu().setVisible(true);
	        }
	    });

	}

	public static GameMenuView getGameView() {
		return gameView;
	}

	public static void setGameMenuView(GameMenuView aGameView) {
		gameView = aGameView;
	}

	
	
}

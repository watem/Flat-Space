package flatSpace.testing.frontend;

import flatSpace.temporaryFrontend.StartMenu;

public class GameStartTesting {

public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	StartMenu view = new StartMenu(); 
	            view.setVisible(true);
	        }
	    });

	}

}

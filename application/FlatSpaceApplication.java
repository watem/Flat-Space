package FlatSpace.application;

import FlatSpace.temporaryFrontend.SystemView;

public class FlatSpaceApplication {
	static SystemView systemView;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	          systemView = new SystemView();
	          systemView.setVisible(true);
	        }
	    });

	}

	public static SystemView getSystemView() {
		return systemView;
	}

	public static void setSystemView(SystemView aSystemView) {
		systemView = aSystemView;
	}

	
	
}

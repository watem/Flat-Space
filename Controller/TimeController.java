package flatSpace.Controller;

import flatSpace.application.FlatSpaceApplication;
import flatSpace.backendData.game.Profile;
import flatSpace.temporaryFrontend.SystemView;

public class TimeController implements Runnable {

	private static int speed = 1;
	private static boolean paused = true;
	
	private static final int wait = 100;
	public static final int secondsPerDay = 86400;
	public static double timePerTick() {
		return secondsPerDay/Profile.getCurrentProfile().getFlatSpace().getCurrentGame().getGameSettings().getTicksPerDay();
	}
	

	public TimeController() {
		
	}
	public static void setSpeed(int i) {
		if(i<1) {
			i=1;
		}
		speed = i;
	}
	
	
	
	@Override
	public void run() {
		int viewCounter = 0;
		int industryCounter = 0;
		while(!paused) {
			
			SpatialController.update();
			//FleetController.update();
			
			
			
			++viewCounter;
			if (viewCounter>Math.pow(10, speed)) {
				updateViews();
				viewCounter=0;
			}
			++industryCounter;
			if (industryCounter>Profile.getCurrentProfile().getFlatSpace().getCurrentGame().getGameSettings().getIndustryLength()) {
//				IndustryController.update();
				industryCounter = 0;
			}
			
			
			
			try {
				Thread.sleep((long) (wait/Math.pow(10, speed)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void updateViews() {
		if (FlatSpaceApplication.getGameView()==null) {
			return;
		}
		for(SystemView v:FlatSpaceApplication.getGameView().getSystemViews()) {
			v.refresh();
		}
		if (FlatSpaceApplication.getGameView().getFlatView() != null) {
			FlatSpaceApplication.getGameView().getFlatView().refresh();
		}
		
	}


	public static int getSpeed() {
		if (speed<0) {
			speed = 0;
		}
		return speed;
	}


	public static int getSecondsperday() {
		return secondsPerDay;
	}

	public static void pause() {
		paused = true;
	}

	public static void unpause() {
		if (paused) {
			new Thread(new TimeController()).start();
		}
		paused = false;
	}
	
	public static boolean isPaused() {
		return paused;
	}
	
	public static boolean togglePause() {
		if(paused) {
			unpause();
		} else {
			pause();
		}
		
		return paused;
	}
	
	
}

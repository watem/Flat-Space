package FlatSpace.Controller;

import FlatSpace.application.FlatSpaceApplication;
import FlatSpace.backendData.game.FlatSpace;

public class TimeController implements Runnable {

	static int speed = 0;
	
	private static final int wait = 100;
	public static final int secondsPerDay = 86400;
	public static double timePerTick() {
		return secondsPerDay/FlatSpace.getCurrentGame().getGameSettings().getTicksPerDay();
	}
	

	public TimeController() {
		
	}
	public static void setSpeed(int i) {
		boolean spawnThread = false;
		if (speed<1 && i>=1) {
//			
			spawnThread = true;
		}
//		System.out.println("speed change");
		speed = i;
		if (spawnThread) {
			new Thread(new TimeController()).start();
//			new TimeController().run();
		}

	}
	
	
	
	@Override
	public void run() {
		int viewCounter = 0;
		int industryCounter = 0;
		while(speed>0) {
			try {
				Thread.sleep((long) (wait/Math.pow(10, speed)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println("loop");
			SpatialController.update();
			++viewCounter;
			if (viewCounter>Math.pow(10, speed)) {
//				System.out.println("view");
				FlatSpaceApplication.getSystemView().refresh();
				viewCounter=0;
			}
			++industryCounter;
			if (industryCounter>FlatSpace.getCurrentGame().getGameSettings().getIndustryLength()) {
				
			}
		}
		
	}

	
	
	
}

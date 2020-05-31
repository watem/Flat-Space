package flatSpace.backendData.game;

import flatSpace.Controller.TO.TOGameSettings;

public class GameSettings {
	private int industryDays = 5;
	private int ticksPerDay = 2880;
	private int startingNPEs = 2;
	private int flatRings;
	private double flatRadialDistance;
	private double flatMinRadialDistance;
	private double flatMinCircDistance;
	public GameSettings() {
		// TODO Auto-generated constructor stub
		
	}
	public GameSettings(TOGameSettings settings) {
		// TODO Auto-generated constructor stub
		
	}
	public long getIndustryLength() {
		return industryDays*ticksPerDay;
	}
	public void setIndustryLength(int industryLength) {
		this.industryDays = industryLength;
	}
	public int getTicksPerDay() {
		return ticksPerDay;
	}
	public void setTicksPerDay(int ticksPerDay) {
		this.ticksPerDay = ticksPerDay;
	}
	public int getIndustryDays() {
		return industryDays;
	}
	public void setIndustryDays(int industryDays) {
		this.industryDays = industryDays;
	}
	public int getStartingNPEs() {
		return startingNPEs;
	}
	public void setStartingNPEs(int startingNPEs) {
		this.startingNPEs = startingNPEs;
	}
	public int numPortals() {
		int num = 0;
		double rad = flatMinRadialDistance;
		for(int i=0;i<flatRings;++i) {
			num += Math.floor(2*Math.PI*rad/flatMinCircDistance);
			rad+=flatRadialDistance;
		}
		return num;
	}
	public int numPortalsOnRing(int ringNumber) {
		double rad = flatMinRadialDistance+(ringNumber-1)*flatRadialDistance;
		int num =(int) Math.floor(2*Math.PI*rad/flatMinCircDistance);
		return num;
	}
	public int getFlatRings() {
		return flatRings;
	}
	public double getFlatRadialDistance() {
		return flatRadialDistance;
	}
	public double getFlatMinRadialDistance() {
		return flatMinRadialDistance;
	}
	public double getFlatMinCircDistance() {
		return flatMinCircDistance;
	}

}

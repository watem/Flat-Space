package flatSpace.Controller.TO;

public class TOGameSettings {
	private int industryDays = 5;
	private int ticksPerDay = 2880;
	private int startingNPEs = 2;
	public TOGameSettings() {
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

}

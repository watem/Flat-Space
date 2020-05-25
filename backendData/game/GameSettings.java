package FlatSpace.backendData.game;

public class GameSettings {
	private int industryDays = 5;
	private int ticksPerDay = 2880;
	public GameSettings() {
		// TODO Auto-generated constructor stub
		
	}
	public long getIndustryLength() {
		return industryDays*ticksPerDay;
	}
	public void setIndustryLength(int industryLength) {
		this.industryDays = industryDays;
	}
	public int getTicksPerDay() {
		return ticksPerDay;
	}
	public void setTicksPerDay(int ticksPerDay) {
		this.ticksPerDay = ticksPerDay;
	}

}

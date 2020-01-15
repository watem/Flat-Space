public class MineRateTech extends Technology {
  private int rate;   //base rate/mine
  public void research(Nation nation) {
    nation.updateMineRate(rate);
    
  }
}

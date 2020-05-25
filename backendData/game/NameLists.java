package FlatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

public class NameLists {
	


	String[] systems = {"Sol", "Alpha Centauri"};
	public List<String> systemNames;
	
	
	public List<String> getSystemNames() {
		if (systemNames == null) {
			systemNames = new ArrayList<>();
			for(String s:systems) {
				systemNames.add(s);
			}
		}
		return systemNames;
	}


	public NameLists() {
			}
	
	public String intToRoman(int i) {
		String str = "";
		if (i<=10) {
			if (i==10) {
				str = "X";
			} else if(i ==9) {
				str = "IX";
			} else if(i>=5) {
				str = "V";
			    i-=5;
			}
			if (i==4) {
				str = "IV";
			} else while (i>0) {
				--i;
				str+="I";
			}
			
		}
		return null;
	}
}

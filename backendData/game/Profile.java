package flatSpace.backendData.game;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	static int nextId = 0;
	static List<Profile> profiles;
	static Profile currentProfile;
	String username;
	int id;
	FlatSpace flatSpace;
	//settings

	public Profile(String name) {
		username = name;
		id = nextId++;
		flatSpace = new FlatSpace(this);
		getProfiles().add(this);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static List<Profile> getProfiles() {
		if (profiles==null) {
			profiles = new ArrayList<>();
		}
		return profiles;
	}

	public int getId() {
		return id;
	}

	public FlatSpace getFlatSpace() {
		if (flatSpace == null ) {
			flatSpace = new FlatSpace(this);
		}
		return flatSpace;
	}

	public static Profile getCurrentProfile() {
		return currentProfile;
	}

	public static void setCurrentProfile(Profile currentProfile) {
		Profile.currentProfile = currentProfile;
	}
	
	

	
}

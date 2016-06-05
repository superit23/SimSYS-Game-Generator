//Used to import character list into table
package edu.utdallas.gamewizard;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CharPrev {
	String name;
	String fileName;
	String type;
	String CharacterName;
	String AutonomousBehavior;
	String Rewards;
	@XmlElement(name = "Profile")
	Profile2 Profile;

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	@XmlElement(name = "fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCharacterName() {
		return CharacterName;
	}

	@XmlElement(name = "CharacterName")
	public void setCharacterName(String CharacterName) {
		this.CharacterName = CharacterName;
	}

	public String getAutonomousBehavior() {
		return AutonomousBehavior;
	}

	@XmlElement(name = "AutonomousBehavior")
	public void setAutonomousBehavior(String AutonomousBehavior) {
		this.AutonomousBehavior = AutonomousBehavior;
	}

	public String getRewards() {
		return Rewards;
	}

	@XmlElement(name = "Rewards")
	public void setRewards(String Rewards) {
		this.Rewards = Rewards;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	public Profile2 getProfile() {
		return Profile;
	}

	@XmlElement
	public void setProfile(Profile2 Profile) {
		this.Profile = Profile;
	}
}

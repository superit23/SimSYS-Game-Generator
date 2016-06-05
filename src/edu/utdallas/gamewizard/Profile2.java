//Used to import characters for character table

package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Profile2 {
	String ProfilePhoto;
	String Title;
	int YearsOfExperience;
	@XmlElementWrapper(name = "Skills")
	@XmlElement(name = "Skill")
	ArrayList<String> Skills;

	@XmlElementWrapper(name = "Demographics")
	@XmlElement(name = "Demographic")
	ArrayList<String> Demographics;

	@XmlElementWrapper(name = "Degrees")
	@XmlElement(name = "Degree")
	ArrayList<String> Degrees;

	public String getProfilePhoto() {
		return ProfilePhoto;
	}

	@XmlElement(name = "ProfilePhoto")
	public void setProfilePhoto(String ProfilePhoto) {
		this.ProfilePhoto = ProfilePhoto;
	}

	public String getTitle() {
		return Title;
	}

	@XmlElement(name = "Title")
	public void setTitle(String Title) {
		this.Title = Title;
	}

	public int getYearsOfExperience() {
		return YearsOfExperience;
	}

	@XmlElement(name = "YearsOfExperience")
	public void setYearsOfExperience(int YearsOfExperience) {
		this.YearsOfExperience = YearsOfExperience;
	}

	public ArrayList<String> getSkills() {
		return Skills;
	}

	@XmlElement
	public void setSkills(ArrayList<String> Skills) {
		this.Skills = Skills;
	}

	public ArrayList<String> getDemographics() {
		return Demographics;
	}

	@XmlElement
	public void setDemographics(ArrayList<String> Demographics) {
		this.Demographics = Demographics;
	}

	public ArrayList<String> getDegrees() {
		return Degrees;
	}

	@XmlElement
	public void setDegrees(ArrayList<String> Degrees) {
		this.Degrees = Degrees;
	}
}

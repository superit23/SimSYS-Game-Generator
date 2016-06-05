//Used for importing acts from template
//Note: Act2, Scene 2, Screen2 are different than the 
//Act, Scene, and Screen found in edu.utdallas.gamePlayEngine.model
//Contains arrayList of act2
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameTemplate {
	@XmlElementWrapper(name = "acts")
	@XmlElement(name = "act")
	ArrayList<Act2> actList;
	String name;

	public GameTemplate() {
		actList = new ArrayList<Act2>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Act2> getActs() {
		return actList;
	}

	@XmlElement
	public void setActs(ArrayList<Act2> actList) {
		this.actList = actList;
	}

}

//Contains arrayList of screen2 and name
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Scene2 {
	@XmlElementWrapper(name = "screens")
	@XmlElement(name = "screen")
	ArrayList<Screen2> screenList;

	public Scene2() {
		screenList = new ArrayList<Screen2>();
	}

	public Scene2(Scene2 scenecopy) {
		this.screenList = new ArrayList<Screen2>(scenecopy.screenList);
		// this.transitionType = new String(scenecopy.transitionType);
	}

	public ArrayList<Screen2> getScreens() {
		return screenList;
	}

	@XmlElement
	public void setScreen(ArrayList<Screen2> screenList) {
		this.screenList = screenList;
	}
}

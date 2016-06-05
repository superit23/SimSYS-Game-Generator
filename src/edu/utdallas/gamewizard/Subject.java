//Every subject has a name
package edu.utdallas.gamewizard;

import javax.xml.bind.annotation.XmlAttribute;

public class Subject {
	String name;

	// String name;

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

}

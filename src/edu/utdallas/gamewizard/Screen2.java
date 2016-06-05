//Contains type which is equivalent to name
package edu.utdallas.gamewizard;

import javax.xml.bind.annotation.XmlElement;

public class Screen2 {
	String type;

	// String name;

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

}

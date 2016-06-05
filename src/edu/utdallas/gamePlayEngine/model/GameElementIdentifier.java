package edu.utdallas.gamePlayEngine.model;

import javax.xml.bind.annotation.XmlElement;

public class GameElementIdentifier {

	private String id;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

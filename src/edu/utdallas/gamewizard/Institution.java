//Every Institution has a name and arrayList of domains
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Institution {
	@XmlElementWrapper(name = "domains")
	@XmlElement(name = "domain")
	ArrayList<Domain> domainList;
	String name;

	public Institution() {
		domainList = new ArrayList<Domain>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Domain> getDomains() {
		return domainList;
	}

	@XmlElement
	public void setDomains(ArrayList<Domain> domainList) {
		this.domainList = domainList;
	}

}

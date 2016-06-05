/*
 * Used to import knowledge areas
 * Every AreaSelection has a name and arrayList of institutions
 */
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AreaSelection {
	@XmlElementWrapper(name = "institutions")
	@XmlElement(name = "institution")
	ArrayList<Institution> institutionList;
	String name;

	public AreaSelection() {
		institutionList = new ArrayList<Institution>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Institution> getInstitutions() {
		return institutionList;
	}

	@XmlElement
	public void setInstitutions(ArrayList<Institution> institutionList) {
		this.institutionList = institutionList;
	}

}

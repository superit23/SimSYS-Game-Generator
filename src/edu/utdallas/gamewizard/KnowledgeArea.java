//Used to import learning objectives for subjects
//Knowledge areas containt arrayList of subKnowledgeAreas
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KnowledgeArea {
	@XmlElementWrapper(name = "subKnowledgeAreas")
	@XmlElement(name = "subKnowledgeArea")
	ArrayList<SubKnowledgeArea> sknowAs;
	String name;

	public KnowledgeArea() {
		sknowAs = new ArrayList<SubKnowledgeArea>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SubKnowledgeArea> getSubKnowledgeArea() {
		return sknowAs;
	}

	@XmlElement
	public void setSubKnowledgeAreas(ArrayList<SubKnowledgeArea> sknowAs) {
		this.sknowAs = sknowAs;
	}

}
//Contains arrayList of knowledge areas
//Used for importing knowledge areas
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KnowledgeRepo {
	@XmlElementWrapper(name = "knowledgeAreas")
	@XmlElement(name = "knowledgeArea")
	ArrayList<KnowledgeArea> knowAs;

	public KnowledgeRepo() {
		knowAs = new ArrayList<KnowledgeArea>();
	}

	public ArrayList<KnowledgeArea> getKnowledgeAreas() {
		return knowAs;
	}

	@XmlElement
	public void setKnowledgeAreas(ArrayList<KnowledgeArea> knowAs) {
		this.knowAs = knowAs;
	}

}
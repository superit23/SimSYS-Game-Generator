//Used for importing learning objectives from subjects into table
//Contains arrayList of learningObjectiveType
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.LearningObjectiveType;

@XmlRootElement
public class SubKnowledgeArea {

	@XmlElementWrapper(name = "learningObjectives")
	@XmlElement(name = "LearningObjective")
	ArrayList<LearningObjectiveType> los;
	String name;

	public SubKnowledgeArea() {
		los = new ArrayList<LearningObjectiveType>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<LearningObjectiveType> getLearningObjectives() {
		return los;
	}

	@XmlElement
	public void setLearningObjectives(ArrayList<LearningObjectiveType> los) {
		this.los = los;
	}

}
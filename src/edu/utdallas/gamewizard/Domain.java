//Every domain has a name and arraylist of grades and LearningObjectiveType
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import simsys.schema.components.*;

public class Domain {
	@XmlElementWrapper(name = "grades")
	@XmlElement(name = "grade")
	ArrayList<Grade> gradeList;
	String name;
	ArrayList<LearningObjectiveType> learningObjective;

	public Domain() {
		gradeList = new ArrayList<Grade>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Grade> getGrades() {
		return gradeList;
	}

	@XmlElement
	public void setGrades(ArrayList<Grade> gradeList) {
		this.gradeList = gradeList;
	}

}

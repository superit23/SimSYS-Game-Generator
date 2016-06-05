//Every grade has an arraylist of learningObjectiveType and Subjects
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import simsys.schema.components.*;

public class Grade {
	@XmlElementWrapper(name = "subjects")
	@XmlElement(name = "subject")
	ArrayList<Subject> subjectList;
	String name;
	ArrayList<LearningObjectiveType> learningObjective;

	public Grade() {
		subjectList = new ArrayList<Subject>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Subject> getSubjects() {
		return subjectList;
	}

	@XmlElement
	public void setSubjects(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}

}

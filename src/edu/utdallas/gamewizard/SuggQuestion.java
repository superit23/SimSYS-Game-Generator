//Used to import questions for game creation
package edu.utdallas.gamewizard;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.QuizChallenge;

@XmlRootElement
public class SuggQuestion {
	@XmlElement(name = "Challenge")
	ArrayList<QuizChallenge> questionList;

	public SuggQuestion() {
		questionList = new ArrayList<QuizChallenge>();
	}

	@XmlElement
	public void setQuesList(ArrayList<QuizChallenge> q) {
		this.questionList = q;
	}

	public ArrayList<QuizChallenge> getQuesList() {
		return questionList;
	}
}

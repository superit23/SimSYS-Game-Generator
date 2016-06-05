package edu.utdallas.gamePlayEngine.model;

import edu.utdallas.gamePlayEngine.controller.Message;

import java.util.Observable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Timer extends Observable {

	private String name;
	private String value;

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void timerStart() {
		setChanged();
		notifyObservers(Message.Start);

	}

	public void timerPlay() {
		setChanged();
		notifyObservers(Message.Play);

	}

	public void timerEnd() {
		setChanged();
		notifyObservers(Message.End);
	}
}

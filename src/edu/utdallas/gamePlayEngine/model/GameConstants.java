package edu.utdallas.gamePlayEngine.model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.annotation.XmlElement;
import edu.utdallas.gamePlayEngine.controller.Message;

public class GameConstants extends Observable {

	private List<Timer> timers;
	private int currentTimer = 0;

	@XmlElement(name = "timer")
	public List<Timer> getTimers() {
		return timers;
	}

	public void setTimers(List<Timer> timers) {
		this.timers = timers;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		for (Timer timer : timers) {
			timer.addObserver(o);
		}
	}

	public void gameConstantStart() {
		setChanged();
		notifyObservers(Message.Start);

	}

	public void gameConstantPlay() {
		setChanged();
		notifyObservers(Message.Play);
		while (currentTimer < timers.size()) {
			Timer timer = timers.get(currentTimer);
			timer.timerStart();
			timer.timerPlay();
			timer.timerEnd();
			currentTimer += 1;
		}
	}

	public void gameConstantEnd() {
		setChanged();
		notifyObservers(Message.End);
	}

}

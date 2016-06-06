package edu.utdallas.gamePlayEngine.model.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simsys.schema.components.Challenge;
import simsys.schema.components.GameElementType;
import simsys.schema.components.Screen;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;

/**
 * @author Sreeram Uses the Screen class in game specification
 */
public class ScreenAdapter extends Observable {

	/**
	 * screen
	 */
	private Screen screen;
	/**
	 * gameElementAdapters
	 */
	private List<GameElementAdapter> gameElementAdapters;
	/**
	 * challengeAdapaters
	 */
	private List<ChallengeAdapter> challengeAdapaters;

	/**
	 * @return
	 */
	public List<GameElementAdapter> getGameElementAdapters() {
		return gameElementAdapters;
	}

	/**
	 * @param gameElementAdapters
	 */
	public void setGameElementAdapters(
			List<GameElementAdapter> gameElementAdapters) {
		this.gameElementAdapters = gameElementAdapters;
	}

	/**
	 * @return
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * @param screen
	 */
	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);

		List<GameElementType> gameElements = getScreen().getGameElement();
		gameElementAdapters = new ArrayList<GameElementAdapter>();
		for (GameElementType gameElem : gameElements) {
			GameElementAdapter gameElementAdapter = new GameElementAdapter();
			gameElementAdapter.setGameElement(gameElem);
			gameElementAdapter.addObserver(o);
			gameElementAdapters.add(gameElementAdapter);
		}

		List<Challenge> challenges = getScreen().getChallenge();
		challengeAdapaters = new ArrayList<ChallengeAdapter>();
		for (Challenge challenge : challenges) {
			ChallengeAdapter challengeAdapter = new ChallengeAdapter();
			challengeAdapter.setChallenge(challenge);
			challengeAdapter.addObserver(o);
			challengeAdapaters.add(challengeAdapter);
		}

	}

	/**
	 * @param gameState
	 */
	public void screenStart(GameState gameState) {
		// Handle Screen specific activities in complex games
		setChanged();
		gameState.setMessage(Message.Start);
		gameState.setScreenAdapter(this);
		notifyObservers(gameState);
	}

	/**
	 * Go through each GameElement and start, play and end it If the process was
	 * to become any complex it can be moved to ScreenControl similar to
	 * GameControl
	 */
	public void screenPlay(GameState gameState) {
		setChanged();
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);
		/*
		 * while (currentGameElement < gameElements.size()) { GameElement
		 * gameElementToPlay = gameElements.get(currentGameElement);
		 * gameElementToPlay.gameElementStart();
		 * gameElementToPlay.gameElementPlay();
		 * gameElementToPlay.gameElementEnd(); currentGameElement += 1; }
		 */
	}

	/**
	 * @param gameState
	 */
	public void screenEnd(GameState gameState) {
		// Handle Screen specific cleanups in complex games
		setChanged();
		gameState.setMessage(Message.End);
		gameState.setGameElementAdapter(null);
		notifyObservers(gameState);
	}

	/**
	 * @return
	 */
	public List<ChallengeAdapter> getChallengeAdapaters() {
		return challengeAdapaters;
	}

	/**
	 * @param challengeAdapaters
	 */
	public void setChallengeAdapaters(List<ChallengeAdapter> challengeAdapaters) {
		this.challengeAdapaters = challengeAdapaters;
	}

}

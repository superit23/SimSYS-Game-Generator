package edu.utdallas.gamePlayEngine.model.adapter;

import java.util.Observable;

import simsys.schema.components.GameElementType;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;

/**
 * @author Sreeram Uses the GameElementType class in game specification
 */
public class GameElementAdapter extends Observable {

	/**
	 * gameElement
	 */
	private GameElementType gameElement;

	/**
	 * @return
	 */
	public GameElementType getGameElement() {
		return gameElement;
	}

	/**
	 * @param gameElement
	 */
	public void setGameElement(GameElementType gameElement) {
		this.gameElement = gameElement;
	}

	/**
	 * @param gameState
	 */
	public void gameElementStart(GameState gameState) {
		setChanged();
		gameState.setMessage(Message.Start);

		gameState.setGameElementAdapter(this);
		notifyObservers(gameState);
	}

	/**
	 * Default Implementation for classes that do not override this method
	 */
	public void gameElementPlay(GameState gameState) {

		System.out.println("Gamestate is " + gameState);
		setChanged();
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);

	}

	/**
	 * Default Implementation for classes that do not override this method
	 */
	public void gameElementEnd(GameState gameState) {
		System.out.println("Gamestate is " + gameState);
		setChanged();
		gameState.setMessage(Message.End);
		notifyObservers(gameState);
	}

}

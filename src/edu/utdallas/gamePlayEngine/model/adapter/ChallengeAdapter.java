package edu.utdallas.gamePlayEngine.model.adapter;

import java.util.Observable;

import simsys.schema.components.Challenge;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;

/**
 * @author Sreeram Uses the ChallengeType class in game specification
 */
public class ChallengeAdapter extends Observable {

	/**
	 * challenge
	 */
	private Challenge challenge;

	/**
	 * quizReward
	 */
	public static int quizReward;
	/**
	 * quizFeedback
	 */
	public static String quizFeedback;
	/**
	 * quizTitleChange
	 */
	public static String quizTitleChange;

	public static int nextDisplay;

	/**
	 * @return
	 */
	public Challenge getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge
	 */
	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	/**
	 * @return
	 */
	public int getNextDisplay() {
		return nextDisplay;
	}

	/**
	 * @param nextDisplay
	 */
	public void setNextDisplay(int nextDisplay) {
		ChallengeAdapter.nextDisplay = nextDisplay;
	}

	/**
	 * @param gameState
	 */
	public void challengeStart(GameState gameState) {

		setChanged();
		gameState.setChallenegeAdapter(this);
		gameState.setMessage(Message.Start);
		notifyObservers(gameState);
	}

	/**
	 * @param gameState
	 * @throws InterruptedException
	 */
	public void challengePlay(GameState gameState) throws InterruptedException {
		setChanged();
		gameState.setChallenegeAdapter(this);
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);

	}

	/**
	 * @param gameState
	 */
	public void challengeEnd(GameState gameState) {
		// backdrop.gameElementEnd();
		setChanged();
		gameState.setMessage(Message.End);
		notifyObservers(gameState);
	}
}

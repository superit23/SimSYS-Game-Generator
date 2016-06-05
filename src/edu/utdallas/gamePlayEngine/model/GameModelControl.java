/**
 * Controller class for Game Model - defines process on Game model
 * This file contains the Control class for Game Model
 */
package edu.utdallas.gamePlayEngine.model;

import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.view.GameView;

/**
 * GameControl class - mainly defines play process for Game
 */
public class GameModelControl {

	// private GameModel gameModel;
	private GameAdapter gameAdapter;

	public GameModelControl(GameAdapter gameAdapter) {
		this.gameAdapter = gameAdapter;
		// just for testing , remove it afterwards
		// System.out.println("model has been initialized");
	}

	public GameAdapter getGameAdapter() {
		return gameAdapter;
	}

	public void setGameAdapter(GameAdapter gameAdapter) {
		this.gameAdapter = gameAdapter;
	}

	public void gmcAddObserver(GameView view) {
		gameAdapter.addObserver(view);
	}

	public void startGame(String xmlFilePath) throws Exception {
		gameAdapter.startGame(xmlFilePath);

	}

	public void gmcStart(GameState gameState) {
		gameAdapter.gameModelStart(gameState);
	}

	/**
	 * Game Play process consists of start, play and end
	 * 
	 * @throws InterruptedException
	 */
	public void gmcPlay() throws InterruptedException {
		// gameModel.gameModelPlay();
	}

	public void gmcEnd() {
		gameAdapter.gameModelEnd();
	}

	public void gmcPause() {
		throw new UnsupportedOperationException();
	}

	public void gmcResume() {
		throw new UnsupportedOperationException();
	}

	public void gmcSave() {
		throw new UnsupportedOperationException();
	}

	public void setGameView(GameView gameView) {
		gameAdapter.setGameView(gameView);

	}

}

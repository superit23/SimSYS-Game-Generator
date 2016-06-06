package edu.utdallas.gamePlayEngine.model.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simsys.schema.components.Act;
import simsys.schema.components.Scene;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;

/**
 * @author Sreeram Uses the act class in game specification
 */
public class ActAdapter extends Observable {

	/**
	 * act
	 */
	private Act act;
	/**
	 * sceneAdapters
	 */
	private List<SceneAdapter> sceneAdapters;

	/**
	 * @return
	 */
	public Act getAct() {
		return act;
	}

	/**
	 * @param act
	 */
	public void setAct(Act act) {
		this.act = act;
	}

	/**
	 * @return
	 */
	public List<SceneAdapter> getSceneAdapters() {
		return sceneAdapters;
	}

	/**
	 * @param sceneAdapter
	 */
	public void setSceneAdapter(List<SceneAdapter> sceneAdapter) {
		this.sceneAdapters = sceneAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);

		List<Scene> scenes = getAct().getScene();
		sceneAdapters = new ArrayList<SceneAdapter>();

		for (Scene scene : scenes) {
			SceneAdapter sceneAdapter = new SceneAdapter();
			sceneAdapter.setScene(scene);
			sceneAdapter.addObserver(o);
			sceneAdapters.add(sceneAdapter);
		}
	}

	/**
	 * @param gameState
	 */
	public void actStart(GameState gameState) {
		// Handle Act specific activities in complex games
		setChanged();
		gameState.setActAdapter(this);
		gameState.setMessage(Message.Start);
		notifyObservers(gameState);
	}

	/**
	 * Go through each Scene and start, play and end it If the process was to
	 * become any complex it can be moved to ActControl similar to GameControl
	 * 
	 * @throws InterruptedException
	 */
	public void actPlay(GameState gameState) throws InterruptedException {
		setChanged();
		gameState.setActAdapter(this);
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);
		/*
		 * while (currentScene < scenes.size()) { sceneToPlay.sceneStart();
		 * sceneToPlay.scenePlay(); sceneToPlay.sceneEnd(); currentScene += 1; }
		 */
	}

	/**
	 * @param gameState
	 */
	public void actEnd(GameState gameState) {
		// Handle Act specific cleanups in complex games
		setChanged();
		gameState.setSceneAdapter(null);
		gameState.setScreenAdapter(null);
		gameState.setGameElementAdapter(null);
		gameState.setMessage(Message.End);
		notifyObservers(gameState);
	}

}

package edu.utdallas.gamePlayEngine.model.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;

/**
 * @author Sreeram Uses the Scene class in game specification
 */
public class SceneAdapter extends Observable {

	/**
	 * scene
	 */
	private Scene scene;
	/**
	 * screenAdapters
	 */
	private List<ScreenAdapter> screenAdapters;

	/**
	 * @return
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * @param scene
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * @return
	 */
	public List<ScreenAdapter> getScreenAdapters() {
		return screenAdapters;
	}

	/**
	 * @param screenAdapters
	 */
	public void setScreenAdapters(List<ScreenAdapter> screenAdapters) {
		this.screenAdapters = screenAdapters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);

		List<Screen> screens = getScene().getScreen();
		screenAdapters = new ArrayList<ScreenAdapter>();
		for (Screen screen : screens) {
			ScreenAdapter screenAdapter = new ScreenAdapter();
			screenAdapter.setScreen(screen);
			screenAdapter.addObserver(o);
			screenAdapters.add(screenAdapter);
		}
	}

	/**
	 * @param gameState
	 */
	public void sceneStart(GameState gameState) {
		setChanged();
		gameState.setSceneAdapter(this);
		gameState.setMessage(Message.Start);
		notifyObservers(gameState);
		// backdrop.gameElementStart();
		// Thread.sleep(5000);
	}

	/**
	 * Go through each Screen and start, play and end it If the process was to
	 * become any complex it can be moved to SceneControl similar to GameControl
	 */
	public void scenePlay(GameState gameState) {
		setChanged();
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);
		// backdrop.gameElementPlay();
		/*
		 * currentScreenToPlay = screens.get(currentScreen); while
		 * (currentScreen < screens.size()) { currentScreenToPlay.screenStart();
		 * currentScreenToPlay.screenPlay(); currentScreenToPlay.screenEnd();
		 * currentScreen += 1; }
		 */
	}

	/**
	 * @param gameState
	 */
	public void sceneEnd(GameState gameState) {
		// backdrop.gameElementEnd();
		setChanged();
		gameState.setMessage(Message.End);
		gameState.setScreenAdapter(null);
		gameState.setGameElementAdapter(null);
		notifyObservers(gameState);
	}

}

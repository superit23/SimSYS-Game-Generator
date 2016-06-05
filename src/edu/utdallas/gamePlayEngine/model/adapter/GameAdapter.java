package edu.utdallas.gamePlayEngine.model.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import simsys.schema.components.Act;
import simsys.schema.components.Game;
import simsys.schema.components.Speed;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;
import edu.utdallas.gamePlayEngine.view.GameView;

/**
 * @author Sreeram Uses the Game class in game specification
 */
public class GameAdapter extends Observable {

	/**
	 * game
	 */
	private Game game;
	/**
	 * actAdapters
	 */
	private List<ActAdapter> actAdapters = new ArrayList<ActAdapter>();
	/**
	 * gameAdapterObj
	 */
	private static GameAdapter gameAdapterObj;
	/**
	 * gameView
	 */
	private GameView gameView;
	/**
	 * gameEnded
	 */
	private static boolean gameEnded = false;
	/**
	 * speedMap
	 */
	public static Map<Speed, Integer> speedMap = new HashMap<Speed, Integer>();

	/**
	 * @return
	 */
	public static boolean hasGameEnded() {
		return gameEnded;
	}

	/**
	 * @param hasGameEnded
	 */
	public static void setGameEnded(boolean hasGameEnded) {
		GameAdapter.gameEnded = hasGameEnded;
	}

	/**
	 * @return
	 */
	public static GameAdapter getGameAdapterObj() {
		return gameAdapterObj;
	}

	/**
   * 
   */
	public GameAdapter() {
		setGameEnded(false);
	}

	/**
	 * @param gameAdapterObj
	 */
	public static void setGameAdapterObj(GameAdapter gameAdapterObj) {
		GameAdapter.gameAdapterObj = gameAdapterObj;
	}

	/**
	 * @param actAdapters
	 */
	public void setActAdapters(List<ActAdapter> actAdapters) {
		this.actAdapters = actAdapters;
	}

	/**
	 * @return
	 */
	public GameView getGameView() {
		return gameView;
	}

	/**
	 * @param gameView
	 */
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
		System.out.println("model knows about view");
	}

	/**
	 * @return
	 */
	public List<ActAdapter> getActAdapters() {
		return actAdapters;
	}

	/**
	 * @param actAdapter
	 */
	public void setActAdapter(List<ActAdapter> actAdapter) {
		this.actAdapters = actAdapter;
	}

	/**
	 * @return
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		List<Act> acts = getGame().getActs();
		actAdapters = new ArrayList<ActAdapter>();
		for (Act act : acts) {
			ActAdapter actAdapter = new ActAdapter();
			actAdapter.setAct(act);
			actAdapter.addObserver(o);
			actAdapters.add(actAdapter);
		}
		gameAdapterObj.actAdapters.addAll(actAdapters);
	}

	/**
	 * Declares the speed types of the animations
	 */
	public static void initializeSpeedTypes() {
		speedMap.put(Speed.FAST, 1000);
		speedMap.put(Speed.MEDIUM, 3000);
		speedMap.put(Speed.SLOW, 5000);
	}

	/**
	 * @param xmlFilePath
	 * @throws Exception
	 */
	public void startGame(String xmlFilePath) throws Exception {
		System.out.println("calling load in GameModel: " + xmlFilePath);
		initializeSpeedTypes();
		ChallengeAdapter.quizReward = 0;
		GameAdapter gameAdapter = load(xmlFilePath);
		gameAdapterObj = gameAdapter;
		// setValues(gameModel);
		addObserver(gameView);

	}

	/**
	 * This is private method Unmarshalls Game XML to create Game object
	 * 
	 * @param xmlFilePath
	 * @return
	 * @throws Exception
	 */
	private GameAdapter load(String xmlFilePath) throws Exception {
		try {
			// Load Game from XML
			JAXBContext context = JAXBContext.newInstance(Game.class);
			System.out.println("instance passed ");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			System.out.println("marshaller created");
			GameAdapter gameAdapter = new GameAdapter();
			Game game = (Game) unmarshaller.unmarshal(new File(xmlFilePath));
			game = game.animationHandler();
			this.setGame(game);
			gameAdapter.setGame(game);
			return gameAdapter;

		} catch (Exception ex) {
			// log the exception, show the error message on UI
			System.out.println(ex.getMessage());
			throw ex;
		}
	}

	public void gameModelStart(GameState gameState) {
		// Handle Game specific initializations if any for complex games
		setChanged();
		gameState.setMessage(Message.Start);
		notifyObservers(gameState);
	}

	/**
	 * Game Play method goes through each of the contained acts and plays those.
	 * 
	 * @throws InterruptedException
	 */
	public void gameModelPlay(GameState gameState) throws InterruptedException {
		setChanged();
		gameState.setMessage(Message.Play);
		notifyObservers(gameState);

		try {
			ActAdapter actToPlay = this.actAdapters.get(0);
			actToPlay.actStart(gameState);
		} catch (Exception e) {
			System.out.println("Hello!!!Here it comes!!!");
			e.printStackTrace();
		}

		// Go through each Act
		/*
		 * while (currentAct < acts.size()) { Act actToPlay =
		 * acts.get(currentAct); actToPlay.actStart(); actToPlay.actPlay();
		 * actToPlay.actEnd(); currentAct += 1; }
		 */
	}

	/**
	 * @param observable
	 * @param message
	 */
	public static void ControllerListener(Observable observable, Message message) {

	}

	/**
   * 
   */
	public void gameModelEnd() {
		// Handle Game specific clean up if any for complex games
		setChanged();
		deleteObservers();
		notifyObservers(Message.End);
	}

	// start,play and end methods to be added and notify obsever from these
	// methods

}

/**
 * Controller for Game Play Engine. Receives messages from View and sends to appropriate model boundary for update.
 * This file contains the Controller class for the Game Play Engine
 */
package edu.utdallas.gamePlayEngine.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simsys.schema.components.Character;
import simsys.schema.components.Reward;
import edu.utdallas.gamePlayEngine.model.GameModelBoundary;
import edu.utdallas.gamePlayEngine.model.adapter.ActAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ChallengeAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameElementAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.SceneAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ScreenAdapter;
import edu.utdallas.gamePlayEngine.view.GameView;
import edu.utdallas.gamePlayEngine.view.GameViewBoundary;
import edu.utdallas.gamePlayEngine.view.GameViewFrame;

/**
 * @author Sreeram Controller class for Game Play Engine
 *
 */
public class GameController implements ActionListener {

    public static boolean nemesisWasRight = false;
    /**
     * gameBoundary
     */
    private GameModelBoundary gameBoundary;
    /**
     * gameViewBoundary
     */
    private GameViewBoundary gameViewBoundary;
    // current last question
    public static int npcCorrect;
    public static int npcIncorrect;
    /**
     * currentActAdapter
     */
    private static int currentActAdapter;
    /**
     * currentSceneAdapter
     */
    private static int currentSceneAdapter;
    /**
     * currentScreenAdapter
     */
    private static int currentScreenAdapter;
    /**
     * currentGameElementAdapter
     */
    private static int currentGameElementAdapter;
    /**
     * currentChallengeAdapter
     */

    public static int currentChallengeAdapter;
    // private static int currentQuiz;
    // private static int currentChallenge;
    /**
     * actAdapterToStart
     */
    private static ActAdapter actAdapterToStart = new ActAdapter();
    /**
     * sceneAdapterToStart
     */
    private static SceneAdapter sceneAdapterToStart = new SceneAdapter();
    /**
     * screenAdapterToStart
     */
    private static ScreenAdapter screenAdapterToStart = new ScreenAdapter();
    /**
     * gameElementAdapterToStart
     */
    private static GameElementAdapter gameElementAdapterToStart = new GameElementAdapter();
    /**
     * challengeAdapterToStart
     */
    private static ChallengeAdapter challengeAdapterToStart = new ChallengeAdapter();

    public static int counter = 0;

    // Threadpool executor for Tasks.
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Initializes GameBoundary and RewardBoundary
     *
     * @param game
     */
    public GameController(GameAdapter gameAdapter, GameView gameView) {
        // Zac ZHANG added: initialize all parameters
        currentActAdapter = 0;
        currentSceneAdapter = 0;
        currentScreenAdapter = 0;
        currentGameElementAdapter = 0;
        currentChallengeAdapter = 0;

        // currentQuiz = 0;
        // currentChallenge = 0;
        npcCorrect = 0;
        npcIncorrect = 0;
        actAdapterToStart = new ActAdapter();
        sceneAdapterToStart = new SceneAdapter();
        screenAdapterToStart = new ScreenAdapter();
        gameElementAdapterToStart = new GameElementAdapter();
        challengeAdapterToStart = new ChallengeAdapter();
        // challengeToStart = new ChallengeStructure();
        // quizToStart = new Quiz();
        this.gameBoundary = new GameModelBoundary(gameAdapter);
        this.gameViewBoundary = new GameViewBoundary(gameView);

        System.out.println("controller has been initialized");
        System.out.println("controller knows about model and view");
    }

    /**
     * Core method that processes both internally generated and externally
     * generated messages Parses the message and then calls appropriate control
     * and action. This method is to be updated to support more games with new
     * messages.
     *
     * @param messageType
     * @param message
     * @throws InterruptedException
     */
    /*
	 * public void gameControllerPlay(MessageType messageType, String message)
	 * throws InterruptedException { if (messageType == MessageType.Internal) {
	 * // In future add processing for Timer generated internal messages // Then
	 * parse message parameter to get Control and Action to be // called.
	 * gameBoundary.gmbPlay(); } else if (messageType == MessageType.External) {
	 * if (message.equals("start")) { // start(gameState);
	 * gameBoundary.gmbPlay(); end(); } else if (message.equals("exit")) { //
	 * Writing following line to console is for demo purpose and // should be
	 * removed when view is built //
	 * System.out.println("Controller: Received user input to end the game");
	 * System.exit(0); } else { // Writing following line to console is for demo
	 * purpose and // should be removed when view is built //
	 * System.out.println("Controller : Received user input from view " // +
	 * message); String[] input = message.split("[.]"); if
	 * (input[0].equals("Reward") && input[1].equals("AddPoints")) {
	 * rewardBoundary.adddPoints(Integer.parseInt(input[2])); } } } }
	 */
    public void start(GameState gameState) {
        gameBoundary.gmbStart(gameState);
    }

    public void end() {
        gameBoundary.gmbEnd();
    }

    /* this method returns the model boundary */
    public GameModelBoundary getModelBoundary() {
        return gameBoundary;
    }

    public GameViewBoundary getViewBoundary() {
        return gameViewBoundary;
    }

    // @Override
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * @param gameState
     * @return
     */
    public static boolean actToStart(GameState gameState) {

        boolean isActStart = currentActAdapter < GameAdapter.getGameAdapterObj().getActAdapters().size();

        if (isActStart) {

            currentSceneAdapter = 0;
            actAdapterToStart = GameAdapter.getGameAdapterObj().getActAdapters().get(currentActAdapter);

//			try {
            actAdapterToStart.actStart(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
        // System.out.println("Iniyan..." + currentAct);
        return isActStart;
    }

    /**
     * @param gameState
     */
    public static void actToPlay(GameState gameState) throws InterruptedException {

        if (currentActAdapter < GameAdapter.getGameAdapterObj().getActAdapters().size()) {

            actAdapterToStart = GameAdapter.getGameAdapterObj().getActAdapters().get(currentActAdapter);

//			try {
            actAdapterToStart.actPlay(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
    }

    /**
     * @param gameState
     */
    public static void actToEnd(GameState gameState) {
        currentActAdapter++;
//		try {
        actAdapterToStart.actEnd(gameState);

//		} catch (Exception e) {
//			System.err.print(e);
//		}
    }

    // ---------------SCENE----------------------------

    /**
     * @param gameState
     * @return
     */
    public static boolean sceneToStart(GameState gameState) {

        boolean isSceneStart = isSceneStart();
        if (isSceneStart) {

            currentScreenAdapter = 0;
            sceneAdapterToStart = (actAdapterToStart.getSceneAdapters().get(currentSceneAdapter));

//			try {
            sceneAdapterToStart.sceneStart(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
        return isSceneStart;
    }

    /**
     * @return
     */
    public static boolean isSceneStart() {
        return currentSceneAdapter < actAdapterToStart.getSceneAdapters().size();
    }

    /**
     * @param gameState
     */
    public static void sceneToPlay(GameState gameState) {
        if (isSceneStart()) {
            sceneAdapterToStart = (actAdapterToStart.getSceneAdapters().get(currentSceneAdapter));

//			try {
            sceneAdapterToStart.scenePlay(gameState);
//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
    }

    /**
     * @param gameState
     */
    public static void sceneToEnd(GameState gameState) {
        currentSceneAdapter++;
//		try {
        sceneAdapterToStart.sceneEnd(gameState);
//		} catch (Exception e) {
//			System.err.print(e);
//		}
    }

    public static void startNextScene(String sceneName, GameState gameState) {

        // TODO Write code for starting next scene.//written by rithika
		/*
		 * List<Scene> sceneList = gameState.getAct().getScenes(); Scene
		 * resultScene = new Scene(); boolean flag = false; for (Scene scene :
		 * sceneList) { for (GameElement gameElement : scene.getGameElements())
		 * { if (gameElement.getGameElementIdentifier().getId().trim()
		 * .equals(sceneName.trim())) { resultScene = scene; flag = true; break;
		 * } } if (flag) break; } if (resultScene != null) {
		 * 
		 * // System.out.print(resultScreen.getGameElements().get(0).
		 * getGameElementIdentifier().getId());
		 * System.out.println("Result Scene is" +
		 * resultScene.getGameElements().size()); sceneToStart = resultScene;
		 * currentGameElement = 0; resultScene.sceneStart(gameState);
		 * 
		 * }
		 */

    }

    // --------------------SCREEN------------------------

    /**
     * @param gameState
     * @return
     */
    public static boolean screenToStart(GameState gameState) {
        boolean isScreenStart = isScreenStart();

        // System.out.println(npcCorrect);

        // System.out.println("Iniyan.." + sceneToStart.getScreens().size());

        if (isScreenStart) {

            currentGameElementAdapter = 0;
            screenAdapterToStart = sceneAdapterToStart.getScreenAdapters().get(currentScreenAdapter);
            npcCorrect = screenAdapterToStart.getScreen().getnpcCorrect();

//			try {
            screenAdapterToStart.screenStart(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
        if (screenAdapterToStart.getScreen().getnpcCorrect() == 1) {
            System.out.println("---------------"
                    + screenAdapterToStart.getScreen().getnpcCorrect()
                    + "-----------------");
            npcCorrect = 1;
        }
        return isScreenStart;
    }

    public static boolean isScreenStart() {
        return currentScreenAdapter < sceneAdapterToStart.getScreenAdapters().size();

    }

    /**
     * @param gameState
     */
    public static void screenToPlay(final GameState gameState) throws InterruptedException {

        if (isScreenStart()) {
            // screenToStart = sceneToStart.getScreens().get(currentScreen);
//			try {

            screenAdapterToStart.screenPlay(gameState);

            System.out.println("Size of challenges in this screen: "
                    + screenAdapterToStart.getChallengeAdapaters().size());

            System.out.println("Size of challenges in this screen: "
                    + currentChallengeAdapter);

            System.out.println("Size of challenges in this screen: "
                    + challengeAdapterToStart);

            // challengeAdapterToStart =
            // screenAdapterToStart.getChallengeAdapaters().get(currentChallengeAdapter);
//			}
//			catch (Exception e) {
//				System.err.print(e);
//			}
            // Kyle: Create a TimerTask for competitive multiple choice
            TimerTask timerTask = new TimerTask() {

                // Kyle: Counter varible to increment every time the timer ticks
                @Override
                public void run() {

                    // System.out.println("TimerTask executing counter is: " +
                    // counter);
                    counter++;
                }
            };
            // Kyle: Create a timer
            final Timer timer;
            timer = new Timer("MyTimer");

            // Kyle: Start the timer if the tag is found in the XML
            if (GameView.timerValue > 0)
                timer.scheduleAtFixedRate(timerTask, 1, 15);

            // Kyle: Find what question to go to if the time runs out
            npcCorrect = screenAdapterToStart.getScreen().getnpcCorrect();
            npcIncorrect = screenAdapterToStart.getScreen().getnpcIncorrect();

            // Kyle: Create a new thread to run the timer in the background
            executor.submit(() -> {
                    while (true) {
//						try {
                        // System.out.println("Thread timer is: " + counter
                        // + "/" + GameView.timerValue);
                        // System.out.println("GOING TO: " + npcCorrect);

                        // Kyle: Check if there is no timer for the
                        // challenge
                        if (GameView.timerValue == 0 && counter > 0) {

                            // Kyle: Stop the timer
                            timer.cancel();

                            // Kyle: Reset the counter
                            counter = 0;
                        }

                        // Kyle: Check if the timer has reached the
                        // designated time
                        if (counter >= GameView.timerValue && GameView.timerValue >= 100) {
                            counter = 0;
                            System.out.println("Counter has reached Timer Value now will terminate");

                            // Jeremy
                            // creating a new AIFunction to generate an
                            // answer for Nemesis
                            //
                            // the GenerateAnswer function takes in a
                            // character so we get the character list and
                            // get the first
                            // character from that list (which is nemesis)
                            AIFunction aiFunction = new AIFunction();
                            nemesisWasRight = aiFunction.GenerateAnswer(
                                    GameAdapter
                                            .getGameAdapterObj()
                                            .getGame()
                                            .getCharacters().get(0)
                            );

                            // get the character list
                            List<Character> charList = GameAdapter
                                    .getGameAdapterObj()
                                    .getGame()
                                    .getCharacters();

                            // run the ai recalculate method to update the
                            // characters attributes with nemesis
                            // having his answer and the user getting it
                            // wrong (because the user didn't answer)
                            charList = aiFunction.recalculate(charList, 3, nemesisWasRight, false);
                            GameAdapter.getGameAdapterObj()
                                    .getGame()
                                    .setCharacters(charList);

                            // if nemesis was wrong
                            if (!nemesisWasRight) {
                                Reward npcReward = GameAdapter
                                        .getGameAdapterObj()
                                        .getGame()
                                        .getCharacters().get(1)
                                        .getRewards();

                                npcReward.setPoints(npcReward.getPoints());

                            }
                            // if nemesis was right
                            else {
                                Reward npcReward = GameAdapter
                                        .getGameAdapterObj()
                                        .getGame()
                                        .getCharacters().get(1)
                                        .getRewards();

                                npcReward.setPoints(npcReward.getPoints() + GameView.correctValue);
                            }

                            System.out.println("The value of NemesisWasRight is " + nemesisWasRight);

                            // Kyle: End the timer
                            timer.cancel();

                            // Kyle: Reset the timer value
                            GameView.timerValue = 0;

                            // Kyle: Reset the graphics pane
                            GameViewFrame.resetLayeredPane();

                            // Kyle: Start the next screen
                            if (nemesisWasRight) {
                                startNextScreen(npcCorrect, gameState);

                            } else if (!nemesisWasRight) {
                                startNextScreen(npcIncorrect, gameState);

                            }
                            // Kyle: End loop
                            break;
                        }

                        // Kyle: Sleep method for the timer threat
                        try{
                            Thread.sleep(100);
                        }
                        catch (InterruptedException ex)
                        {
                            ex.printStackTrace();
                        }


//						} catch (InterruptedException ex) {
//							ex.printStackTrace();
//						}
                    }
                return;

            });
            // Kyle: Start the thread to display the counter

        }
    }

    /**
     * @param gameState
     */
    public static void screenToEnd(GameState gameState) {

        currentScreenAdapter++;
//		try {
        screenAdapterToStart.screenEnd(gameState);

//		} catch (Exception e) {
//			System.err.print(e);
//		}

    }

    // Get the screen no from the view and start that screen.

    /**
     * @param screenNo
     * @param gameState
     */
    public static void startNextScreen(int screenNo, GameState gameState) {

        List<ScreenAdapter> screenAdapterList = gameState.getSceneAdapter().getScreenAdapters();
        ScreenAdapter resultScreen = new ScreenAdapter();

        for (ScreenAdapter screenAdapter : screenAdapterList) {
            if (screenAdapter.getScreen().getSequence().getSequenceNumber() == screenNo) {
                resultScreen = screenAdapter;
                break;
            }
        }

        screenAdapterToStart = resultScreen;
        currentGameElementAdapter = 0;
        resultScreen.screenStart(gameState);

    }

    // ------------------GAME ELEMENT--------------------------

    /**
     * @param gameState
     * @return
     */
    public static boolean gameElementToStart(GameState gameState) {
        boolean isGameElementStart = currentGameElementAdapter < screenAdapterToStart.getGameElementAdapters().size();
        if (isGameElementStart) {

            gameElementAdapterToStart = screenAdapterToStart.getGameElementAdapters().get(currentGameElementAdapter);

//			try {
            gameElementAdapterToStart.gameElementStart(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }
        return isGameElementStart;
    }

    /**
     * @param gameState
     */
    public static void gameElementToPlay(GameState gameState) {
        System.out.println("GameState is:" + gameElementAdapterToStart);
        gameElementAdapterToStart.gameElementPlay(gameState);

    }

    /**
     * @param gameState
     */
    public static void gameElementToEnd(GameState gameState) {
        currentGameElementAdapter++;
//		try {
        if (gameElementAdapterToStart != null) {
            gameElementAdapterToStart.gameElementEnd(gameState);
        }
//		} catch (Exception e) {
//			System.out.println("Exception in GameController gameElementToEnd():" + e);
//		}
    }

    /**
     * @param gameState
     * @return
     */
    public static boolean challengeToStart(GameState gameState) {
        boolean isChallengeStart = currentChallengeAdapter < screenAdapterToStart.getChallengeAdapaters().size();

        System.out.println("Current Chal: " + currentChallengeAdapter + " vs. Adapter size: " + screenAdapterToStart.getChallengeAdapaters().size());

        if (isChallengeStart) {

            challengeAdapterToStart = screenAdapterToStart.getChallengeAdapaters().get(currentChallengeAdapter);

            System.out.println("Adapter: " + screenAdapterToStart.getChallengeAdapaters().get(currentChallengeAdapter));

//			try {
            challengeAdapterToStart.challengeStart(gameState);

//			} catch (Exception e) {
//				System.err.print(e);
//			}
        }

        return isChallengeStart;
    }

    /**
     * @return
     */
    public static boolean isChallengeStart() {
        return currentChallengeAdapter > 0;

    }

    /**
     * @param gameState
     */
    public static void challengeToPlay(GameState gameState) throws InterruptedException {

//		try {
        challengeAdapterToStart.challengePlay(gameState);

//		}
//
//		catch (Exception e) {
//			System.err.print(e);
//		}

    }

    /**
     * @param gameState
     */
    public static void challengeToEnd(GameState gameState) {
        currentChallengeAdapter = 0;
//		try {
        challengeAdapterToStart.challengeEnd(gameState);

//		} catch (Exception e) {
//			System.err.print(e);
//		}
    }

	/*
	 * public static boolean quizToStart(GameState gameState) {
	 * 
	 * boolean isQuizStart = false; if (challengeToStart.getQuiz() != null) {
	 * isQuizStart = true; currentQuiz = 0; quizToStart =
	 * challengeToStart.getQuiz(); try {
	 * 
	 * quizToStart.quizStart(gameState); }
	 * 
	 * catch (Exception e) { System.err.print(e); }
	 * 
	 * } return isQuizStart; }
	 * 
	 * public static boolean isQuizStart() { boolean isQuizStart = false; if
	 * (currentQuiz > 0) { isQuizStart = true; } return isQuizStart;
	 * 
	 * }
	 * 
	 * public static void quizToPlay(GameState gameState) {
	 * 
	 * try {
	 * 
	 * quizToStart.quizPlay(gameState);
	 * 
	 * }
	 * 
	 * catch (Exception e) { System.err.print(e); }
	 * 
	 * 
	 * }
	 * 
	 * public static void quizToEnd(GameState gameState) { currentQuiz++; try {
	 * quizToStart.quizEnd(gameState);
	 * 
	 * } catch (Exception e) { System.err.print(e); } }
	 */

    // ----------------PROP----------------------------

	/*
	 * public static boolean propToStart(GameState gameState) { boolean
	 * isPropStart = false; if (currentProp <
	 * gameElementToStart.getGameElements().size()) { isGameElementStart = true;
	 * gameElementToStart = gameElementToStart.getProp().get(
	 * currentGameElement); try {
	 * gameElementToStart.gameElementStart(gameState); } catch (Exception e) {
	 * System.err.print(e); } } return isGameElementStart; }
	 * 
	 * public static void propToPlay(GameState gameState) {
	 * 
	 * try { gameElementToStart.gameElementPlay(gameState); } catch (Exception
	 * e) { System.err.print(e); } }
	 * 
	 * public static void propToEnd(GameState gameState) { currentGameElement++;
	 * try { gameElementToStart.gameElementEnd(gameState); } catch (Exception e)
	 * { System.err.print(e); } }
	 */
    // ------------------PROP END---------------------------------

	/*
	 * public static void displayNext(Prop prop, GameState gameState) { if (prop
	 * != null) { String next = prop.getNext();
	 * System.out.println("Next to display is" + prop.getNext());
	 * 
	 * // Start the nextscreen
	 * 
	 * if (next != null) { if (next.contains("screen")) {
	 * GameController.startNextScreen(next, gameState); } //added by rithika..to
	 * start next scene if(next.contains("scene")) {
	 * GameController.startNextScene(next, gameState); } } else {
	 * System.out.println("Need to start sequencing"); } } }
	 */

    /**
     * @param observable
     * @param gameState
     */
    public static void viewListener(Observable observable, GameState gameState) throws InterruptedException {

//		try {

        Message message = gameState.getMessage();

        if (GameAdapter.class.isInstance(observable)) {
            if (message == Message.StartComplete) {

                System.out.println("Controller :GameModelStartComplete message is received");
                GameAdapter gameAdapter = (GameAdapter) observable;

//					try {
					gameAdapter.gameModelPlay(gameState);
//
//					} catch (InterruptedException e) {
//						System.out.println("Exception" + e);
//
//					}

            }

        }

        if (ActAdapter.class.isInstance(observable)) {

            if (message == Message.StartComplete) {
                System.out.println("Controller :ActStartComplete message is received");
                actToPlay(gameState);
            }

            if (message == Message.PlayComplete) {
                System.out.println("Controller :ActPlayComplete message is received");

                if (!sceneToStart(gameState))
                    System.out.println("Scenes are over");

            }
            if (message == Message.EndComplete) {
                System.out.println("Controller :ActEndComplete message is received");
                // Check if anymore acts left to start. If yes. Play those
                // acts.
                // otherwise print message "Acts are over".

                if (!actToStart(gameState)) {
                    System.out.println("All acts are over");

                }

            }

        }

        if (SceneAdapter.class.isInstance(observable)) {

            if (message == Message.StartComplete) {
                System.out.print("Controller :SceneStartComplete message is received");
                sceneToPlay(gameState);
            }
            if (message == Message.PlayComplete) {
                System.out.println("Controller :ScenePlayComplete message is received");
                screenToStart(gameState);
            }
            if (message == Message.EndComplete) {
                System.out.println("Controller :SceneEndComplete message is received");

                if (isSceneStart()) {
                    sceneToStart(gameState);

                } else {
                    actToEnd(gameState);

                }

            }

        }

        if (ScreenAdapter.class.isInstance(observable)) {
            if (message == Message.StartComplete) {
                System.out.println("Controller :ScreenStartComplete message is received");
                screenToPlay(gameState);
                // System.out.println("Screens are over");

            }
            if (message == Message.PlayComplete) {
                System.out.println("Controller :ScreenPlayComplete message is received");
                // check for challenges before starting game elements
					/*
					 * if(gameState.getScreenAdapter().getScreen().getChallenge()
					 * !=null &&
					 * gameState.getScreenAdapter().getScreen().getChallenge
					 * ().size()>0 && !gameState.isChallengeProcessed()) {
					 * challengeAdapterToStart }
					 */

                if (!challengeToStart(gameState)) {
                    System.out.println("Challenges are over");
                }

                if (!gameElementToStart(gameState)) {
                    System.out.println("GameElements are over");
                }
            }
            if (message == Message.EndComplete) {
                System.out.println("Controller: Screen end complete message is received.");
                // displayNext((Prop) gameState.getGameElement(),gameState);

                if (isScreenStart())
                    screenToStart(gameState);
                else
                    sceneToEnd(gameState);
            }
        }

        if (GameElementAdapter.class.isInstance(observable)) {

            if (message == Message.StartComplete) {
                System.out.println("Controller :GameElementStartComplete message is received");
                gameElementToPlay(gameState);

            }

            if (message == Message.PlayComplete) {
                System.out.println("Controller :GameElementPlayComplete message is received");
                gameElementToEnd(gameState);

            }

            if (message == Message.EndComplete) {
                System.out.println("Controller :GameElementEndComplete message is received");

                // 1. To check whether there are any new gameElements to
                // start.
                // If there is any, start that new game element.
                // Otherwise End the current screen.
                // 2. If there is no next tag in xml, follow the sequencing
                // approach.

                // System.out.println("Game sequence is" +
                // GameModel.isSequence);
                // if (GameModel.isSequence) {
                if (!gameElementToStart(gameState)) {
                    System.out.println("No more game elements to start");
                    screenToEnd(gameState);
                }
                // }
            }
        }

			/*
			 * if (Quiz.class.isInstance(observable)) { if (message ==
			 * Message.StartComplete) { System.out
			 * .println("Controller :QuizStartComplete message is received");
			 * quizToStart(gameState);
			 * 
			 * 
			 * } if (message == Message.PlayComplete) { System.out
			 * .println("Controller :QuizPlayComplete message is received");
			 * quizToPlay(gameState); } if (message == Message.EndComplete) {
			 * System.out
			 * .println("Controller: Quiz end complete message is received.");
			 * //displayNext((Prop) gameState.getGameElement(),gameState); if
			 * (isQuizStart()) quizToStart(gameState); else
			 * quizToEnd(gameState); } }
			 */

        if (ChallengeAdapter.class.isInstance(observable)) {
            if (message == Message.StartComplete) {
                System.out.println("Controller : ChallengeStartComplete message is received");
                challengeToPlay(gameState);

            }
            if (message == Message.PlayComplete) {
                System.out.println("Controller : ChallengePlayComplete message is received");
                challengeToEnd(gameState);
            }
            if (message == Message.EndComplete) {
                System.out.println("Controller: Challengeend complete message is received.");
                // displayNext((Prop) gameState.getGameElement(),gameState);

                if (isChallengeStart())
                    challengeToStart(gameState);
                else
                    gameElementToStart(gameState);// once challenge ends
                // start game element
            }
        }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }
}
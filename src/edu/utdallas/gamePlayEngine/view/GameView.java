package edu.utdallas.gamePlayEngine.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import simsys.schema.components.Behavior;
import simsys.schema.components.BehaviorType;
import simsys.schema.components.CharacterProp;
import simsys.schema.components.Color;
import simsys.schema.components.ColorName;
import simsys.schema.components.ElementColor;
import simsys.schema.components.GameElementType;
import simsys.schema.components.ImageProp;
import simsys.schema.components.Location;
import simsys.schema.components.MultipleChoiceItem;
import simsys.schema.components.Option;
import simsys.schema.components.Prop;
import simsys.schema.components.QuizChallenge;
import simsys.schema.components.SandBox;
import simsys.schema.components.Size;
import simsys.schema.components.Stem;
import simsys.schema.components.StemQuestion;
import simsys.schema.components.StemText;
import edu.utdallas.gamePlayEngine.controller.GameController;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.controller.Message;
import edu.utdallas.gamePlayEngine.model.adapter.ActAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ChallengeAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameElementAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.SceneAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ScreenAdapter;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.AudioPlayer;

public class GameView implements Observer {
	int count;
	GameViewFrame gameViewFrame;
	public static boolean drawTriangle;
	private String displayInfo;
	public static int correctValue = 0;
	public static int[] angles = new int[3];
	public static int desiredAngle;
	public static final String Button1 = "Button1";
	public static final String Button2 = "Button2";
	public static final String Button = "Button";
	public static final String Image = "Image";
	public static final String png = "png";
	public static final String jpg = "jpg";
	public static String musicFolderPath = "AudioAssetRepository\\music\\";
	public static String effectsFolderPath = "AudioAssetRepository\\effects\\";
	public int noCount = 0;
	private List<Timer> timerList; // Zac ZHANG: Add for removing all timers
	public static int[] nextScreen = new int[4];
	private int loopVar = 0;
	public static int timerValue = 0;

	public GameView() {
		timerList = new ArrayList<Timer>();
		gameViewFrame = new GameViewFrame(timerList);
	}

	public void resetView() {
		// Zac ZHANG added: stop all timers
		for (int i = 0; i < timerList.size(); i++) {
			timerList.get(i).stop();
		}

		this.count = 0;
		gameViewFrame.setController(null);
		this.timerList = new ArrayList<Timer>();
		this.noCount = 0;
	}

	// @Override
	@Override
	public void update(Observable arg0, Object arg1) {

		try {

			if (arg1 instanceof GameState) { // Zac ZHANG added to run gameEnd
												// function successfully
				count++;

				GameState gameState = (GameState) arg1;
				Message msg = gameState.getMessage();
				System.out.println("Gameview update message is: " + msg);

				if (GameAdapter.class.isInstance(arg0)) {
					if (msg == Message.Start) {
						// Do something for GameModel Start
						// TODO Find why it is called in the second time.
						System.out
								.println("View: Message GameModelStartComplete sent");
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.Play) {
						// Do something for GameModel Play
						System.out
								.println("View: Message GameModelPlayComplete sent");
						gameViewFrame.viewStartAct(gameState.getMenuFrame());
						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);
					}
				}

				else if (ActAdapter.class.isInstance(arg0)) {
					if (msg == Message.Start) {
						// Do something for Act Start.

						System.out
								.println("View: Message ActStartComplete sent");
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);

					}
					if (msg == Message.Play) {
						// Do something for Act Play.
						System.out
								.println("View: Message ActPlayComplete sent");
						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);

					}
					if (msg == Message.End) {
						// Do something for Act Play.
						System.out.println("View: Message ActEndComplete sent");
						gameState.setMessage(Message.EndComplete);
						GameController.viewListener(arg0, gameState);

					}
				}

				else if (SceneAdapter.class.isInstance(arg0)) {
					if (msg == Message.Start) {
						System.out
								.println("View: Message SceneStartComplete sent");

						// TODO Auto-generated method stub
						if (((SceneAdapter) (arg0)).getScene().getMusic() != null) {
							String music = ((SceneAdapter) (arg0)).getScene()
									.getMusic().getMusic();
							AudioPlayer.playAudio(musicFolderPath + music);
						}

						String backDrop = ((SceneAdapter) (arg0)).getScene()
								.getBackground().getBackground();
						if (backDrop != null)
							gameViewFrame.setBackgroundImage(backDrop);
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.Play) {
						System.out
								.println("View: Message ScenePlayComplete sent");
						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.End) {
						System.out
								.println("View: Message SceneEndComplete sent");
						gameState.setMessage(Message.EndComplete);
						GameController.viewListener(arg0, gameState);
					}
				}

				else if (ScreenAdapter.class.isInstance(arg0)) {
					if (msg == Message.Start) {
						System.out
								.println("View: Message ScreenStartComplete sent");
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);

					} else if (msg == Message.Play) {
						System.out
								.println("View: Message ScreenPlayComplete sent");
						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.End) {
						System.out
								.println("View: Message ScreenEndComplete sent");
						gameState.setMessage(Message.EndComplete);
						GameController.viewListener(arg0, gameState);
					}
				} else if (ChallengeAdapter.class.isInstance(arg0)) {

					if (msg == Message.Start) {
						System.out
								.println("View: Message ChallengeStartComplete sent");
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.Play) {
						System.out
								.println("View: Message ChallengePlayComplete sent");
						gameState.setMessage(Message.PlayComplete);
						try {
							SandBox SBchallenge = (SandBox) gameState
									.getChallenegeAdapter().getChallenge();
							if (SBchallenge.getItem().get(0).getType()
									.equals("SandBox")) {
								// System.out.println("QQQQQ: " +
								// SBchallenge.getItem().get(0).getGrade());
								int[] points = new int[18];
								String[] xmlPoints = SBchallenge.getItem()
										.get(0).getPoints().split(",");
								String[] xmlAngles = SBchallenge.getItem()
										.get(0).getAngles().split(",");
								desiredAngle = SBchallenge.getItem().get(0)
										.getDesiredAngle();

								for (int i = 0; i < xmlPoints.length; i++)
									points[i] = Integer.parseInt(xmlPoints[i]);
								for (int i = 0; i < xmlAngles.length; i++)
									angles[i] = Integer.parseInt(xmlAngles[i]);
								drawTriangle = true;
								ChallengeAdapter.quizReward = SBchallenge
										.getItem().get(0).getReward();
								gameState.setQuizReward(SBchallenge.getItem()
										.get(0).getReward());
								gameState.setInCorrectFeedback(SBchallenge
										.getItem().get(0)
										.getIncorrectFeedback());
								gameState.setCorrectFeedBack(SBchallenge
										.getItem().get(0).getFeedback());
								gameState.setPromotedTitle(SBchallenge
										.getItem().get(0).getPromotionTitle());
								RoundedPanel.startSandBox(points, gameState);
							}
						} catch (Exception e) {

						}

						QuizChallenge challenge = (QuizChallenge) gameState
								.getChallenegeAdapter().getChallenge();

						System.out.println("QQQQ" + challenge.getItem().get(0));
						// System.out.println("TIMER TEST: " +
						// challenge.getTimer());
						timerValue = challenge.getTimer();

						// Kyle: Debug statement to see if two questions are
						// viable
						System.out.println("Challenge Question = " + challenge);

						MultipleChoiceItem multiChoice = (challenge
								.getItem().get(0));

						Stem stem = multiChoice.getStem();
						StemText stemText = stem.getStemText();
						StemQuestion stemQ = stem.getStemQuestion();
						// Kyle: Debug statement to see if two questions are
						// viable
						System.out.println("Stem Question = " + stemQ);

						List<Option> options = multiChoice.getOption();

						Color color = new Color();
						color.setColorName(ColorName.YELLOW);
						ElementColor elemColor = new ElementColor();
						elemColor.setBackgroundColor(color);
						Location loc = new Location();
						loc.setX(370);
						loc.setY(0);
						Size extra_large = new Size();
						extra_large.setHeight(300);
						extra_large.setWidth(200);
						Size large = new Size();
						large.setHeight(125);
						large.setWidth(200);

						Prop textProp = new Prop();
						textProp.setText(stemText.getText());
						textProp.setColor(elemColor);
						textProp.setLocation(loc);
						textProp.setName("Quiz");
						textProp.setSize(extra_large);
						gameViewFrame.addinformationBox(textProp, gameState);

						Prop questionProp = new Prop();
						questionProp.setText(stemQ.getText());
						questionProp.setColor(elemColor);
						loc.setY(325);
						questionProp.setLocation(loc);
						questionProp.setName("Quiz");
						questionProp.setSize(large);
						gameViewFrame
								.addinformationBox(questionProp, gameState);

						try {
							
							for(GameElementAdapter adapter : gameState.getScreenAdapter().getGameElementAdapters())
							{
								GameElementType element = adapter.getGameElement();
								
								if(element.getName().contains("Character"))
								{
									CharacterProp charProp = (CharacterProp) element;
									gameViewFrame.addImage(charProp.getNameTag(),
											charProp.getLocation().getX(),charProp.getLocation().getY(), element.getSize().getWidth(), element.getSize().getHeight(), gameState);
								} 
								else if(element.getName().contains("ImageProp"))
								{
									ImageProp imgProp = (ImageProp) element;
									gameViewFrame.addImage(imgProp);
								}
							}

						} catch (NullPointerException e) {
							e.printStackTrace();
						}

						int yvalue = 0;
						loc.setX(600);

						for (Option option : options) {
							int listCount = options.size();
							loc.setX(615);
							Prop optionProp = new Prop();
							optionProp.setText(option.getText());

							System.out.println("bob:"
									+ option.getText().getText());
							optionProp.setColor(elemColor);
							loc.setY(yvalue);
							optionProp.setLocation(loc);
							optionProp.setHint(option.getHint());
							// Kyle: Here we getting the screen number for the
							// next question for each independent option
							ChallengeAdapter.nextDisplay = option.getReward()
									.getNextQuestion();
							// Kyle: Next screen options are stored in an array
							// to be passed to the GameViewFrame
							nextScreen[loopVar] = ChallengeAdapter.nextDisplay;
							// Kyle: Increment the option that is currently
							// under review
							loopVar++;
							if (option.getAssessment().toString().charAt(0) == 'C') {
								gameState.setCorrectFeedBack(option
										.getFeedback());
								gameState.setQuizReward(option.getReward()
										.getPoints());
								correctValue = option.getReward().getPoints();
								gameState.setPromotedTitle(option.getReward()
										.getPromotionDemotion());
								ChallengeAdapter.nextDisplay = option
										.getReward().getNextQuestion();

								if (option.getReward().getNextQuestion() > 0)
									gameState.setNextQuestion(option
											.getReward().getNextQuestion());

							} else {

								gameState.setInCorrectFeedback(option
										.getFeedback());

								ChallengeAdapter.quizFeedback = option
										.getFeedback();// this will be displayed
														// when no option is
														// selected
							}

							gameViewFrame
									.addButton(optionProp, gameState,
											option.getAssessment().toString()
													.charAt(0), yvalue,
											listCount);
							yvalue += 140;
							// yvalue += 5;
						}
						options.clear();
						loopVar = 0;
						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.End) {
						// chck if timer needed TODO

						System.out
								.println("View: Message ChallengeEndComplete sent");
						gameState.setMessage(Message.EndComplete);
						GameController.viewListener(arg0, gameState);

					}

				} else if (GameElementAdapter.class.isInstance(arg0)) {// Zac
																		// ZHANG:
																		// added
																		// "else"
					if (msg == Message.Start) {
						System.out
								.println("View: Message GameElementStartComplete sent");
						gameState.setMessage(Message.StartComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.Play) {
						System.out
								.println("View: Message GameElementPlayComplete sent");
						gameState.setMessage(Message.PlayComplete);

						if (!((gameState.getGameElementAdapter()
								.getGameElement()) instanceof Prop)) {
							gameState.setMessage(Message.PlayComplete);
							GameController.viewListener(arg0, gameState);
						}
						Prop currentProp = (Prop) (gameState
								.getGameElementAdapter().getGameElement());

						if (currentProp.getName() != null) {
							if (currentProp.getName().trim().startsWith(Button)) {
								gameViewFrame.addButtonNew(gameState);
							}
							if (currentProp.getName().startsWith("Information")) {
								System.out
										.println("Calling method to add information box!!"
												+ currentProp);

								if (currentProp.getBehaviors() != null) {
									for (Behavior behavior : currentProp
											.getBehaviors()) {
										if (behavior.getBehaviorType().equals(
												BehaviorType.CURRENT_CHALLENGE)) {
											currentProp
													.getText()
													.setText(
															ChallengeAdapter.quizFeedback);
										}
									}
								}

								gameViewFrame.addinformationBox(currentProp,
										gameState);
							}

							// Zac ZHANG: Added for ConversationBubble // TO
							// DO----
							if (currentProp.getName().startsWith(
									"ConversationBubble")) {
								System.out
										.println("Calling method to add Conversation Bubble! "
												+ currentProp);
								gameViewFrame.addConversationBubble(
										currentProp, gameState);
							}

							if ((currentProp.getName()).endsWith(png)) {
								Prop prop = (Prop) gameState
										.getGameElementAdapter()
										.getGameElement();
								// GameViewFrame.resetLayeredPane();
								String[] characterGroup = prop.getName().split(
										",");
								for (int i = 0; i < characterGroup.length; i++) {
									gameViewFrame.addImage(characterGroup[i],
											i * 150 - 20, 130, gameState);
									// gameViewFrame.addImage(gameState);
								}
								System.out.println("Displaying Image.");
							}

							else {
								Prop prop = (Prop) gameState
										.getGameElementAdapter()
										.getGameElement();

//								String[] characterGroup = prop.getName().split(
//										",");
//								for (int i = 0; i < characterGroup.length; i++) {
//									gameViewFrame.addImage(characterGroup[i],
//											i * 150 - 20, 130, gameState);
//								}
								System.out.println("Displaying Image.");
							}
						}

						gameState.setMessage(Message.PlayComplete);
						GameController.viewListener(arg0, gameState);
					} else if (msg == Message.End) {

						if (!((gameState.getGameElementAdapter()
								.getGameElement()) instanceof Prop)) {
							gameState.setMessage(Message.EndComplete);
							GameController.viewListener(arg0, gameState);
						}
						Prop currentProp = (Prop) gameState
								.getGameElementAdapter().getGameElement();
						try {
							try {

								int timerValue = 10; // TO DO .. fetch this
														// appropriate place -
														// has to be more for
														// buttons .. next
														// elements starts after
														// this time

								if (currentProp.getAnimationEffect() != null
										&& currentProp.getAnimationEffect()
												.getSpeed() != null) {
									timerValue = GameAdapter.speedMap
											.get(currentProp
													.getAnimationEffect()
													.getSpeed());
								}

								// if
								// (currentProp.getName().startsWith("Information")
								// && currentProp.getBehaviors()!=null &&
								// currentProp.getBehaviors().size()>0)
								// timerValue=0;

								final GameState localGameState = gameState;
								final Observable localArg = arg0;
								final String propName = currentProp.getName();
								final Behavior localBehavior = (currentProp
										.getBehaviors() != null && currentProp
										.getBehaviors().size() > 0) ? currentProp
										.getBehaviors().get(0) : null;
								if (currentProp.getName() != null
										&& currentProp.getName().startsWith(
												"Information")
										&& !(localBehavior != null && localBehavior
												.getBehaviorType()
												.equals(BehaviorType.TRANSITION_BEHAVIOR)))
									timerValue = 0;
								if (!(propName != null && propName.trim()
										.startsWith(Button))) // if its a button
																// dont proceed
																// untill click
																// .. so no need
																// to go to
																// EndComplete
								{
									Timer timer = new Timer(timerValue,
											new ActionListener() {
												// @Override
												@Override
												public void actionPerformed(
														ActionEvent arg1) {
													if (GameAdapter
															.hasGameEnded())
														return;
													if (localBehavior != null
															&& localBehavior
																	.getBehaviorType()
																	.equals(BehaviorType.TRANSITION_BEHAVIOR)) // to
																												// retain
																												// info
																												// box
																												// when
																												// button
																												// is
																												// displayed
														GameViewFrame
																.resetLayeredPane();

													System.out
															.println("View: Message GameElementEndComplete sent");
													localGameState
															.setMessage(Message.EndComplete);
													GameController
															.viewListener(
																	localArg,
																	localGameState);

												}
											});
									timer.setRepeats(false);
									timer.start();
									timerList.add(timer);
								}
							} catch (Exception e) {
								e.printStackTrace();
								System.out
										.println("GameView Class: Exception Point 1: "
												+ e.getMessage());
								final GameState localGameState = gameState;
								final Observable localArg = arg0;
								localGameState.setMessage(Message.EndComplete);
								GameController.viewListener(localArg,
										localGameState);
							}
							// }
						} catch (Exception e) {
							e.printStackTrace();
							System.out
									.println("GameView Class: Exception Point 2: "
											+ e.getMessage());
							final GameState localGameState = gameState;
							final Observable localArg = arg0;
							localGameState.setMessage(Message.EndComplete);
							GameController.viewListener(localArg,
									localGameState);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() {
		for (int i = 0; i < timerList.size(); i++)// Zac ZHANG: stop all timers
		{
			timerList.get(i).stop();
		}

		this.count = 0;
		this.gameViewFrame = null;
		this.displayInfo = null;
		this.noCount = 0;
		this.timerList = null;
	}
}
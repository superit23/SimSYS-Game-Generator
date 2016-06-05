package edu.utdallas.previewtool.ErrorChecker;

import java.util.List;

import simsys.schema.components.Act;
import simsys.schema.components.Challenge;
import simsys.schema.components.Character;
import simsys.schema.components.Game;
import simsys.schema.components.Introduction;
import simsys.schema.components.MultipleChoiceItem;
import simsys.schema.components.Option;
import simsys.schema.components.Profile;
import simsys.schema.components.QuizChallenge;
import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import simsys.schema.components.Stem;
import simsys.schema.components.Summary;
import edu.utdallas.previewtool.Errors.GameErrorList;
import edu.utdallas.previewtool.Errors.PreviewError;
import edu.utdallas.previewtool.Errors.PreviewError.Level;
import edu.utdallas.previewtool.Errors.PreviewError.Severity;

/**
 * The Class GameErrorChecker.
 */
public final class GameErrorChecker {
	// Check entire Game hierarchy for errors and return a list of errors
	/** The errors. */
	private static GameErrorList errors = new GameErrorList();

	/**
	 * Instantiates a new game error checker.
	 */
	private GameErrorChecker() {

	}

	/**
	 * Check errors.
	 *
	 * @param game
	 *            the game
	 * @param panelWidth
	 *            the panel width
	 * @param panelHeight
	 *            the panel height
	 * @return the game error list
	 */
	public static GameErrorList checkErrors(final Game game,
			final int panelWidth, final int panelHeight) {
		// don't save if no game file open

		// Check for Game-level errors
		if (game == null) {
			errors.add(new PreviewError(Level.GAME, Severity.HIGH,
					"No <Game> detected in XML file") {
				@Override
				public void fixError() {
				}
			}); // can't fix this
		} else { // Check for global character errors
			if (game.getCharacters() == null || game.getCharacters().size() == 0) {
				errors.add(new PreviewError(Level.GAME, Severity.HIGH,
						"No <Characters> detected in Game") {
					@Override
					public void fixError() {
					}
				});
			} else {
				List<Character> characters = game.getCharacters();
				for (int i = 0; i < characters.size(); i++) {
					// Need a way to refer to which Character has an error
					String cName = characters.get(i).getName();

					if (isNullOrEmpty(cName)) {
						errors.add(new PreviewError(Level.GAME, Severity.LOW,
								"The <Name> property of Character found in position "
										+ (i + 1) + " is not specified") {
							@Override
							public void fixError() {
							}
						});
						cName = "[in position " + (i + 1) + "]";
					}

					if (characters.get(i).getAutonomousBehaviour() == null) {
						errors.add(new PreviewError(Level.GAME, Severity.LOW,
								"The <Behavior> property of Character " + cName
										+ "is not specified") {
							@Override
							public void fixError() {
							}
						});
					}

					if (characters.get(i).getProfile() == null) {
						errors.add(new PreviewError(Level.GAME, Severity.LOW,
								"The <Profile> property of Character " + cName
										+ " is not specified") {
							@Override
							public void fixError() {
							}
						});
					} else {
						checkProfile(characters.get(i));
					}
				}
			}
			// Check for Act-level errors
			// Acts wrapper check needed, or if this is not possible
			// may need to change error to be more general
			checkAct(game);
		}
		return errors;
	}

	/**
	 * Check profile properties.
	 *
	 * @param character
	 *            the character
	 */
	public static void checkProfile(final Character character) {

		Profile profile = character.getProfile();
		String cName = character.getName();

		if (profile.getLevel() == null) {
			errors.add(new PreviewError(Level.GAME, Severity.LOW,
					"The <Level> property of Character " + cName
							+ " profile is missing or empty") {
				@Override
				public void fixError() {
				}
			});
		}

		if (profile.getDegrees() == null || profile.getDegrees().size() == 0) {
			errors.add(new PreviewError(Level.GAME, Severity.LOW,
					"The <Degrees> property of Character " + cName
							+ " profile is missing or empty") {
				@Override
				public void fixError() {
				}
			});
		}

		if (profile.getSkills() == null || profile.getSkills().size() == 0) {
			errors.add(new PreviewError(Level.GAME, Severity.LOW,
					"The <Skills> property of Character " + cName
							+ " profile is missing or empty") {
				@Override
				public void fixError() {
				}
			});
		}

		if (isNullOrEmpty(profile.getTitle())) {
			errors.add(new PreviewError(Level.GAME, Severity.LOW,
					"The <Title> property of Character " + cName
							+ " profile is missing or empty") {
				@Override
				public void fixError() {
				}
			});
		}

		if (profile.getYearsOfExperience() == 0) {
			errors.add(new PreviewError(Level.GAME, Severity.LOW,
					"The <YearsOfExperience> " + "property of Character "
							+ cName + " profile is missing or empty") {
				@Override
				public void fixError() {
				}
			});
		}
	}

	/**
	 * Check act properties.
	 *
	 * @param game
	 *            the game
	 */
	private static void checkAct(final Game game) {
		// TODO Auto-generated method stub
		if (game.getActs() == null || game.getActs().size() == 0) {
			errors.add(new PreviewError(Level.ACT, Severity.HIGH,
					"No <Acts> detected in Game") {
				@Override
				public void fixError() {
				} // TODO
			});
		} else {
			List<Act> acts = game.getActs();

			// Check for Scene-level errors
			// Scenes wrapper check needed, or if this is not
			// possible may need to change error to be more general
			checkScene(acts);
		}
	}

	/**
	 * Check scene properties.
	 *
	 * @param acts
	 *            the acts
	 */
	private static void checkScene(final List<Act> acts) {
		for (int i = 0; i < acts.size(); i++) {
			// Need a way to refer to which Act has an error
			String aName = "Act [in position " + (i + 1) + "]";

			List<Scene> scenes = acts.get(i).getScene();
			if (scenes == null || scenes.size() == 0) {
				errors.add(new PreviewError(Level.SCENE, Severity.HIGH,
						"No <Scenes> detected for act in position " + aName) {
					@Override
					public void fixError() {
					} // TODO
				});
			} else {
				for (int j = 0; j < scenes.size(); j++) {
					// Need a way to refer to which Scene has an error
					String sName = aName + " " + "Scene [position " + (j + 1)
							+ "]";
					if (scenes.get(j).getBackground() == null) {
						errors.add(new PreviewError(Level.SCENE, Severity.LOW,
								"The <Background> property of " + sName
										+ " is not specified") {
							@Override
							public void fixError() {
							}
						});
					}
					checkScreen(sName, scenes.get(j));
				}
			}
		}
	}

	/**
	 * Check screen properties.
	 *
	 * @param sName
	 *            the s name
	 * @param scene
	 *            the scene
	 */
	private static void checkScreen(final String sName, final Scene scene) {
		// TODO Auto-generated method stub
		List<Screen> screens = scene.getScreen();
		if (screens == null || screens.size() == 0) {
			errors.add(new PreviewError(Level.SCREEN, Severity.HIGH,
					"No <Screens> detected for " + sName) {
				@Override
				public void fixError() {
				} // TODO
			});
		} else {
			for (int k = 0; k < screens.size(); k++) {
				// Need a way to refer to which Screen has an error
				String srName = sName + " " + "Screen [position " + (k + 1)
						+ "]";
				// Need a way to check if their are two
				// Challenges in a screen. First thought was
				// getting a list and checking if size
				// was> 1 but the Screen class doesn't return
				// Challenges as lists cause it assumes their is only one.
				List<Challenge> challenges = screens.get(k).getChallenge();
				for (int l = 0; l < challenges.size(); l++) {
					Challenge challenge = challenges.get(l);
					if (challenge == null)
						continue;
					
					if (! (challenge instanceof QuizChallenge) ) {
						errors.add(new PreviewError(
								Level.SCREEN,
								Severity.MEDIUM,
								"The xsi:type isn't specified for the challenge of Screen found in position " + (k + 1)) {
							@Override
							public void fixError() {
							} // TODO
						});
						continue;
					}
					
					QuizChallenge qChallenge = (QuizChallenge) challenge;
					if (qChallenge.getLayout() == null) {
						errors.add(new PreviewError(Level.SCREEN,
								Severity.MEDIUM,
								"Layout in Challenge in " + srName
										+ " is missing or empty") {
							@Override
							public void fixError() {
							} // TODO
						});
					}
					
					List<MultipleChoiceItem> item = qChallenge.getItem();
					// Catches C_13 & C_14
					if (item == null) {
						errors.add(new PreviewError(Level.SCREEN,
								Severity.MEDIUM,
								String.format("There are no Questions in the Challenge in %1$s", srName)) {
							@Override
							public void fixError() {
							} // TODO
						});
					} else {
						for (int u = 0; u < 1; u++) {
							MultipleChoiceItem mcItem = item.get(u);
							Option option = mcItem.getOption().get(0);
							if (option == null) {
								
								errors.add(new PreviewError(
										Level.SCREEN,
										Severity.MEDIUM,
										String.format("There are no answers to choose from in question %1$s in %2$s", (u + 1), srName)) {
									@Override
									public void fixError() {
									} // TODO
								});
							} else {
								if (option == null
										|| isNullOrEmpty(option
												.getHint().getHint())) {
									errors.add(new PreviewError(
											Level.SCREEN, Severity.LOW,
											"Option in Challenge Question in "
													+ srName
													+ " has no hint") {
										@Override
										public void fixError() {
										} // TODO
									});
								}
							}

							Stem stem = mcItem.getStem();
							if (stem == null) {
								errors.add(new PreviewError(
										Level.SCREEN, Severity.MEDIUM,
										"No STEM Collection in Challenge in "
												+ srName) {
									@Override
									public void fixError() {
									} // TODO
								});
							} else {
								if (stem.getStemQuestion() == null) {
									errors.add(new PreviewError(
											Level.SCREEN,
											Severity.MEDIUM,
											"No STEM question in Challenge in "
													+ srName) {
										@Override
										public void fixError() {
										} // TODO
									});
								} else if (stem.getStemQuestion()
										.getHint() == null
										|| isNullOrEmpty(stem
												.getStemQuestion()
												.getHint().getHint())) {
									errors.add(new PreviewError(
											Level.SCREEN, Severity.LOW,
											"STEM Question with empty Text field in Challenge in "
													+ srName) {
										@Override
										public void fixError() {
										} // TODO
									});
								}
								if (stem.getStemText() == null) {
									errors.add(new PreviewError(
											Level.SCREEN,
											Severity.MEDIUM,
											"No STEM text in Challenge in "
													+ srName) {
										@Override
										public void fixError() {
										} // TODO
									});
								} else if (stem.getStemText().getHint() == null
										|| isNullOrEmpty(stem
												.getStemText()
												.getHint().getHint())) {
									errors.add(new PreviewError(
											Level.SCREEN, Severity.LOW,
											"STEM Text with empty Text field in Challenge in "
													+ srName) {
										@Override
										public void fixError() {
										} // TODO
									});
								}
							}
						}
					}
					// Only one summary for a Quiz Challenge.
					// So replacing List with a single Summary object
					Summary summary = qChallenge.getSummary();
					if (summary == null) {
						errors.add(new PreviewError(Level.SCREEN,
								Severity.MEDIUM,
								"No Summaries in Challenge in "
										+ srName) {
							@Override
							public void fixError() {
							} // TODO
						});
					}
					Introduction intro = qChallenge.getIntroduction();
					if (intro == null) {
						errors.add(new PreviewError(Level.SCREEN,
								Severity.MEDIUM,
								"No Introduction in Challenge in "
										+ srName) {
							@Override
							public void fixError() {
							} // TODO
						});
					}
				}
				// Check for Asset-level errors
				checkAsset(screens);

			}
		}
	}

	/**
	 * Check asset properties.
	 *
	 * @param screens
	 *            the screens
	 */
	private static void checkAsset(final List<Screen> screens) {
		
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param s
	 *            the s
	 * @return true, if is null or empty
	 */
	private static boolean isNullOrEmpty(final String s) {
		return s == null || s.equals("");
	}

}

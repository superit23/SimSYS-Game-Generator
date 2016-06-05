package edu.utdallas.gamegeneratorcollection.GameComposition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import simsys.schema.components.Act;
import simsys.schema.components.BackgroundType;
import simsys.schema.components.BehaviorType;
import simsys.schema.components.Character;
import simsys.schema.components.CharacterType;
import simsys.schema.components.GameElementType;
import simsys.schema.components.GenericInteraction;
import simsys.schema.components.Layout;
import simsys.schema.components.LayoutName;
import simsys.schema.components.QuizChallenge;
import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import simsys.schema.components.Sequence;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.BaseScreen;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ButtonLocationType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ChallengeScreen;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.CharacterAssetType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.Characters;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameCharacter;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.LearningAct;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.LearningActCharacter;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.LessonAct;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.LocaleScreen;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ScreenType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ThemeStory;

/**
 * User: clocke. Date: 2/17/13 Time: 5:54 PM
 */
@XmlRootElement(name = "Locale")
public class Locale {
	/**
	 * Holds default font.
	 */
	private static final String DEFAULTFONT = "Comic Sans MS";
	/**
	 * Holds the default font size.
	 */
	private static final int DEFAULTFONTSIZE = 15;
	/**
	 * Holds the learning acts, which are sets of lesson acts.
	 */
	private List<LearningAct> learningActs;
	/**
	 * The characters listed in the locale repo.
	 */
	private Characters characters;
	/**
	 * The theme details.
	 */
	private Theme theme;
	/**
	 * A mapping of each screen with its screen type.
	 */
	private Map<ScreenType, LocaleScreen> localeScreens;

	/**
	 * A mapping of each behavior type to an ID.
	 */
	private Map<BehaviorType, UUID> screenTransitions = new HashMap<BehaviorType, UUID>();

	/**
	 * Provides helper methods for converting game objects taken from the
	 * repository to the new game specification.
	 */
	// private ConversionFactory converter = new ConversionFactory();
	/**
	 * Returns the learning acts.
	 * 
	 * @return {@link LearningAct}
	 */
	public final List<LearningAct> getLearningActs() {
		return learningActs;
	}

	/**
	 * Sets the list of learning acts.
	 * 
	 * @param actList
	 *            {@link LearningAct}
	 */
	@XmlTransient
	public final void setLearningActs(final List<LearningAct> actList) {
		this.learningActs = actList;
	}

	/**
	 * Returns the characters in the act.
	 * 
	 * @return {@link Characters}
	 */
	public final Characters getCharacters() {
		return characters;
	}

	/**
	 * Sets the characters in the act.
	 * 
	 * @param actChars
	 *            {@link Characters}
	 */
	@XmlTransient
	public final void setCharacters(final Characters actChars) {
		this.characters = actChars;
	}

	/**
	 * Returns the theme set for the act.
	 * 
	 * @return {@link Theme}
	 */
	public final Theme getTheme() {
		return theme;
	}

	/**
	 * Sets the theme for the act.
	 * 
	 * @param themeVals
	 *            {@link Theme}
	 */
	@XmlTransient
	public final void setTheme(final Theme themeVals) {
		this.theme = themeVals;
	}

	/**
	 * Creates a list of ScreenNode representing the act corresponding to the
	 * passed in learning act.
	 * 
	 * @param learningActId
	 *            the index of the learning act
	 * @return a list of ScreenNode
	 */
	public final Act getAct(final int learningActId) {
		Act currentAct = new Act();
		List<Scene> actScenes = new ArrayList<Scene>();
		actScenes.addAll(buildScreens(learningActId,
				ScreenType.LESSON_STORY_INTRO));
		actScenes.addAll(buildLesson(learningActId));
		actScenes.addAll(buildChallenges(learningActId));
		actScenes.addAll(buildScreens(learningActId,
				ScreenType.LESSON_STORY_OUTRO));
		currentAct.getScene().addAll(actScenes);
		return currentAct;
	}

	/**
	 * Builds all challenge screens for the learning act.
	 * 
	 * @param learningActId
	 *            the id of the learning act to build
	 * @return a list of ScreenNode representing the built challenge screens
	 */
	private List<Scene> buildChallenges(final int learningActId) {
		UUID currentScreen = screenTransitions
				.get(BehaviorType.BEGINNING_OF_CHALLENGE);
		LessonAct lessonAct = learningActs.get(learningActId).getLessonActs()
				.get(0);
		Scene testScene = new Scene();
		testScene = null;

		List<ChallengeScreen> challenges = lessonAct.getLessonChallenges();
		List<Scene> allScenes = new ArrayList<Scene>();
		for (ChallengeScreen challenge : challenges) {
			UUID nextChallenge = UUID.randomUUID();
			testScene = buildChallenge(learningActId, challenge, currentScreen);
			int numScenes = allScenes.size();
			if (numScenes >= 1) {
				if (allScenes.get(numScenes - 1).getBackground()
						.getBackground()
						.equals(testScene.getBackground().getBackground())) {
					allScenes.get(numScenes - 1).getScreen()
							.addAll(testScene.getScreen());
				} else {
					allScenes.add(testScene);
				}
			} else {
				allScenes.add(testScene);
			}

			screenTransitions.put(BehaviorType.NEXT_CHALLENGE, nextChallenge);
			screenTransitions
					.put(BehaviorType.CURRENT_CHALLENGE, currentScreen);
			currentScreen = nextChallenge;
		}

		return allScenes;
	}

	/**
	 * Builds a single challenge including any additional screens.
	 * 
	 * @param learningActId
	 *            the id of the learning act
	 * @param challenge
	 *            the ChallengeScreen screen
	 * @param currentScreen
	 *            the UUID of the screen to be built
	 * @return a list of ScreenNode containing the challenge
	 */
	private Scene buildChallenge(final int learningActId,
			final ChallengeScreen challenge, final UUID currentScreen) {

		Scene currentScene = new Scene();

		Screen challengeScreen = new Screen();
		LocaleScreen localeScreen = localeScreens.get(ScreenType.CHALLENGE);
		currentScene.setBackground(new BackgroundType(localeScreen
				.getBackground()));

		QuizChallenge currentQuiz = challenge.getScreenQuiz();
		if (currentQuiz.getLayout() == null) {
			Layout currentLayout = new Layout();
			// This is the default layout.
			currentLayout.setLayoutName(LayoutName.TWOBOXSIDE);
			currentQuiz.setLayout(currentLayout);
		}

		List<GenericInteraction> screenInformationBoxes = challenge
				.getInformationBoxes();
		Map<CharacterType, GenericInteraction> localeInformationBoxes = localeScreen
				.getInformationBoxes();
		if (screenInformationBoxes != null) {
			currentQuiz.getGameElement().addAll(
					convertInfoBoxes(screenInformationBoxes,
							localeInformationBoxes));
		}

		List<GenericInteraction> gameButtons = new ArrayList<GenericInteraction>(
				challenge.getButtons());
		// Buttons will need layout data to set size and position.
		List<GenericInteraction> buttonsToAdjust = new ArrayList<GenericInteraction>();
		Map<ButtonLocationType, GenericInteraction> localeButtons = localeScreen
				.getButtons();
		if (gameButtons != null) {
			buttonsToAdjust.addAll(convertButtons(gameButtons, localeButtons));
		}
		// This will return not only the buttons, but infoboxes for
		// the Stem text/question.
		currentQuiz.getGameElement().addAll(
				currentQuiz.getLayout().generateLayout(buttonsToAdjust,
						currentQuiz.getItem().get(0)));

		for (LearningActCharacter character : challenge.getCharacters()) {
			currentQuiz.getGameElement().add(
					convertCharacter(character, localeScreen));
		}

		challengeScreen.getChallenge().add(currentQuiz);
		challengeScreen.setSequence(new Sequence());
		challengeScreen.getSequence().setSequenceName("CHALLENGE");
		currentScene.getScreen().add(challengeScreen);
		List<Screen> additionalScreens = buildAdditionalScreens(challenge
				.getAdditionalScreens());
		currentScene.getScreen().addAll(additionalScreens);

		return currentScene;
	}

	/**
	 * Builds a list of ScreenNode for the passed learningActId and ScreenType.
	 * 
	 * @param learningActId
	 *            index of the learning act used to build the screens
	 * @param screenType
	 *            the type of screens to build
	 * @return a list of ScreenNode
	 */
	private List<Scene> buildScreens(final int learningActId,
			final ScreenType screenType) {

		List<Scene> allScenes = new ArrayList<Scene>();
		Scene testScene = new Scene();
		testScene = null;
		UUID currentScreen = UUID.randomUUID();
		UUID nextScreen = null;
		ThemeStory themeStory = theme.getThemeStories().get(learningActId);
		List<BaseScreen> themeStoryScreen;
		if (screenType.equals(ScreenType.LESSON_STORY_INTRO)) {
			themeStoryScreen = new ArrayList<BaseScreen>(themeStory.getIntro());
		} else {
			screenTransitions.put(BehaviorType.END_OF_STORY, currentScreen);
			themeStoryScreen = new ArrayList<BaseScreen>(themeStory.getOutro());
		}

		for (BaseScreen screen : themeStoryScreen) {
			testScene = buildScreen(screen, localeScreens.get(screenType));
			int numScenes = allScenes.size();
			if (numScenes >= 1) {
				if (allScenes.get(numScenes - 1).getBackground()
						.getBackground()
						.equals(testScene.getBackground().getBackground())) {
					allScenes.get(numScenes - 1).getScreen()
							.addAll(testScene.getScreen());
				} else {
					allScenes.add(testScene);
				}
			} else {
				allScenes.add(testScene);
			}
		}

		if (screenType.equals(ScreenType.LESSON_STORY_INTRO)) {
			screenTransitions.put(BehaviorType.BEGINNING_OF_LESSON, nextScreen);
		}

		return allScenes;
	}

	/**
	 * Builds all lesson screens for a learning act.
	 * 
	 * @param learningActId
	 *            the id of the learning act
	 * @return a list of ScreenNode containing the built lesson screens
	 */
	private List<Scene> buildLesson(final int learningActId) {

		List<Scene> allScenes = new ArrayList<Scene>();
		Scene testScene = new Scene();

		UUID nextScreen = null;
		LessonAct lessonAct = learningActs.get(learningActId).getLessonActs()
				.get(0);
		List<? extends BaseScreen> screens = lessonAct.getLessonScreens();
		for (BaseScreen screen : screens) {
			nextScreen = UUID.randomUUID();

			testScene = buildScreen(screen, localeScreens.get(screen.getType()));
			int numScenes = allScenes.size();
			if (numScenes >= 1) {
				if (allScenes.get(numScenes - 1).getBackground()
						.getBackground()
						.equals(testScene.getBackground().getBackground())) {
					allScenes.get(numScenes - 1).getScreen()
							.addAll(testScene.getScreen());
				} else {
					allScenes.add(testScene);
				}
			} else {
				allScenes.add(testScene);

			}
		}

		screenTransitions.put(BehaviorType.BEGINNING_OF_CHALLENGE, nextScreen);

		return allScenes;
	}

	/**
	 * Builds a list of ScreenNode from the requested screen. Will return a
	 * single screen unless the screen has additional screens. Challenge screens
	 * should not be built here, but passed to buildChallenge from the calling
	 * method.
	 * 
	 * @param screen
	 *            the screen used to create the ScreenNode
	 * @param localeScreen
	 *            the LocaleScreen used to create the ScreenNode
	 * @return a list of ScreenNode
	 */
	private Scene buildScreen(final BaseScreen screen,
			final LocaleScreen localeScreen) {

		Scene currentScene = new Scene();
		List<Screen> screenNodes = new ArrayList<Screen>();
		Screen screenNode = new Screen();

		List<GenericInteraction> localeObjects = localeScreen.getGameObjects();
		List<LearningActCharacter> screenCharacters = screen.getCharacters();

		currentScene.setBackground(new BackgroundType(localeScreen
				.getBackground()));

		if (localeObjects != null) {
			for (GenericInteraction object : localeObjects) {
				screenNode.getGameElement().add(object);
			}
		}

		if (screenCharacters != null) {
			for (LearningActCharacter themeCharacter : screenCharacters) {
				screenNode.getGameElement().add(
						convertCharacter(themeCharacter, localeScreen));
			}
		}

		List<GenericInteraction> screenInformationBoxes = screen
				.getInformationBoxes();
		Map<CharacterType, GenericInteraction> localeInformationBoxes = localeScreen
				.getInformationBoxes();
		if (screenInformationBoxes != null) {
			screenNode.getGameElement().addAll(
					convertInfoBoxes(screenInformationBoxes,
							localeInformationBoxes));
		}

		List<GenericInteraction> gameButtons = new ArrayList<GenericInteraction>(
				screen.getButtons());
		Map<ButtonLocationType, GenericInteraction> localeButtons = localeScreen
				.getButtons();
		if (gameButtons != null) {
			screenNode.getGameElement().addAll(
					convertButtons(gameButtons, localeButtons));
		}

		screenNode.setSequence(new Sequence());
		screenNode.getSequence().setSequenceName(screen.getType().toString());
		screenNodes.add(screenNode);
		currentScene.getScreen().addAll(screenNodes);

		return currentScene;
	}

	/**
	 * Builds the additional screens tied to a challenge option.
	 * 
	 * @param additionalScreens
	 *            a list of additional screens
	 * @return a list of ScreenNode representing the built additional screens
	 */
	private List<Screen> buildAdditionalScreens(
			final List<BaseScreen> additionalScreens) {
		List<Screen> screenNodes = new ArrayList<Screen>();
		for (BaseScreen screen : additionalScreens) {
			Scene extraScene = buildScreen(screen,
					localeScreens.get(screen.getType()));
			for (Screen currentScreen : extraScene.getScreen()) {
				currentScreen.setSequence(new Sequence());
				// May need for the additional screen trigger.
				// currentScreen.getSequence().setSequenceName("ADDITIONAL");
			}
			screenNodes.addAll(extraScene.getScreen());
		}

		return screenNodes;
	}

	/**
	 *
	 * @param rawCharacter
	 *            Expected object type is {@link LearningActCharacter}
	 * @param screen
	 *            Expected object type is {@link LocaleScreen}
	 * @return Will return {@link GameElementType}
	 */
	public final GameElementType convertCharacter(
			final LearningActCharacter rawCharacter, final LocaleScreen screen) {
		/*
		 * Extract the character type from the learningAct character. From the
		 * locale, extract the same character type. from the GameCharacters,
		 * extract the same character type.
		 */
		CharacterType characterType = rawCharacter.getCharacterType();
		Character localeCharacter = screen.getCharacters().get(characterType);
		GameCharacter gameCharacter = characters.getCharacter(characterType);
		/*
		 * Needed, because sometimes characters get passed more than once
		 * through here and trying to set the pose more than once throws an
		 * exception.
		 */
		if (!(localeCharacter.getPose().contains(".png"))) {
			CharacterAssetType localeAsset = CharacterAssetType
					.valueOf(localeCharacter.getPose());
			String fullPoseString = gameCharacter
					.getCharacterAsset(localeAsset);
			localeCharacter.setPose(fullPoseString);
		}

		// convertedCharacter.setName(characterAsset.getDisplayImage());

		return localeCharacter;
	}

	/**
	 * Takes a list of generic interaction objects and a map of character types
	 * to generic interaction objects. screenInformationBoxes provides textual
	 * data, while taking the right type of box from the map will provide
	 * location and size data.
	 * 
	 * @param screenInformationBoxes
	 *            {@link GenericInteraction}
	 * @param localeInformationBoxes
	 *            {@link CharacterType} {@link GenericInteraction}
	 * @return {@link GenericInteraction}
	 */
	public final List<GenericInteraction> convertInfoBoxes(
			final List<GenericInteraction> screenInformationBoxes,
			final Map<CharacterType, GenericInteraction> localeInformationBoxes) {

		List<GenericInteraction> returnList = new ArrayList<GenericInteraction>();
		for (GenericInteraction gameText : screenInformationBoxes) {
			GenericInteraction matchingLocaleBox = localeInformationBoxes
					.get(CharacterType.fromValue(gameText.getName()));
			matchingLocaleBox.setText(gameText.getText());
			// Adds in default font information.
			if (matchingLocaleBox.getText().getFont() == null) {
				matchingLocaleBox.getText().setFont(DEFAULTFONT);
			}
			if (matchingLocaleBox.getText().getFontSize() == 0) {
				matchingLocaleBox.getText().setFontSize(DEFAULTFONTSIZE);
			}
			returnList.add(matchingLocaleBox);
		}

		return returnList;
	}

	/**
	 * Takes a list of generic interaction objects and a map of button locations
	 * to generic interaction objects. gameButtons provides behavior and text
	 * data, while taking the right type of box from the map will provide
	 * location and size data.
	 * 
	 * @param gameButtons
	 *            {@link GenericInteraction}
	 * @param localeButtons
	 *            {@link ButtonLocationType} {@link GenericInteraction}
	 * @return {@link GenericInteraction}
	 */
	public final List<GenericInteraction> convertButtons(
			final List<GenericInteraction> gameButtons,
			final Map<ButtonLocationType, GenericInteraction> localeButtons) {

		List<GenericInteraction> returnList = new ArrayList<GenericInteraction>();
		for (GenericInteraction gameButton : gameButtons) {
			GenericInteraction matchingLocaleButton = localeButtons
					.get(ButtonLocationType.valueOf(gameButton.getName()));
			matchingLocaleButton.setText(gameButton.getText());
			matchingLocaleButton.getBehaviors().addAll(
					gameButton.getBehaviors());
			// Adds in default font information.
			if (matchingLocaleButton.getText().getFont() == null) {
				matchingLocaleButton.getText().setFont(DEFAULTFONT);
			}
			if (matchingLocaleButton.getText().getFontSize() == 0) {
				matchingLocaleButton.getText().setFontSize(DEFAULTFONTSIZE);
			}
			returnList.add(matchingLocaleButton);
		}

		return returnList;
	}

	/**
	 * Returns a map of LocaleScreens to their type.
	 * 
	 * @return {@link ScreenType} {@link LocaleScreen}
	 */
	public final Map<ScreenType, LocaleScreen> getLocaleScreens() {
		return localeScreens;
	}

	/**
	 * Sets the mapping of LocaleScreens to their types.
	 * 
	 * @param localeMapping
	 *            {@link ScreenType} {@link LocaleScreen}
	 */
	@XmlElementWrapper(name = "LocaleScreens")
	public final void setLocaleScreens(
			final Map<ScreenType, LocaleScreen> localeMapping) {
		this.localeScreens = localeMapping;
	}

	/**
	 * Sets the mapping of Behaviors to the IDs.
	 * 
	 * @return {@link BehaviorType} {@link UUID}
	 */
	public final Map<BehaviorType, UUID> getScreenTransitions() {
		return screenTransitions;
	}

	/**
	 * Sets the mappings of Behaviors to the IDs.
	 * 
	 * @param behaviorMappings
	 *            {@link BehaviorType} {@link UUID}
	 */
	@XmlTransient
	public final void setScreenTransitions(
			final Map<BehaviorType, UUID> behaviorMappings) {
		this.screenTransitions = behaviorMappings;
	}
}

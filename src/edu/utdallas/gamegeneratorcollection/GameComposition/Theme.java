package edu.utdallas.gamegeneratorcollection.GameComposition;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import simsys.schema.components.BackgroundType;
import simsys.schema.components.Character;
import simsys.schema.components.CharacterType;
import simsys.schema.components.GenericInteraction;
import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import simsys.schema.components.Transition;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.CharacterAssetType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.Characters;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameCharacter;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.Subject;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ThemeScreen;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ThemeStory;

/**
 * User: clocke. Date: 2/17/13 Time: 6:04 PM
 */
@XmlRootElement(name = "Theme")
public class Theme {
	/**
	 * Subject object, pulled from Subject XML.
	 */
	private Subject subject;
	/**
	 * Characters object, pulled from Characters XML.
	 */
	private Characters characters;
	/**
	 * A list of screens, typically listed in a Theme XML file.
	 */

	private List<ThemeScreen> introScreens;
	/**
	 * A list of screens, typically listed in a Theme XML file.
	 */

	private List<ThemeScreen> outroScreens;
	/**
	 * A list of story screens.
	 */
	private List<ThemeStory> themeStories;

	/**
	 * Returns a list of ScreenNode built from the intro screens. If there are
	 * no intro screens it will return an empty list
	 * 
	 * @return the list of intro screens
	 */
	public final Scene getIntro() {
		if (introScreens != null) {
			return getScreens(introScreens);
		} else {
			return new Scene();
		}
	}

	/**
	 * Returns a list of ScreenNode built from the outro screens. If there are
	 * no outro screens it will return an empty list
	 * 
	 * @return the list of outro screens
	 */
	public final Scene getOutro() {
		if (outroScreens != null) {
			return getScreens(outroScreens);
		} else {
			return new Scene();
		}
	}

	/**
	 * Returns a list of ScreenNode built from the passed list of ThemeScreen.
	 * Nearly all objects in ThemeScreen are GameElementTypes elements of some
	 * sort (GenericInteraction, Character), except for ThemeCharacters, which
	 * contains data taken from Characters XML files.
	 * 
	 * @param screens
	 *            a list of ThemeScreen
	 * @return a list of ScreenNode
	 */
	private Scene getScreens(final List<ThemeScreen> screens) {
		Scene currentScene = new Scene();

		for (ThemeScreen screen : screens) {
			Screen currentScreen = new Screen();
			currentScene.setBackground(new BackgroundType(screen
					.getBackground()));

			if (screen.getGameObjects() != null) {
				for (GenericInteraction object : screen.getGameObjects()) {
					currentScreen.getGameElement().add(object);
				}
			}

			if (screen.getInformationBoxes() != null) {
				for (GenericInteraction informationBox : screen
						.getInformationBoxes()) {
					currentScreen.getGameElement().add(informationBox);
				}
			}
			if (screen.getButtons() != null) {
				for (GenericInteraction button : screen.getButtons().values()) {
					currentScreen.getGameElement().add(button);
				}
			}

			if (screen.getThemeCharacters() != null) {
				for (Character character : screen.getThemeCharacters().values()) {
					CharacterType characterType = character.getCharacterType();
					GameCharacter gameCharacter = characters
							.getCharacter(characterType);
					/*
					 * Extracts the pose from the string stored in character's
					 * pose field. Since we need the corresponding string
					 * returned by CharacterAssetType, the getAssetType returns
					 * the string. to a CharacterAssetType so getCharacterAsset
					 * can be used.
					 */
					String fullPoseFilePath = gameCharacter
							.getCharacterAsset(getAssetType(character.getPose()));
					character.setPose(fullPoseFilePath);

					currentScreen.getGameElement().add(character);
				}
			}
			/*
			 * Currently only the default transition is set for screens and
			 * scenes. This is due to the fact that transitions are not
			 * specified in the Theme XML.
			 */
			currentScreen.setTransition(new Transition());
			currentScene.getScreen().add(currentScreen);
		}

		currentScene.setTransition(new Transition());
		return currentScene;
	}

	/**
	 * Takes the string from a GameCharacter's pose value and returns a
	 * CharacterAssetType. If no match is found for the type, it will return
	 * null.
	 * 
	 * @param asset
	 *            {@link String}
	 * @return {@link CharacterAssetType}
	 */
	public final CharacterAssetType getAssetType(final String asset) {
		switch (asset) {
		case "STAND_SMILE_CLOSED":
			return CharacterAssetType.STAND_SMILE_CLOSED;
		case "WALK_RIGHT_BEHIND":
			return CharacterAssetType.WALK_RIGHT_BEHIND;
		case "WALK_LEFT_OPEN":
			return CharacterAssetType.WALK_LEFT_OPEN;
		case "RIGHT_POINT_UP":
			return CharacterAssetType.RIGHT_POINT_UP;
		case "RIGHT_POINT_NO":
			return CharacterAssetType.RIGHT_POINT_NO;
		case "WALK_RIGHT_CLOSED":
			return CharacterAssetType.WALK_RIGHT_CLOSED;
		case "WALK_RIGHT_OPEN":
			return CharacterAssetType.WALK_RIGHT_OPEN;
		case "WALK_LEFT_BEHIND":
			return CharacterAssetType.WALK_LEFT_BEHIND;
		case "LEFT_POINT_NO":
			return CharacterAssetType.LEFT_POINT_NO;
		case "LEFT_EVIL":
			return CharacterAssetType.LEFT_EVIL;
		case "LEFT_POINT_UP":
			return CharacterAssetType.LEFT_POINT_UP;
		default:
			return null;
		}
	}

	/**
	 * @return {@link Subject}
	 */
	public final Subject getSubject() {
		return subject;
	}

	/**
	 * @param subjectToBeSet
	 *            {@link Subject}
	 */
	@XmlTransient
	public final void setSubject(final Subject subjectToBeSet) {
		this.subject = subjectToBeSet;
	}

	/**
	 * @return {@link Characters}
	 */
	public final Characters getCharacters() {
		return characters;
	}

	/**
	 * @param charactersToBeSet
	 *            {@link Characters}
	 */
	@XmlTransient
	public final void setCharacters(final Characters charactersToBeSet) {
		this.characters = charactersToBeSet;
	}

	/**
	 * @return {@link ThemeScreen}
	 */
	@XmlElementWrapper(name = "IntroScreens")
	@XmlElement(name = "IntroScreen")
	public final List<ThemeScreen> getIntroScreens() {
		return introScreens;
	}

	/**
	 * @param screens
	 *            {@link ThemeScreen}
	 */

	public final void setIntroScreens(final List<ThemeScreen> screens) {
		this.introScreens = screens;
	}

	/**
	 * @return {@link ThemeScreen}
	 */
	@XmlElementWrapper(name = "OutroScreens")
	@XmlElement(name = "OutroScreen")
	public final List<ThemeScreen> getOutroScreens() {
		return outroScreens;
	}

	/**
	 * @param screens
	 *            {@link ThemeScreen}
	 */

	public final void setOutroScreens(final List<ThemeScreen> screens) {
		this.outroScreens = screens;
	}

	/**
	 * @return {@link ThemeStory}
	 */
	public final List<ThemeStory> getThemeStories() {
		return themeStories;
	}

	/**
	 * @param stories
	 *            {@link ThemeStory}
	 */
	@XmlElementWrapper(name = "ThemeStories")
	@XmlElement(name = "ThemeStory")
	public final void setThemeStories(final List<ThemeStory> stories) {
		this.themeStories = stories;
	}
}

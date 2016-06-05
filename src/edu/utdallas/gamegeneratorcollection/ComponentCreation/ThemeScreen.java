package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import simsys.schema.components.Character;
import simsys.schema.components.CharacterType;
import simsys.schema.components.GenericInteraction;

import java.util.List;
import java.util.Map;

/**
 * Contains the information necessary to build a single-screen Scene object. The
 * Background should be put in a screen object, and the ThemeCharacters,
 * GameObjects, ThemeButtons, and InformationBoxes should be used to construct a
 * screen.
 *
 * This class currently only supports single-screen scenes (due to the XML
 * format for Theme XML files).
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThemeScreen", propOrder = { "background", "buttons",
		"gameObjects", "informationBoxes", "themeCharacters" })
public class ThemeScreen {
	/**
	 * Holds the background image name.
	 */
	@XmlElement(name = "Background")
	private String background;
	/**
	 * Maps character types to characters.
	 */
	@XmlElementWrapper(name = "ThemeCharacters")
	private Map<CharacterType, Character> themeCharacters;
	/**
	 * The game objects.
	 */
	@XmlElementWrapper(name = "GameObjects")
	@XmlElement(name = "GenericInteraction")
	private List<GenericInteraction> gameObjects;
	// private List<GameObject> gameObjects;
	/**
	 * Maps of button locations to buttons.
	 */
	@XmlElementWrapper(name = "ThemeButtons")
	private Map<ButtonLocationType, GenericInteraction> buttons;
	/**
	 * The screen's information boxes.
	 */
	@XmlElementWrapper(name = "InformationBoxes")
	@XmlElement(name = "GenericInteraction")
	private List<GenericInteraction> informationBoxes;

	/**
	 * Returns the background.
	 * 
	 * @return {@link String}
	 */
	public final String getBackground() {
		return background;
	}

	/**
	 * Sets the background.
	 * 
	 * @param backgroundVal
	 *            {@link String}
	 */

	public final void setBackground(final String backgroundVal) {
		this.background = backgroundVal;
	}

	/**
	 * Returns the theme characters.
	 * 
	 * @return {@link LearningActCharacterType} {@link Character}
	 */
	public final Map<CharacterType, Character> getThemeCharacters() {
		return themeCharacters;
	}

	/**
	 * Sets the theme characters.
	 * 
	 * @param characterVals
	 *            {@link LearningActCharacterType} {@link Character}
	 */

	public final void setThemeCharacters(
			final Map<CharacterType, Character> characterVals) {
		this.themeCharacters = characterVals;
	}

	/**
	 * Gets the game objects, which are GenericInteraction elements.
	 * 
	 * @return {@link GenericInteraction}
	 */
	public final List<GenericInteraction> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Sets the game objects, which are GenericInteractionElements.
	 * 
	 * @param objectList
	 *            {@link GenericInteraction}
	 */
	public final void setGameObjects(final List<GenericInteraction> objectList) {
		this.gameObjects = objectList;
	}

	/**
	 * Gets the buttons.
	 * 
	 * @return {@link ButtonLocationType} {@link GenericInteraction}
	 */
	public final Map<ButtonLocationType, GenericInteraction> getButtons() {
		return buttons;
	}

	/**
	 * Sets the buttons.
	 * 
	 * @param buttonVals
	 *            {@link ButtonLocationType} {@link GenericInteraction}
	 */

	public final void setButtons(
			final Map<ButtonLocationType, GenericInteraction> buttonVals) {
		this.buttons = buttonVals;
	}

	/**
	 * Returns the list of information boxes.
	 * 
	 * @return {@link GenericInteraction}
	 */
	public final List<GenericInteraction> getInformationBoxes() {
		return informationBoxes;
	}

	/**
	 * Sets the information boxes.
	 * 
	 * @param boxList
	 *            {@link GenericInteraction}
	 */
	public final void setInformationBoxes(final List<GenericInteraction> boxList) {
		this.informationBoxes = boxList;
	}
}

package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.Character;
import simsys.schema.components.CharacterType;
import simsys.schema.components.GenericInteraction;

import java.util.List;
import java.util.Map;

/**
 * User: clocke. Date: 3/17/13 Time: 9:28 PM
 */
@XmlRootElement(name = "LocaleScreen")
public class LocaleScreen {
	/**
	 * Holds the background.
	 */
	private String background;
	/**
	 * The game objects for the screen.
	 */
	private List<GenericInteraction> gameObjects;
	/**
	 * Maps the character types to the screen's characters.
	 */
	private Map<CharacterType, Character> characters;
	/**
	 * Maps the text type to the infoboxes.
	 */
	private Map<CharacterType, GenericInteraction> informationBoxes;
	/**
	 * Maps the button locations to the buttons.
	 */
	private Map<ButtonLocationType, GenericInteraction> buttons;

	/**
	 * Gets the background.
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
	@XmlElement(name = "Background")
	public final void setBackground(final String backgroundVal) {
		this.background = backgroundVal;
	}

	/**
	 * Gets the game objects for the screen.
	 * 
	 * @return {@link GameObjects}
	 */
	public final List<GenericInteraction> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Sets the game objects for the screen.
	 * 
	 * @param objectList
	 *            {@link GameObjects}
	 */
	@XmlElementWrapper(name = "GameObjects")
	@XmlElement(name = "GenericInteraction")
	public final void setGameObjects(final List<GenericInteraction> objectList) {
		this.gameObjects = objectList;
	}

	/**
	 * Gets the characters for the screen.
	 * 
	 * @return {@link LearningActCharacterType} {@link Character}
	 */
	public final Map<CharacterType, Character> getCharacters() {
		return characters;
	}

	/**
	 * Sets the characters for the screen.
	 * 
	 * @param charMap
	 *            {@link LearningActCharacterType} {@link Character}
	 */
	@XmlElementWrapper(name = "Characters")
	public final void setCharacters(final Map<CharacterType, Character> charMap) {
		this.characters = charMap;
	}

	/**
	 * Gets the information boxes for the screen.
	 * 
	 * @return {@link TextType} {@link GenericInteraction}
	 */
	public final Map<CharacterType, GenericInteraction> getInformationBoxes() {
		return informationBoxes;
	}

	/**
	 * Sets the information boxes for the screen.
	 * 
	 * @param boxMap
	 *            {@link TextType} {@link GenericInteraction}
	 */
	@XmlElementWrapper(name = "InformationBoxes")
	public final void setInformationBoxes(
			final Map<CharacterType, GenericInteraction> boxMap) {
		this.informationBoxes = boxMap;
	}

	/**
	 * Gets the buttons for the screen.
	 * 
	 * @return {@link ButtonLocationType} {@link GenericInteraction}
	 */
	public final Map<ButtonLocationType, GenericInteraction> getButtons() {
		return buttons;
	}

	/**
	 * Sets the buttons for the screen.
	 * 
	 * @param buttonMap
	 *            {@link ButtonLocationType} {@link GenericInteraction}
	 */
	@XmlElementWrapper(name = "Buttons")
	public final void setButtons(
			final Map<ButtonLocationType, GenericInteraction> buttonMap) {
		this.buttons = buttonMap;
	}
}

package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import simsys.schema.components.GenericInteraction;

import java.util.List;


@XmlTransient
@XmlSeeAlso({ FailureScreen.class, ThemeStoryScreenIntro.class,
		ThemeStoryScreenOutro.class })
public abstract class BaseScreen implements Cloneable {
	
	/**
	 * Holds the info boxes for the screen. These are Generic Interaction
	 * Elements in the new spec.
	 */
	private List<GenericInteraction> informationBoxes;
	
	/**
	 * Holds the buttons on the screen. These are Generic Interaction Elements
	 * in the new spec.
	 */
	private List<GenericInteraction> buttons;
	/**
	 * Holds the characters on the screen. These are Game Elements in the new
	 * spec.
	 */
	private List<LearningActCharacter> characters;

	/**
	 * Returns the infoboxes for the screen.
	 * @return {@link GenericInteraction}
	 */
	public final List<GenericInteraction> getInformationBoxes() {
		return informationBoxes;
	}

	/**
	 * Sets the information boxes.
	 * @param boxList
	 *            {@link GenericInteraction}
	 */
	@XmlElementWrapper(name = "InformationBoxes")
	@XmlElement(name = "InformationBox")
	public final void setInformationBoxes(final List<GenericInteraction> boxList) {
		this.informationBoxes = boxList;
	}

	/**
	 * Gets the buttons.
	 * @return {@link GameButton}
	 */
	public final List<GenericInteraction> getButtons() {
		return buttons;
	}

	/**
	 * Sets the buttons.
	 * @param buttonList
	 *            {@link GameButton}
	 */
	@XmlElementWrapper(name = "Buttons")
	@XmlElement(name = "Button")
	public final void setButtons(final List<GenericInteraction> buttonList) {
		this.buttons = buttonList;
	}

	/**
	 * Sets the characters.
	 * @return {@link LearningActCharacter}
	 */
	public final List<LearningActCharacter> getCharacters() {
		return characters;
	}

	/**
	 * Sets the characters.
	 * @param charList
	 *            {@link LearningActCharacter}
	 */
	@XmlElementWrapper(name = "LOCharacters")
	@XmlElement(name = "Character")
	public final void setCharacters(final List<LearningActCharacter> charList) {
		this.characters = charList;
	}

	/**
	 * Returns the screen type. Abstract.
	 * 
	 * @return {@link ScreenType}
	 */
	public abstract ScreenType getType();

	/**
	 * Implements the Cloneable interface.
	 * @return {@link BaseScreen}
	 */
	@Override
	public final BaseScreen clone() {
		try {
			return (BaseScreen) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Clone Not Supported.");
		}
		return null;
	}
}

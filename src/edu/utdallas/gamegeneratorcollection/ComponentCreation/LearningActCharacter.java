package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.AnimationEffect;
import simsys.schema.components.CharacterType;

/**
 * User: clocke. Date: 2/17/13 Time: 3:29 PM
 */
@XmlRootElement(name = "LOCharacters")
public class LearningActCharacter extends GameObject {
	/**
	 * The character type.
	 */
	private CharacterType characterType;
	/**
	 * The movement (animation) type.
	 */
	private AnimationEffect movementType;
	/**
	 * The timer value.
	 */
	private int timer;

	/**
	 * Returns the character type.
	 * 
	 * @return {@link LearningActCharacterType}
	 */
	public final CharacterType getCharacterType() {
		return characterType;
	}

	/**
	 * Sets the character type.
	 * 
	 * @param typeVal
	 *            {@link LearningActCharacterType}
	 */
	@XmlElement(name = "CharacterType")
	public final void setCharacterType(final CharacterType typeVal) {
		this.characterType = typeVal;
	}

	/**
	 * Gets the movement type.
	 * 
	 * @return {@link ObjectMovementType}
	 */
	public final AnimationEffect getMovementType() {
		return movementType;
	}

	/**
	 * Sets the movement type.
	 * 
	 * @param movementVal
	 *            {@link ObjectMovementType}
	 */
	@XmlElement(name = "AnimationEffectType")
	public final void setMovementType(final AnimationEffect movementVal) {
		this.movementType = movementVal;
	}

	/**
	 * Gets the timer value.
	 * 
	 * @return {@link int}
	 */
	public final int getTimer() {
		return timer;
	}

	/**
	 * Sets the timer value.
	 * 
	 * @param timeVal
	 *            {@link int}
	 */
	@XmlElement(name = "Timer")
	public final void setTimer(final int timeVal) {
		this.timer = timeVal;
	}
}

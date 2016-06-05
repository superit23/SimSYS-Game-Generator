package edu.utdallas.old;

import javax.xml.bind.annotation.XmlElement;

import simsys.schema.components.Text;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.TextType;

/**
 * User: clocke. Date: 2/17/13 Time: 3:25 PM
 */
public class GameText {
	/**
	 * The type of the text.
	 */
	private TextType textType;
	/**
	 * The text.
	 */
	private Text text;
	/**
	 * Timer value.
	 */
	private int timer;

	/**
	 * Returns the text type.
	 * 
	 * @return {@link TextType}
	 */
	public final TextType getTextType() {
		return textType;
	}

	/**
	 * Sets the text type.
	 * 
	 * @param typeVal
	 *            {@link TextType}
	 */
	@XmlElement(name = "TextType")
	public final void setTextType(final TextType typeVal) {
		this.textType = typeVal;
	}

	/**
	 * Gets the text.
	 * 
	 * @return {@link String}
	 */
	public final Text getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param textVal
	 *            {@link String}
	 */
	@XmlElement(name = "Text")
	public final void setText(final Text textVal) {
		this.text = textVal;
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

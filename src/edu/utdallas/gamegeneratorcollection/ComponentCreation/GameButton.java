package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.Behavior;
import simsys.schema.components.Reward;
import simsys.schema.components.Text;

@XmlRootElement(name = "Button")
public class GameButton {
	private ButtonLocationType buttonLocationType;
	private Text text;
	private int timer;
	private Behavior behavior;
	private Reward reward;

	/**
	 * Gets the location type.
	 * @return {@link ButtonLocationType}
	 */
	public final ButtonLocationType getButtonLocationType() {
		return buttonLocationType;
	}

	/**
	 * Sets the location type.
	 * @param buttonLocation
	 *            {@link ButtonLocationType}
	 */
	@XmlElement(name = "ButtonLocationType")
	public final void setButtonLocationType(ButtonLocationType buttonLocation) {
		buttonLocationType = buttonLocation;
	}

	/**
	 * Returns the text of the button.
	 * @return {@link String}
	 */
	public final Text getText() {
		return text;
	}

	/**
	 * Sets the text for the button. 
	 * @param textVal
	 *            {@link String}
	 */
	@XmlElement(name = "Text")
	public final void setText(Text textVal) {
		text = textVal;
	}

	/**
	 * Gets the timer value.
	 * @return {@link int}
	 */
	public final int getTimer() {
		return timer;
	}

	/**
	 * Sets the timer value.
	 * @param timeVal
	 *            {@link int}
	 */
	@XmlElement(name = "Timer")
	public final void setTimer(int timeVal) {
		timer = timeVal;
	}

	/**
	 * Returns the transition type.
	 * 
	 * @return {@link BehaviorType}
	 */
	public final Behavior getBehavior() {
		return behavior;
	}

	/**
	 * Sets the transition type.
	 * @param typeVal
	 *            {@link BehaviorType}
	 */
	@XmlElement(name = "Behavior")
	public final void setBehavior(Behavior typeVal) {
		behavior = typeVal;
	}

	/**
	 * Gets the reward.
	 * @return {@link Reward}
	 */
	public final Reward getReward() {
		return reward;
	}

	/**
	 * Sets the reward.
	 * @param rewardVal
	 *            {@link Reward}
	 */
	@XmlElement(name = "Reward")
	public final void setReward(Reward rewardVal) {
		reward = rewardVal;
	}
}

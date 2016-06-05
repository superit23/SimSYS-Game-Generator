package edu.utdallas.old;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.BaseScreen;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ButtonLocationType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameButton;

import javax.xml.bind.annotation.XmlElement;

import simsys.schema.components.Behavior;
import simsys.schema.components.BehaviorType;
import simsys.schema.components.Reward;
import simsys.schema.components.Text;

import java.util.List;

/**
 * User: clocke. Date: 2/17/13 Time: 3:13 PM
 */
public class ChallengeOption extends GameButton {
	/**
	 * The type of the option.
	 */
	private ChallengeOptionType challengeOptionType;
	/**
	 * A list of additional screens.
	 */
	private List<BaseScreen> additionalScreens;

	/**
	 * Default contructor.
	 */
	public ChallengeOption() {
	}

	/**
	 * Constructor to set more details.
	 * 
	 * @param optionType
	 *            {@link ChallengeOptionType}
	 * @param text
	 *            {@link String}
	 * @param reward
	 *            {@link Reward}
	 * @param transitionType
	 *            {@link BehaviorType}
	 * @param screenList
	 *            {@link BaseScreen}
	 * @param buttonLocationType
	 *            {@link ButtonLocationType}
	 */
	public ChallengeOption(final ChallengeOptionType optionType,
			final String text, final Reward reward,
			final BehaviorType transitionType,
			final List<BaseScreen> screenList,
			final ButtonLocationType buttonLocationType) {
		this.challengeOptionType = optionType;
		setText(new Text(text));
		setReward(reward);
		setBehavior(new Behavior());
		getBehavior().setBehaviorType(transitionType);
		setButtonLocationType(buttonLocationType);
		this.additionalScreens = screenList;
	}

	/**
	 * Returns the option type.
	 * 
	 * @return {@link ChallengeOptionType}
	 */
	public final ChallengeOptionType getChallengeOptionType() {
		return challengeOptionType;
	}

	/**
	 * Sets the option type.
	 * 
	 * @param optionType
	 *            {@link ChallengeOptionType}
	 */
	@XmlElement(name = "ChallengeOptionType")
	public final void setChallengeOptionType(
			final ChallengeOptionType optionType) {
		this.challengeOptionType = optionType;
	}

}

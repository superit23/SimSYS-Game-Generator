package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.QuizChallenge;

import java.util.List;

@XmlRootElement(name = "LessonChallenge")
public class ChallengeScreen extends BaseScreen {
	
	// Timer value for the screen.
	private int timer;
	//The list of options (answers).
	private QuizChallenge screenQuiz;
	private List<BaseScreen> additionalScreens;

	/**
	 * Returns the screentype.
	 * 
	 * @return {@link ScreenType}
	 */
	@Override
	public final ScreenType getType() {
		return ScreenType.CHALLENGE;
	}

	/**
	 * Returns the timer.
	 * 
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
	public final void setTimer(final int timeVal) {
		this.timer = timeVal;
	}

	/**
	 * Returns the list of additional screens.
	 * @return {@link BaseScreen}
	 */
	public final List<BaseScreen> getAdditionalScreens() {
		return additionalScreens;
	}

	/**
	 * Sets the list of additional screens.
	 * @param screenList
	 *            {@link BaseScreen}
	 */
	@XmlElementWrapper(name = "AdditionalScreens")
	@XmlElement(name = "AdditionalScreen")
	public final void setAdditionalScreens(final List<BaseScreen> screenList) {
		additionalScreens = screenList;
	}

	/**
	 * Gets the quiz associated with this challenge.
	 * @return {@link QuizChallenge}
	 */
	@XmlElement(name = "Quiz")
	public final QuizChallenge getScreenQuiz() {
		return screenQuiz;
	}

	/**
	 * Sets the QuizChallenge associated with this screen.
	 * @param quiz
	 *            {@link QuizChallenge}
	 */

	public final void setScreenQuiz(final QuizChallenge quiz) {
		screenQuiz = quiz;
	}

}

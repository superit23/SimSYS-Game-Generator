package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

/**
 * User: clocke. Date: 2/17/13 Time: 5:15 PM
 */
public class LessonAct {
	/**
	 * Returns the lesson screens.
	 */
	private List<LessonScreen> lessonScreens;
	/**
	 * Returns the challenges.
	 */
	private List<ChallengeScreen> lessonChallenges;

	/**
	 * Gets the lesson screens.
	 * 
	 * @return {@link LessonScreen}
	 */
	public final List<LessonScreen> getLessonScreens() {
		return lessonScreens;
	}

	/**
	 * Sets the lesson screens.
	 * 
	 * @param screenList
	 *            {@link LessonScreen}
	 */
	@XmlElementWrapper(name = "LessonScreens")
	@XmlElement(name = "LessonScreen")
	public final void setLessonScreens(final List<LessonScreen> screenList) {
		this.lessonScreens = screenList;
	}

	/**
	 * Gets the Challenge screens.
	 * 
	 * @return {@link ChallengeScreen}
	 */
	public final List<ChallengeScreen> getLessonChallenges() {
		return lessonChallenges;
	}

	/**
	 * Sets the Challenge Screens.
	 * 
	 * @param challengeScreens
	 *            {@link ChallengeScreen}
	 */
	@XmlElementWrapper(name = "LessonChallenges")
	@XmlElement(name = "LessonChallenge")
	public final void setLessonChallenges(
			final List<ChallengeScreen> challengeScreens) {
		this.lessonChallenges = challengeScreens;
	}
}

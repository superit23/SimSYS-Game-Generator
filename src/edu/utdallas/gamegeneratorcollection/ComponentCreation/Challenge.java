package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement(name = "Challenge")
public class Challenge {
	
	/**
	 * Holds the challenge screens.
	 */
	private List<ChallengeScreen> lessonChallenges;

	/**
	 * @return a list of type ChallengeScreen {@link ChallengeScreen}
	 */
	public final List<ChallengeScreen> getLessonChallenges() {
		return lessonChallenges;
	}

	/**
	 * @param challenges
	 *            {@link ChallengeScreen}
	 */
	@XmlElementWrapper(name = "LessonChallenges")
	@XmlElement(name = "LessonChallenge")
	public final void setLessonChallenges(final List<ChallengeScreen> challenges) {
		lessonChallenges = challenges;
	}
}

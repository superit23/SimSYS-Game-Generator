package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * User: clocke. Date: 4/28/13 Time: 7:49 PM
 */
@XmlRootElement(name = "Lesson")
public class Lesson {
	/**
	 * Holds the screens for the lesson.
	 */
	private List<LessonScreen> lessonScreens;

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
	 * @param screens
	 *            {@link LessonScreen}
	 */
	@XmlElementWrapper(name = "LessonScreens")
	@XmlElement(name = "LessonScreen")
	public final void setLessonScreens(final List<LessonScreen> screens) {
		this.lessonScreens = screens;
	}
}

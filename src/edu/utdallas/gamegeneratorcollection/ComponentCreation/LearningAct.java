package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * User: clocke. Date: 2/17/13 Time: 4:58 PM
 */
@XmlRootElement(name = "LearningAct")
public class LearningAct {
	/**
	 * The list of lessonActs that comprise the LearningAct object.
	 */
	private List<LessonAct> lessonActs;

	/**
	 * @return possible object is {@link LessonAct}
	 */

	public final List<LessonAct> getLessonActs() {
		return lessonActs;
	}

	/**
	 *
	 * @param acts
	 *            allowed object is {@link LessonAct}
	 */
	@XmlElementWrapper(name = "Lessons")
	@XmlElement(name = "LessonAct")
	public final void setLessonActs(final List<LessonAct> acts) {
		this.lessonActs = acts;
	}
}

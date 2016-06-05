package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: clocke. Date: 2/17/13 Time: 6:48 PM
 */
@XmlRootElement(name = "Subject")
public class Subject {
	/**
	 * The subject name.
	 */
	private String subject;
	/**
	 * A general category for the subject.
	 */
	private String introText;

	/**
	 *
	 * @return Expected type is {@link String}
	 */
	public final String getSubject() {
		return subject;
	}

	/**
	 *
	 * @param subjectName
	 *            Allowed object is {@link String}
	 */

	@XmlElement(name = "Subject")
	public final void setSubject(final String subjectName) {
		this.subject = subjectName;
	}

	/**
	 *
	 * @return Expected type is {@link String}
	 */
	public final String getIntroText() {
		return introText;
	}

	/**
	 *
	 * @param introTxt
	 *            Allowed object is {@link String}
	 */
	@XmlElement(name = "IntroText")
	public final void setIntroText(final String introTxt) {
		this.introText = introTxt;
	}
}

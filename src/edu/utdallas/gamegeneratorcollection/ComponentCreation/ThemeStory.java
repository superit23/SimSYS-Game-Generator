package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * User: clocke. Date: 3/12/13 Time: 9:23 PM
 */
@XmlRootElement(name = "ThemeStory")
public class ThemeStory {
	/**
	 * Holds the screens for the Intro.
	 */
	private List<ThemeStoryScreenIntro> intro;
	/**
	 * Holds the screens for the Outro.
	 */
	private List<ThemeStoryScreenOutro> outro;

	/**
	 * Returns the Intro.
	 * 
	 * @return {@link ThemeStoryScreenIntro}
	 */
	public final List<ThemeStoryScreenIntro> getIntro() {
		return intro;
	}

	/**
	 * Sets the Intro.
	 * 
	 * @param introScreen
	 *            {@link ThemeStoryScreenIntro}
	 */
	@XmlElementWrapper(name = "StoryIntroScreens")
	@XmlElement(name = "StoryIntroScreen")
	public final void setIntro(final List<ThemeStoryScreenIntro> introScreen) {
		this.intro = introScreen;
	}

	/**
	 * Returns the Outro.
	 * 
	 * @return {@link ThemeStoryScreenOutro}
	 */
	public final List<ThemeStoryScreenOutro> getOutro() {
		return outro;
	}

	/**
	 * Sets the Outro.
	 * 
	 * @param outroScreen
	 *            {@link ThemeStoryScreenOutro}
	 */
	@XmlElementWrapper(name = "StoryOutroScreens")
	@XmlElement(name = "StoryOutroScreen")
	public final void setOutro(final List<ThemeStoryScreenOutro> outroScreen) {
		this.outro = outroScreen;
	}
}

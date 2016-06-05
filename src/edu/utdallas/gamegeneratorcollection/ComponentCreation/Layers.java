package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import edu.utdallas.gamegeneratorcollection.GameComposition.Locale;
import edu.utdallas.gamegeneratorcollection.GameComposition.Structure;
import edu.utdallas.gamegeneratorcollection.GameComposition.Theme;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Layers {
	public static final String CHARACTERS = "Characters";
	public static final String LOCALE = "Locale";
	public static final String SUBJECT = "Subject";
	public static final String THEME = "Theme";
	public static final String STRUCTURE = "Structure";
	public static final String LESSONS = "Lessons";
	public static final String CHALLENGES = "Challenges";
	/**
	 * Prefix for the path to the XML repository. Change this if you move the
	 * repo folders.
	 */
	private String pathPrefix = "src\\edu\\utdallas\\gamegeneratorcollection"+ "\\XMLrepo\\";
	private Characters characters;
	/**
	 * Holds structured act data in raw form. Generally, this is pulled from
	 * theme and locale XML files, with some data from Challenges.
	 */
	private List<LearningAct> learningActs;
	private Locale locale;
	private Subject subject;
	private Structure structure;
	private Theme theme;

	/**
	 * The number of components that are pulled from: Characters, Theme,
	 * Lessons, Challenges, Locale, and Subject. Most of the matrices built in
	 * this file will be using this as the size.
	 */
	private static final int NUMCOMPONENTS = 6;
	private static final int CHARACTERINDEX = 0;
	private static final int LESSONSINDEX = 1;
	private static final int CHALLENGESINDEX = 2;
	private static final int LOCALEINDEX = 3;
	private static final int SUBJECTINDEX = 4;
	private static final int THEMEINDEX = 5;

	/**
	 * Constructor for the Layers class. Fileargs contains the files to be
	 * unmarshalled, filePath is passed along to further parts of the pipeline
	 * for export.
	 * 
	 * @param fileArgs
	 *            {@link String}
	 * @param filePath
	 *            {@link String}
	 */
	public Layers(String[] fileArgs, String filePath) {
		Map<String, String> xmlFiles = new HashMap<String, String>();
		if (fileArgs.length == NUMCOMPONENTS) {
			xmlFiles.put(CHARACTERS, fileArgs[CHARACTERINDEX]);
			xmlFiles.put(LESSONS, fileArgs[LESSONSINDEX]);
			xmlFiles.put(CHALLENGES, fileArgs[CHALLENGESINDEX]);
			xmlFiles.put(LOCALE, fileArgs[LOCALEINDEX]);
			xmlFiles.put(SUBJECT, fileArgs[SUBJECTINDEX]);
			xmlFiles.put(THEME, fileArgs[THEMEINDEX]);
		}

		if (xmlFiles != null) {
			try {
				loadXmlComponents(xmlFiles);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		@SuppressWarnings("unused")
		Structure gameBuilder = new Structure(this.getLocale(),
				this.getTheme(), this.getCharacters(), filePath);
	}

	/**
	 * Loads the xml for each layer from a map of filenames.
	 * @param xmlFiles
	 *            a map of layer constants to filenames
	 *
	 * @throws JAXBException
	 *             if the data is badly formed
	 */
	public final void loadXmlComponents(Map<String, String> xmlFiles) throws JAXBException {

		JAXBContext jaxbContext = null;
		File file = null;
		Unmarshaller unmarshaller = null;

		jaxbContext = JAXBContext.newInstance(Characters.class);
		file = new File(pathPrefix + xmlFiles.get(CHARACTERS) + ".xml");
		unmarshaller = jaxbContext.createUnmarshaller();
		this.setCharacters((Characters) unmarshaller.unmarshal(file));

		jaxbContext = JAXBContext.newInstance(Subject.class);
		file = new File(pathPrefix + xmlFiles.get(SUBJECT) + ".xml");
		unmarshaller = jaxbContext.createUnmarshaller();
		this.setSubject((Subject) unmarshaller.unmarshal(file));

		jaxbContext = JAXBContext.newInstance(Theme.class);
		file = new File(pathPrefix + xmlFiles.get(THEME) + ".xml");
		unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller
				.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		this.setTheme((Theme) unmarshaller.unmarshal(file));

		jaxbContext = JAXBContext.newInstance(Locale.class);
		file = new File(pathPrefix + xmlFiles.get(LOCALE) + ".xml");
		unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller
				.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		this.setLocale((Locale) unmarshaller.unmarshal(file));

		String[] lessonFiles = xmlFiles.get(LESSONS).split(",");

		jaxbContext = JAXBContext.newInstance(Lesson.class);
		unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller
				.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		List<Lesson> lessons = new ArrayList<Lesson>();
		for (int i = 0; i < lessonFiles.length; i++) {
			file = new File(pathPrefix + lessonFiles[i] + ".xml");
			Lesson lesson = ((Lesson) unmarshaller.unmarshal(file));
			lessons.add(lesson);
		}

		String[] challengeFiles = xmlFiles.get(CHALLENGES).split(",");

		jaxbContext = JAXBContext.newInstance(Challenge.class);
		unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller
				.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		List<Challenge> challenges = new ArrayList<Challenge>();
		for (int i = 0; i < challengeFiles.length; i++) {
			file = new File(pathPrefix + challengeFiles[i] + ".xml");
			Challenge challenge = ((Challenge) unmarshaller.unmarshal(file));
			challenges.add(challenge);
		}

		learningActs = new ArrayList<LearningAct>();
		for (int i = 0; i < lessons.size(); i++) {
			LearningAct learningAct = new LearningAct();
			List<LessonAct> lessonActs = new ArrayList<LessonAct>();
			LessonAct lessonAct = new LessonAct();
			lessonAct.setLessonScreens(lessons.get(i).getLessonScreens());
			lessonAct.setLessonChallenges(challenges.get(i)
					.getLessonChallenges());
			lessonActs.add(lessonAct);
			learningAct.setLessonActs(lessonActs);
			learningActs.add(learningAct);
		}

		this.setLearningActs(learningActs);
		wireUpLayers();

	}

	/**
	 * Injects dependent layers into each other.
	 */
	private void wireUpLayers() {
		this.getLocale().setTheme(this.getTheme());
		this.getLocale().setLearningActs(this.getLearningActs());
		this.getLocale().setCharacters(this.getCharacters());
		this.getTheme().setSubject(this.getSubject());
		this.getTheme().setCharacters(this.getCharacters());
	}

	/**
	 * @return {@link Characters}
	 */
	public final Characters getCharacters() {
		return characters;
	}

	/**
	 * @param charsData
	 *            {@link Characters}
	 */
	public final void setCharacters(Characters charsData) {
		characters = charsData;
	}

	/**
	 * @return {@link LearningAct}
	 */
	public final List<LearningAct> getLearningActs() {
		return learningActs;
	}

	/**
	 * @param learnActData
	 *            {@link LearningAct}
	 */
	public final void setLearningActs(List<LearningAct> learnActData) {
		learningActs = learnActData;
	}

	/**
	 * @return {@link Locale}
	 */
	public final Locale getLocale() {
		return locale;
	}

	/**
	 * @param localeData
	 *            {@link Locale}
	 */

	public final void setLocale(Locale localeData) {
		this.locale = localeData;
	}

	/**
	 * @return {@link Subject}
	 */
	public final Subject getSubject() {
		return subject;
	}

	/**
	 *
	 * @param subjectData
	 *            {@link Subject}
	 */
	public final void setSubject(Subject subjectData) {
		this.subject = subjectData;
	}

	/**
	 * @return {@link Structure}
	 */
	public final Structure getStructure() {
		return structure;
	}

	/**
	 * @param structureData
	 *            {@link Structure}
	 */
	public final void setStructure(Structure structureData) {
		this.structure = structureData;
	}

	/**
	 * @return {@link Theme}
	 */
	public final Theme getTheme() {
		return theme;
	}

	/**
	 * @param themeData
	 *            {@link Theme}
	 */
	public final void setTheme(Theme themeData) {
		this.theme = themeData;
	}

	/**
	 * @return {@link String}
	 */
	public final String getPathPrefix() {
		return pathPrefix;
	}

	/**
	 * @param prefix
	 *            {@link String}
	 */
	public final void setPathPrefix(String prefix) {
		this.pathPrefix = prefix;
	}

}

package edu.utdallas.old;

/**
 * User: clocke. Date: 4/15/13 Time: 8:55 PM hello world this is a test(this is
 * another test)
 */
public class SimSYSGamePlatform {
	/*    *//**
	 * The number of components that are pulled from: Characters, Theme,
	 * Lessons, Challenges, Locale, and Subject. Most of the matrices built in
	 * this file will be using this as the size.
	 */
	/*
	 * private static final int NUMCOMPONENTS = 6;
	 *//**
	 * The index used for character files in the arguments.
	 */
	/*
	 * private static final int CHARACTERINDEX = 0;
	 *//**
	 * The index used for lesson files in the arguments.
	 */
	/*
	 * private static final int LESSONSINDEX = 1;
	 *//**
	 * The index used for challenge files in the arguments.
	 */
	/*
	 * private static final int CHALLENGESINDEX = 2;
	 *//**
	 * The index used for locale files in the arguments.
	 */
	/*
	 * private static final int LOCALEINDEX = 3;
	 *//**
	 * The index used for subject files in the arguments.
	 */
	/*
	 * private static final int SUBJECTINDEX = 4;
	 *//**
	 * The index used for theme files in the arguments.
	 */
	/*
	 * private static final int THEMEINDEX = 5;
	 *//**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	/*
	 * public static void main(final String[] args) { //Copy the argument array
	 * before manipulating the data in it. String[] fileArgs = (String[])
	 * args.clone();
	 * 
	 * Search search = new Search(); fileArgs = search.getAllFiles();
	 * 
	 * if (fileArgs.length == NUMCOMPONENTS) { Map<String, String> xmlFiles =
	 * new HashMap<String, String>(); xmlFiles.put(CHARACTERS,
	 * fileArgs[CHARACTERINDEX]); xmlFiles.put(LESSONS, fileArgs[LESSONSINDEX]);
	 * xmlFiles.put(CHALLENGES, fileArgs[CHALLENGESINDEX]); xmlFiles.put(LOCALE,
	 * fileArgs[LOCALEINDEX]); xmlFiles.put(SUBJECT, fileArgs[SUBJECTINDEX]);
	 * xmlFiles.put(THEME, fileArgs[THEMEINDEX]);
	 * 
	 * SimSYSGamePlatform gameGenerator = new SimSYSGamePlatform();
	 * 
	 * //String exportFilename = search.getFileLocation();
	 * 
	 * try { Layers layers = gameGenerator.loadXmlComponents(xmlFiles); Game
	 * game = gameGenerator.buildGame(layers); gameGenerator.exportGame(game,
	 * exportFilename); } catch (JAXBException e) { e.printStackTrace(); } }
	 * else {
	 * System.out.println("Please enter the file names of the layers:\n");
	 * System.out.println( "\tie: GameGenerator characters.xml lesson1.xml," +
	 * "lesson2xml challenge1.xml," +
	 * "challenge2.xml locale.xml subject.xml, theme.xml\n\n"); } }
	 *//**
	 * Exports the Game object to xml.
	 * 
	 * @param game
	 *            the Game object containing the game
	 * @param exportFilename
	 *            the name of the file to export to
	 * @throws JAXBException
	 *             if the data is badly formed
	 */
	/*
	 * public final void exportGame(final Game game, final String
	 * exportFilename) throws JAXBException { JAXBContext jaxbContext =
	 * JAXBContext.newInstance(Game.class); Marshaller marshaller =
	 * jaxbContext.createMarshaller();
	 * marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); File file
	 * = new File(exportFilename); marshaller.marshal(game, file); }
	 *//**
	 * Builds the Game object from the layers.
	 * 
	 * @param layers
	 *            a Layer object containing all game layers
	 * @return a Game object built from the layers
	 */
	/*
	 * public final Game buildGame(final Layers layers) { // return
	 * layers.getStructure().createGame(); }
	 *//**
	 * Loads the xml for each layer from a map of filenames.
	 * 
	 * @param xmlFiles
	 *            a map of layer constants to filenames
	 * @return Layers object containing the loaded xml
	 * @throws JAXBException
	 *             if the data is badly formed
	 */
	/*
	 * public final Layers loadXmlComponents(final Map<String, String> xmlFiles)
	 * throws JAXBException { //Layers layers = new Layers();
	 * 
	 * JAXBContext jaxbContext = null; File file = null; Unmarshaller
	 * unmarshaller = null;
	 * 
	 * jaxbContext = JAXBContext.newInstance(Characters.class); file = new
	 * File("XMLrepo\\" + xmlFiles.get(CHARACTERS) + ".xml"); unmarshaller =
	 * jaxbContext.createUnmarshaller(); layers.setCharacters((Characters)
	 * unmarshaller.unmarshal(file));
	 * 
	 * jaxbContext = JAXBContext.newInstance(Subject.class); file = new
	 * File("XMLrepo\\" + xmlFiles.get(SUBJECT) + ".xml"); unmarshaller =
	 * jaxbContext.createUnmarshaller(); layers.setSubject((Subject)
	 * unmarshaller.unmarshal(file));
	 * 
	 * jaxbContext = JAXBContext.newInstance(Theme.class); file = new
	 * File("XMLrepo\\" + xmlFiles.get(THEME) + ".xml"); unmarshaller =
	 * jaxbContext.createUnmarshaller(); unmarshaller.setEventHandler( new
	 * javax.xml.bind.helpers.DefaultValidationEventHandler());
	 * layers.setTheme((Theme) unmarshaller.unmarshal(file));
	 * 
	 * jaxbContext = JAXBContext.newInstance(Locale.class); file = new
	 * File("XMLrepo\\" + xmlFiles.get(LOCALE) + ".xml"); unmarshaller =
	 * jaxbContext.createUnmarshaller(); unmarshaller.setEventHandler( new
	 * javax.xml.bind.helpers.DefaultValidationEventHandler());
	 * layers.setLocale((Locale) unmarshaller.unmarshal(file));
	 * 
	 * String[] lessonFiles = xmlFiles.get(LESSONS).split(",");
	 * 
	 * jaxbContext = JAXBContext.newInstance(Lesson.class); unmarshaller =
	 * jaxbContext.createUnmarshaller(); unmarshaller.setEventHandler( new
	 * javax.xml.bind.helpers.DefaultValidationEventHandler()); List<Lesson>
	 * lessons = new ArrayList<Lesson>(); for (int i = 0; i <
	 * lessonFiles.length; i++) { file = new File("XMLrepo\\" + lessonFiles[i] +
	 * ".xml"); Lesson lesson = ((Lesson) unmarshaller.unmarshal(file));
	 * lessons.add(lesson); }
	 * 
	 * String[] challengeFiles = xmlFiles.get(CHALLENGES).split(",");
	 * 
	 * jaxbContext = JAXBContext.newInstance(Challenge.class); unmarshaller =
	 * jaxbContext.createUnmarshaller(); unmarshaller.setEventHandler( new
	 * javax.xml.bind.helpers.DefaultValidationEventHandler()); List<Challenge>
	 * challenges = new ArrayList<Challenge>(); for (int i = 0; i <
	 * challengeFiles.length; i++) { file = new File("XMLrepo\\" +
	 * challengeFiles[i] + ".xml"); Challenge challenge = ( (Challenge)
	 * unmarshaller.unmarshal(file)); challenges.add(challenge); }
	 * 
	 * List<LearningAct> learningActs = new ArrayList<LearningAct>(); for (int i
	 * = 0; i < lessons.size(); i++) { LearningAct learningAct = new
	 * LearningAct(); List<LessonAct> lessonActs = new ArrayList<LessonAct>();
	 * LessonAct lessonAct = new LessonAct();
	 * lessonAct.setLessonScreens(lessons.get(i).getLessonScreens());
	 * lessonAct.setLessonChallenges(challenges.get(i) .getLessonChallenges());
	 * lessonActs.add(lessonAct); learningAct.setLessonActs(lessonActs);
	 * learningActs.add(learningAct); }
	 * 
	 * layers.setLearningActs(learningActs);
	 * 
	 * layers.setStructure(new Structure()); wireUpLayers(layers);
	 * 
	 * return layers; }
	 *//**
	 * Injects dependent layers into each other.
	 * 
	 * @param layers
	 *            a Layers object with all layers populated
	 */
	/*
	 * private void wireUpLayers(final Layers layers) {
	 * layers.getLocale().setTheme(layers.getTheme());
	 * layers.getLocale().setLearningActs(layers.getLearningActs());
	 * layers.getLocale().setCharacters(layers.getCharacters());
	 * layers.getTheme().setSubject(layers.getSubject());
	 * layers.getTheme().setCharacters(layers.getCharacters());
	 * layers.getStructure().setLocale(layers.getLocale());
	 * layers.getStructure().setTheme(layers.getTheme());
	 * layers.getStructure().setCharacters(layers.getCharacters()); }
	 */

}

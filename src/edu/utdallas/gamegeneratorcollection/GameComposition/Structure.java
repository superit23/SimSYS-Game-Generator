package edu.utdallas.gamegeneratorcollection.GameComposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import simsys.schema.components.Act;
import simsys.schema.components.AutonomousBehavior;
import simsys.schema.components.Character;
import simsys.schema.components.Decoration;
import simsys.schema.components.EducationInteraction;
import simsys.schema.components.Game;
import simsys.schema.components.GameElementType;
import simsys.schema.components.GenericInteraction;
import simsys.schema.components.NonPlayer;
import simsys.schema.components.Player;
import simsys.schema.components.Profile;
import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import simsys.schema.components.Sequence;
import simsys.schema.components.Transition;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.Characters;
import edu.utdallas.gamegeneratorcollection.GameOutput.GameExport;

/**
 * User: clocke. Date: 2/17/13 Time: 6:52 PM
 */
public class Structure {
	/**
	 * Holds data pulled from Theme XML data.
	 */
	private Theme theme;
	/**
	 * Holds data pulled from Locale XML data.
	 */
	private Locale locale;
	/**
	 * Holds data pulled from the Characters XML data.
	 */
	private Characters characters;
	/**
	 * Set to 4 currently. Before operating with this as the constant, should
	 * verify that each Characters XML file has only 4 characters possible.
	 */

	/**
	 * The list of acts used to build the game.
	 */
	private List<Act> acts;
	/**
	 * The game object to be output in XML format.
	 */
	private Game game;
	/**
	 * The filename that the game will be exported as. Obtained at the start of
	 * the pipeline, passed to the GameExport class.
	 */
	private String fileName;

	/**
	 * Constructor for the class. Games are built using the data the Layers
	 * class injects into Locale and Theme objects. The filepath is passed to
	 * the exporter after the game is created.
	 * 
	 * @param localeArg
	 *            {@link Locale}
	 * @param themeArg
	 *            {@link Theme}
	 * @param charsArg
	 *            {@link Characters}
	 * @param filePath
	 *            {@link String}
	 */
	public Structure(final Locale localeArg, final Theme themeArg,
			final Characters charsArg, final String filePath) {
		this.setLocale(localeArg);
		this.setTheme(themeArg);
		this.setCharacters(charsArg);
		this.fileName = filePath;
		try {
			createGame();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Builds the game from the Acts, Characters, and LearningObjectives.
	 * 
	 * @throws SAXException
	 *             If the XSD for export is not found.
	 */
	public final void createGame() throws SAXException {

		acts = new ArrayList<Act>();
		// Adds the intro. This act has no challenges.
		acts.add(createActFromScreens(theme.getIntro()));
		// Adds the middle acts, which contain challenges.
		for (int i = 0; i < locale.getLearningActs().size(); i++) {
			acts.add(locale.getAct(i));
		}
		// Adds the outro act. This act has no challenges.
		acts.add(createActFromScreens(theme.getOutro()));

		game = new Game();

		wireUpActs(acts);
		game.getCharacters().addAll(convertCharacters(characters));
		game.getActs().addAll(acts);

		convertAssetsAndBehaviors();
		GameExport exporter = new GameExport();
		try {
			exporter.exportGame(game, fileName);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param chars
	 *            allowed object types is {@link Characters}
	 * @return expected object types {@link Character} {@link Player}
	 *         {@link NonPlayer}
	 */

	private Collection<? extends Character> convertCharacters(
			final Characters chars) {
		List allCharacters = new ArrayList<Character>();
		Player player = new Player();
		player.setName(chars.getPlayer().getName());
		player.setProfile(new Profile());
		player.setAutonomousBehaviour(new AutonomousBehavior());
		allCharacters.add(player);
		NonPlayer hero = new NonPlayer();
		hero.setName(chars.getHero().getName());
		hero.setProfile(new Profile());
		hero.setAutonomousBehaviour(new AutonomousBehavior());
		allCharacters.add(hero);
		NonPlayer villain = new NonPlayer();
		villain.setName(chars.getVillain().getName());
		villain.setProfile(new Profile());
		villain.setAutonomousBehaviour(new AutonomousBehavior());
		allCharacters.add(villain);
		NonPlayer alt = new NonPlayer();
		alt.setName(chars.getAlt().getName());
		alt.setProfile(new Profile());
		alt.setAutonomousBehaviour(new AutonomousBehavior());
		allCharacters.add(alt);

		return allCharacters;
	}

	/**
	 * Wires the transition behaviors between acts together. It will search for
	 * transition behaviors with a null transitionId and set it to the next
	 * act's first scene's first screen's id Currently the choice seems to be
	 * between Transition and Sequence; We have defaulted to setting a
	 * transition only. Also, Transitions are currently string values. When this
	 * is updated to reflect a proper transition class, the transitions will
	 * need to be set properly.
	 * 
	 * @param actList
	 *            Allowed types are {@link Act}
	 */

	private void wireUpActs(final List<Act> actList) {
		for (int i = 0; i < actList.size(); i++) {
			if (actList.get(i).getTransition() == null) {
				actList.get(i).setTransition(new Transition());
			}
			actList.get(i).setSequence(new Sequence(i));
			for (int j = 0; j < actList.get(i).getScene().size(); j++) {

				if (actList.get(i).getScene().get(j).getTransition() == null) {
					actList.get(i).getScene().get(j)
							.setTransition(new Transition());
				}
				actList.get(i).getScene().get(j).setSequence(new Sequence(j));
				for (int k = 0; k < actList.get(i).getScene().get(j)
						.getScreen().size(); k++) {

					if (actList.get(i).getScene().get(j).getScreen().get(k) == null) {
						actList.get(i).getScene().get(j).getScreen().get(k)
								.setTransition(new Transition());
					}
					if (actList.get(i).getScene().get(j).getScreen().get(k)
							.getSequence() != null) {
						actList.get(i).getScene().get(j).getScreen().get(k)
								.getSequence().setSequenceNumber(k);
					} else {
						actList.get(i).getScene().get(j).getScreen().get(k)
								.setSequence(new Sequence(k));
					}
				}
			}
		}
	}

	/**
	 * Converts every asset into it a new object of the correct type.
	 */

	private void convertAssetsAndBehaviors() {
		for (int a = 0; a < game.getActs().size(); a++) {
			Act act = game.getActs().get(a);
			for (int b = 0; b < act.getScene().size(); b++) {
				Scene scene = act.getScene().get(b);
				for (int c = 0; c < scene.getScreen().size(); c++) {
					Screen screen = scene.getScreen().get(c);

					for (int d = 0; d < screen.getGameElement().size(); d++) {
						GameElementType element = screen.getGameElement()
								.get(d);
						GameElementType newElement = null;
						System.out.println("Element is: "
								+ element.getClass().getName());
						if (element.getClass().getName() == "GenericInteraction") {
							newElement = new GenericInteraction();
						}
						if (element.getClass().getName() == "Decoration") {
							newElement = new Decoration();
						}
						if (element.getClass().getName() == "EducationInteraction") {
							newElement = new EducationInteraction();
						}
						if (element.getClass().getName() == "Player") {
							newElement = new Player();
						}
						if (element.getClass().getName() == "NonPlayer") {
							newElement = new NonPlayer();
						}
						for (int e = 0; e < screen.getChallenge().size(); e++) {
							GameElementType newChallengeElement = null;
							for (GameElementType challengeElement : screen
									.getChallenge().get(e).getGameElement()) {
								if (challengeElement.getClass().getName() == "GenericInteraction") {
									newChallengeElement = new GenericInteraction();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Creates an Act object from a list of ScreenNode. Sets learning objective
	 * from the scene.
	 * 
	 * @param screenNodes
	 *            Allowed types are {@link Scene}
	 * @return {@link Act}
	 */
	private Act createActFromScreens(final Scene screenNodes) {
		Act act = new Act();
		act.getScene().add(screenNodes);
		return act;
	}

	/**
	 * @return {@link Theme}
	 */
	public final Theme getTheme() {
		return theme;
	}

	/**
	 * @param themeStructure
	 *            {@link Theme}
	 */
	public final void setTheme(final Theme themeStructure) {
		this.theme = themeStructure;
	}

	/**
	 * @return {@link Locale}
	 */
	public final Locale getLocale() {
		return locale;
	}

	/**
	 * @param localeStructure
	 *            {@link Locale}
	 */
	public final void setLocale(final Locale localeStructure) {
		this.locale = localeStructure;
	}

	/**
	 * @return {@link Act}
	 */
	public final List<Act> getActs() {
		return acts;
	}

	/**
	 * @param actList
	 *            {@link Act}
	 */
	public final void setActs(final List<Act> actList) {
		this.acts = actList;
	}

	/**
	 * @return {@link Characters}
	 */
	public final Characters getCharacters() {
		return characters;
	}

	/**
	 * @param charactersStructure
	 *            {@link Characters}
	 */
	public final void setCharacters(final Characters charactersStructure) {
		this.characters = charactersStructure;
	}
}

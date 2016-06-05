package edu.utdallas.simsysmain;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import simsys.schema.components.Act;
import simsys.schema.components.AnimationEffect;
import simsys.schema.components.BackgroundType;
import simsys.schema.components.Behavior;
import simsys.schema.components.Challenge;
import simsys.schema.components.Character;
import simsys.schema.components.Game;
import simsys.schema.components.GameElementType;
import simsys.schema.components.Introduction;
import simsys.schema.components.Item;
import simsys.schema.components.Layout;
import simsys.schema.components.LearningObjectiveType;
import simsys.schema.components.Location;
import simsys.schema.components.MultipleChoiceItem;
import simsys.schema.components.MusicType;
import simsys.schema.components.Prop;
import simsys.schema.components.QuizChallenge;
import simsys.schema.components.Scene;
import simsys.schema.components.Screen;
import simsys.schema.components.Size;
import simsys.schema.components.Summary;

import java.lang.reflect.Field;

import java.util.HashMap;
//import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import edu.utdallas.gamePlayEngine.MenuFrame;
import edu.utdallas.gamePlayEngine.controller.GameController;
import edu.utdallas.gamePlayEngine.model.GameModelBoundary;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.view.GameView;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.AudioPlayer;
import edu.utdallas.gamegeneratorcollection.ui.WizardTab;
import edu.utdallas.old.Asset;
import edu.utdallas.old.CharacterAsset;
import edu.utdallas.old.ImageAsset;
import edu.utdallas.previewtool.UserInterface.CharacterProfileWindow;
import edu.utdallas.previewtool.UserInterface.ScenePanel;
import edu.utdallas.previewtool.UserInterface.AssetSelectionWindows.BackgroundSelectWindow;
import edu.utdallas.previewtool.UserInterface.AssetSelectionWindows.CharacterSelectWindow;
import edu.utdallas.previewtool.UserInterface.AssetSelectionWindows.PropSelectWindow;
import edu.utdallas.previewtool.UserInterface.AssetSelectionWindows.SoundSelectWindow;
import edu.utdallas.previewtool.ErrorChecker.GameErrorChecker;
import edu.utdallas.previewtool.Errors.GameErrorList;
import edu.utdallas.previewtool.Errors.PreviewError;
import edu.utdallas.repositoryupdater.Updates;
import Jama.Matrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class InputWizard.
 */
public class InputWizard implements ActionListener {

	/**
	 * The Constant WIDTH.
	 *
	 * @Authors Kaleb Breault, Alex Hunsberger, Zayed Alfalasi, Abdulla
	 *          Alfalasi, Jacob Dahleen This class makes a GUI interface for
	 *          entering input and previewing XML games implements
	 *          ActionListener so a subclass for it is not needed.
	 */

	public static final int WIDTH = 1280;
	/** The Constant HEIGHT. */
	public static final int HEIGHT = 720;

	/** The component inputs. */
	private Matrix[] componentInputs;

	/** The submit clicked. */
	private boolean submitClicked = false;

	/** The window. */
	public static JFrame window = new JFrame();

	/** The main pannel. */
	private JPanel mainPannel;
	// TODO code below is in place for switch to a separate file
	// For the Game Generator's UI. Please leave in.
	// private WizardTab mainPannel;

	/** The menu bar. */
	private JMenuBar menuBar;

	/** The menu. */
	private JMenu menu;

	/** The file menu. */
	private JMenu fileMenu;

	/** The gameengine. */
	private JMenu gameengine;

	/** The game engine menu. */
	private JMenu gameEngineMenu;

	/** The open file item. */
	private JMenuItem openFileItem;

	/** The opengame. */
	private JMenuItem opengame;

	/** The add to repo. */
	private JMenuItem addToRepo;

	/** The remake repo. */
	private JMenuItem remakeRepo;

	/** The save to repo. */
	private JMenuItem saveToRepo;

	/** The save to repo as. */
	private JMenuItem saveToRepoAs;

	/** The check error list. */
	private JMenuItem checkErrorList;

	/** The open engine. */
	private JMenuItem openEngine;

	/** The label1. */
	private static String label1 = "Preview after generating: ";

	/** The game tree. */
	private JTree gameTree;

	/** The scene panel. */
	private static ScenePanel scenePanel;
	// JD character selection class parameters
	/** The character select window. */
	private CharacterSelectWindow characterSelectWindow;

	/** The character select asset. */
	private CharacterAsset characterSelectAsset;

	/** The prop select window. */
	private PropSelectWindow propSelectWindow;

	/** The prop select asset. */
	private ImageAsset propSelectAsset;

	/** The background select window. */
	private BackgroundSelectWindow backgroundSelectWindow;

	/** The background select path. */
	private String backgroundSelectPath;

	/** The sound select window. */
	private SoundSelectWindow soundSelectWindow;

	/** The sound select path. */
	private String soundSelectPath;

	/**
	 * The Enum gameLevel.
	 */
	public enum gameLevel {
		/** The game. */
		GAME,
		/** The act. */
		ACT,
		/** The scene. */
		SCENE,
		/** The screen. */
		SCREEN,
		/** The challenge. */
		CHALLENGE
	};

	/** The selected level. */
	private static gameLevel selectedLevel = null;

	/** The character button. */
	private static JButton characterButton;

	/** The prop button. */
	private static JButton propButton;

	/** The background and hidden button. */
	private static JButton backgroundAndHiddenButton;

	/** The sound button. */
	private static JButton soundButton;

	/** The text button. */
	private static JButton textButton;

	/** The button button. */
	private static JButton buttonButton;

	/** The last selected scene. */
	private static Scene lastSelectedScene = null;

	/** The last selected screen. */
	private static Screen lastSelectedScreen = null;

	/** The Currentfile. */
	private File Currentfile = null;

	/** The has critical game errors. */
	private boolean hasCriticalGameErrors = false;

	/** The toggle hidden text. */
	private String toggleHiddenText = "Show/Hide";
	// JD end

	/** The game. */
	private Game game;

	/** The save file chooser. */
	private JFileChooser saveFileChooser;

	/** The game save path. */
	private String gameSavePath = "C:\\";

	/** The Constant wizardRowSize. */
	private static final int wizardRowSize = 10; // row size for wizard

	/** The game grade level. */
	private String gameGradeLevel = "none";

	/** The updater. */
	private Updates updater;

	/** The actTable. */
	//private Hashtable actTable = new Hashtable();
	private HashMap actTable = new HashMap();

	/** The sceneTable. */
	//private Hashtable sceneTable = new Hashtable();
	private HashMap sceneTable = new HashMap();

	/** The screenTable. */
	//private Hashtable screenTable = new Hashtable();
	private HashMap screenTable = new HashMap();

	/** The objectLog. */
	// private static Hashtable objectBackdropLog = new Hashtable();

	/** The sceneBackdrop. */
	// private static Hashtable sceneBackdrop = new Hashtable();

	/** The sceneSound. */
	// private static Hashtable sceneSound = new Hashtable();

	// WINDOWS
	/** The char base dir. */
	private static String charBaseDir = "Office, Classroom\\Characters\\";
	/** The Constant soundFolder. */
	public static final String soundFolder = "AudioAssetRepository\\";
	/** The insideSoundFolderPath. */
	private String insideSoundFolderPath = "AudioAssetRepository\\music\\";
	/** The backgroundPath. */
	private static String backgroundPath = "Office, Classroom\\Backdrops\\";
	// MAC
	// /** The char base dir. */
	// private static String charBaseDir = "Office, Classroom//Characters//";
	// /** The Constant soundFolder. */
	// public static final String soundFolder = "AudioAssetRepository//";
	// /** The insideSoundFolderPath. */
	// private String insideSoundFolderPath = "AudioAssetRepository//music//";
	// /** The backgroundPath. */
	// private static String backgroundPath = "Office, Classroom//Backdrops//";

	/** The selected value. */
	private int selectedValue = 1;

	CharacterAsset c;

	/** The tabbed pane. */
	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Instantiates a new input wizard.
	 *
	 * @param input
	 *            the input
	 */
	public InputWizard(/* final Matrix[] input */) {

		// componentInputs = input;
		// initializeComponentInputs();
		window.setSize(WIDTH, HEIGHT);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int nextOpenRow = 0;
		// next available row slot
		final String none = "no";
		// mainPannel = new JPanel(new GridLayout(wizardRowSize, 1));
		// TODO code below is in place for switch to a separate file
		// For the Game Generator's UI. Please leave in.
		mainPannel = new WizardTab(new GridLayout(wizardRowSize, 1));
		// making menu
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		openFileItem = new JMenuItem("Open Game File");
		openFileItem.setActionCommand("openFile");
		openFileItem.setMnemonic(KeyEvent.VK_O);
		openFileItem.addActionListener(this);
		fileMenu.add(openFileItem);
		menuBar.add(fileMenu);
		menu = new JMenu("Repository Tools");
		menu.setMnemonic(KeyEvent.VK_R);
		menuBar.add(menu);

		// Change ends
		addToRepo = new JMenuItem("Add game to repository", KeyEvent.VK_D);
		addToRepo.setActionCommand("addToRepo");
		addToRepo.addActionListener(this);
		remakeRepo = new JMenuItem("Remake the repository", KeyEvent.VK_R);
		remakeRepo.addActionListener(this);
		remakeRepo.setActionCommand("remakeRepo");
		menu.add(addToRepo);
		menu.add(remakeRepo);
		saveToRepo = new JMenuItem("Save Game File", KeyEvent.VK_S);
		saveToRepo.addActionListener(this);
		saveToRepo.setActionCommand("saveToRepo");
		fileMenu.add(saveToRepo);
		saveToRepo.setEnabled(false);
		saveToRepoAs = new JMenuItem("Save Game File As", KeyEvent.VK_A);
		saveToRepoAs.addActionListener(this);
		saveToRepoAs.setActionCommand("saveToRepoAs");
		fileMenu.add(saveToRepoAs);
		saveToRepoAs.setEnabled(false);
		checkErrorList = new JMenuItem("Check XML Errors", KeyEvent.VK_C);
		checkErrorList.addActionListener(this);
		checkErrorList.setActionCommand("viewErrorList");
		checkErrorList.setEnabled(false);
		fileMenu.add(checkErrorList);

		// Create Character Select Window
		characterSelectWindow = new CharacterSelectWindow(window);
		characterSelectWindow.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(final WindowEvent arg0) {
			}

			@Override
			public void windowClosed(final WindowEvent e) {
			}

			@Override
			public void windowClosing(final WindowEvent e) {
			}

			@Override
			public void windowDeactivated(final WindowEvent e) {
				/*
				 * if(characterSelectWindow.getNewCharacterAsset() == null) {
				 * return; } else { List<Asset> currentAssets =
				 * lastSelectedScreen.getAssets();
				 * currentAssets.add(characterSelectWindow
				 * .getNewCharacterAsset());
				 * lastSelectedScreen.setAssets(currentAssets);
				 * displayScreen(lastSelectedScene, lastSelectedScreen); }
				 */
			}

			@Override
			public void windowDeiconified(final WindowEvent e) {
			}

			@Override
			public void windowIconified(final WindowEvent e) {
			}

			@Override
			public void windowOpened(final WindowEvent e) {
			}
		});

		// Create Prop Select Window
		propSelectWindow = new PropSelectWindow(window);
		propSelectWindow.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(final WindowEvent e) {
			}

			@Override
			public void windowClosed(final WindowEvent e) {
			}

			@Override
			public void windowClosing(final WindowEvent e) {
			}

			@Override
			public void windowDeactivated(final WindowEvent e) {
				/*
				 * if(propSelectWindow.getNewImageAsset() == null) { return; }
				 * else { List<Asset> currentAssets =
				 * lastSelectedScreen.getAssets();
				 * currentAssets.add(propSelectWindow.getNewImageAsset());
				 * lastSelectedScreen.setAssets(currentAssets);
				 * displayScreen(lastSelectedScene, lastSelectedScreen); }
				 */
			}

			@Override
			public void windowDeiconified(final WindowEvent e) {
			}

			@Override
			public void windowIconified(final WindowEvent e) {
			}

			@Override
			public void windowOpened(final WindowEvent e) {
			}
		});

		// Create Background Select Window
		backgroundSelectWindow = new BackgroundSelectWindow(window);
		backgroundSelectWindow.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(final WindowEvent e) {
			}

			@Override
			public void windowClosed(final WindowEvent e) {
			}

			@Override
			public void windowClosing(final WindowEvent e) {
			}

			@Override
			public void windowDeactivated(final WindowEvent e) {
				if (backgroundSelectWindow.getNewBackgroundPath() == null) {
					return;
				} else {
					// lastSelectedScene.setBackground(backgroundSelectWindow.getNewBackgroundPath());
					// scenePanel.loadBackground(backgroundPath+lastSelectedScene.getBackground().getBackground());
				}
			}

			@Override
			public void windowDeiconified(final WindowEvent e) {
			}

			@Override
			public void windowIconified(final WindowEvent e) {
			}

			@Override
			public void windowOpened(final WindowEvent e) {
			}
		});

		// Create Sound Select Window
		soundSelectWindow = new SoundSelectWindow(window);
		soundSelectWindow.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(final WindowEvent e) {
			}

			@Override
			public void windowClosed(final WindowEvent e) {
			}

			@Override
			public void windowClosing(final WindowEvent e) {
			}

			@Override
			public void windowDeactivated(final WindowEvent e) {
				if (soundSelectWindow.getNewSoundPath() == null) {
					System.out.println("got here, but null");
					return;
				} else {
					soundSelectPath = soundSelectWindow.getNewSoundPath();
					System.out.println(soundSelectPath);
				}
			}

			@Override
			public void windowDeiconified(final WindowEvent e) {
			}

			@Override
			public void windowIconified(final WindowEvent e) {
			}

			@Override
			public void windowOpened(final WindowEvent e) {
			}
		});

		// create tree-structure for viewing Acts/Scenes/Screens/Challenges
		gameTree = new JTree();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
				"No game file selected");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		gameTree.setModel(model);
		gameTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				if (game == null || hasCriticalGameErrors) {
					return;
				} // don't try to display an empty game
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) gameTree
						.getLastSelectedPathComponent();
				AudioPlayer.stopAudio();
				if (isQuestionNode(selectedNode)) {
					Item item = (Item) selectedNode.getUserObject();
					Challenge challenge = ((Screen) ((DefaultMutableTreeNode) selectedNode
							.getParent()).getUserObject()).getChallenge()
							.get(0); // Temporary addition of .get(0) to make it
										// play nice with a list
					Scene scene = (Scene) ((DefaultMutableTreeNode) selectedNode
							.getParent().getParent()).getUserObject();
					selectedLevel = gameLevel.CHALLENGE;
					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText(toggleHiddenText);
					backgroundAndHiddenButton
							.setActionCommand("toggleHiddenElements");
					backgroundAndHiddenButton.setEnabled(true);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					displayChallenge(scene, challenge, item);
				}
				// TODO 1. When going to a Question node in game the buttons do
				// not refresh Assuming this method ^^^^^ is why.
				// TODO 2. The only difference between the two below is a cast
				// and variable type, and we have a unused isChallange method
				// TODO that encompasses Intro, Question, Summary. Feels like we
				// could tackle todo #1 and reduce the code
				else if (isIntroNode(selectedNode)) {
					// System.out.println("Intro Node Active");
					// System.out.println(selectedNode);
					// String[] introPart = selectedNode.toString().split(" ");
					// System.out.println("Intro Part"+java.util.Arrays.toString(introPart));
					// int actIndex = Integer.parseInt(introPart[2]) - 1;
					// int sceneIndex = Integer.parseInt(introPart[4]) - 1;
					// int screenIndex = Integer.parseInt(introPart[6]) - 1;
					// List<Act> acts = game.getAct();
					// Scene scene =
					// acts.get(actIndex).getScene().get(sceneIndex);
					// String intro = introPart[7];
					// // Introduction intro =
					// (Introduction)selectedNode.getUserObject();
					// // Scene scene =
					// (Scene)((DefaultMutableTreeNode)selectedNode.getParent().getParent()).getUserObject();
					String[] actPart = selectedNode.getParent().getParent()
							.getParent().toString().split(" ");
					String[] scenePart = selectedNode.getParent().getParent()
							.toString().split(" ");
					String[] screenPart = selectedNode.getParent().toString()
							.split(" ");
					int actIndex = Integer.parseInt(actPart[1]);
					int sceneIndex = Integer.parseInt(scenePart[1]);
					int screenIndex = Integer.parseInt(screenPart[1]);
					Act actObj = (Act) actTable.get(actIndex);
					int i = 0;
					for (i = 0; i < actObj.getScene().size(); i++)
						if (actObj.getScene().get(i).getSequence()
								.getSequenceNumber() == sceneIndex)
							break;
					Scene s = actObj.getScene().get(i);
					for (i = 0; i < s.getScreen().size(); i++)
						if (s.getScreen().get(i).getSequence()
								.getSequenceNumber() == screenIndex)
							break;
					lastSelectedScene = s;
					QuizChallenge quiz = null;
					Introduction intro = null;
					if(s.getScreen().size() >= i)
					{
						lastSelectedScreen = s.getScreen().get(i);
						List<Challenge> challenge = lastSelectedScreen
								.getChallenge();
						if(challenge.size() > 0) {
							quiz = (QuizChallenge) challenge.get(0);
							intro = quiz.getIntroduction();						
						}						
					}


					selectedLevel = gameLevel.CHALLENGE;
					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText(toggleHiddenText);
					backgroundAndHiddenButton
							.setActionCommand("toggleHiddenElements");
					backgroundAndHiddenButton.setEnabled(true);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					scenePanel.clear();
					scenePanel
							.loadBackground(backgroundPath
									+ lastSelectedScene.getBackground()
											.getBackground());
					JButton playerProfiles = new JButton("Introduction");
					playerProfiles.setBounds(20, 10, 180, 40);
					playerProfiles.setFont(new Font("Comic Sans MS", Font.BOLD,
							15));
					playerProfiles.setVerticalTextPosition(SwingConstants.TOP);
					playerProfiles
							.setHorizontalTextPosition(SwingConstants.CENTER);
					scenePanel.add(playerProfiles);
					if(intro != null) {
						JLabel summaryDescription = new JLabel(intro.getIntroductionName());
						summaryDescription.setBounds(0, 400, 985, 200);
						summaryDescription.setFont(new Font("Comic Sans MS",
								Font.BOLD, 15));
						summaryDescription
								.setVerticalTextPosition(SwingConstants.BOTTOM);
						summaryDescription
								.setHorizontalTextPosition(SwingConstants.CENTER);
						scenePanel.add(summaryDescription);
					}
				} else if (isSummaryNode(selectedNode)) {
					// // Summary summary =
					// (Summary)selectedNode.getUserObject();
					// // Scene scene =
					// (Scene)((DefaultMutableTreeNode)selectedNode.getParent().getParent()).getUserObject();
					// System.out.println(selectedNode);
					// String[] summaryPart =
					// selectedNode.toString().split(" ");
					// System.out.println("Summary :"+java.util.Arrays.toString(summaryPart));
					// int actIndex = Integer.parseInt(summaryPart[2]) - 1;
					// int sceneIndex = Integer.parseInt(summaryPart[4]) - 1;
					// int screenIndex = Integer.parseInt(summaryPart[6]) - 1;
					// List<Act> acts = game.getAct();
					// Scene scene =
					// acts.get(actIndex).getScene().get(sceneIndex);
					// String summary = summaryPart[7];
					String[] actPart = selectedNode.getParent().getParent()
							.getParent().toString().split(" ");
					String[] scenePart = selectedNode.getParent().getParent()
							.toString().split(" ");
					String[] screenPart = selectedNode.getParent().toString()
							.split(" ");
					int actIndex = Integer.parseInt(actPart[1]);
					int sceneIndex = Integer.parseInt(scenePart[1]);
					int screenIndex = Integer.parseInt(screenPart[1]);
					Act actObj = (Act) actTable.get(actIndex);
					int i = 0;
					for (i = 0; i < actObj.getScene().size(); i++)
						if (actObj.getScene().get(i).getSequence()
								.getSequenceNumber() == sceneIndex)
							break;
					Scene s = actObj.getScene().get(i);
					for (i = 0; i < s.getScreen().size(); i++)
						if (s.getScreen().get(i).getSequence()
								.getSequenceNumber() == screenIndex)
							break;
					lastSelectedScene = s;
					Summary sum = null;
					if(s.getScreen().size() >= i)
					{
						lastSelectedScreen = s.getScreen().get(i);
						List<Challenge> challenge = lastSelectedScreen
								.getChallenge();		
						
						if(challenge.size() > 0)
						{
							QuizChallenge quiz = (QuizChallenge) challenge.get(0);
							sum = quiz.getSummary();							
						}
					}

					//QuizChallenge quiz = (QuizChallenge) challenge.get(0);
					//Summary sum = quiz.getSummary();
					selectedLevel = gameLevel.CHALLENGE;
					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText(toggleHiddenText);
					backgroundAndHiddenButton
							.setActionCommand("toggleHiddenElements");
					backgroundAndHiddenButton.setEnabled(true);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					scenePanel.clear();
					scenePanel
							.loadBackground(backgroundPath
									+ lastSelectedScene.getBackground()
											.getBackground());
					JButton playerProfiles = new JButton("Summary");
					playerProfiles.setBounds(20, 10, 180, 40);
					playerProfiles.setFont(new Font("Comic Sans MS", Font.BOLD,
							15));
					playerProfiles.setVerticalTextPosition(SwingConstants.TOP);
					playerProfiles
							.setHorizontalTextPosition(SwingConstants.CENTER);
					scenePanel.add(playerProfiles);
					if(sum != null)
					{
						JLabel summaryDescription = new JLabel(sum.getSummaryName());
						summaryDescription.setBounds(0, 400, 985, 200);
						summaryDescription.setFont(new Font("Comic Sans MS",
								Font.BOLD, 15));
						summaryDescription
								.setVerticalTextPosition(SwingConstants.BOTTOM);
						summaryDescription
								.setHorizontalTextPosition(SwingConstants.CENTER);
						scenePanel.add(summaryDescription);						
					}

				} else if (isScreenNode(selectedNode)) {
					characterButton.setEnabled(true);
					propButton.setEnabled(true);
					backgroundAndHiddenButton.setText(toggleHiddenText);
					backgroundAndHiddenButton
							.setActionCommand("toggleHiddenElements");
					backgroundAndHiddenButton.setEnabled(true);
					soundButton.setEnabled(true);
					textButton.setEnabled(true);
					buttonButton.setEnabled(true);
					selectedLevel = gameLevel.SCREEN;
					String[] actPart = selectedNode.getParent().getParent()
							.toString().split(" ");
					String[] scenePart = selectedNode.getParent().toString()
							.split(" ");
					String[] screenPart = selectedNode.getUserObject()
							.toString().split(" ");
					int actIndex = Integer.parseInt(actPart[1]);
					int sceneIndex = Integer.parseInt(scenePart[1]);
					int screenIndex = Integer.parseInt(screenPart[1]);
					Act actObj = (Act) actTable.get(actIndex);
					int i = 0;
					for (i = 0; i < actObj.getScene().size(); i++)
						if (actObj.getScene().get(i).getSequence()
								.getSequenceNumber() == sceneIndex)
							break;
					
					if(actObj.getScene().size() < i) return;
					
					Scene s = actObj.getScene().get(i);
					for (i = 0; i < s.getScreen().size(); i++)
						if (s.getScreen().get(i).getSequence()
								.getSequenceNumber() == screenIndex)
							break;
					lastSelectedScene = s;
					if(s.getScreen().size() < i) return;
					lastSelectedScreen = s.getScreen().get(i);
					scenePanel.clear();
					scenePanel.loadBackground(backgroundPath
							+ s.getBackground().getBackground());
					displayScreen(lastSelectedScene, lastSelectedScreen);
				} else if (isSceneNode(selectedNode)) {
					String[] actPart = selectedNode.getParent().toString()
							.split(" ");
					String[] scenePart = selectedNode.getUserObject()
							.toString().split(" ");
					int actIndex = Integer.parseInt(actPart[1]);
					int sceneIndex = Integer.parseInt(scenePart[1]);
					// Scene s = acts.get(actIndex).getScene().get(sceneIndex);
					// Scene s = (Scene) selectedNode.getUserObject();
					Scene s = null;
					int i;
					Act actObj = (Act) actTable.get(actIndex);
					for (i = 0; i < actObj.getScene().size(); i++)
						if (actObj.getScene().get(i).getSequence()
								.getSequenceNumber() == sceneIndex)
							break;
					s = actObj.getScene().get(i);
					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText("Background");
					backgroundAndHiddenButton
							.setActionCommand("backgroundToolbar");
					backgroundAndHiddenButton.setEnabled(true);
					soundButton.setEnabled(true);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					selectedLevel = gameLevel.SCENE;
					lastSelectedScene = s;
					scenePanel.clear();
					scenePanel.loadBackground(backgroundPath
							+ s.getBackground().getBackground());
					scenePanel.backgroundMusicPreview(lastSelectedScene
							.getMusic() != null);
				} else if (isGameNode(selectedNode)) {
					List<? extends Character> chars = game.getCharacters();

					scenePanel.clear();
					System.out.println("calling clear rootnode\n");
					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText("Background");
					backgroundAndHiddenButton
							.setActionCommand("backgroundToolbar");
					backgroundAndHiddenButton.setEnabled(false);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					selectedLevel = gameLevel.GAME;

					int xSpacing = 180;
					int charCounter = 0;
					int defaultHeight = 300;
					int defaultWidth = 180;
					for (final Character ca : chars) {

						// create a copy as not to modify the original
						// coordinates
						CharacterAsset c = new CharacterAsset();

						// set image path for character
						// c.setDisplayImage(ca.getProfile().getProfilePhoto());
						// make all images the standard character width and
						// height

						c.setWidth(defaultWidth);
						c.setHeight(defaultHeight);
						// set image location
						c.setX(xSpacing * charCounter);
						c.setY(0);
						//scenePanel.loadAsset(c, charBaseDir, true);
						// button for viewing profile
						// JButton charButton = new JButton("View Profile");
						JButton charButton = new JButton(ca.getName(),
								new ImageIcon(charBaseDir
										+ ca.getProfile().getProfilePhoto()));
						charButton.setBounds(xSpacing * charCounter
								+ defaultWidth, 80, defaultWidth * 3 / 4, 400);
						charButton.setFont(new Font("Comic Sans MS", Font.BOLD,
								15));
						charButton.setVerticalTextPosition(SwingConstants.TOP);
						charButton
								.setHorizontalTextPosition(SwingConstants.CENTER);
						charButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(final ActionEvent e) {
								CharacterProfileWindow cpw = new CharacterProfileWindow(
										window, ca);
								cpw.setVisible(true);
							}
						});
						scenePanel.add(charButton);
						charCounter++;
					}
					JButton playerProfiles = new JButton("Player Profiles");
					playerProfiles.setBounds(20, 10, defaultWidth, 40);
					playerProfiles.setFont(new Font("Comic Sans MS", Font.BOLD,
							15));
					playerProfiles.setVerticalTextPosition(SwingConstants.TOP);
					playerProfiles
							.setHorizontalTextPosition(SwingConstants.CENTER);
					scenePanel.add(playerProfiles);
				} else if (isActNode(selectedNode)) { // Act node

					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText("Background");
					backgroundAndHiddenButton
							.setActionCommand("backgroundToolbar");
					backgroundAndHiddenButton.setEnabled(false);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					selectedLevel = gameLevel.ACT;
					scenePanel.clear();
					String[] part = selectedNode.getUserObject().toString()
							.split(" ");
					int index = Integer.parseInt(part[1]);
					scenePanel.displayAct((Act) actTable.get(index));
					// List<Act> acts = game.getAct();
					// scenePanel.displayAct(acts.get(index));
				} else { // Just in case... Possibly error out

					characterButton.setEnabled(false);
					propButton.setEnabled(false);
					backgroundAndHiddenButton.setText("Background");
					backgroundAndHiddenButton
							.setActionCommand("backgroundToolbar");
					backgroundAndHiddenButton.setEnabled(false);
					soundButton.setEnabled(false);
					textButton.setEnabled(false);
					buttonButton.setEnabled(false);
					// gameLevel is unknown?
					selectedLevel = gameLevel.ACT;
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(gameTree);

		// create tabbed layout
		final JTabbedPane gameGenerationPane = new JTabbedPane(
				SwingConstants.TOP);

		JPanel generateTab = new JPanel(new BorderLayout());
		JPanel previewTab = new JPanel(new BorderLayout());
		JPanel gamePlayEngineTab = new JPanel(new BorderLayout());
		JPanel gamePlayAssessmentTab = new JPanel(new BorderLayout());
		JPanel gamePlayAdaptationTab = new JPanel(new BorderLayout());
		JPanel gameBasedCurricularTab = new JPanel(new BorderLayout());
		gameGenerationPane.addTab("Generate", null, generateTab);
		gameGenerationPane.addTab("Preview", null, previewTab);
		tabbedPane.addTab("Game Generation", null, gameGenerationPane);
		tabbedPane.addTab("Game Play Engine", null, gamePlayEngineTab);
		tabbedPane.addTab("Game Play Assessment", null, gamePlayAssessmentTab);
		tabbedPane.addTab("Game Play Adaptation", null, gamePlayAdaptationTab);
		tabbedPane
				.addTab("Game Based Curricular", null, gameBasedCurricularTab);
		// disabling the tools that are yet to be developed
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
		tabbedPane.setEnabledAt(4, false);

		// Adding game play engine menu in main panel
		JMenu games = new JMenu("Games");
		JMenuItem openGame = new JMenuItem("Open Game");
		JMenuItem quit = new JMenuItem("Quit");

		games.add(openGame);
		games.addSeparator();
		games.add(quit);

		menuBar.add(games);

		final GameView gameView = new GameView();

		final JPanel jPanel = new JPanel(new BorderLayout());
		// jPanel.setLocationRelativeTo(null);
		// jPanel.pack();
		jPanel.setVisible(true);
		jPanel.setSize(1280, 720);
		jPanel.setLayout(new BorderLayout());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jPanel.setLocation(dim.width / 2 - jPanel.getSize().width / 2,
				dim.height / 2 - jPanel.getSize().height / 2);

		// LOAD THE GAME
		openGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				JFileChooser myFileChooser = new JFileChooser();
				int retval = myFileChooser.showOpenDialog(null);
				if (retval == JFileChooser.APPROVE_OPTION) {
					File myFile = myFileChooser.getSelectedFile();
					System.out.println("Opening File: " + myFile.toString());
					try {
						gameView.resetView();
						GameController gameController = new GameController(
								new GameAdapter(), gameView);
						final GameModelBoundary gameModelBoundary = gameController
								.getModelBoundary();
						gameModelBoundary.setView(gameView);
						gameModelBoundary.gmbEnd();

						gameModelBoundary.startGame(myFile.toString(), jPanel);
					} catch (Exception e) {
						System.out
								.println("Exception in GameViewFrame.java, startGame: "
										+ e.toString());
						e.printStackTrace();
					}
				}
			}
		});
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				System.exit(0);
			}
		});
		gamePlayEngineTab.add(jPanel);

		JPanel browsePanel = new JPanel(new BorderLayout()); // browse/click on
																// Acts/Scenes
		browsePanel.add(scrollPane);

		JPanel toolbarPanel = new JPanel(new GridLayout(3, 2, 0, 0));
		characterButton = new JButton("Character");
		characterButton.addActionListener(this);
		characterButton.setEnabled(false);
		characterButton.setActionCommand("charactersToolbarModified");
		toolbarPanel.add(characterButton);
		propButton = new JButton("Prop");
		propButton.addActionListener(this);
		propButton.setEnabled(false);
		propButton.setActionCommand("propToolbar");
		toolbarPanel.add(propButton);
		// TODO Stood-up though unused Text Button
		textButton = new JButton("Text");
		textButton.setEnabled(false);
		textButton.setToolTipText("Not yet implemented");
		toolbarPanel.add(textButton);
		// TODO Stood-up though unused Button Button
		buttonButton = new JButton("Button");
		buttonButton.setEnabled(false);
		buttonButton.setToolTipText("Not yet implemented");
		toolbarPanel.add(buttonButton);
		// TODO "How much do any of these other buttons do?" -Ryan 2/27/14
		soundButton = new JButton("Sound");
		soundButton.addActionListener(this);
		soundButton.setEnabled(false);
		soundButton.setActionCommand("soundToolbar");
		toolbarPanel.add(soundButton);
		backgroundAndHiddenButton = new JButton("Background");
		backgroundAndHiddenButton.addActionListener(this);
		backgroundAndHiddenButton.setEnabled(false);
		backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
		toolbarPanel.add(backgroundAndHiddenButton);
		browsePanel.add(toolbarPanel, BorderLayout.SOUTH);
		toolbarPanel.setPreferredSize(new Dimension(0, 80));

		scenePanel = new ScenePanel(this); // view/edit the Scene selected in
											// the browse panel
		scenePanel.setLayout(null);
		JSplitPane splitPreviewPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, browsePanel, scenePanel);
		splitPreviewPane.setEnabled(false);
		previewTab.add(splitPreviewPane);

		// Jcheckbox
		JCheckBox tickBox = new JCheckBox(label1);
		tickBox.setHorizontalTextPosition(SwingConstants.LEFT);
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent actionEvent) {

				AbstractButton absB = (AbstractButton) actionEvent.getSource();

				boolean slct = absB.getModel().isSelected();

				selectedValue = (slct ? 0 : 1);

				System.out.println(selectedValue);

			}

		};
		tickBox.addActionListener(actionListener);
		// tickBox.setMnemonic(KeyEvent.VK_S);

		mainPannel.setVisible(true);
		generateTab.add(mainPannel);

		window.add(tabbedPane, BorderLayout.CENTER);
		window.setJMenuBar(menuBar);
		// The below needs to happen last to avoid blank window on start-up
		window.setVisible(true);
	}

	/**
	 * Checks if is game node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is game node
	 */
	private boolean isGameNode(final DefaultMutableTreeNode node) {
		return node != null && node.isRoot();
	}

	/**
	 * Checks if is act node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is act node
	 */
	private boolean isActNode(final DefaultMutableTreeNode node) {
		// return node != null && node.getUserObject() != null &&
		// node.getUserObject() instanceof Act;

		return node != null && node.getUserObject() != null
				&& node.getUserObject().toString().contains("Act");
	}

	/**
	 * Checks if is scene node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is scene node
	 */
	private boolean isSceneNode(final DefaultMutableTreeNode node) {
		// return node != null && node.getUserObject() != null &&
		// node.getUserObject() instanceof Scene;
		return node != null && node.getUserObject() != null
				&& node.getUserObject().toString().contains("Scene");
	}

	/**
	 * Checks if is screen node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is screen node
	 */
	private boolean isScreenNode(final DefaultMutableTreeNode node) {
		// return node != null && node.getUserObject() != null &&
		// node.getUserObject() instanceof Screen;
		return node != null && node.getUserObject() != null
				&& node.getUserObject().toString().contains("Screen");
	}

	/**
	 * Checks if is question node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is question node
	 */
	private boolean isQuestionNode(final DefaultMutableTreeNode node) {
		return node != null && node.getUserObject() != null
				&& node.getUserObject() instanceof Item;

	}

	/**
	 * Checks if is summary node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is summary node
	 */
	private boolean isSummaryNode(final DefaultMutableTreeNode node) {
		// return node != null && node.getUserObject() != null &&
		// node.getUserObject() instanceof Summary;
		return node != null && node.getUserObject() != null
				&& node.getUserObject().toString().contains("Summary");
	}

	/**
	 * Checks if is intro node.
	 *
	 * @param node
	 *            the node
	 * @return true, if is intro node
	 */
	private boolean isIntroNode(final DefaultMutableTreeNode node) {
		// return node != null && node.getUserObject() != null &&
		// node.getUserObject() instanceof Introduction;
		return node != null && node.getUserObject() != null
				&& node.getUserObject().toString().contains("Intro");
	}

	/**
	 * Gets the screen character names.
	 *
	 * @param chars
	 *            the chars
	 * @return the screen character names
	 */
	private ArrayList<String> getScreenCharacterNames(
			final ArrayList<CharacterAsset> chars) {
		ArrayList<String> charStrings = new ArrayList<String>();
		for (CharacterAsset charAsset : chars) {
			String filePath = charAsset.getDisplayImage();
			String charName = filePath.substring(0, filePath.indexOf('\\'));

			if (!charStrings.contains(charName)) {
				charStrings.add(charName);
			}
		}

		return charStrings;
	}

	/**
	 * Gets the game generic character names.
	 *
	 * @return the game generic character names
	 */
	private ArrayList<String> getGameGenericCharacterNames() {
		ArrayList<String> charStrings = new ArrayList<String>();
		for (Character ch : game.getCharacters()) {
			// String filePath = ch.getProfile().getProfilePhoto();
			// String charName = filePath.substring(0, filePath.indexOf('\\'));
			String charName = ch.getName();

			if (!charStrings.contains(charName)) {
				charStrings.add(charName);
			}
		}

		return charStrings;
	}

	// read in and return an XML game file given the path to the file
	/**
	 * Read game file.
	 *
	 * @param gameFile
	 *            the game file
	 * @return the game
	 */
	private Game readGameFile(final File gameFile) {
		Game game1 = null;

		try {
			JAXBContext context = JAXBContext.newInstance(Game.class);
			System.out.println("instance passed ");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			System.out.println("marshaller created");
			game1 = (Game) unmarshaller.unmarshal(gameFile);
		} catch (JAXBException e) {
			// TODO this was printing errors before, do we want it in?
			e.printStackTrace();

			System.out.println("error" + e.toString());
			System.out.println("Unable to open " + gameFile);
		}

		return game1;
	}

	/**
	 * Save game file.
	 *
	 * @param gameFile
	 *            the game file
	 * @return the game
	 */
	private Game saveGameFile(final File gameFile) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(game, gameFile);
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("Unable to open " + gameFile);
		}
		return game;
	}

	/**
	 * Save game file as.
	 */
	private void saveGameFileAs() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Enter a name for the file");
		chooser.setFileFilter(new FileNameExtensionFilter("Game XML", "XML"));
		chooser.setAcceptAllFileFilterUsed(false);
		int retval = chooser.showSaveDialog(null);

		if (retval == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			// check for .xml (of any case variation) at the end ($) of the
			// filename
			if (!file.getName().matches(".*[.][Xx][Mm][Ll]$")) {
				System.out.println("didn't match");
				file = new File(file.getPath() + ".XML");
			}
			game = saveGameFile(file);
			Currentfile = file;
			System.out.println("saved as " + file.getPath());
		} else {
			System.out.println("Save command cancelled by user.");
		}
	}

	// C40 handle loading an XML game into the preview window
	/**
	 * Load game.
	 */
	
	// PREVIEW CONTENT
	private void loadGame() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a game XML file");
		chooser.setFileFilter(new FileNameExtensionFilter("Game XML", "xml",
				"xmL", "xMl", "xML", "Xml", "XmL", "XMl", "XML"));
		chooser.setAcceptAllFileFilterUsed(false);
		int retval = chooser.showOpenDialog(null);

		if (retval == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			game = readGameFile(file);
			hasCriticalGameErrors = false;
			loadAndDisplayErrors(game);
			if (!hasCriticalGameErrors) {
				displayGame(game, file.getName());
			} else {
				((DefaultMutableTreeNode) gameTree.getModel().getRoot())
						.removeAllChildren();
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
				rootNode.setUserObject("No game file selected");
				((DefaultTreeModel) gameTree.getModel()).setRoot(rootNode);
			}
			checkErrorList.setEnabled(true);
			saveToRepo.setEnabled(true);
			saveToRepoAs.setEnabled(true);
			Currentfile = file;
			System.out.println("calling clear loadgame\n");
		} else {
			System.out.println("Open command cancelled by user.");
		}
	}

	// return true if there are critical errors
	/**
	 * Load and display errors.
	 *
	 * @param game
	 *            the game
	 */
	private void loadAndDisplayErrors(final Game game) {
		GameErrorList errorList = GameErrorChecker.checkErrors(game,
				scenePanel.getWidth(), scenePanel.getHeight());
		scenePanel.clear();
		scenePanel.loadErrors(errorList);
		hasCriticalGameErrors = errorList.hasCriticalErrors();

		// Debug
		for (PreviewError e : errorList) {
			System.out.println(e);
		}
	}

	// divide game into Acts and Scenes translating to java swing TreeNodes
	// file name is required because it will be the name of the root node
	/**
	 * Display game.
	 *
	 * @param game1
	 *            the game1
	 * @param name
	 *            the name
	 */
	private void displayGame(final Game game1, final String name) {
		((DefaultMutableTreeNode) gameTree.getModel().getRoot())
				.removeAllChildren();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
		rootNode.setUserObject("Game");
		((DefaultTreeModel) gameTree.getModel()).setRoot(rootNode);

		List<Act> acts = game1.getActs();
		for (int i = 0; i < acts.size(); i++) {
			DefaultMutableTreeNode actNode = new DefaultMutableTreeNode("Act "
					+ (i + 1));
			// actNode.setUserObject(acts.get(i));
			// actNode.setUserObject("Act " + (i + 1));
			if (!actTable.containsKey(acts.get(i).getSequence()
					.getSequenceNumber()))
				actTable.put(acts.get(i).getSequence().getSequenceNumber(),
						acts.get(i));
			else
				System.out.println("Error!! Duplicate Invalid Act Number "
						+ acts.get(i).getSequence().getSequenceNumber());
			actNode.setUserObject("Act "
					+ acts.get(i).getSequence().getSequenceNumber());
			List<Scene> scenes = acts.get(i).getScene();
			sceneTable.clear();
			//sceneTable = new Hashtable();
			sceneTable = new HashMap();
			for (int j = 0; j < scenes.size(); j++) {
				DefaultMutableTreeNode sceneNode = new DefaultMutableTreeNode(
						"Scene " + (j + 1));
				// sceneNode.setUserObject(scenes.get(j));
				// sceneNode.setUserObject("Scene "+(i+1)+" "+ + (j + 1));
				if (!sceneTable.containsKey(scenes.get(j).getSequence()
						.getSequenceNumber()))
					sceneTable.put(scenes.get(j).getSequence()
							.getSequenceNumber(), scenes.get(j));
				else
					System.out
							.println("Error!! Duplicate Invalid Scene Number "
									+ scenes.get(j).getSequence()
											.getSequenceNumber());
				sceneNode.setUserObject("Scene "
						+ scenes.get(j).getSequence().getSequenceNumber());

				actNode.add(sceneNode);
				List<Screen> screens = scenes.get(j).getScreen();
				screenTable.clear();
				//screenTable = new Hashtable();
				screenTable = new HashMap();
				for (int k = 0; k < screens.size(); k++) {
					DefaultMutableTreeNode screenNode = new DefaultMutableTreeNode(
							"Screen " + (k + 1));
					// screenNode.setUserObject(screens.get(k));
					// screenNode.setUserObject("Screen "+(i+1)+" "+(j+1)+" "+(k
					// + 1));
					if (!screenTable.containsKey(screens.get(k).getSequence()
							.getSequenceNumber()))
						screenTable.put(screens.get(k).getSequence()
								.getSequenceNumber(), screens.get(k));
					else
						System.out
								.println("Error!! Duplicate Invalid Screen Number "
										+ screens.get(k).getSequence()
												.getSequenceNumber());
					screenNode.setUserObject("Screen "
							+ screens.get(k).getSequence().getSequenceNumber());
					if (screens.get(k).getChallenge() != null) {
						List<Challenge> challenge = (screens.get(k)
								.getChallenge());

						for (int l = 0; l < challenge.size(); l++) {
							QuizChallenge qChallenge = (QuizChallenge) challenge
									.get(l);
							Introduction intro = qChallenge.getIntroduction();
							if (intro != null) {
								DefaultMutableTreeNode introNode = new DefaultMutableTreeNode(
										"Introduction");
								// introNode.setUserObject(qChallenge.getIntroduction().getIntroductionName());
								introNode.setUserObject("Introduction");
								screenNode.add(introNode);
							}

							//
							List<MultipleChoiceItem> item = qChallenge
									.getItem();
							if (item != null) {
								for (int m = 0; m < item.size(); m++) {
									DefaultMutableTreeNode questionNode = new DefaultMutableTreeNode(
											"Challenge Question ");
									// questionNode.setUserObject(item.get(m));
									questionNode.setUserObject(item.get(m)
											.getClass().toString()
											+ (m + 1));
									screenNode.add(questionNode);
								}
							}

							// List<Summary> summaries = challenge.getSummary();
							Summary summary = qChallenge.getSummary();
							if (summary != null) {
								// for(int m = 0; m < summaries.size(); m++)
								// {
								DefaultMutableTreeNode summaryNode = new DefaultMutableTreeNode(
										"Summary ");
								// summaryNode.setUserObject(summary.getSummaryName());
								summaryNode.setUserObject("Summary");
								screenNode.add(summaryNode);
								// }
							}
						}
					}

					sceneNode.add(screenNode);
				}
			}

			rootNode.add(actNode);
		}
		gameTree.expandRow(0);
	}

	// paint the scene in all of its glory
	/**
	 * Display screen.
	 *
	 * @param scene
	 *            the scene
	 * @param screen
	 *            the screen
	 */
	private void displayScreen(final Scene scene, final Screen screen) {
		// List<Asset> assets = screen.getAssets();
		// List<Asset> assets = null; //Temporary to get this working
		// if (assets != null){
		// scenePanel.loadAssets(assets, false);
		// } else {
		// System.out.println("assets null");
		// }
		scenePanel.clear();
		List<GameElementType> gameElements = screen.getGameElement();
		List<LearningObjectiveType> learningObj = screen.getLearningObjective();
		for (int i = 0; i < learningObj.size(); i++) {
			// Yet to work
		}

		for (int i = 0; i < gameElements.size(); i++) {
			String name = gameElements.get(i).getName();
			Location location = gameElements.get(i).getLocation();
			Size size = gameElements.get(i).getSize();
			List<Behavior> behaviors = gameElements.get(i).getBehaviors();
			AnimationEffect animation = gameElements.get(i)
					.getAnimationEffect();
			try {

				Character c = (Character) gameElements.get(i);
				if (c.getProfile().getProfilePhoto() != null) {
					ImageIcon icon = new ImageIcon(charBaseDir
							+ c.getProfile().getProfilePhoto());
					JLabel profile = new JLabel(icon);
					profile.setBounds(c.getLocation().getX(), c.getLocation()
							.getY(), c.getSize().getWidth(), c.getSize()
							.getHeight());
					profile.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
					scenePanel.add(profile);
				}
			} catch (Exception ex) {
			}
		}
		scenePanel.loadAssets(gameElements, false);
		scenePanel.loadBackground(backgroundPath
				+ scene.getBackground().getBackground());

	}

	private Color stringToColor(String value) {

		if (value == null) {
			return Color.black;
		}
		try {
			// get color by hex or octal value
			return Color.decode(value);
		} catch (NumberFormatException nfe) {
			// if we can't decode lets try to get it by name
			try {
				// try to get a color by name using reflection
				final Field f = Color.class.getField(value);

				return (Color) f.get(null);
			} catch (Exception ce) {
				// if we can't get any color return black
				return Color.black;
			}
		}
	}

	// paint the Challenge Multiple Choice question
	/**
	 * Display challenge.
	 *
	 * @param scene
	 *            the scene
	 * @param challenge
	 *            the challenge
	 * @param item
	 *            the item
	 */
	private void displayChallenge(final Scene scene, final Challenge challenge,
			final Item item) {
		System.out.println("in displayChallenge()");

		if (challenge instanceof QuizChallenge) {
			Layout layout = null;
			if (((QuizChallenge) challenge).getLayout() == null) {
				return;
			}

			/*
			 * switch (((QuizChallenge) challenge).getLayout().getLayoutName())
			 * { //case MULTIPLE_CHOICE_LAYOUT: case "Multiple Choice Layout":
			 * //Temporary until the above constant is defined again if (item
			 * instanceof MultipleChoiceItem) { //layout = new
			 * Layout((MultipleChoiceItem)item); layout = new Layout();
			 * layout.setLayoutName("MultipleChoiceItem"); } break; default:
			 * break; }
			 */

			// scenePanel.loadAssets(layout.getAssets(), true);
			scenePanel.loadBackground(backgroundPath
					+ scene.getBackground().getBackground());
		}
	}

	/**
	 * Preview game.
	 *
	 * @param filename
	 *            the filename
	 */
	public final void previewGame(final File filename) {
		System.out.println(selectedValue);
		if (selectedValue == 0) {
			tabbedPane.setSelectedIndex(1);

			// We are literally guessing how long it will take to write to disk.
			// TODO: sync filesystem write
			// note: 2 seconds was a sufficient amount of time for my computer
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			}
			game = readGameFile(filename);
			displayGame(game, filename.getName());

		}
	}

	// a way to weight the options for each component against each other (ie age
	// v gender) on which is more important
	// add a third input to this method called weight and replace the
	// optionTotal in the assignment statement with it.
	/**
	 * Option matrix.
	 *
	 * @param optionNumber
	 *            the option number
	 * @param optionTotal
	 *            the option total
	 * @return the matrix
	 */

	// called once after the submit button was clicked and a valid location is
	// given.

	/**
	 * Gets the file location.
	 *
	 * @return the file location
	 */
	public final String getFileLocation() {
		return gameSavePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public final void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
		case "openEngine": // ---Game Engine code added by Sreeram---
			System.out.println("Invoking Game Engine..");
			GameView gameView = new GameView();
			MenuFrame myMenuFrame = new MenuFrame(gameView);
			break;
		case "openFile":
			loadGame();

			break;
		case "charactersToolbarModified":
			System.out.println("Char Button Pressed");

			characterSelectWindow.setVisible(true);
			break;
		case "charactersToolbar":
			// JD
			characterSelectAsset = null;
			characterSelectWindow.setCharacterAsset(characterSelectAsset);
			ArrayList<CharacterAsset> chars = new ArrayList<CharacterAsset>();
			// List<Asset> assets = lastSelectedScreen.getAssets();
			List<Asset> assets = null; // Temporary fix to stop the code from
										// breaking for now.
			for (Asset as : assets) {
				if (as instanceof CharacterAsset) {
					chars.add((CharacterAsset) as);
				}
			}
			ArrayList<String> charNamesInScreen = getScreenCharacterNames(chars);
			ArrayList<String> charNamesInGame = getGameGenericCharacterNames();
			ArrayList<String> availableChars = new ArrayList<String>();
			for (String charName : charNamesInGame) {
				if (!charNamesInScreen.contains(charName)) {
					availableChars.add(charName);
				}
			}
			if (availableChars.isEmpty()) {
				JOptionPane
						.showMessageDialog(null,
								"All characters are currently in this Screen, none to add");
			} else {
				characterSelectWindow.setCharacterChoices(availableChars);
				characterSelectWindow.setVisible(true);
			}
			break;
		case "propToolbar":
			propSelectAsset = null;
			propSelectWindow.setImageAsset(propSelectAsset);
			propSelectWindow.setVisible(true);
			break;
		case "backgroundToolbar":
			backgroundSelectPath = null;
			String currentBackgroundPath = lastSelectedScene.getBackground()
					.getBackground();
			currentBackgroundPath = "Backdrops";
			// currentBackgroundPath = currentBackgroundPath.substring(0,
			// currentBackgroundPath.lastIndexOf('\\'));
			backgroundSelectWindow
					.setBackgroundPathString(backgroundSelectPath);
			backgroundSelectWindow
					.setBackgroundFolderPath(currentBackgroundPath);
			backgroundSelectWindow.setVisible(true);
			break;
		case "soundToolbar":
			soundSelectPath = null;
			if (selectedLevel.equals(gameLevel.SCENE)) {
				soundSelectWindow
						.setSoundFolderPath(SoundSelectWindow.MUSICFOLDER);
			} else if (selectedLevel.equals(gameLevel.SCREEN)) {
				soundSelectWindow
						.setSoundFolderPath(SoundSelectWindow.EFFECTSFOLDER);
			} else {
				break;
			}
			soundSelectWindow.setVisible(true);
			break;
		// JD end
		case "deleteElement":

			// Comments below are to temporarily remove code until we know what
			// to do with Assets.
			/*
			 * Asset toDelete = scenePanel.getTargetedAsset();
			 * if(lastSelectedScreen.getAssets().contains(toDelete)) {
			 * lastSelectedScreen.getAssets().remove(toDelete);
			 * displayScreen(lastSelectedScene, lastSelectedScreen); } else {
			 * System
			 * .out.println("Error: Attempt to delete asset not in screen!"); }
			 */
			break;
		case "previewSound":
			/*
			 * Asset toPreviewSound = scenePanel.getTargetedAsset();
			 * if(lastSelectedScreen.getAssets().contains(toPreviewSound)) {
			 * String insideSoundFolderPath = toPreviewSound.getSoundEffect();
			 * AudioPlayer.playAudio(soundFolder + insideSoundFolderPath); }
			 * else {
			 * System.out.println("Error: Attempt to preview asset not in screen!"
			 * ); }
			 */
			break;
		case "backgroundMusicPreviewPlay":
			// TODO finish
			if (lastSelectedScene.getMusic() != null) {
				String insideSound = lastSelectedScene.getMusic().getMusic();
				// AudioPlayer.playAudio(soundFolder + insideSoundFolderPath);
				AudioPlayer.playAudio(insideSoundFolderPath + insideSound);
			} else {
				System.out.println("Error: No background music found.");
			}
			break;
		case "backgroundMusicPreviewStop":
			AudioPlayer.stopAudio();
			break;
		case "toggleHiddenElements":
			scenePanel.toggleHiddenElements();
			break;
		case "resizeAsset":
			displayScreen(lastSelectedScene, lastSelectedScreen);
			break;
		case "saveToRepo":
			if (Currentfile != null && !Currentfile.equals("")) {
				int retval = JOptionPane.showConfirmDialog(null, "Overwrite "
						+ Currentfile + " and save changes?", "Warning",
						JOptionPane.YES_NO_OPTION);
				if (retval == JOptionPane.YES_OPTION) {
					System.out.println("overwriting " + Currentfile);
					saveGameFile(Currentfile);
				}
			}
			break;
		case "saveToRepoAs":
			if (game != null) {
				saveGameFileAs();
			}
			break;
		case "viewErrorList":
			loadAndDisplayErrors(game);
			break;
		case "addToRepo":
			File parent = new File("New Games\\");
			saveFileChooser = new JFileChooser(parent);
			saveFileChooser.setDialogTitle("Choose the game folder");
			saveFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			updater = new Updates();
			int returnValue2 = saveFileChooser.showOpenDialog(saveFileChooser);
			String gameName;
			if (returnValue2 == JFileChooser.APPROVE_OPTION) {
				File file = saveFileChooser.getSelectedFile();
				gameName = file.getAbsolutePath();

				// if(gameName.contains("New Games"))
				// {
				// System.out.println("Game name: "+
				// gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));
				// updater.addGame(gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));
				// }
				System.out.println("Game name: "
						+ gameName.substring(gameName.lastIndexOf('\\') + 1,
								gameName.length()));
				updater.addGame(gameName.substring(
						gameName.lastIndexOf('\\') + 1, gameName.length()));
			} else if (returnValue2 == JFileChooser.CANCEL_OPTION) {
				System.out.println("Open cancelled by user. /n Returning.");
			}

			break;
		case "remakeRepo":
			updater = new Updates();
			updater.remakeRepo();
			break;

		default:
			System.out.println("Unanticipated Input in ActionPerformed:"
					+ e.getActionCommand());
			break;
		}
	}

	public static void loadBackground(String substring) {
		characterButton.setEnabled(false);
		propButton.setEnabled(false);
		backgroundAndHiddenButton.setText("Background");
		backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
		backgroundAndHiddenButton.setEnabled(true);
		soundButton.setEnabled(true);
		textButton.setEnabled(false);
		buttonButton.setEnabled(false);
		selectedLevel = gameLevel.SCENE;
		scenePanel.clear();
		BackgroundType backgroundImage = new BackgroundType();
		backgroundImage.setBackground(substring);
		lastSelectedScene.setBackground(backgroundImage);
		scenePanel.loadBackground(backgroundPath + substring);
		scenePanel.backgroundMusicPreview(lastSelectedScene.getMusic() != null);

	}

	public static void loadCharBackground(String imgstrng) {

		System.out.println("New Image :" + imgstrng);
		ImageIcon icon = new ImageIcon(charBaseDir + imgstrng);
		System.out.println(charBaseDir + imgstrng);

		JLabel summaryDescription = new JLabel(icon);
		summaryDescription.setBounds(0, 170, 985, 250);
		summaryDescription.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		summaryDescription.setVerticalTextPosition(SwingConstants.BOTTOM);
		summaryDescription.setHorizontalTextPosition(SwingConstants.CENTER);
		scenePanel.add(summaryDescription);
	}

	public static void loadBackgroundSound(String soundPathString) {
		characterButton.setEnabled(false);
		propButton.setEnabled(false);
		backgroundAndHiddenButton.setText("Background");
		backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
		backgroundAndHiddenButton.setEnabled(true);
		soundButton.setEnabled(true);
		textButton.setEnabled(false);
		buttonButton.setEnabled(false);
		selectedLevel = gameLevel.SCENE;
		scenePanel.loadBackground(backgroundPath
				+ lastSelectedScene.getBackground().getBackground());
		MusicType music = new MusicType();
		music.setMusic(soundPathString);
		lastSelectedScene.setMusic(music);
		scenePanel.backgroundMusicPreview(lastSelectedScene.getMusic() != null);
	}
}

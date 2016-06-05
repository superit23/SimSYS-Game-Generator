/**
 * Dummy View class implementation - Will be replaced by actual view classes 
 */
package edu.utdallas.gamePlayEngine.view;

import edu.utdallas.components.CImage;
import edu.utdallas.gamePlayEngine.controller.AIFunction;
import edu.utdallas.gamePlayEngine.controller.GameController;
import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.model.Location;
import edu.utdallas.gamePlayEngine.model.Util;
import edu.utdallas.gamePlayEngine.model.adapter.ChallengeAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.view.ImagePanel;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.AudioPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.Timer;

import simsys.schema.components.Behavior;
import simsys.schema.components.BehaviorType;
import simsys.schema.components.Character;
import simsys.schema.components.ImageProp;
import simsys.schema.components.NonPlayer;
import simsys.schema.components.Profile;
import simsys.schema.components.Prop;
import simsys.schema.components.Reward;
import simsys.schema.components.SandBox;
import simsys.schema.components.Size;

//import org.apache.log4j.Logger;

public class GameViewFrame extends javax.swing.JFrame {
	public static JFrame jFrame = new JFrame();
	private static final int JPANELSCENECONSTANT = 100;
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 800;
	public static boolean win = false;
	public static boolean sbWin = true;
	public static JButton[] button;
	private static int state = 0;
	private static int i = 0;
	private GameController controller;
	private JPanel jPanel;
	private JPanel jPanelScene;
	public static JButton btn;
	private JLabel jLabelScene; // Zac ZHANG: use for new class
	private static JLayeredPane layeredPane;
	private BufferedImage myPicture;
	private ImagePanel myImagePanel;
	private boolean firstTimeRun = true;
	private List<Timer> timerList; // Zac ZHANG: Add for removing all timers
	private static int playerPoints = 0;
	private static int npcPoints = 0;

	public GameViewFrame(List<Timer> timerList) {
		super("GameViewFrame"); // Zac ZHANG Added
		this.timerList = timerList;
		// System.out.println("view has been initialized");
	}

	/**
	 * @return
	 */
	public GameController getController() {
		return controller;
	}

	/**
	 * @param controller
	 */
	public void setController(GameController controller) {
		this.controller = controller;
	}

	/**
	 * @param myMenuFrame
	 */
	public void viewStartAct(JPanel myMenuFrame) {
		if (this.firstTimeRun) {
			this.firstTimeRun = false;
			this.jPanel = myMenuFrame;

			// this.jPanel.setLocationRelativeTo(null);
			// this.jPanel.pack();
			this.jPanel.setVisible(true);
			this.jPanel.setSize(600, 800);
			this.jPanel.setLayout(new BorderLayout());

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.jPanel.setLocation(dim.width / 2 - this.jPanel.getSize().width
					/ 2, dim.height / 2 - this.jPanel.getSize().height / 2);

			layeredPane = new JLayeredPane();
			this.jPanel.add(layeredPane, BorderLayout.CENTER);
			layeredPane.setBounds(0, 0, 1000, 800);
		} else {
			layeredPane.removeAll();
			layeredPane.revalidate();
			layeredPane.repaint();
			layeredPane.setVisible(true);
			controller = null;
			jPanel.remove(layeredPane);
			layeredPane = new JLayeredPane();
			this.jPanel.add(layeredPane, BorderLayout.CENTER);
			layeredPane.setBounds(0, 0, 1000, 800);
		}
	}

	/**
	 * @param imageName
	 */
	public void setBackgroundImage(String imageName) {

		Image image = null;
		try {
			System.out.println(getClass().getResource(imageName));
			image = ImageIO.read(getClass().getResource(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.myImagePanel != null)
			this.jPanel.remove(myImagePanel);
		myImagePanel = new ImagePanel(image);
		this.jPanel.add(myImagePanel, 1);
		this.jPanel.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		// this.jPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jPanel.setVisible(true);
	}

	/*
	 * public void displayNext(Prop prop, GameState gameState) { if (prop !=
	 * null) { String next = prop.getNext();
	 * System.out.println("Iniyan ... Next to display is" + prop.getNext());
	 * 
	 * if (next != null) { if (next.contains("screen")) {
	 * GameController.startNextScreen(next, gameState);
	 * 
	 * } } else { System.out.println("Need to start sequencing"); }
	 * setVisible(false); } }
	 */
	public void displayNextNew(int nextScreen, GameState gameState) {
		// Kyle: Variable to determine which button is pressed
		int selectedButton = 0;
		// Kyle: Check if there is a next screen
		if (nextScreen != 0) {
			boolean sandBox = false;
			try {
				SandBox test = (SandBox) gameState.getChallenegeAdapter()
						.getChallenge();
				sandBox = true;
			} catch (Exception e) {

			}
			// Kyle: Check if a challenge exists
			if (gameState.getScreenAdapter().getChallengeAdapaters().toString()
					.length() > 3
					&& !sandBox) {
				// Kyle: Loop through the four options
				for (int i = 0; i < 4; i++) {
					// Kyle: Print debug statement
					System.out.println("Next Screen Option[" + i + "] = "
							+ GameView.nextScreen[i]);
					// Kyle: Check which button is gray
					// The gray button will be the selected button
					if (button[i].getBackground().equals(Color.GRAY)) {
						// Kyle: Signal which button is selected
						selectedButton = i;
						// Kyle: Break out of the loop
						break;
					}
				}
				// Kyle: Start the screen according to which button was selected
				GameController.startNextScreen(
						GameView.nextScreen[selectedButton], gameState);

				setVisible(false);
			}
			// Kyle: Challenge does not exist
			else {
				GameController.startNextScreen(nextScreen, gameState);
				setVisible(false);
			}

		}
	}

	public void addCheckBox(final Prop currentProp, final GameState gameState,
			char answerValue, final int yvalue) {
		// jPanelScene = Util.panelPosition(currentProp.getLocation(), false,
		// currentProp);
		jPanelScene = Util.panelPostionNew(currentProp.getLocation().getX(),
				currentProp.getLocation().getY(), 200, 160, false, currentProp);
		JCheckBox checkBox = new JCheckBox(currentProp.getText().getText());
		System.out.println("Answer Value is : " + answerValue);
		checkBox.setMnemonic(answerValue);
		checkBox.setSelected(false);

		// Zac ZHANG: Modify to get color from XML file
		try {
			checkBox.setBackground(Util.StringToColor(currentProp.getColor()
					.toString().trim()));
		} catch (Exception e) {
			checkBox.setBackground(Color.yellow);
		}
		checkBox.setBackground(Color.yellow);

		// Zac ZHANG: Added to get size from XML file
		final int hintFixLocation;
		if (currentProp.getSize() != null) {
			Dimension dimension = new Dimension();
			dimension.height = currentProp.getSize().getHeight();
			dimension.width = currentProp.getSize().getWidth();

			checkBox.setPreferredSize(dimension);
			// hintFixLocation =
			// (int)Util.panelDimension(currentProp.getSize()).getHeight()-20;
			hintFixLocation = dimension.height - 20;
		} else
			hintFixLocation = 0;

		jPanelScene.add(checkBox);
		layeredPane.add(jPanelScene, JPANELSCENECONSTANT);

		checkBox.addMouseListener(new MouseListener() {
			private JTextArea ta = new JTextArea();
			private JScrollPane scrollPane = new JScrollPane();

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// Zac ZHANG: Modify this part to fit the size of checkBox
				jPanelScene = Util
						.hintPositionNew(600, yvalue, hintFixLocation);

				ta = new JTextArea(currentProp.getHint().getHint(), 5, 15);

				ta.setLineWrap(true);
				ta.setWrapStyleWord(true);

				jPanelScene.add(new JLabel("Hint : "));

				jPanelScene.add(ta);
				layeredPane.add(jPanelScene);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				System.out.println("Exitting!!!");
				ta.setVisible(false);
				jPanelScene.remove(ta);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		checkBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String[] params = e.paramString().split(",");
				if (((JCheckBox) e.getSource()).getMnemonic() == 'C') {
					ChallengeAdapter.quizFeedback = gameState
							.getCorrectFeedBack();
					ChallengeAdapter.quizTitleChange = gameState
							.getPromotedTitle();
					ChallengeAdapter.quizReward = gameState.getQuizReward();
				} else
					ChallengeAdapter.quizFeedback = gameState
							.getInCorrectFeedback();

			}
		});
	}

	// Mike KEMP: Added for button selection in challenges
	public void addButton(final Prop currentProp, final GameState gameState,
			char answerValue, final int yvalue, final int size) {
		System.out.println("BOOOM:" + GameController.currentChallengeAdapter);
		if (i == 0) {
			button = new JButton[size];
		}
		jPanelScene = Util.panelPostionNew(currentProp.getLocation().getX(),
				currentProp.getLocation().getY(), 200, 160, false, currentProp);
		button[i] = new JButton(currentProp.getText().getText());
		System.out.println("Answer Value is : " + answerValue);
		button[i].setMnemonic(answerValue);
		button[i].setSelected(false);
		button[i].setFont(new Font("Aerial", Font.PLAIN, 16));
		try {
			button[i].setBackground(Util.StringToColor(currentProp.getColor()
					.toString().trim()));
		} catch (Exception e) {
			button[i].setBackground(Color.yellow);
		}
		button[i].setBackground(Color.yellow);
		button[i].setBorder(BorderFactory.createLineBorder(Color.black, 4));
		final int hintFixLocation;
		System.out.println(currentProp.getSize());
		Size large = new Size();
		large.setHeight(50);
		large.setWidth(200);
		currentProp.setSize(large);
		if (currentProp.getSize() != null) {
			Dimension dimension = new Dimension();
			dimension.height = currentProp.getSize().getHeight() + 50;
			dimension.width = currentProp.getSize().getWidth();
			button[i].setPreferredSize(dimension);
			hintFixLocation = dimension.height - 20;
		} else {
			hintFixLocation = 0;
		}
		// add a mouse listener to each button to check to see if the button has
		// been "hovered" over
		// if the button has been hovered over show the hint and hide the hint
		// once it has been moved out of the button
		button[i].addMouseListener(new MouseListener() {
			private JTextArea ta = new JTextArea();
			private JScrollPane scrollPane = new JScrollPane();

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				jPanelScene = Util.hintPositionNew(815, yvalue - 75,
						hintFixLocation);
				ta = new JTextArea(currentProp.getHint().getHint(), 5, 17);
				ta.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				ta.setBackground(Color.yellow);
				ta.setLineWrap(true);
				ta.setWrapStyleWord(true);
				jPanelScene.add(ta);
				layeredPane.add(jPanelScene);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ta.setVisible(false);
				jPanelScene.remove(ta);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		// check to see if button is pressed
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					// if button is selected change background to gray and
					// change all other button backgrounds to yellow
					// set win to true to verify that the correct answer has
					// been selected, win will be used in GameViewFrame
					// after Submit has been selected

					if (((JButton) e.getSource()).getMnemonic() == 'C') {
						win = true;
						for (int i = 0; i < size; i++) {
							button[i].setBackground(Color.YELLOW);
						}
						((JButton) e.getSource()).setBackground(Color.GRAY);
					}
					// if button is selected change background to gray and
					// change all other button backgrounds to yellow
					// set win to false to verify that the incorrect answer has
					// been selected, win will be used in GameViewFrame
					// after Submit has been selected
					else if ((((JButton) e.getSource()).getMnemonic() == 'I')) {
						for (int i = 0; i < size; i++) {
							button[i].setBackground(Color.YELLOW);
						}
						((JButton) e.getSource()).setBackground(Color.GRAY);
						win = false;
					}
				}
			}
		};
		System.out.println(button[i].getSize());
		button[i].addActionListener(listener);
		jPanelScene.add(button[i]);
		layeredPane.add(jPanelScene, JPANELSCENECONSTANT);
		i++;
		// check to see if you are at the end of the option choices, reset for
		// new game
		if (i >= size) {
			i = 0;
		}
	}

	/**
	 * @param currentProp
	 * @param gameState
	 */
	public void addinformationBox(final Prop currentProp,
			final GameState gameState) {
		try {
			// jPanelScene = Util.panelPosition(currentProp.getLocation(),
			// true,currentProp);
			jPanelScene = Util.panelPostionNew(
					currentProp.getLocation().getX(), currentProp.getLocation()
							.getY(), 2000, 2000, true, currentProp);
		} catch (NullPointerException e) {
			jPanelScene = Util.panelPosition(Location.C, true, currentProp);
		}
		layeredPane.add(jPanelScene, 200);
		// displayNext(currentProp, gameState);
	}

	// Zac ZHANG: Added for ConversationBox
	/**
	 * @param currentProp
	 * @param gameState
	 */
	public void addConversationBubble(final Prop currentProp,
			final GameState gameState) {/*
										 * try { //jLabelScene =
										 * Util.labelPosition
										 * (currentProp.getLocation(),
										 * currentProp); } catch
										 * (NullPointerException e) {
										 * //jLabelScene =
										 * Util.labelPosition(Location.C,
										 * currentProp); }
										 * layeredPane.add(jLabelScene, 200);
										 * 
										 * displayNext(currentProp, gameState);
										 */
	}

	/**
	 * @param name
	 * @param image
	 * @return
	 */
	public JFrame createNewFrame(String name, ImagePanel image) {

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.setSize(1000, 800);
		jFrame.setLayout(new BorderLayout());

		jFrame.pack();
		jFrame.validate();
		jFrame.setVisible(true);
		return jFrame;

	}

	public static void resetLayeredPane() {
		layeredPane.removeAll();
		layeredPane.revalidate();
		layeredPane.repaint();
		layeredPane.setVisible(true);
	}
	
	public void addImage(String characterName, final int xloc, final int yloc, GameState gameState)
	{
		addImage(characterName, xloc, yloc, 250, 400, gameState);
	}

	public void addImage(String characterName, final int xloc, final int yloc, final int width, final int height,
			GameState gameState) {

		List<Character> charList = GameAdapter.getGameAdapterObj().getGame()
				.getCharacters();

		for (int i = 0; i < charList.size(); i++) {

			Character currentChar = charList.get(i);
			String name = currentChar.getName();

			if (name.equals(characterName)) {

				int mood = currentChar.getAttributes().getMood().getValue();
				int charisma = currentChar.getAttributes().getCharisma();
				int skill = currentChar.getAttributes().getSkill();
				int resilience = currentChar.getAttributes().getResilience();

				// refresh the mood so the right image is displayed
				currentChar.getAttributes().RefreshMood();

				String imageStr = currentChar.getAttributes().getMood()
						.getCurrentImage();

				Image image = null;
				try {
					System.err.println(imageStr);
					image = ImageIO.read(getClass().getResource(imageStr));
					System.out.println(getClass().getResource(imageStr));
					System.out.println("Reading image from the file");
				} catch (IOException e) {
					e.printStackTrace();
				}

				ImagePanel ip = new ImagePanel(image);
				ip.setImage(image);
				ip.setLocation(xloc, yloc);
				ip.setOpaque(false);
				ip.setSize(width, height);


				final String toDisplay = " Attributes\r\n Mood:          "
						+ mood + "\r\n Skill:         " + skill
						+ "\r\n Charisma:      " + charisma
						+ "\r\n Resilience:    " + resilience;

				JTextArea ta;
				ta = new JTextArea(toDisplay);
				ta.setEditable(false); // TODO so that it cannot be edited
				ta.setLocation(xloc + 40, yloc + 280);
				ta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				ta.setSize(100, 100);
				ta.setLineWrap(true);
				ta.setWrapStyleWord(true);
				layeredPane.add(ta);
				layeredPane.add(ip);

			}
		}
	}

	/**
	 * @param gameState
	 */
	public void addImage(final GameState gameState) {

		final Prop prop = (Prop) gameState.getGameElementAdapter()
				.getGameElement();

		try {
			System.out.println("Background: "
					+ gameState.getSceneAdapter().getScene().getBackground()
							.getBackground());
			setBackgroundImage(gameState.getSceneAdapter().getScene()
					.getBackground().getBackground());
		} catch (NullPointerException ns) {
			System.out.println("Yes.. backdrop is null!!!");
		}

		/*
		 * try{ jPanelScene = Util.panelPosition(prop.getLocation(), false,
		 * prop);
		 * 
		 * }catch(Exception e){ jPanelScene = Util.panelPosition(Location.C,
		 * false, prop); }
		 */

		jPanelScene = Util.panelPostionNew(prop.getLocation().getX(), prop
				.getLocation().getY(), prop.getSize().getWidth(), prop
				.getSize().getHeight(), false, prop);

		String imageName = ((Prop) gameState.getGameElementAdapter()
				.getGameElement()).getName();
		System.out.println(imageName);
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(imageName));
			System.out.println("Reading image from the file");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon i = new ImageIcon("src/" + imageName);
		System.out.println("Creating image");
		JLabel l = new JLabel(i);
		System.out.println("Creating image2");
		add(l);
		jPanelScene.add(l);
		l.setSize(250, 400);
		// layeredPane.add(jPanelScene);
		// layeredPane.setVisible(true);
		ImagePanel ip = new ImagePanel(image);
		ip.setImage(image);
		ip.setLocation(jPanelScene.getX(), jPanelScene.getY());
		ip.setOpaque(false);
		ip.setSize(250, 400);
		// jFrame.add(l);
		// jPanelScene.add(ip);

		layeredPane.add(ip);
		// layeredPane.add(jPanelScene, JLayeredPane.PALETTE_LAYER);
		// layeredPane.setVisible(true);

		System.out.println("Image done!!");
		// Fetch the non player details.
		Profile profile = new Profile();
		Reward reward = new Reward();
		NonPlayer nonPlayer = new NonPlayer();
		List<Character> charList = GameAdapter.getGameAdapterObj().getGame()
				.getCharacters();
		for (Character character : charList) {
			if (character instanceof NonPlayer) {
				nonPlayer = (NonPlayer) character;

				profile = nonPlayer.getProfile();
				reward = nonPlayer.getRewards();
			}
		}

		String name = nonPlayer.getName();

		String title = profile.getTitle();
		List<String> degrees = profile.getDegrees();
		List<String> trophies = reward.getTrophies();
		List<String> certificates = reward.getCertificates();

		StringBuffer s = new StringBuffer("");
		StringBuffer trophiesStrBuf = new StringBuffer("");
		StringBuffer certificatesStrBuf = new StringBuffer("");

		Iterator it = degrees.iterator();

		while (it.hasNext()) {
			s.append(it.next());
		}

		if (trophies != null) {
			it = trophies.iterator();

			while (it.hasNext()) {
				trophiesStrBuf.append(it.next());
			}
		} else
			trophiesStrBuf.append("NONE");

		if (certificates != null) {
			it = certificates.iterator();

			while (it.hasNext()) {
				certificatesStrBuf.append(it.next());
			}
		} else
			certificatesStrBuf.append("NONE");

		List<String> skills = profile.getSkills();
		StringBuffer s2 = new StringBuffer("");
		Iterator itr = skills.iterator();

		while (itr.hasNext()) {
			s2.append(itr.next());
		}
		int experienceYrs = profile.getYearsOfExperience();
		String level = profile.getLevel();
		// int points = reward.getPoints();
		final String str = "CHARACTER PROFILE";
		final String toDisplay = str + "\n" + "\r\nName: " + name + "\n"
				+ "\r\nTitle : " + title + "\n" + "\r\nTrophies : "
				+ trophiesStrBuf + "\r\nLevel : " + level + "\n"
				+ "\r\nDegrees : " + s + "\n" + "\r\nSkills : " + s2
				+ "\r\nExperience : " + experienceYrs + "\n"
				+ "\r\nCertificates: " + certificatesStrBuf;

		ip.addMouseListener(new MouseListener() {
			private JTextArea ta;

			// private JScrollPane pane;

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("Profile Information is : " + toDisplay);
				jPanelScene = Util.panelPosition(Location.UL, false, prop);
				ta = new JTextArea(toDisplay);
				ta.setEditable(false); // TODO so that it cannot be edited
				ta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				ta.setSize(jPanelScene.getWidth(), jPanelScene.getHeight());
				ta.setLineWrap(true);
				ta.setWrapStyleWord(true);
				jPanelScene.add(ta);
				layeredPane.add(jPanelScene);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				ta.setVisible(false);
				jPanelScene.remove(ta);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

	/**
	 * @param gameState
	 *            Generic method for adding a buttonon the frame Actions depend
	 *            on behavior
	 */
	public void addButtonNew(final GameState gameState) {
		final Prop prop = (Prop) (gameState.getGameElementAdapter()
				.getGameElement());
		// Text to be taken from current element after sean updates
		btn = new JButton(prop.getText().getText());
		btn.setBackground(Util.StringToColor(prop.getColor()
				.getBackgroundColor().getColorName().toString()));

		btn.setFont(new Font(prop.getText().getFont(), Font.PLAIN, (prop
				.getText().getFontSize())));
		Dimension dimension = new Dimension();
		dimension.height = prop.getSize().getHeight();
		dimension.width = prop.getSize().getWidth();
		btn.setPreferredSize(dimension);
		btn.setBorder(new LineBorder(Color.BLACK, 4));

		// jPanelScene = Util.panelPosition(prop.getLocation(), false, prop);
		jPanelScene = Util.panelPostionNew(prop.getLocation().getX(), prop
				.getLocation().getY(), 250, 700, false, prop);
		jPanelScene.add(btn);
		layeredPane.add(jPanelScene, new Integer(0), 100);

		// Action listeners based on behaviors
		final List<Behavior> behaviorList = prop.getBehaviors();
		final String effect = prop.getSoundEffect();

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController.counter = 0;
				GameView.timerValue = 0;
				// NEW
				Profile profile = new Profile();
				Reward reward = new Reward();
				AIFunction AI = new AIFunction();
				NonPlayer nonPlayer = new NonPlayer();

				// gets the character list
				List<Character> charList = GameAdapter.getGameAdapterObj()
						.getGame().getCharacters();

				for (Character character : charList) {
					if (character instanceof NonPlayer) {
						nonPlayer = (NonPlayer) character;

						profile = nonPlayer.getProfile();
						reward = nonPlayer.getRewards();
					}
				}

				// String name = nonPlayer.getName();

				// String title = profile.getTitle();
				// List<String> degrees = profile.getDegrees();
				// List<String> trophies = reward.getTrophies();
				// List<String> certificates = reward.getCertificates();
				// END
				try {
					if (win == true) {
						boolean nemesisWasRight = AI.GenerateAnswer(GameAdapter
								.getGameAdapterObj().getGame().getCharacters()
								.get(0));
						System.out.println("NIM ANSWER: " + nemesisWasRight);
						charList = AI.recalculate(charList, 3, nemesisWasRight,
								win);
						GameAdapter.getGameAdapterObj().getGame()
								.setCharacters(charList);

						ChallengeAdapter.quizFeedback = gameState
								.getCorrectFeedBack();

						ChallengeAdapter.quizTitleChange = gameState
								.getPromotedTitle();

						ChallengeAdapter.quizReward = gameState.getQuizReward();
						System.out.println("Points: "
								+ gameState.getQuizReward());
						win = false;

					} else if (win == false) {

						boolean nemesisWasRight = AI.GenerateAnswer(GameAdapter
								.getGameAdapterObj().getGame().getCharacters()
								.get(0));
						System.out.println("NIM ANSWER: " + nemesisWasRight);
						charList = AI.recalculate(charList, 3, nemesisWasRight,
								win);
						GameAdapter.getGameAdapterObj().getGame()
								.setCharacters(charList);

						ChallengeAdapter.quizFeedback = gameState
								.getInCorrectFeedback();
						ChallengeAdapter.quizReward = 0;

					}
				} catch (Exception e) {

				}

				if (effect != null)
					AudioPlayer.playAudio(GameView.effectsFolderPath + effect);

				for (Behavior behavior : behaviorList) {
					if (behavior.getBehaviorType().equals(
							BehaviorType.REWARD_BEHAVIOR)) {
						String[] action = behavior.getAction().split("\\.");
						System.out.println("ACTION!: " + action[0]);
						if (action[0].equals("AddPoints")) {
							playerPoints = Integer.parseInt(action[1]);
							Reward myReward = GameAdapter.getGameAdapterObj()
									.getGame().getCharacters().get(0)
									.getRewards();
							myReward.setPoints(myReward.getPoints()
									+ playerPoints);
						}
						if (action[0].equals("AddNPCPoints")) {
							npcPoints = GameView.correctValue;
							Reward npcReward = GameAdapter.getGameAdapterObj()
									.getGame().getCharacters().get(1)
									.getRewards();
							npcReward.setPoints(npcReward.getPoints()
									+ npcPoints);
						}
						if (action[0].equals("AddSBPoints")) {
							SandBox SBchallenge = (SandBox) gameState
									.getChallenegeAdapter().getChallenge();

							if (sbWin) {
								playerPoints = SBchallenge.getItem().get(0)
										.getReward();
								ChallengeAdapter.quizFeedback = gameState
										.getCorrectFeedBack();
								System.out.println("NI: "
										+ gameState.getPromotedTitle());
								ChallengeAdapter.quizTitleChange = gameState
										.getPromotedTitle();
							} else if (!sbWin) {
								playerPoints = SBchallenge.getItem().get(0)
										.getReward()
										/ (TriangleFrame.currentAngle - SBchallenge
												.getItem().get(0)
												.getDesiredAngle());
								ChallengeAdapter.quizFeedback = gameState
										.getInCorrectFeedback()
										+ " You created an angle "
										+ (TriangleFrame.currentAngle - SBchallenge
												.getItem().get(0)
												.getDesiredAngle())
										+ "� larger than desired.";
								TriangleFrame.currentAngle = 0;
							}
							Reward myReward = GameAdapter.getGameAdapterObj()
									.getGame().getCharacters().get(0)
									.getRewards();
							myReward.setPoints(myReward.getPoints()
									+ playerPoints);
						} else if (action[0].equals("AddQuizPoints"))
							playerPoints = ChallengeAdapter.quizReward;

						// Kyle : Reset points for multiple questions
						playerPoints = 0;
						npcPoints = 0;
						// GameAdapter.getGameAdapterObj().getGame().getCharacter().get(0).setRewards(myReward);
					}
					if (behavior.getBehaviorType().equals(

					BehaviorType.TRANSITION_BEHAVIOR)) {
						System.out
								.println("Calling Next screen.......................");
						String[] action = behavior.getAction().split("\\.");
						int nextscreen = 0;

						if (action[0].equals("NextScreen")) {

							nextscreen = Integer.parseInt(action[1]);

						} else if (action[0].equals("NextScene")) {
							resetLayeredPane();
							// GameController.screenToEnd(gameState);
							GameController.sceneToEnd(gameState);
						}
						resetLayeredPane();
						displayNextNew(nextscreen, gameState);
					}
					if (behavior.getBehaviorType().equals(
							BehaviorType.END_GAME_BEHAVIOR)) {
						GameAdapter.setGameEnded(true);
						AudioPlayer.stopAudio();
						resetLayeredPane();
						for (int i = 0; i < timerList.size(); i++)// Zac ZHANG:
																	// stop all
																	// timers
						{
							timerList.get(i).stop();
						}
					}

				}

			}
		});
		jPanel.setVisible(true);

	}

	public void addImage(ImageProp prop) 
	{
		URL imageName = getClass().getResource(prop.getImage());
		
		System.out.println(imageName.toString());
		BufferedImage image;
		try {
			image = ImageIO.read(imageName);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		ImagePanel ip = new ImagePanel(image);
		ip.setImage(image);
		ip.setLocation(prop.getLocation().getX(), prop.getLocation().getY());
		ip.setOpaque(false);
		ip.setSize(prop.getSize().getWidth(), prop.getSize().getHeight());

		layeredPane.add(ip);
	}
}
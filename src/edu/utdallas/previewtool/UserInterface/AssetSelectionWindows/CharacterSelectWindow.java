package edu.utdallas.previewtool.UserInterface.AssetSelectionWindows;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.ImageHelper;
import edu.utdallas.old.CharacterAsset;
import edu.utdallas.simsysmain.InputWizard;

/**
 * The Class CharacterSelectWindow.
 */
public class CharacterSelectWindow extends JDialog {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	public static final int WIDTH = 1050, HEIGHT = 700;

	/** The Constant TWENTYNINE. */
	private static final int TWENTYNINE = 29;

	/** The Constant SEVENHUNDRED. */
	private static final int SEVENHUNDRED = 700;

	/** The Constant TEN. */
	private static final int TEN = 10;

	/** The Constant HUNDREDPOINTZERO. */
	protected static final double HUNDREDPOINTZERO = 100.0;

	/** The Constant ONETWENTY. */
	private static final Integer ONETWENTY = 120;

	/** The Constant TWENTY. */
	private static final Integer TWENTY = 20;

	/** The Constant FIFTEEN. */
	protected static final int FIFTEEN = 15;

	/** The Constant THREEHUNDRED. */
	protected static final int THREEHUNDRED = 300;

	/** The Constant FIFTY. */
	protected static final int FIFTY = 50;

	/** The Constant FORTY. */
	private static final int FORTY = 40;

	/** The Constant TWOEIGHTY. */
	private static final int TWOEIGHTY = 280;

	/** The Constant THREETWENTYFIVE. */
	private static final int THREETWENTYFIVE = 325;

	/** The Constant SIXFIFTY. */
	private static final int SIXFIFTY = 650;

	/** The Constant POINTFIVE. */
	private static final double POINTFIVE = 0.5;

	/** The pic. */
	private final JLabel pic = new JLabel();

	/** The w panel. */
	private final JPanel wPanel = new JPanel(new GridLayout(0, 4));
	// final JPanel ePanel = new JPanel(new GridLayout(0, 1, 0, 0));
	/** The e panel. */
	private final JPanel ePanel = new JPanel(new BorderLayout());

	/** The slider. */
	private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 130, 80);

	/** The combo box. */
	private final JComboBox<String> comboBox = new JComboBox<String>();

	/** The selected path. */
	private String selectedPath = "";

	/** The char asset. */
	private CharacterAsset charAsset;

	/**
	 * Instantiates a new character select window.
	 *
	 * @param owner
	 *            the owner
	 */
	public CharacterSelectWindow(final JFrame owner) {
		super(owner, "Character Selection", Dialog.DEFAULT_MODALITY_TYPE);
		setSize(WIDTH, HEIGHT);

		JPanel nPanel = new JPanel();
		for (int i = 1; i <= TWENTYNINE; i++) {
			comboBox.addItem("Character_" + i);
		}
		comboBox.addItem("Hero-Villian");
		nPanel.add(comboBox);

		JScrollPane wPane = new JScrollPane();
		wPane.setPreferredSize(new Dimension(SEVENHUNDRED, SEVENHUNDRED));
		wPane.add(wPanel);
		wPane.setViewportView(wPanel);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				handleChangeCharacter();
			}
		});

		JPanel panel2 = new JPanel(new BorderLayout());
		slider.setMajorTickSpacing(TEN);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		labels.put(TWENTY, new JLabel("Smaller"));
		labels.put(ONETWENTY, new JLabel("Larger"));
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				System.out.println("Slider Value: " + slider.getValue());
				double sValue = slider.getValue() / HUNDREDPOINTZERO;

				try {
					BufferedImage img1 = ImageHelper.getScaledImage(
							ImageIO.read(new File(selectedPath)), sValue);
					System.out.println(selectedPath);
					pic.setIcon(new ImageIcon(img1));
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
		});
		panel2.add(slider, BorderLayout.CENTER);
		JButton place = new JButton("Place");
		place.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				charAsset = new CharacterAsset();
				String imgstrng = selectedPath.substring(selectedPath
						.indexOf((String) comboBox.getSelectedItem()));
				charAsset.setDisplayImage(imgstrng);
				charAsset.setFontFamily("Comic Sans MS");
				charAsset.setFontSize(FIFTEEN);
				charAsset.setHeight(pic.getIcon().getIconHeight());
				charAsset.setWidth(pic.getIcon().getIconWidth());
				charAsset.setX(THREEHUNDRED);
				charAsset.setX2(THREEHUNDRED + charAsset.getWidth());
				charAsset.setY(FIFTY);
				charAsset.setY2(FIFTY + charAsset.getHeight());
				charAsset.setOpacity(1);
				setVisible(false);
				InputWizard.loadCharBackground(imgstrng);
			}
		});
		place.setPreferredSize(new Dimension(TWOEIGHTY, FORTY));
		panel2.add(place, BorderLayout.SOUTH);

		// ePanel.add(pic);
		// ePanel.add(panel2);
		ePanel.add(pic, BorderLayout.CENTER);
		ePanel.add(panel2, BorderLayout.SOUTH);
		ePanel.setPreferredSize(new Dimension(THREETWENTYFIVE, SIXFIFTY));

		add(ePanel, BorderLayout.EAST);
		add(wPane, BorderLayout.WEST);
		add(nPanel, BorderLayout.NORTH);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);

		handleChangeCharacter();
	}

	/**
	 * Handle change character.
	 */
	private void handleChangeCharacter() {
		if (comboBox.getSelectedItem() == null) {
			return;
		}
		final ArrayList<JLabel> jlabels = new ArrayList<JLabel>();
		String item = (String) comboBox.getSelectedItem();

		System.out.println(item);

		File dir = new File("Office, Classroom/Characters/" + item + "/");
		wPanel.removeAll();

		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				continue;
			}
			try {
				BufferedImage image = ImageHelper.getScaledImage(
						ImageIO.read(child), POINTFIVE);

				final JLabel l = new JLabel(new ImageIcon(image));
				l.setName(child.getPath().toString());
				jlabels.add(l);
				wPanel.add(l);
				wPanel.validate();
				wPanel.repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		for (final JLabel l : jlabels) {
			l.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(final MouseEvent e) {
				}

				@Override
				public void mouseEntered(final MouseEvent e) {
				}

				@Override
				public void mouseExited(final MouseEvent e) {
				}

				@Override
				public void mousePressed(final MouseEvent e) {
					for (int i = 0; i < jlabels.size(); i++) {
						jlabels.get(i).setBorder(null);
					}
					l.setBorder(BorderFactory.createLoweredBevelBorder());
					try {
						BufferedImage img1 = ImageHelper.getScaledImage(
								ImageIO.read(new File(l.getName())),
								slider.getValue() / HUNDREDPOINTZERO);
						pic.setIcon(new ImageIcon(img1));
						selectedPath = l.getName();
					} catch (Exception e4) {
						e4.printStackTrace();
					}
				}

				@Override
				public void mouseReleased(final MouseEvent e) {
				}
			});
		}
	}

	/**
	 * Sets the character asset.
	 *
	 * @param characterAsset
	 *            the new character asset
	 */
	public final void setCharacterAsset(final CharacterAsset characterAsset) {
		charAsset = characterAsset;
	}

	/**
	 * Gets the new character asset.
	 *
	 * @return the new character asset
	 */
	public final CharacterAsset getNewCharacterAsset() {
		return charAsset;
	}

	/**
	 * Sets the character choices.
	 *
	 * @param charNames
	 *            the new character choices
	 */
	public final void setCharacterChoices(final ArrayList<String> charNames) {
		comboBox.removeAllItems();
		for (String charName : charNames) {
			comboBox.addItem(charName);
		}
		handleChangeCharacter();
	}
}

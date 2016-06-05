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

import edu.utdallas.gamegeneratorcollection.ComponentCreation.ImageHelper;
import edu.utdallas.simsysmain.InputWizard;

/**
 * The Class BackgroundSelectWindow.
 */
public class BackgroundSelectWindow extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	public static final int WIDTH = 1050, HEIGHT = 500;

	/** The pic. */
	private final JLabel pic = new JLabel();

	/** The Constant SUBSTRING. */
	public static final int SUBSTRING = 10;
	/** The w panel. */
	private final JPanel wPanel = new JPanel(new GridLayout(0, 2));
	// final JPanel ePanel = new JPanel(new GridLayout(0, 1, 0, 0));
	/** The e panel. */
	private final JPanel ePanel = new JPanel(new BorderLayout());
	// final JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 130, 80);
	/** The combo box. */
	private final JComboBox<String> comboBox = new JComboBox<String>();

	/** The selected path. */
	private String selectedPath = "";

	/** The back path string. */
	private String backPathString;

	/** Magic Numbers. */
	public static final int SEVENHUNDRED = 700;

	/** The Constant TWOEIGHTY. */
	public static final int TWOEIGHTY = 280;

	/** The Constant THREETWENTYFIVE. */
	public static final int THREETWENTYFIVE = 325;

	/** The Constant SIXFIFTY. */
	public static final int SIXFIFTY = 650;

	/** The Constant FOURTY. */
	public static final int FOURTY = 40;

	/** The Constant _FOURTHREE. */
	public static final double FOURTHREE = 0.43;

	/**
	 * Instantiates a new background select window.
	 *
	 * @param owner
	 *            the owner
	 */
	public BackgroundSelectWindow(final JFrame owner) {
		super(owner, "Background Selection", Dialog.DEFAULT_MODALITY_TYPE);
		setSize(WIDTH, HEIGHT);

		JPanel nPanel = new JPanel();
		// for(int i = 1; i <= 29; i++)
		// {
		// comboBox.addItem("Character_" + i);
		// }
		// comboBox.addItem("Hero-Villian");
		nPanel.add(comboBox);

		JScrollPane wPane = new JScrollPane();
		wPane.setPreferredSize(new Dimension(SEVENHUNDRED, SEVENHUNDRED));
		wPane.add(wPanel);
		wPane.setViewportView(wPanel);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				handleChangeBackgroundFolder();
			}
		});

		JPanel panel2 = new JPanel(new BorderLayout());
		JButton place = new JButton("Place");
		place.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				backPathString = selectedPath.substring(selectedPath
						.indexOf((String) comboBox.getSelectedItem()));
				setVisible(false);
				InputWizard.loadBackground(backPathString.substring(SUBSTRING));
			}
		});
		place.setPreferredSize(new Dimension(TWOEIGHTY, FOURTY));
		panel2.add(place, BorderLayout.SOUTH);

		ePanel.add(pic, BorderLayout.CENTER);
		ePanel.add(panel2, BorderLayout.SOUTH);
		ePanel.setPreferredSize(new Dimension(THREETWENTYFIVE, SIXFIFTY));

		add(ePanel, BorderLayout.EAST);
		add(wPane, BorderLayout.WEST);
		add(nPanel, BorderLayout.NORTH);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);
		setResizable(false);

		handleChangeBackgroundFolder();
	}

	/**
	 * Handle change background folder.
	 */
	private void handleChangeBackgroundFolder() {
		if (comboBox.getSelectedItem() == null) {
			return;
		}
		final ArrayList<JLabel> jlabels = new ArrayList<JLabel>();
		String item = (String) comboBox.getSelectedItem();

		File dir = new File("Office, Classroom/" + item + "/");
		wPanel.removeAll();
		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				continue;
			}
			try {
				// System.out.println(child);
				BufferedImage image = ImageHelper.getScaledImage(
						ImageIO.read(child), FOURTHREE);

				final JLabel l = new JLabel(new ImageIcon(image));
				l.setName(child.getPath().toString());
				jlabels.add(l);
				wPanel.add(l);
				wPanel.validate();
				wPanel.repaint();
			} catch (NullPointerException ex) {
				System.out.println(item + " has an invalid file in it!!");
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
								ImageIO.read(new File(l.getName())), FOURTHREE);
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
	 * Sets the background path string.
	 *
	 * @param backgroundString
	 *            the new background path string
	 */
	public final void setBackgroundPathString(final String backgroundString) {
		backPathString = backgroundString;
	}

	/**
	 * Gets the new background path.
	 *
	 * @return the new background path
	 */
	public final String getNewBackgroundPath() {
		return backPathString;
	}

	/**
	 * Sets the background folder path.
	 *
	 * @param folderPath
	 *            the new background folder path
	 */
	public final void setBackgroundFolderPath(final String folderPath) {
		comboBox.removeAllItems();
		/*
		 * for(String charName : charNames) { comboBox.addItem(charName); }
		 */
		comboBox.addItem(folderPath);
		handleChangeBackgroundFolder();
	}
}

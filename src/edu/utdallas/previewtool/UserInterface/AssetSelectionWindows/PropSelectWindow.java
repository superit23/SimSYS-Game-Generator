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
import edu.utdallas.old.ImageAsset;

/**
 * The Class PropSelectWindow.
 */
public class PropSelectWindow extends JDialog {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	public static final int WIDTH = 1150, HEIGHT = 700;

	/** The Constant SEVENHUNDRED. */
	private static final int SEVENHUNDRED = 700;

	/** The Constant TEN. */
	private static final int TEN = 10;

	/** The Constant TWENTY. */
	private static final int TWENTY = 20;

	/** The Constant ONETWENTY. */
	private static final int ONETWENTY = 120;

	/** The Constant HUNDREDPOINTZERO. */
	protected static final double HUNDREDPOINTZERO = 100.0;

	/** The Constant FIFTEEN. */
	protected static final int FIFTEEN = 15;

	/** The Constant THREEHUNDRED. */
	protected static final int THREEHUNDRED = 300;

	/** The Constant FIFTY. */
	protected static final int FIFTY = 50;

	/** The Constant TWOEIGHTY. */
	private static final int TWOEIGHTY = 0;

	/** The Constant FORTY. */
	private static final int FORTY = 0;

	/** The Constant THREETWENTYFIVE. */
	private static final int THREETWENTYFIVE = 325;

	/** The Constant HUNDRED. */
	private static final int HUNDRED = 100;

	/** The Constant SIXFIFTY. */
	private static final int SIXFIFTY = 650;

	/** The Constant ONEFIFTYF. */
	private static final float ONEFIFTYF = 150f;

	/** The image base dir. */
	private String imageBaseDir = "Office, Classroom\\";

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

	/** The img asset. */
	private ImageAsset imgAsset;

	/**
	 * Instantiates a new prop select window.
	 *
	 * @param owner
	 *            the owner
	 */
	public PropSelectWindow(final JFrame owner) {

		super(owner, "Prop Selection", Dialog.DEFAULT_MODALITY_TYPE);
		setSize(WIDTH, HEIGHT);
		// setResizable(false);

		JPanel nPanel = new JPanel();

		File dir = new File("Office, Classroom/Props/SetDecoration/");
		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				comboBox.addItem(child.getName());
			}
		}
		nPanel.add(comboBox);

		JScrollPane wPane = new JScrollPane();
		wPane.setPreferredSize(new Dimension(SEVENHUNDRED, SEVENHUNDRED));
		wPane.add(wPanel);
		wPane.setViewportView(wPanel);

		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				handleChangePropCatagory();
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
				imgAsset = new ImageAsset();
				String imgstrng = selectedPath.substring(selectedPath
						.indexOf(imageBaseDir) + imageBaseDir.length());
				imgAsset.setDisplayImage(imgstrng);
				imgAsset.setFontFamily("Comic Sans MS");
				imgAsset.setFontSize(FIFTEEN);
				imgAsset.setHeight(pic.getIcon().getIconHeight());
				imgAsset.setWidth(pic.getIcon().getIconWidth());
				imgAsset.setX(THREEHUNDRED);
				imgAsset.setX2(THREEHUNDRED + imgAsset.getWidth());
				imgAsset.setY(FIFTY);
				imgAsset.setY2(FIFTY + imgAsset.getHeight());
				imgAsset.setOpacity(1);
				setVisible(false);
			}
		});
		place.setPreferredSize(new Dimension(TWOEIGHTY, FORTY));
		panel2.add(place, BorderLayout.SOUTH);

		ePanel.add(pic, BorderLayout.CENTER);
		ePanel.add(panel2, BorderLayout.SOUTH);
		ePanel.setPreferredSize(new Dimension(THREETWENTYFIVE + HUNDRED,
				SIXFIFTY));

		add(ePanel, BorderLayout.EAST);
		add(wPane, BorderLayout.WEST);
		add(nPanel, BorderLayout.NORTH);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);

		handleChangePropCatagory();

	}

	/**
	 * Handle change prop catagory.
	 */
	private void handleChangePropCatagory() {
		if (comboBox.getSelectedItem() == null) {
			return;
		}
		final ArrayList<JLabel> jlabels = new ArrayList<JLabel>();
		String folder = (String) comboBox.getSelectedItem();

		System.out.println(folder);

		File dir = new File("Office, Classroom/Props/SetDecoration/" + folder
				+ "/");
		wPanel.removeAll();

		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				continue;
			}
			try {
				BufferedImage image = ImageIO.read(child);
				BufferedImage scaledImage = ImageHelper.getScaledImage(image,
						(ONEFIFTYF) / (image.getWidth()));

				final JLabel l = new JLabel(new ImageIcon(scaledImage));
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
					handleGridClick(l, jlabels);
				}

				@Override
				public void mouseReleased(final MouseEvent e) {
				}
			});
		}
		handleGridClick(jlabels.get(0), jlabels);
	}

	/**
	 * Handle grid click.
	 *
	 * @param label
	 *            the label
	 * @param jlabels
	 *            the jlabels
	 */
	private void handleGridClick(final JLabel label,
			final ArrayList<JLabel> jlabels) {
		for (int i = 0; i < jlabels.size(); i++) {
			jlabels.get(i).setBorder(null);
		}
		label.setBorder(BorderFactory.createLoweredBevelBorder());
		try {
			BufferedImage img1 = ImageHelper.getScaledImage(
					ImageIO.read(new File(label.getName())), slider.getValue()
							/ HUNDREDPOINTZERO);
			pic.setIcon(new ImageIcon(img1));
			selectedPath = label.getName();
		} catch (Exception e4) {
			e4.printStackTrace();
		}
	}

	/**
	 * Sets the image asset.
	 *
	 * @param imageAsset
	 *            the new image asset
	 */
	public final void setImageAsset(final ImageAsset imageAsset) {
		imgAsset = imageAsset;
	}

	/**
	 * Gets the new image asset.
	 *
	 * @return the new image asset
	 */
	public final ImageAsset getNewImageAsset() {
		return imgAsset;
	}

	/**
	 * Sets the image choices.
	 *
	 * @param imagefolders
	 *            the new image choices
	 */
	public final void setImageChoices(final ArrayList<String> imagefolders) {
		comboBox.removeAllItems();
		for (String imageFolder : imagefolders) {
			comboBox.addItem(imageFolder);
		}
		handleChangePropCatagory();
	}
}

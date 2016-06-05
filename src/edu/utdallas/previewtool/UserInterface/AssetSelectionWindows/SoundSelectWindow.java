package edu.utdallas.previewtool.UserInterface.AssetSelectionWindows;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.AudioPlayer;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ImageHelper;
import edu.utdallas.simsysmain.InputWizard;

/**
 * The Class SoundSelectWindow.
 */
public class SoundSelectWindow extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	public static final int WIDTH = 450, HEIGHT = 300;
	// Windows
	/** The Constant soundFolder. */
	public static final String SOUNDFOLDER = "AudioAssetRepository\\";

	/** The Constant effectsFolder. */
	public static final String EFFECTSFOLDER = "effects\\sound "
			+ "effects from WavSource\\";

	/** The Constant musicFolder. */
	public static final String MUSICFOLDER = "music\\";
	// MAC
	// /** The Constant soundFolder. */
	// public static final String SOUNDFOLDER = "AudioAssetRepository//";
	//
	// /** The Constant effectsFolder. */
	// public static final String EFFECTSFOLDER = "effects//sound "
	// + "effects from WavSource//";
	//
	// /** The Constant musicFolder. */
	// public static final String MUSICFOLDER = "music//";

	/** The sound folder string. */
	private String soundFolderString;

	/** The sound path string. */
	private String soundPathString;

	/** The list. */
	private JList<String> list;

	/** The select. */
	private ListSelectionModel select;

	/** The Constant HUNDRED. */
	public static final int HUNDRED = 100;

	/** The Constant THIRTY. */
	public static final int THIRTY = 30;

	/** The Constant pointFIVE. */
	public static final double POINTFIVE = 0.5;

	/**
	 * Instantiates a new sound select window.
	 *
	 * @param owner
	 *            the owner
	 */
	public SoundSelectWindow(final JFrame owner) {
		super(owner, "Sound Selection", Dialog.DEFAULT_MODALITY_TYPE);
		setSize(WIDTH, HEIGHT);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);

		list = new JList<String>();

		addWindowListener(new WindowListener() {
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
				AudioPlayer.stopAudio();
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

		JScrollPane listPane = new JScrollPane(list);
		add(listPane, BorderLayout.CENTER);

		JPanel flow = new JPanel(new FlowLayout());

		JButton preview = new JButton("Preview");
		try {
			BufferedImage img = ImageHelper.getScaledImage(
					ImageIO.read(new File("Office, Classroom/Asst "
							+ "Bitstrips and Composite images/play.png")),
					POINTFIVE);
			preview.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			e.printStackTrace();
		}
		preview.setPreferredSize(new Dimension(HUNDRED, THIRTY));
		preview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				// TODO Auto-generated method stub
				int selectedIndex = list.getSelectedIndex();
				if (selectedIndex == -1) {
					return;
				}
				String selected = list.getSelectedValue().toString();
				AudioPlayer.playAudio(SOUNDFOLDER + soundFolderString
						+ selected);
			}
		});
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				AudioPlayer.stopAudio();
			}
		});
		try {
			BufferedImage img = ImageHelper.getScaledImage(
					ImageIO.read(new File("Office, Classroom/Asst "
							+ "Bitstrips and Composite images/stop.png")), 1.0);
			stop.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			e.printStackTrace();
		}
		stop.setPreferredSize(new Dimension(HUNDRED, THIRTY));
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				soundPathString = SOUNDFOLDER + soundFolderString
						+ list.getSelectedValue().toString();
				AudioPlayer.stopAudio();
				setVisible(false);
				InputWizard.loadBackgroundSound(list.getSelectedValue()
						.toString());
			}
		});
		add.setPreferredSize(new Dimension(HUNDRED, THIRTY));

		flow.add(preview);
		flow.add(stop);
		flow.add(add);
		add(flow, BorderLayout.SOUTH);

		soundFolderString = EFFECTSFOLDER;
		handleChangeSoundFolder();
	}

	/**
	 * Handle change sound folder.
	 */
	private void handleChangeSoundFolder() {
		ArrayList<String> soundFiles = new ArrayList<String>();
		File dir = new File(SOUNDFOLDER + soundFolderString);

		for (File child : dir.listFiles()) {
			if (child.isDirectory()) {
				continue;
			}
			soundFiles.add(child.getName());
		}

		String[] soundFilesArray = new String[soundFiles.size()];
		for (int i = 0; i < soundFiles.size(); i++) {
			soundFilesArray[i] = soundFiles.get(i);
		}
		list.setListData(soundFilesArray);
		select = list.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	/**
	 * Sets the sound path string.
	 *
	 * @param soundString
	 *            the new sound path string
	 */
	public final void setSoundPathString(final String soundString) {
		soundPathString = soundString;
	}

	/**
	 * Gets the new sound path.
	 *
	 * @return the new sound path
	 */
	public final String getNewSoundPath() {
		return soundPathString;
	}

	/**
	 * Sets the sound folder path.
	 *
	 * @param folderPath
	 *            the new sound folder path
	 */
	public final void setSoundFolderPath(final String folderPath) {
		soundFolderString = folderPath;
		handleChangeSoundFolder();
	}
}

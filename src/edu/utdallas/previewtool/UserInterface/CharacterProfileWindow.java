package edu.utdallas.previewtool.UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import simsys.schema.components.Character;

/**
 * The Class CharacterProfileWindow.
 */
public class CharacterProfileWindow extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	public static final int WIDTH = 640, HEIGHT = 480;

	/** The font. */
	private final Font font;

	/** The element grid. */
	private JPanel tagGrid, elementGrid;

	/** The Constant FIVE. */
	public static final int FIVE = 5;

	/** The Constant FIFTEEN. */
	public static final int FIFTEEN = 15;

	/** The Constant FORTY. */
	public static final int FORTY = 40;

	/** The Constant FOURHUNDRED. */
	public static final int FOURHUNDRED = 400;

	/** The bottom of label. */
	private Border bottomOfLabel = BorderFactory.createMatteBorder(0, 0, FIVE,
			0, Color.black);

	/**
	 * Instantiates a new character profile window.
	 *
	 * @param owner
	 *            the owner
	 * @param ch
	 *            the ch
	 */
	public CharacterProfileWindow(final JFrame owner, final Character ch) {
		super(owner, ch.getName() + "'s Profile", Dialog.DEFAULT_MODALITY_TYPE);
		setSize(WIDTH, HEIGHT);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);
		setBackground(Color.WHITE);
		font = new Font("Comic Sans MS", Font.BOLD, FIFTEEN);

		tagGrid = new JPanel(new GridLayout(0, 1));
		tagGrid.setBackground(Color.WHITE);
		elementGrid = new JPanel(new GridLayout(0, 1));
		elementGrid.setBackground(Color.WHITE);
		JPanel profilePanel = new JPanel(new BorderLayout());
		((BorderLayout) profilePanel.getLayout()).setHgap(2);

		// Name
		JLabel nameLabel = addProfileLabel("Name", ch.getName(), true);
		nameLabel.setBorder(bottomOfLabel);

		// Hiding Profile Photo temporarily
		/*
		 * JLabel profilePicture = addProfilePhoto("Resume Photo",
		 * ch.getProfile().getProfilePhoto(), true);
		 * profilePicture.setBorder(bottomOfLabel);
		 */
		// Commented out the fields not in Profile Class
		/*
		 * //Attendance JLabel attendanceLabel = addProfileLabel("Attendance",
		 * ch.getProfile().getAttendance(), true);
		 * attendanceLabel.setBorder(bottomOfLabel);
		 * 
		 * //Availability JLabel availabilityLabel =
		 * addProfileLabel("Availability", ch.getProfile().getAvailability(),
		 * true); availabilityLabel.setBorder(bottomOfLabel);
		 * 
		 * //Communication JLabel communicationLabel =
		 * addProfileLabel("Communication", ch.getProfile().getCommunication(),
		 * true); communicationLabel.setBorder(bottomOfLabel);
		 * 
		 * //Teamwork JLabel teamworkLabel = addProfileLabel("Teamwork",
		 * ch.getProfile().getTeamwork(), true);
		 * teamworkLabel.setBorder(bottomOfLabel);
		 */

		// Title
		JLabel titleLabel = addProfileLabel("Title",
				ch.getProfile().getTitle(), true);
		titleLabel.setBorder(bottomOfLabel);

		// Experience
		JLabel experienceLabel = addProfileLabel("Experience",
				Integer.toString(ch.getProfile().getYearsOfExperience()), true);
		experienceLabel.setBorder(bottomOfLabel);

		// Skills
		if (ch.getProfile().getSkills().size() > 0) {
			JLabel skillsLabel;
			String skills = "";
			for (int i = 0; i < ch.getProfile().getSkills().size(); i++) {
				skills += ch.getProfile().getSkills().get(i);
				if (i + 1 != ch.getProfile().getSkills().size()) {
					skills += ", ";
				}

			}
			skillsLabel = addProfileLabel("Skills", skills.toString(), true);
			skillsLabel.setBorder(bottomOfLabel);
		}
		// Level

		JLabel levelLabel;
		levelLabel = addProfileLabel("Gender", ch.getProfile().getLevel(), true);
		levelLabel.setBorder(bottomOfLabel);

		// Degrees
		if (ch.getProfile().getDegrees().size() > 0) {
			JLabel degreesLabel;
			String degrees = "";
			for (int i = 0; i < ch.getProfile().getDegrees().size(); i++) {
				degrees += ch.getProfile().getDegrees().get(i);
				if (i + 1 != ch.getProfile().getDegrees().size()) {
					degrees += ", ";
				}
			}
			degreesLabel = addProfileLabel("Degrees", degrees.toString(), true);
			degreesLabel.setBorder(bottomOfLabel);
		}

		profilePanel.add(tagGrid, BorderLayout.WEST);
		profilePanel.add(elementGrid, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(profilePanel);
		add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * @param title
	 *            the title
	 * @param profilePhoto
	 *            the profilePhoto
	 * @param isLastEntry
	 *            the is last entry
	 * @return j label
	 */
	@SuppressWarnings("unused")
	private JLabel addProfilePhoto(final String title,
			final String profilePhoto, final boolean isLastEntry) {
		JLabel label = new JLabel(title);
		label.setFont(font);
		ImageIcon icon = new ImageIcon(profilePhoto);
		JLabel label1 = new JLabel(icon);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		if (isLastEntry) {
			label1.setBorder(bottomOfLabel);
		}
		((GridLayout) tagGrid.getLayout()).setRows(((GridLayout) tagGrid
				.getLayout()).getRows() + 1);
		((GridLayout) elementGrid.getLayout())
				.setRows(((GridLayout) elementGrid.getLayout()).getRows() + 1);
		tagGrid.add(label);
		elementGrid.add(label1);
		revalidate();
		return label;
	}

	/**
	 * Adds the profile label.
	 *
	 * @param title
	 *            the title
	 * @param text
	 *            the text
	 * @param isLastEntry
	 *            the is last entry
	 * @return the j label
	 */
	private JLabel addProfileLabel(final String title, final String text,
			final boolean isLastEntry) {
		JLabel label = new JLabel(title);
		label.setFont(font);
		JTextField experienceField = new JTextField(text);
		experienceField.setEditable(false);
		experienceField.setFont(font);
		if (isLastEntry) {
			experienceField.setBorder(bottomOfLabel);
		}
		((GridLayout) tagGrid.getLayout()).setRows(((GridLayout) tagGrid
				.getLayout()).getRows() + 1);
		((GridLayout) elementGrid.getLayout())
				.setRows(((GridLayout) elementGrid.getLayout()).getRows() + 1);
		tagGrid.add(label);
		elementGrid.add(experienceField);
		// tagGrid.setPreferredSize(new Dimension(3, 3));
		elementGrid.setPreferredSize(new Dimension(FORTY, FOURHUNDRED));
		revalidate();
		return label;
	}
}

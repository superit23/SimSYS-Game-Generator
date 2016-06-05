package edu.utdallas.gamePlayEngine.view;

import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.model.Size;
import edu.utdallas.gamePlayEngine.model.Util;
import edu.utdallas.gamePlayEngine.model.adapter.ChallengeAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.Animator.Direction;
import org.jdesktop.animation.timing.Animator.RepeatBehavior;

import simsys.schema.components.BehaviorType;
import simsys.schema.components.Prop;
import simsys.schema.components.Reward;

public class RoundedPanel extends JPanel implements TimingTarget {
	/** Stroke size. it is recommended to set it to 1 for better view */
	protected int strokeSize = 1;
	/** Color of shadow */
	protected Color shadowColor = Color.black;
	/** Sets if it drops shadow */
	protected boolean shady = true;
	/** Sets if it has an High Quality view */
	protected boolean highQuality = true;
	/** Double values for Horizontal and Vertical radius of corner arcs */
	protected Dimension arcs = new Dimension(20, 20);
	/** Distance between shadow border and opaque panel border */
	protected int shadowGap = 5;
	/** The offset of shadow. */
	protected int shadowOffset = 4;
	/** The transparency value of shadow. ( 0 - 255) */
	protected int shadowAlpha = 150;

	private String stringTextToDisplay;
	private Prop prop = null;

	float alpha = 1.0f; // current opacity of button
	Animator animator; // for later start/stop actions
	BufferedImage buttonImage = null;
	String toDisplay = "";
	String image = "";
	Image image1;
	boolean hasImage = false;
	int textHeight;
	int pointsText;
	boolean rewardDrawn;

	public RoundedPanel(Prop prop) {
		super();

		this.prop = prop;
		setOpaque(false);
		// Replace speed Type here

		int timerValue = 1000000000; // TO DO .. fetch this appropriate place
		if (prop.getAnimationEffect() != null
				&& prop.getAnimationEffect().getSpeed() != null) {
			timerValue = GameAdapter.speedMap.get(prop.getAnimationEffect()
					.getSpeed());
			/*
			 * if(prop.getAnimationEffect().getSpeed().equals(Speed.FAST))
			 * timerValue=1000; else
			 * if(prop.getAnimationEffect().getSpeed().equals(Speed.MEDIUM))
			 * timerValue=3000; else
			 * if(prop.getAnimationEffect().getSpeed().equals(Speed.SLOW))
			 * timerValue=7000;
			 */
		}

		animator = new Animator(timerValue, 1, RepeatBehavior.REVERSE, this);
		animator.setStartFraction(1.0f);
		animator.setStartDirection(Direction.BACKWARD);

		if (prop.getName() != null && prop.getName().equals("Quiz"))
			animator.setStartDirection(Direction.FORWARD);

		animator.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = 200;
		int height = 100;
		int shadowGap = this.shadowGap;
		Color shadowColorA = new Color(shadowColor.getRed(),
				shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);

		Graphics2D graphics = (Graphics2D) g;
		// Kyle: Dimension d will be the dimensions of the image
		Dimension d = new Dimension();
		// Try to save the image location into a string
		try {
			image = this.prop.getText().getImage();
		} catch (Exception e) {
		}
		;
		// Check if the image does not exist
		if (image == null || image.isEmpty()) {
			// Do Nothing
			hasImage = false;
		}
		// Image exists!
		else {
			image1 = new ImageIcon(image).getImage();

			d.width = image1.getWidth(null);
			d.height = image1.getHeight(null);
			setPreferredSize(d);

			hasImage = true;
			/*
			 * graphics.setStroke(new BasicStroke(10)); // This draws the
			 * outline of the box (the black shadow) graphics.drawRoundRect(0,
			 * 9, (int)d.width + 15, d.height + 53, arcs.width, arcs.height);
			 * graphics.setColor(Util.StringToColor("YELLOW"));
			 * graphics.fillRoundRect(5, 10, (int)d.width + 10, (int)d.height +
			 * 50, arcs.width, arcs.height); graphics.drawImage(image1, 10, 15,
			 * null); graphics.setColor(Color.BLACK);
			 */
		}
		// Sets antialiasing if HQ.
		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Draws shadow borders if any.
		if (!shady) {
			shadowGap = 1;
		}

		AlphaComposite newComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, alpha);
		graphics.setComposite(newComposite);
		graphics.setStroke(new BasicStroke(10));
		graphics.setColor(Color.BLACK);
		// System.out.println("Getting size!!!");
		if (!hasImage) {

			Dimension dim = new Dimension();
			try {
				dim.height = prop.getSize().getHeight();
				dim.width = prop.getSize().getWidth();

			} catch (Exception e) {
				dim = Util.panelDimension(Size.MEDIUM);
			}

			try {
				toDisplay = this.prop.getText().getText();

				try {
					if (prop.getBehaviors().get(0).getBehaviorType()
							.equals(BehaviorType.REWARD_BEHAVIOR)
							&& prop.getBehaviors().get(0).getAction()
									.equals("GetNPCPoints")) {
						Reward myReward = GameAdapter.getGameAdapterObj()
								.getGame().getCharacters().get(1).getRewards();

						// System.out.println("POINTS: " +
						// GameAdapter.getGameAdapterObj().getGame().getCharacter().get(0).getRewards().getPoints());
						// fetch rewards TO DO---
						toDisplay = toDisplay + " " + myReward.getPoints()
								+ ". ";

						if (ChallengeAdapter.quizReward != 0) {
							toDisplay = "Nimesis's title is "
									+ ChallengeAdapter.quizTitleChange + "."
									+ toDisplay;
							pointsText = getTextHeight(graphics, 3, 3,
									(int) dim.getWidth() - 5,
									(int) dim.getHeight() - 5, 1.5f, toDisplay);

							// System.out.println("Point Text Height returned was: "
							// + pointsText);
							graphics.drawRoundRect(3, 3,
									(int) dim.getWidth() - 7,
									(textHeight), arcs.width, arcs.height);
							try {

								graphics.setColor(Util.StringToColor(this.prop
										.getColor().getBackgroundColor()
										.getColorName().toString()));
								// System.out.println("the color of the information box is: "
								// +
								// this.prop.getColor().getBackgroundColor().getColorName().toString());

							} catch (Exception e) {
								graphics.setColor(Util.StringToColor("YELLOW"));
							}
							graphics.fillRoundRect(3, 3,
									(int) dim.getWidth() - 5,
									(textHeight), arcs.width, arcs.height);
							rewardDrawn = true;
						}

					} else if (prop.getBehaviors().get(0).getBehaviorType()
							.equals(BehaviorType.REWARD_BEHAVIOR)
							&& prop.getBehaviors().get(0).getAction()
									.equals("GetPoints")) {
						Reward myReward = GameAdapter.getGameAdapterObj()
								.getGame().getCharacters().get(0).getRewards();

						// System.out.println("POINTS: " +
						// GameAdapter.getGameAdapterObj().getGame().getCharacter().get(0).getRewards().getPoints());
						// fetch rewards TO DO---
						toDisplay = toDisplay + " " + myReward.getPoints()
								+ ". ";
						System.out.println(ChallengeAdapter.quizReward);
						if (ChallengeAdapter.quizReward != 0) {
							System.out.println("NUM2");
							if (GameViewFrame.sbWin)
								System.out.println("NUM3");
							toDisplay = "Your Title is "
									+ ChallengeAdapter.quizTitleChange + "."
									+ toDisplay;
							pointsText = getTextHeight(graphics, 3, 3,
									(int) dim.getWidth() - 5,
									(int) dim.getHeight() - 5, 1.5f, toDisplay);

							// System.out.println("Point Text Height returned was: "
							// + pointsText);
							graphics.drawRoundRect(3, 3,
									(int) dim.getWidth() - 7,
									(textHeight), arcs.width, arcs.height);
							try {

								graphics.setColor(Util.StringToColor(this.prop
										.getColor().getBackgroundColor()
										.getColorName().toString()));
								// System.out.println("the color of the information box is: "
								// +
								// this.prop.getColor().getBackgroundColor().getColorName().toString());

							} catch (Exception e) {
								graphics.setColor(Util.StringToColor("YELLOW"));
							}
							graphics.fillRoundRect(3, 3,
									(int) dim.getWidth() - 5,
									(textHeight), arcs.width, arcs.height);
							rewardDrawn = true;
						}

					}
				} catch (Exception e) {

				}

				// This method draws the black outline around our plain
				// text bubble without an image
				if (!rewardDrawn)
					graphics.drawRoundRect(3, 3, (int) dim.getWidth() - 7,
							(textHeight), arcs.width, arcs.height);

				// Sets strokes to default, is better.
				graphics.setStroke(new BasicStroke());
				try {

					graphics.setColor(Util.StringToColor(this.prop.getColor()
							.getBackgroundColor().getColorName().toString()));
					// System.out.println("the color of the information box is: "
					// +
					// this.prop.getColor().getBackgroundColor().getColorName().toString());

				} catch (Exception e) {
					graphics.setColor(Util.StringToColor("YELLOW"));
				}
				if (!rewardDrawn)
					graphics.fillRoundRect(3, 3, (int) dim.getWidth() - 5,
							(textHeight) + 2, arcs.width, arcs.height);

				try {
					// Zac ZHANG: Modify to get font from XML files
					graphics.setFont(new Font(prop.getText().getFont(),
							Font.PLAIN, this.prop.getText().getFontSize()));
				} catch (Exception e) {
					graphics.setFont(new Font("Serif", Font.PLAIN, Integer
							.parseInt("10")));
				}
				graphics.setColor(Color.BLACK);

				// System.out.println("This is the text: " + toDisplay);
				textHeight = getTextHeight(graphics, 3, 3,
						(int) dim.getWidth() - 5, (int) dim.getHeight() - 5,
						1.5f, toDisplay);

				// System.out.println("Text Height returned was: " +
				// textHeight);
				drawStringRect(graphics, 3, 3, (int) dim.getWidth() - 5,
						(int) dim.getHeight() - 5, 1.5f, toDisplay);
			} catch (Exception e) {
				drawStringRect(graphics, 3, 3, 200, 100 - 5, 1.5f, " ");

				graphics.drawRoundRect(3, 3, (int) dim.getWidth() - 6,
						(int) dim.getHeight() - 5, arcs.width, arcs.height);
				try {

					graphics.setColor(Util.StringToColor(this.prop.getColor()
							.getBackgroundColor().getColorName().toString()));
					// System.out.println("the color of the information box is: "
					// +
					// this.prop.getColor().getBackgroundColor().getColorName().toString());

				} catch (Exception e1) {
					graphics.setColor(Util.StringToColor("YELLOW"));
				}
				graphics.fillRoundRect(3, 3, (int) dim.getWidth() - 7,
						(int) dim.getHeight() - 5, arcs.width, arcs.height);
			}
		} else if (hasImage) {
			textHeight = 0;
			// First we are going to draw the text to get a y value
			try {
				toDisplay = this.prop.getText().getText();
				// System.out.println("This is the text: " + toDisplay);
				textHeight = getTextHeight(graphics, 3, 3, d.width - 5,
						d.height - 5, 1.5f, toDisplay);
				// System.out.println("Text Height returned was: " +
				// textHeight);

			} catch (Exception e) {

			}
			// Draw the Black outline with the width of the image (plus buffer)
			// and
			// height of the image + the text in the bubble + buffer
			graphics.drawRoundRect(3, 3, d.width + 3, textHeight
					+ d.height + 19, arcs.width, arcs.height);
			// Sets strokes to default, is better.
			graphics.setStroke(new BasicStroke());
			try {

				graphics.setColor(Util.StringToColor(this.prop.getColor()
						.getBackgroundColor().getColorName().toString()));
				// System.out.println("the color of the information box is: " +
				// this.prop.getColor().getBackgroundColor().getColorName().toString());

			} catch (Exception e) {
				graphics.setColor(Util.StringToColor("YELLOW"));
			}
			// Draw the information bubble with the width of the image and the
			// height of the image
			// plus the height of the text plus a buffer value
			graphics.fillRoundRect(3, 3, d.width + 6, d.height
					+ textHeight + 20, arcs.width, arcs.height);
			try {
				// Zac ZHANG: Modify to get font from XML files
				graphics.setFont(new Font(prop.getText().getFont(), Font.PLAIN,
						this.prop.getText().getFontSize()));
			} catch (Exception e) {
				graphics.setFont(new Font("Serif", Font.PLAIN, Integer
						.parseInt("10")));
			}
			graphics.setColor(Color.BLACK);

			try {
				toDisplay = this.prop.getText().getText();
				try {

					if (prop.getBehaviors().get(0).getBehaviorType()
							.equals(BehaviorType.REWARD_BEHAVIOR)
							&& prop.getBehaviors().get(0).getAction()
									.equals("GetPoints")) {
						Reward myReward = GameAdapter.getGameAdapterObj()
								.getGame().getCharacters().get(0).getRewards();

						// fetch rewards TO DO---
						toDisplay = toDisplay + " " + myReward.getPoints()
								+ ". ";

						if (ChallengeAdapter.quizReward != 0) {
							toDisplay = "Your Title is "
									+ ChallengeAdapter.quizTitleChange + "."
									+ toDisplay;

						}

					}
				} catch (Exception e) {

				}
				drawStringPlusImageRect(graphics, 3, 3, d.width,
						d.height, 1.5f, toDisplay, image1);
			} catch (Exception e) {
				drawStringPlusImageRect(graphics, 3, 3, d.width,
						d.height - 5, 1.5f, " ", image1);
			}
		}
	}

	private int getTextHeight(Graphics2D graphics, int x1, int y1, int x2,
			int y2, float interline, String txt) {
		AttributedString as = new AttributedString(txt);
		as.addAttribute(TextAttribute.FOREGROUND, graphics.getPaint());
		as.addAttribute(TextAttribute.FONT, graphics.getFont());
		// System.out.println("Font name is " +
		// graphics.getFont().getFontName());
		AttributedCharacterIterator aci = as.getIterator();
		FontRenderContext frc = new FontRenderContext(null, true, false);
		LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
		float width = x2 - x1;
		// System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		while (lbm.getPosition() < txt.length()) {
			TextLayout tl = lbm.nextLayout(width);
			// Changes made by Kyle Duckworth 26.10.2014
			// Determine the text length of the current layout
			float textLength = tl.getAdvance();
			y1 += tl.getAscent();
			// Center the text within the box
			// Don't draw when determining the height
			// tl.draw(graphics, ((width-textLength)/2) + x1 + 2, y1 + 2);
			y1 += tl.getDescent() + tl.getLeading() + (interline - 1.0f)
					* tl.getAscent();
			// System.out.println("This is y1: " + y1);
			if (y1 > y2) {

				break;
			}
		}
		// System.out.println("Let's verify that y1 is: " + y1);
		return y1;
	}

	private void drawStringRect(Graphics2D graphics, int x1, int y1, int x2,
			int y2, float interline, String txt) {
		int temp = y1;
		AttributedString as = new AttributedString(txt);
		as.addAttribute(TextAttribute.FOREGROUND, graphics.getPaint());
		as.addAttribute(TextAttribute.FONT, graphics.getFont());
		// System.out.println("Font name is " +
		// graphics.getFont().getFontName());
		AttributedCharacterIterator aci = as.getIterator();
		FontRenderContext frc = new FontRenderContext(null, true, false);
		LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
		float width = x2 - x1;

		while (lbm.getPosition() < txt.length()) {
			TextLayout tl = lbm.nextLayout(width);
			// Changes made by Kyle Duckworth 26.10.2014
			// Determine the text length of the current layout
			float textLength = tl.getAdvance();
			y1 += tl.getAscent();
			// Center the text within the box
			tl.draw(graphics, ((width - textLength) / 2) + x1 + 2, y1 + 2);
			y1 += tl.getDescent() + tl.getLeading() + (interline - 1.0f)
					* tl.getAscent();
			// System.out.println("This is y1: " + y1);
			if (y1 > y2) {
				break;
			}
		}
		// System.out.println("Let's verify that y1 is: " + y1);

	}

	private void drawStringPlusImageRect(Graphics2D graphics, int x1, int y1,
			int x2, int y2, float interline, String txt, Image iamge) {
		int temp = y1;
		AttributedString as = new AttributedString(txt);
		as.addAttribute(TextAttribute.FOREGROUND, graphics.getPaint());
		as.addAttribute(TextAttribute.FONT, graphics.getFont());
		// System.out.println("Font name is " +
		// graphics.getFont().getFontName());
		AttributedCharacterIterator aci = as.getIterator();
		FontRenderContext frc = new FontRenderContext(null, true, false);
		LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
		float width = x2 - x1;

		while (lbm.getPosition() < txt.length()) {
			TextLayout tl = lbm.nextLayout(width);
			// Changes made by Kyle Duckworth 26.10.2014
			// Determine the text length of the current layout
			float textLength = tl.getAdvance();
			y1 += tl.getAscent();
			// Center the text within the box
			tl.draw(graphics, ((width - textLength) / 2) + x1 + 2, y1 + 2);
			y1 += tl.getDescent() + tl.getLeading() + (interline - 1.0f)
					* tl.getAscent();
			if (y1 > y2) {
				break;
			}
		}
		graphics.drawImage(iamge, x1 + 3, y1, null);

	}

	/*
	 * public void paint(Graphics g) { // Create an image for the button
	 * graphics if necessary System.out.println("In the paint method !!!");
	 * //setOpaque(false); if (buttonImage == null || buttonImage.getWidth() !=
	 * getWidth() || buttonImage.getHeight() != getHeight()) {
	 * System.out.println("Yes.. Here it is"); buttonImage =
	 * getGraphicsConfiguration(). createCompatibleImage(getWidth(),
	 * getHeight()); System.out.println("Yes.. Here it was");
	 * //setOpaque(false); }else{ //setOpaque(false); } Graphics gButton =
	 * buttonImage.getGraphics(); gButton.setClip(g.getClip());
	 * //gButton.setColor(Color.GREEN); // Have the superclass render the button
	 * for us super.paint(gButton); super.setOpaque(false);
	 * //setBackground(Color.YELLOW); // Make the graphics object sent to this
	 * paint() method translucent Graphics2D g2d = (Graphics2D)g; AlphaComposite
	 * newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	 * alpha); g2d.setComposite(newComposite);
	 * 
	 * // Copy the button's image to the destination graphics, translucently
	 * g2d.drawImage(buttonImage, 0, 0, null); //setOpaque(false); }
	 */

	// @Override
	@Override
	public void begin() {
		// TODO Auto-generated method stub

	}

	// @Override
	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	// @Override
	@Override
	public void repeat() {
		// TODO Auto-generated method stub

	}

	// @Override
	@Override
	public void timingEvent(float fraction) {
		// TODO Auto-generated method stub
		alpha = fraction;
		if (alpha > 0) {
			repaint();
		} else {
			animator.stop();
		}
	}

	public static void startSandBox(int[] points, final GameState gameState) {
		TrianglePanel triangle = new TrianglePanel(points);
		triangle.setPreferredSize(new Dimension(500, 500));
		triangle.setBackground(Color.WHITE);
		TriangleFrame frame = new TriangleFrame(points, gameState);
		frame.setVisible(true);

	}
}

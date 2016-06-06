/*Source baseline: https://code.google.com/p/jmingle/source/browse/trunk/
 * src/org/oep/widgets/SpeechBubble.java
 */
package edu.utdallas.old;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Contains information for drawing conversation bubbles.
 * 
 * @author Sean
 *
 */
public class ConversationBubble extends JLabel {
	/**
	 * Arrow height.
	 */
	private int ARROW_HEIGHT = 7;
	/**
	 * Arrow width.
	 */
	private int ARROW_WIDTH = 4;
	/**
	 * Padding.
	 */
	private int PADDING = 8;
	/**
	 * The x position.
	 */
	private int xPos;
	/**
	 * The y position.
	 */
	private int yPos;
	/**
	 * The width of the bubble.
	 */
	private int widthC;
	/**
	 * The height of the bubble.
	 */
	private int heightC;
	/**
	 * The text for the bubble.
	 */
	private String textC;
	/**
	 * Font details for the text.
	 */
	private Font fontC = new Font("Comic Sans MS", Font.BOLD, 15);

	/**
	 * The point direction.
	 * 
	 * @author Sean
	 *
	 */
	public static enum PointDirection {
		LEFT_DOWN, CENTER_DOWN, RIGHT_DOWN
	}

	/**
	 * Default point direction.
	 */
	private PointDirection directionC = PointDirection.LEFT_DOWN;
	/**
	 * The label for the bubble, where the text will be placed.
	 */
	JLabel innerLabel = new JLabel();

	/**
	 * Default constructor.
	 */
	public ConversationBubble() {
		this("");
	}

	/**
	 * Constructor, creates a bubble with the string passed as parameter.
	 * 
	 * @param text
	 *            {@link String}
	 */
	public ConversationBubble(final String text) {
		super();
		textC = text;
		innerLabel.setFont(fontC);
		innerLabel.setForeground(Color.BLACK);
		innerLabel.setBackground(Color.WHITE);
		innerLabel.setOpaque(true);
		innerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		innerLabel.setVerticalAlignment(SwingConstants.TOP);
		add(innerLabel);
	}

	/**
	 * Sets the point direction.
	 * 
	 * @param p
	 *            {@link PointDirection}
	 */
	public final void setPointDirection(final PointDirection p) {
		directionC = p;
	}

	/**
	 * Set the bounds of the label capturing the necessary variables for drawing
	 * the bubble.
	 * 
	 * @param x
	 *            {@link int}
	 * @param y
	 *            {@link int}
	 * @param width
	 *            {@link int}
	 * @param height
	 *            {@link int}
	 */
	@Override
	public final void setBounds(final int x, final int y, final int width,
			final int height) {
		xPos = x;
		yPos = y;
		widthC = width;
		heightC = height;
		ARROW_HEIGHT = widthC / 8;
		ARROW_WIDTH = widthC / 10;
		PADDING = widthC / 15;
		super.setBounds(x, y, width, height);
	}

	/**
	 * Sets the font.
	 * 
	 * @param font
	 *            {@link Font}
	 */
	@Override
	public final void setFont(final Font font) {
		fontC = font;
		super.setFont(font);
	}

	/**
	 * Draw a string in a speech bubble.
	 * 
	 * @param text
	 *            the string to draw
	 */
	private void paintString(final String text) {
		innerLabel.setText("<html><p style=\"text-align:center\">" + text
				+ "</p></html>");
		innerLabel.setBounds(PADDING, PADDING / 2, widthC - PADDING * 2,
				heightC - PADDING - ARROW_HEIGHT);
	}

	/**
	 * Draw an empty speech bubble with the point in the center.
	 * 
	 * @param g
	 *            the graphics object to paint to
	 * @param x
	 *            the x position of the object (relative to the pointy part)
	 * @param y
	 *            the y position of the object (relative to the pointy part)
	 * @param height
	 *            the height of the body part
	 * @param width
	 *            the width of the body part
	 */
	private void drawBubblePointCenter(final Graphics g, final int x,
			final int y, final int width, final int height) {
		// Save the graphics object color and font so that
		// we may restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x;
		ycoords[0] = y;
		xcoords[1] = x - ARROW_WIDTH / 2;
		ycoords[1] = y - ARROW_HEIGHT;
		xcoords[2] = x + ARROW_WIDTH / 2;
		ycoords[2] = y - ARROW_HEIGHT;

		// Draw the base shape -- the rectangle the image will fit into
		// as well as its outline
		g.setColor(Color.WHITE);
		g.fillRoundRect(x - width / 2, y - height - ARROW_HEIGHT, width,
				height, PADDING * 2, PADDING * 2);
		g.setColor(Color.BLACK);
		g.drawRoundRect(x - width / 2, y - height - ARROW_HEIGHT, width,
				height, PADDING * 2, PADDING * 2);

		// Next is to draw the pointy part that indicates who is speaking
		g.setColor(Color.WHITE);
		g.fillPolygon(xcoords, ycoords, numTriangleCoords);
		g.drawLine(xcoords[1] + 1, ycoords[1], xcoords[2] - 1, ycoords[2]);

		// This is the outline to the pointy part
		g.setColor(Color.BLACK);
		g.drawLine(x, y, xcoords[1], ycoords[1]);
		g.drawLine(x, y, xcoords[2], ycoords[2]);

		// Restore the font and color
		g.setColor(origColor);
		g.setFont(origFont);
	}

	/**
	 * Draw an empty speech bubble with the point on the left.
	 * 
	 * @param g
	 *            the graphics object to paint to
	 * @param x
	 *            the x position of the object (relative to the pointy part)
	 * @param y
	 *            the y position of the object (relative to the pointy part)
	 * @param height
	 *            the height of the body part
	 * @param width
	 *            the width of the body part
	 */
	private void drawBubblePointLeft(final Graphics g, final int x,
			final int y, final int width, final int height) {
		// Save the graphics object color and font so that we
		// may restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x;
		ycoords[0] = y;
		xcoords[1] = x + ARROW_WIDTH;
		ycoords[1] = y - ARROW_HEIGHT;
		xcoords[2] = x + ARROW_WIDTH * 2;
		ycoords[2] = y - ARROW_HEIGHT;

		// Draw the base shape -- the rectangle the image will fit into
		// as well as its outline
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y - height - ARROW_HEIGHT, width, height,
				PADDING * 2, PADDING * 2);
		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y - height - ARROW_HEIGHT, width, height,
				PADDING * 2, PADDING * 2);

		// Next is to draw the pointy part that indicates who is speaking
		g.setColor(Color.WHITE);
		g.fillPolygon(xcoords, ycoords, numTriangleCoords);
		g.drawLine(xcoords[1] + 1, ycoords[1], xcoords[2] - 1, ycoords[2]);

		// This is the outline to the pointy part
		g.setColor(Color.BLACK);
		g.drawLine(x, y, xcoords[1], ycoords[1]);
		g.drawLine(x, y, xcoords[2], ycoords[2]);

		// Restore the font and color
		g.setColor(origColor);
		g.setFont(origFont);
	}

	/**
	 * Draw an empty speech bubble with the point on the right.
	 * 
	 * @param g
	 *            the graphics object to paint to
	 * @param x
	 *            the x position of the object (relative to the pointy part)
	 * @param y
	 *            the y position of the object (relative to the pointy part)
	 * @param height
	 *            the height of the body part
	 * @param width
	 *            the width of the body part
	 */
	private void drawBubblePointRight(final Graphics g, final int x,
			final int y, final int width, final int height) {
		// Save the graphics object color and font so that we may
		// restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x;
		ycoords[0] = y;
		xcoords[1] = x - ARROW_WIDTH;
		ycoords[1] = y - ARROW_HEIGHT;
		xcoords[2] = x - ARROW_WIDTH * 2;
		ycoords[2] = y - ARROW_HEIGHT;

		// Draw the base shape -- the rectangle the image will fit into as
		// well as its outline
		g.setColor(Color.WHITE);
		g.fillRoundRect(x - width, y - height - ARROW_HEIGHT, width, height,
				PADDING * 2, PADDING * 2);
		g.setColor(Color.BLACK);
		g.drawRoundRect(x - width, y - height - ARROW_HEIGHT, width, height,
				PADDING * 2, PADDING * 2);

		// Next is to draw the pointy part that indicates who is speaking
		g.setColor(Color.WHITE);
		g.fillPolygon(xcoords, ycoords, numTriangleCoords);
		g.drawLine(xcoords[1] - 1, ycoords[1], xcoords[2] + 1, ycoords[2]);

		// This is the outline to the pointy part
		g.setColor(Color.BLACK);
		g.drawLine(x, y, xcoords[1], ycoords[1]);
		g.drawLine(x, y, xcoords[2], ycoords[2]);

		// Restore the font and color
		g.setColor(origColor);
		g.setFont(origFont);
	}

	@Override
	protected final void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		switch (directionC) {
		case LEFT_DOWN:
			drawBubblePointLeft(g, 0, heightC, widthC - 2, heightC
					- ARROW_HEIGHT - 1);
			break;
		case CENTER_DOWN:
			drawBubblePointCenter(g, widthC / 2, heightC, widthC - 2, heightC
					- ARROW_HEIGHT - 1);
			break;
		case RIGHT_DOWN:
			drawBubblePointRight(g, widthC, heightC, widthC - 2, heightC
					- ARROW_HEIGHT - 1);
			break;
		default:
			break;
		}
		paintString(textC);
	}
}

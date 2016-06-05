/*Source baseline: https://code.google.com/p/jmingle/source/browse/trunk
    /src/org/oep/widgets/SpeechBubble.java
 *
 */
package edu.utdallas.old;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.utdallas.old.ConversationBubble.PointDirection;

/**
 * Holds information needed for drawing ThoughtBubble assets.
 * 
 * @author Sean
 *
 */
@SuppressWarnings("serial")
public class ThoughtBubble extends JLabel {

	/**
	 * Used in certain calculations.
	 */
	private static final int CIRCLEDEGREES = 360;
	/**
	 * Used in certain calculations.
	 */
	private static final int NINETYDEGREES = 90;
	/**
	 * Used in certain calculations.
	 */
	private static final int THIRTYDEGREES = 30;
	/**
	 * Used in drawing circles leading to the bubbles.
	 */
	private static final int LARGECIRCDIVISOR = 5;
	/**
	 * Used in drawing circles leading to the bubbles.
	 */
	private static final int MEDCIRCDIVISOR = 3;

	/**
	 * the arrow height.
	 */
	private int ARROW_HEIGHT = 7;
	/**
	 * The arrow width.
	 */
	private int ARROW_WIDTH = 4;
	/**
	 * The padding.
	 */
	private int PADDING = 8;
	/**
	 * The padding for the top of the text.
	 */
	private int TEXT_TOP = 8;
	/**
	 * The padding for the left of the text.
	 */
	private int TEXT_LEFT = 8;
	/**
	 * Width.
	 */
	private int widthC;
	/**
	 * Height.
	 */
	private int heightC;
	/**
	 * The text.
	 */
	private String textC;
	/**
	 * The font.
	 */
	private Font fontC = new Font("Comic Sans MS", Font.BOLD, 15);
	/**
	 * Default direction for the pointer is left and down.
	 */
	private PointDirection directionC = PointDirection.LEFT_DOWN;

	/**
	 * The default constructor.
	 */
	public ThoughtBubble() {
		super();
		textC = "";
	}

	/**
	 * Constructor, sets the text.
	 * 
	 * @param text
	 *            {@link String}
	 */
	public ThoughtBubble(final String text) {
		super();
		textC = text;
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
     *
     */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		widthC = width;
		heightC = height;
		ARROW_HEIGHT = widthC / 8;
		ARROW_WIDTH = widthC / 8;
		PADDING = widthC / 14;
		TEXT_LEFT = PADDING / 2 + (heightC - ARROW_HEIGHT - PADDING) / 4;
		TEXT_TOP = PADDING / 2 + (heightC - ARROW_HEIGHT - PADDING) / 8;
		super.setBounds(x, y, width, height);
	}

	/**
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
	 *            the string to draw {@link String}
	 */
	private void paintString(final String text) {
		JLabel innerLabel = new JLabel("<html><p>" + text + "</p></html>");
		innerLabel.setBounds(TEXT_LEFT, TEXT_TOP, widthC - TEXT_LEFT * 2,
				heightC - TEXT_TOP * 2 - ARROW_HEIGHT);
		innerLabel.setFont(fontC);
		innerLabel.setForeground(Color.BLACK);
		innerLabel.setBackground(Color.WHITE);
		innerLabel.setOpaque(true);
		innerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		innerLabel.setVerticalAlignment(SwingConstants.TOP);
		add(innerLabel);
	}

	/**
	 * Draw an empty speech bubble with the point in the center.
	 * 
	 * @param g
	 *            the graphics object to paint to {@link Graphics}
	 * @param x
	 *            the x position of the object (relative to the pointy part)
	 *            {@link int}
	 * @param y
	 *            the y position of the object (relative to the pointy part)
	 *            {@link int}
	 * @param height
	 *            the height of the body part {@link int}
	 * @param width
	 *            the width of the body part {@link int}
	 */
	private void drawBubblePointCenter(final Graphics g, final int x,
			final int y, final int width, final int height) {
		// Save the graphics object color and font so that we may
		// restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x - ARROW_WIDTH / 10;
		ycoords[0] = y - ARROW_HEIGHT / 5 - 1;
		xcoords[1] = x - ARROW_WIDTH / 6;
		ycoords[1] = y - ARROW_HEIGHT / 5 - ARROW_HEIGHT / MEDCIRCDIVISOR - 1;
		xcoords[2] = x - ARROW_WIDTH / 4;
		ycoords[2] = y - ARROW_HEIGHT / 5 - ARROW_HEIGHT / MEDCIRCDIVISOR
				- ARROW_HEIGHT / 2 - 1;

		// Draw the base shape -- the rectangle the image will fit into as
		// well as its outline
		g.setColor(Color.WHITE);
		g.fillRoundRect(x - width / 2 + PADDING / 2, y - height - ARROW_HEIGHT
				+ PADDING / 2, width - PADDING, height - PADDING, height
				- PADDING, height - PADDING);
		int per = perimeterRoundedRect(width - PADDING, height - PADDING);
		Point[] points = getRoundedRectPoints(x - width / 2 + PADDING / 2, y
				- height - ARROW_HEIGHT + PADDING / 2, width - PADDING, height
				- PADDING, (height - PADDING) / 2, 30);

		for (int i = 0; i < points.length - 1; i++) {
			paintArc2PointsRadius(points[i].x, points[i].y, points[i + 1].x,
					points[i + 1].y, per / 30 + 2, g);
		}

		// draw little circles indicating who is thinking
		g.setColor(Color.WHITE);
		g.fillArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

		// outline the circles
		g.setColor(Color.BLACK);
		g.drawArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

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
		// Save the graphics object color and font so that we may
		// restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x;
		ycoords[0] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - 1;
		xcoords[1] = x + ARROW_WIDTH / LARGECIRCDIVISOR;
		ycoords[1] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - ARROW_HEIGHT
				/ MEDCIRCDIVISOR - 1;
		xcoords[2] = x + ARROW_WIDTH / LARGECIRCDIVISOR + ARROW_WIDTH
				/ MEDCIRCDIVISOR;
		ycoords[2] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - ARROW_HEIGHT
				/ MEDCIRCDIVISOR - ARROW_HEIGHT / 2 - 1;

		// Draw the base shape -- the rectangle the image will fit
		// into as well as its outline
		g.setColor(Color.WHITE);
		g.fillRoundRect(x + PADDING / 2, y - height - ARROW_HEIGHT + PADDING
				/ 2, width - PADDING, height - PADDING, height - PADDING,
				height - PADDING);

		int per = perimeterRoundedRect(width - PADDING, height - PADDING);
		Point[] points = getRoundedRectPoints(x + PADDING / 2, y - height
				- ARROW_HEIGHT + PADDING / 2, width - PADDING,
				height - PADDING, (height - PADDING) / 2, 30);

		for (int i = 0; i < points.length - 1; i++) {
			paintArc2PointsRadius(points[i].x, points[i].y, points[i + 1].x,
					points[i + 1].y, per / 30 + 2, g);
		}

		// draw little circles indicating who is thinking
		g.setColor(Color.WHITE);
		g.fillArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

		// outline the circles
		g.setColor(Color.BLACK);
		g.drawArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

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
		// Save the graphics object color and font so that we
		// may restore it later
		Color origColor = g.getColor();
		Font origFont = g.getFont();
		int numTriangleCoords = 3;
		int[] xcoords = new int[numTriangleCoords];
		int[] ycoords = new int[numTriangleCoords];
		xcoords[0] = x - ARROW_WIDTH / LARGECIRCDIVISOR;
		ycoords[0] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - 1;
		xcoords[1] = x - ARROW_WIDTH / LARGECIRCDIVISOR - ARROW_WIDTH
				/ MEDCIRCDIVISOR;
		ycoords[1] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - ARROW_HEIGHT
				/ MEDCIRCDIVISOR - 1;
		xcoords[2] = x - ARROW_WIDTH / LARGECIRCDIVISOR - ARROW_WIDTH
				/ MEDCIRCDIVISOR - ARROW_WIDTH / 2;
		ycoords[2] = y - ARROW_HEIGHT / LARGECIRCDIVISOR - ARROW_HEIGHT
				/ MEDCIRCDIVISOR - ARROW_HEIGHT / 2 - 1;

		g.setColor(Color.WHITE);
		g.fillRoundRect(x - width + PADDING / 2, y - height - ARROW_HEIGHT
				+ PADDING / 2, width - PADDING, height - PADDING, height
				- PADDING, height - PADDING);

		int per = perimeterRoundedRect(width - PADDING, height - PADDING);
		Point[] points = getRoundedRectPoints(x - width + PADDING / 2, y
				- height - ARROW_HEIGHT + PADDING / 2, width - PADDING, height
				- PADDING, (height - PADDING) / 2, THIRTYDEGREES);

		for (int i = 0; i < points.length - 1; i++) {
			paintArc2PointsRadius(points[i].x, points[i].y, points[i + 1].x,
					points[i + 1].y, per / THIRTYDEGREES + 2, g);
		}

		// draw little circles indicating who is thinking
		g.setColor(Color.WHITE);
		g.fillArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.fillArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

		// outline the circles
		g.setColor(Color.BLACK);
		g.drawArc(xcoords[0], ycoords[0], ARROW_WIDTH / LARGECIRCDIVISOR,
				ARROW_HEIGHT / LARGECIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[1], ycoords[1], ARROW_WIDTH / MEDCIRCDIVISOR,
				ARROW_HEIGHT / MEDCIRCDIVISOR, 0, CIRCLEDEGREES);
		g.drawArc(xcoords[2], ycoords[2], ARROW_WIDTH / 2, ARROW_HEIGHT / 2, 0,
				CIRCLEDEGREES);

		// Restore the font and color
		g.setColor(origColor);
		g.setFont(origFont);
	}

	/**
	 *
	 * @param g
	 *            {@link Graphics}
	 * @param x
	 *            {@link int}
	 * @param y
	 *            {@link int}
	 */
	private void drawPlus(final Graphics g, final int x, final int y) {
		final int sOFFSET = 10;
		g.drawLine(x - sOFFSET, y, x + sOFFSET, y);
		g.drawLine(x, y - sOFFSET, x, y + sOFFSET);
	}

	/**
	 *
	 * @param x
	 *            {@link int}
	 * @param y
	 *            {@link int}
	 * @param width
	 *            {@link int}
	 * @param height
	 *            {@link int}
	 * @param radius
	 *            {@link int}
	 * @param numPoints
	 *            {@link int}
	 * @return {@link Point}
	 */
	// assumes the radius of the end arcs is height / 2
	private Point[] getRoundedRectPoints(final int x, final int y,
			final int width, final int height, final int radius,
			final int numPoints) {
		Point[] points = new Point[numPoints + 1];
		int perimeter = perimeterRoundedRect(width, height);

		// initial point
		points[0] = new Point();
		points[0].x = x;
		points[0].y = y + height / 2;
		points[numPoints] = new Point();
		points[numPoints].x = points[0].x;
		points[numPoints].y = points[0].y;

		int curX = x
				+ (int) (radius * (1 - Math.cos(getAngleForArcLength((perimeter
						/ THIRTYDEGREES + 1), radius))));
		int i = 1;

		while (curX < x + radius) {
			points[i] = new Point();
			points[i].x = curX;
			points[i].y = y
					+ height
					/ 2
					- (int) (radius * Math.sin(getAngleForArcLength((i
							* perimeter / THIRTYDEGREES + 1), radius)));
			points[numPoints - i] = new Point();
			points[numPoints - i].x = curX;
			points[numPoints - i].y = y
					+ height
					/ 2
					+ (int) (radius * Math.sin(getAngleForArcLength((i
							* perimeter / THIRTYDEGREES + 1), radius)));

			i++;
			curX = x
					+ (int) (radius * (1 - Math.cos(getAngleForArcLength((i
							* perimeter / THIRTYDEGREES + 1), radius))));
		}
		while (curX < x + width - radius) {
			points[i] = new Point();
			points[i].x = curX;
			points[i].y = y;
			points[numPoints - i] = new Point();
			points[numPoints - i].x = curX;
			points[numPoints - i].y = y + height;

			i++;
			curX = curX + (perimeter / THIRTYDEGREES + 1);
		}
		if (numPoints % 2 == 0) {
			int j = numPoints / 2;
			points[j] = new Point();
			points[j].x = x + width;
			points[j].y = y + height / 2;
			j--;
			curX = x
					+ width
					- (int) (radius * (1 - Math
							.cos(getAngleForArcLength(((15 - j) * perimeter
									/ THIRTYDEGREES + 1), radius))));
			while (j >= i) {
				points[j] = new Point();
				points[j].x = curX;
				points[j].y = y
						+ height
						/ 2
						- (int) (radius * Math.sin(getAngleForArcLength(
								((15 - j) * perimeter / THIRTYDEGREES + 1),
								radius)));
				points[numPoints - j] = new Point();
				points[numPoints - j].x = curX;
				points[numPoints - j].y = y
						+ height
						/ 2
						+ (int) (radius * Math.sin(getAngleForArcLength(
								((15 - j) * perimeter / THIRTYDEGREES + 1),
								radius)));
				j--;
				curX = x
						+ width
						- (int) (radius * (1 - Math.cos(getAngleForArcLength(
								((15 - j) * perimeter / THIRTYDEGREES + 1),
								radius))));
			}
		}

		return points;
	}

	/**
	 *
	 * @param width
	 *            {@link int}
	 * @param height
	 *            {@link int}
	 * @return {@link int}
	 */
	// assumes the radius of the end arcs is height / 2
	private int perimeterRoundedRect(final int width, final int height) {
		int perimeter = 0;
		perimeter = (width - height) * 2;
		perimeter += height * Math.PI;
		return perimeter;
	}

	/**
	 *
	 * @param length
	 *            {@link int}
	 * @param radius
	 *            {@link int}
	 * @return {@link double}
	 */
	private double getAngleForArcLength(final int length, final int radius) {

		double angle = (double) length / (double) radius;
		return angle;
	}

	/**
	 *
	 * @param x1
	 *            {@link int}
	 * @param y1
	 *            {@link int}
	 * @param x2
	 *            {@link int}
	 * @param y2
	 *            {@link int}
	 * @param diameter
	 *            {@link int}
	 * @param g
	 *            {@link Graphics}
	 */
	// applies only for the rounded rectangle used in this class
	private void paintArc2PointsRadius(final int x1, final int y1,
			final int x2, final int y2, final int diameter, final Graphics g) {
		int xm, ym, xo, yo;
		int dist;
		double angle1, angle2;
		int deg1, deg2;
		Color origColor = g.getColor();
		// points along the top of the shape
		if (x1 < x2) {
			// find midpoint between points
			dist = (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1)
					* (y2 - y1));
			xm = (x1 + x2) / 2;
			ym = (y1 + y2) / 2;

			// shift toward center 1-2 px
			// find arc origin x,y and angles
			xo = xm - dist / 2;
			yo = ym - dist / 2;
			// need to fix this

			angle1 = Math.atan2(x1 - xm, y1 - ym);
			deg1 = (int) Math.toDegrees(angle1) + NINETYDEGREES;

			angle2 = Math.atan2(x2 - xm, y2 - ym);
			deg2 = (int) Math.toDegrees(angle2) + NINETYDEGREES;
			g.setColor(Color.WHITE);
			g.fillArc(xo, yo, diameter, diameter, 0, CIRCLEDEGREES);
			g.setColor(Color.BLACK);
			g.drawArc(xo, yo, diameter, diameter, deg1, deg2 - deg1);
		} else { // points along the bottom of the image

			dist = (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1)
					* (y2 - y1));
			xm = (x1 + x2) / 2;
			ym = (y1 + y2) / 2;

			// shift toward center 1-2 px
			// find arc origin x,y and angles
			xo = xm - dist / 2;
			yo = ym - dist / 2;
			// need to fix this

			angle1 = Math.atan2(x1 - xm, y1 - ym);
			deg1 = (int) Math.toDegrees(angle1) + NINETYDEGREES;

			angle2 = Math.atan2(x2 - xm, y2 - ym);
			deg2 = (int) Math.toDegrees(angle2) + NINETYDEGREES;
			g.setColor(Color.WHITE);
			g.fillArc(xo, yo, diameter, diameter, 0, CIRCLEDEGREES);
			g.setColor(Color.BLACK);
			g.drawArc(xo, yo, diameter, diameter, deg1, deg1 - deg2);
		}
		g.setColor(origColor);
	}

	/**
	 * Paints the component.
	 * 
	 * @param g
	 *            {@link Graphics g}
	 */
	@Override
	protected final void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		switch (directionC) {
		case LEFT_DOWN:
			drawBubblePointLeft(g, 0, heightC, widthC, heightC - ARROW_HEIGHT);
			break;
		case CENTER_DOWN:
			drawBubblePointCenter(g, widthC / 2, heightC, widthC, heightC
					- ARROW_HEIGHT);
			break;
		case RIGHT_DOWN:
			drawBubblePointRight(g, widthC, heightC, widthC, heightC
					- ARROW_HEIGHT);
			break;
		default:
			break;
		}
		paintString(textC);
	}
}

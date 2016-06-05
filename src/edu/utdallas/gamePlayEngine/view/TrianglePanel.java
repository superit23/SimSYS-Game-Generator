package edu.utdallas.gamePlayEngine.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TrianglePanel extends JPanel implements MouseListener,
		MouseMotionListener {

	public static Polygon polygon, polygon2, polygon3;
	private int xLoc = 300, yLoc = 50, width = 298, height = 198;
	private float center1x, center2x, center3x, center1y, center2y, center3y;
	private Color triangleYellow = new Color(255, 255, 0);
	public static int poly1 = GameView.angles[0], poly2 = GameView.angles[1],
			poly3 = GameView.angles[2], triangleCount = 0;
	public static Area rectangle;
	public static Area triangle;
	int lastXPos = 0;
	int lastYPos = 0;
	int angle;
	boolean draggable = false;
	boolean draggable2 = false;
	boolean draggable3 = false;
	private static int[] points;

	public TrianglePanel(int[] points) {
		// Point[] points
		TrianglePanel.points = points;
		Point p1 = new Point(points[0], points[1]);
		Point p2 = new Point(points[2], points[3]);
		Point p3 = new Point(points[4], points[5]);
		Point p4 = new Point(points[6], points[7]);
		Point p5 = new Point(points[8], points[9]);
		Point p6 = new Point(points[10], points[11]);
		Point p7 = new Point(points[12], points[13]);
		Point p8 = new Point(points[14], points[15]);
		Point p9 = new Point(points[16], points[17]);

		// creates 3 new polygons
		this.setDoubleBuffered(true);
		// p1.x =
		polygon = new Polygon();
		polygon2 = new Polygon();
		polygon3 = new Polygon();
		polygon.addPoint(p1.x, p1.y);
		polygon.addPoint(p2.x, p2.y);
		polygon.addPoint(p3.x, p3.y);
		polygon2.addPoint(p4.x, p4.y);
		polygon2.addPoint(p5.x, p5.y);
		polygon2.addPoint(p6.x, p6.y);
		polygon3.addPoint(p7.x, p7.y);
		polygon3.addPoint(p8.x, p8.y);
		polygon3.addPoint(p9.x, p9.y);
		// Calculates the center to locate where to put the angle number
		center1x = ((p1.x + p2.x + p3.x) / 3);
		center2x = ((p4.x + p5.x + p6.x) / 3);
		center3x = ((p7.x + p8.x + p9.x) / 3);
		center1y = ((p1.y + p2.y + p3.y) / 3);
		center2y = ((p4.y + p5.y + p6.y) / 3);
		center3y = ((p7.y + p8.y + p9.y) / 3);
		lastXPos = this.getX();
		lastYPos = this.getY();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		TriangleFrame.redrawButton();
		Graphics2D graphics = (Graphics2D) g;
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		g.drawRect(300, 50, 300, 200);
		g.drawRect(299, 49, 300, 200);
		g.drawRect(298, 48, 300, 200);
		g.drawRect(297, 47, 300, 200);
		g.setColor(new Color(242, 215, 196));
		g.fill3DRect(xLoc, yLoc, width, height, true);
		g.setColor(triangleYellow);
		g.fillPolygon(polygon);
		g.fillPolygon(polygon2);
		g.fillPolygon(polygon3);
		g.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(3));
		graphics.drawPolygon(polygon);
		graphics.drawPolygon(polygon2);
		graphics.drawPolygon(polygon3);
		g.setFont((new Font("Aerial", Font.PLAIN, 16)));
		g.drawString(poly1 + "°", (int) center1x - 8, (int) center1y);
		g.drawString(poly2 + "°", (int) center2x - 2, (int) center2y);
		g.drawString(poly3 + "°", (int) center3x - 10, (int) center3y);
		// g.drawString("Current Angle: " + TriangleFrame.currentAngle + "°",
		// 385, 265);
		// g.drawString("Desired Angle: " + GameView.desiredAngle + "°", 385,
		// 285);
		g.setFont((new Font("Aerial", Font.BOLD, 26)));
		g.drawString("The SandBox", 365, 40);
		// DFG poster asthetic updates
		// Add Ima Coder on the right hand of the screen with
		// Information bubble
		// This is currently hard coded for the POC
		// TO DO: Move into XML
		Image image = null;
		try {
			image = ImageIO.read(getClass()
					.getResource("char9_StandClosed.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, 600, 100, null);
		// Draw her bubble
		int strokeThickness = 5;
		int padding = 130;
		int radius = 10;
		int arrowSize = 6;
		final Graphics2D graphics2D = (Graphics2D) g;
		RenderingHints qualityHints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHints(qualityHints);
		graphics2D.setColor(Color.BLACK);
		graphics2D.setStroke(new BasicStroke(strokeThickness));
		if (TriangleFrame.currentAngle == 0) {
			padding = 100;
			int x = 750;
			int width = 250;
			int height = 125;
			// Black backdrop to bubble
			RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(x - 2,
					padding - 2, width + 5, height + 5, radius, radius);
			Area area1 = new Area(rect1);
			Polygon arrow1 = new Polygon();
			arrow1.addPoint(752, 198);
			arrow1.addPoint(738, 204);
			arrow1.addPoint(752, 210);
			area1.add(new Area(arrow1));
			graphics2D.draw(area1);
			// Black backdrop to triagle leading to mouth

			graphics2D.setColor(triangleYellow);
			graphics2D.fillRect(x, padding, width, height);

			RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x,
					padding, width, height, radius, radius);

			Polygon arrow = new Polygon();
			arrow.addPoint(750, 200);
			arrow.addPoint(742, 204);
			arrow.addPoint(750, 206);
			Area area = new Area(rect);
			area.add(new Area(arrow));
			graphics2D.draw(area);
			g.setFont((new Font("Aerial", Font.PLAIN, 18)));
			graphics2D.setColor(Color.BLACK);
			g.drawString(" The portal to the lair of the", 760, 120);
			g.drawString("  wizard of right angles lies ", 760, 150);
			g.drawString("before  you use your purse of ", 760, 180);
			g.drawString("  acute angles to form a key. ", 760, 210);
		} else {
			int x = 750;
			int width = 200;
			int height = 90;
			// Black backdrop to bubble
			RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(x - 2,
					padding - 2, width + 5, height + 5, radius, radius);
			Area area1 = new Area(rect1);
			Polygon arrow1 = new Polygon();
			arrow1.addPoint(752, 198);
			arrow1.addPoint(738, 204);
			arrow1.addPoint(752, 210);
			area1.add(new Area(arrow1));
			graphics2D.draw(area1);
			// Black backdrop to triagle leading to mouth

			graphics2D.setColor(triangleYellow);
			graphics2D.fillRect(x, padding, width, height);

			RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x,
					padding, width, height, radius, radius);

			Polygon arrow = new Polygon();
			arrow.addPoint(750, 200);
			arrow.addPoint(742, 204);
			arrow.addPoint(750, 206);
			Area area = new Area(rect);
			area.add(new Area(arrow));
			graphics2D.draw(area);
			g.setFont((new Font("Aerial", Font.PLAIN, 18)));
			graphics2D.setColor(Color.BLACK);
			g.drawString("   The portal remains ", 760, 150);
			g.drawString("closed the current key", 760, 180);
			g.drawString("       is a " + TriangleFrame.currentAngle
					+ "° angle.", 760, 210);
		}

		graphics2D.dispose();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// checks to see if a triangle has been selected
		draggable = false;
		draggable2 = false;
		draggable3 = false;
		if (polygon.contains(e.getPoint())) {
			draggable = true;
			lastXPos = e.getX();
			lastYPos = e.getY();
		}
		if (polygon2.contains(e.getPoint())) {
			draggable2 = true;
			lastXPos = e.getX();
			lastYPos = e.getY();
		}
		if (polygon3.contains(e.getPoint())) {
			draggable3 = true;
			lastXPos = e.getX();
			lastYPos = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// checks to see how many triangles are in the rectangle
		// checks first triangle to see if inside rectangle
		triangleCount = 0;
		if (TrianglePanel.polygon.xpoints[0] < xLoc + width
				&& TrianglePanel.polygon.xpoints[0] > xLoc
				&& TrianglePanel.polygon.ypoints[0] > yLoc
				&& TrianglePanel.polygon.ypoints[0] < yLoc + height
				&& TrianglePanel.polygon.xpoints[1] < xLoc + width
				&& TrianglePanel.polygon.xpoints[1] > xLoc
				&& TrianglePanel.polygon.ypoints[1] > yLoc
				&& TrianglePanel.polygon.ypoints[1] < yLoc + height
				&& TrianglePanel.polygon.xpoints[2] < xLoc + width
				&& TrianglePanel.polygon.xpoints[2] > xLoc
				&& TrianglePanel.polygon.ypoints[2] > yLoc
				&& TrianglePanel.polygon.ypoints[2] < yLoc + height) {
			triangleCount++;
			TriangleFrame.currentAngleTemp = TrianglePanel.poly1;
		} else {
			triangleCount--;
		}
		// checks second triangle to see if inside rectangle
		if (TrianglePanel.polygon2.xpoints[0] < xLoc + width
				&& TrianglePanel.polygon2.xpoints[0] > xLoc
				&& TrianglePanel.polygon2.ypoints[0] > yLoc
				&& TrianglePanel.polygon2.ypoints[0] < yLoc + height
				&& TrianglePanel.polygon2.xpoints[1] < xLoc + width
				&& TrianglePanel.polygon2.xpoints[1] > xLoc
				&& TrianglePanel.polygon2.ypoints[1] > yLoc
				&& TrianglePanel.polygon2.ypoints[1] < yLoc + height
				&& TrianglePanel.polygon2.xpoints[2] < xLoc + width
				&& TrianglePanel.polygon2.xpoints[2] > xLoc
				&& TrianglePanel.polygon2.ypoints[2] > yLoc
				&& TrianglePanel.polygon2.ypoints[2] < yLoc + height) {
			triangleCount++;
			TriangleFrame.currentAngleTemp = TrianglePanel.poly2;
		} else {
			triangleCount--;
		}
		// checks third triangle to see if inside rectangle
		if (TrianglePanel.polygon3.xpoints[0] < xLoc + width
				&& TrianglePanel.polygon3.xpoints[0] > xLoc
				&& TrianglePanel.polygon3.ypoints[0] > yLoc
				&& TrianglePanel.polygon3.ypoints[0] < yLoc + height
				&& TrianglePanel.polygon3.xpoints[1] < xLoc + width
				&& TrianglePanel.polygon3.xpoints[1] > xLoc
				&& TrianglePanel.polygon3.ypoints[1] > yLoc
				&& TrianglePanel.polygon3.ypoints[1] < yLoc + height
				&& TrianglePanel.polygon3.xpoints[2] < xLoc + width
				&& TrianglePanel.polygon3.xpoints[2] > xLoc
				&& TrianglePanel.polygon3.ypoints[2] > yLoc
				&& TrianglePanel.polygon3.ypoints[2] < yLoc + height) {
			triangleCount++;
			TriangleFrame.currentAngleTemp = TrianglePanel.poly3;
		} else {
			triangleCount--;
		}
		// Knows when to enable and disable button
		if (triangleCount >= 1) {
			TriangleFrame.button.setEnabled(false);
			TriangleFrame.button.setBackground(Color.GRAY);
		} else {
			TriangleFrame.button.setEnabled(true);
			TriangleFrame.button.setBackground(triangleYellow);
		}
		// Checks which triangle was moved and creates a new polygon for that
		if (draggable) {
			polygon = new Polygon(polygon.xpoints, polygon.ypoints,
					polygon.npoints);

		}
		if (draggable2) {
			polygon2 = new Polygon(polygon2.xpoints, polygon2.ypoints,
					polygon2.npoints);

		}
		if (draggable3) {
			polygon3 = new Polygon(polygon3.xpoints, polygon3.ypoints,
					polygon3.npoints);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Checks which mouse is being dragged and redraws the polygon as it is
		// dragged
		if (draggable) {
			int xPos = e.getX();
			int[] xPoints = polygon.xpoints;
			for (int i = 0; i < xPoints.length; i++) {
				xPoints[i] = xPoints[i] - (lastXPos - xPos);
			}
			lastXPos = xPos;

			int yPos = e.getY();
			int[] yPoints = polygon.ypoints;
			for (int i = 0; i < yPoints.length; i++) {
				yPoints[i] = yPoints[i] - (lastYPos - yPos);
			}
			lastYPos = yPos;
			center1x = (polygon.xpoints[0] + polygon.xpoints[1] + polygon.xpoints[2]) / 3;
			center1y = (polygon.ypoints[0] + polygon.ypoints[1] + polygon.ypoints[2]) / 3;
			repaint();
		}
		if (draggable2) {
			int xPos = e.getX();
			int[] xPoints = polygon2.xpoints;
			for (int i = 0; i < xPoints.length; i++) {
				xPoints[i] = xPoints[i] - (lastXPos - xPos);
			}
			lastXPos = xPos;

			int yPos = e.getY();
			int[] yPoints = polygon2.ypoints;
			for (int i = 0; i < yPoints.length; i++) {
				yPoints[i] = yPoints[i] - (lastYPos - yPos);
			}
			lastYPos = yPos;
			center2x = (polygon2.xpoints[0] + polygon2.xpoints[1] + polygon2.xpoints[2]) / 3;
			center2y = (polygon2.ypoints[0] + polygon2.ypoints[1] + polygon2.ypoints[2]) / 3;
			repaint();
		}
		if (draggable3) {
			int xPos = e.getX();
			int[] xPoints = polygon3.xpoints;
			for (int i = 0; i < xPoints.length; i++) {
				xPoints[i] = xPoints[i] - (lastXPos - xPos);
			}
			lastXPos = xPos;

			int yPos = e.getY();
			int[] yPoints = polygon3.ypoints;
			for (int i = 0; i < yPoints.length; i++) {
				yPoints[i] = yPoints[i] - (lastYPos - yPos);
			}
			lastYPos = yPos;
			center3x = (polygon3.xpoints[0] + polygon3.xpoints[1]
					+ polygon3.xpoints[2] - 17) / 3;
			center3y = (polygon3.ypoints[0] + polygon3.ypoints[1] + polygon3.ypoints[2]) / 3;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void resetTrianglePosition() {
		Point p1 = new Point(points[0], points[1]);
		Point p2 = new Point(points[2], points[3]);
		Point p3 = new Point(points[4], points[5]);
		Point p4 = new Point(points[6], points[7]);
		Point p5 = new Point(points[8], points[9]);
		Point p6 = new Point(points[10], points[11]);
		Point p7 = new Point(points[12], points[13]);
		Point p8 = new Point(points[14], points[15]);
		Point p9 = new Point(points[16], points[17]);

		// p1.x =
		polygon = new Polygon();
		polygon2 = new Polygon();
		polygon3 = new Polygon();
		polygon.addPoint(p1.x, p1.y);
		polygon.addPoint(p2.x, p2.y);
		polygon.addPoint(p3.x, p3.y);
		polygon2.addPoint(p4.x, p4.y);
		polygon2.addPoint(p5.x, p5.y);
		polygon2.addPoint(p6.x, p6.y);
		polygon3.addPoint(p7.x, p7.y);
		polygon3.addPoint(p8.x, p8.y);
		polygon3.addPoint(p9.x, p9.y);
		center1x = ((p1.x + p2.x + p3.x) / 3);
		center2x = ((p4.x + p5.x + p6.x) / 3);
		center3x = ((p7.x + p8.x + p9.x) / 3);
		center1y = ((p1.y + p2.y + p3.y) / 3);
		center2y = ((p4.y + p5.y + p6.y) / 3);
		center3y = ((p7.y + p8.y + p9.y) / 3);
		repaint();
	}
}

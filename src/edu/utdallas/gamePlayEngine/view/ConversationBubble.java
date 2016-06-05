/*
 * This file is used to create conversation bubble with a arrow at left-bottom position
 * Author: Zac (Qi ZHANG)
 * 
 * Source baseline: https://code.google.com/p/jmingle/source/browse/trunk/src/org/oep/widgets/SpeechBubble.java
 */

package edu.utdallas.gamePlayEngine.view;

public class ConversationBubble {
	// extends JLabel implements TimingTarget{
	/*
	 * } private static final long serialVersionUID = 1L;
	 *//** Stroke size. it is recommended to set it to 1 for better view */
	/*
	 * protected int strokeSize = 1;
	 *//** Color of shadow */
	/*
	 * protected Color shadowColor = Color.black;
	 *//** Sets if it drops shadow */
	/*
	 * protected boolean shady = true;
	 *//** Sets if it has an High Quality view */
	/*
	 * protected boolean highQuality = true;
	 *//** Double values for Horizontal and Vertical radius of corner arcs */
	/*
	 * protected Dimension arcs = new Dimension(20, 20);
	 *//** Distance between shadow border and opaque panel border */
	/*
	 * protected int shadowGap = 5;
	 *//** The offset of shadow. */
	/*
	 * protected int shadowOffset = 4;
	 *//** The transparency value of shadow. ( 0 - 255) */
	/*
	 * protected int shadowAlpha = 150;
	 * 
	 * private String stringTextToDisplay; private Prop prop = null;
	 * 
	 * float alpha = 1.0f; // current opacity of button Animator animator; //
	 * for later start/stop actions BufferedImage buttonImage = null;
	 * 
	 * private int ARROW_HEIGHT = 7; private int ARROW_WIDTH = 4; private int
	 * PADDING = 4; private int xPos; private int yPos; private int widthC;
	 * private int heightC; private Font fontC = new Font("Comic Sans MS",
	 * Font.BOLD, 15); public static enum
	 * PointDirection{LEFT_DOWN,CENTER_DOWN,RIGHT_DOWN}; private PointDirection
	 * directionC = PointDirection.LEFT_DOWN; JLabel innerLabel = new JLabel();
	 * 
	 * public ConversationBubble() { this(""); }
	 * 
	 * public ConversationBubble(String text) { super(); stringTextToDisplay =
	 * text; innerLabel.setFont(fontC); innerLabel.setForeground(Color.BLACK);
	 * innerLabel.setBackground(Color.WHITE); innerLabel.setOpaque(true);
	 * innerLabel.setHorizontalAlignment(JLabel.CENTER);
	 * innerLabel.setVerticalAlignment(JLabel.TOP); add(innerLabel); }
	 * 
	 * public void setPointDirection(PointDirection p) { directionC = p; }
	 *//**
	 * Set the bounds of the label capturing the necessary variables for
	 * drawing the bubble
	 * 
	 */
	/*
	 * public void setBounds(int x, int y, int width, int height) { xPos = x;
	 * yPos = y; widthC = width; heightC = height; ARROW_HEIGHT = widthC/8;
	 * ARROW_WIDTH = widthC/10; PADDING = widthC/15; super.setBounds(xPos, yPos,
	 * width, height); }
	 * 
	 * public void setFont(Font font) { fontC = font; super.setFont(font); }
	 *//**
	 * Draw a string in a speech bubble
	 * 
	 * @param text
	 *            , the string to draw
	 */
	/*
	 * private void paintString(String text) {
	 * innerLabel.setText("<html><p style=\"text-align:center\">" + text +
	 * "</p></html>"); innerLabel.setBounds(PADDING, PADDING/2,
	 * widthC-PADDING*2, heightC-PADDING-ARROW_HEIGHT); }
	 *//**
	 * Draw an empty speech bubble with the point in the center
	 * 
	 * @param g
	 *            , the graphics object to paint to
	 * @param x
	 *            , the x position of the object (relative to the pointy part)
	 * @param y
	 *            , the y position of the object (relative to the pointy part)
	 * @param height
	 *            , the height of the body part
	 * @param width
	 *            , the width of the body part
	 */
	/*
	 * private void drawBubblePointCenter(Graphics g, int x, int y, int width,
	 * int height) { // Save the graphics object color and font so that we may
	 * restore it later Color origColor = g.getColor(); Font origFont =
	 * g.getFont(); int numTriangleCoords = 3; int[] xcoords = new
	 * int[numTriangleCoords]; int[] ycoords = new int[numTriangleCoords];
	 * xcoords[0] = x; ycoords[0] = y; xcoords[1] = x - ARROW_WIDTH / 2;
	 * ycoords[1] = y - ARROW_HEIGHT; xcoords[2] = x + ARROW_WIDTH / 2;
	 * ycoords[2] = y - ARROW_HEIGHT;
	 * 
	 * // Draw the base shape -- the rectangle the image will fit into as well
	 * as its outline g.setColor(Color.WHITE); g.fillRoundRect(x - width / 2, y
	 * - height - ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);
	 * g.setColor(Color.BLACK); g.drawRoundRect(x - width / 2, y - height -
	 * ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);
	 * 
	 * // Next is to draw the pointy part that indicates who is speaking
	 * g.setColor(Color.WHITE); g.fillPolygon(xcoords, ycoords,
	 * numTriangleCoords); g.drawLine(xcoords[1] + 1, ycoords[1], xcoords[2] -
	 * 1, ycoords[2]);
	 * 
	 * // This is the outline to the pointy part g.setColor(Color.BLACK);
	 * g.drawLine(x, y, xcoords[1], ycoords[1]); g.drawLine(x, y, xcoords[2],
	 * ycoords[2]);
	 * 
	 * // Restore the font and color g.setColor(origColor); g.setFont(origFont);
	 * }
	 *//**
	 * Draw an empty speech bubble with the point on the left
	 * 
	 * @param g
	 *            , the graphics object to paint to
	 * @param x
	 *            , the x position of the object (relative to the pointy part)
	 * @param y
	 *            , the y position of the object (relative to the pointy part)
	 * @param height
	 *            , the height of the body part
	 * @param width
	 *            , the width of the body part
	 */
	/*
	 * private void drawBubblePointLeft(Graphics g, int x, int y, int width, int
	 * height) { // Save the graphics object color and font so that we may
	 * restore it later Color origColor = g.getColor(); Font origFont =
	 * g.getFont();
	 * 
	 * int numTriangleCoords = 3; int[] xcoords = new int[numTriangleCoords];
	 * int[] ycoords = new int[numTriangleCoords];
	 * 
	 * // Zac ZHANG: Get Text try { stringTextToDisplay = this.prop.getText();
	 * if (stringTextToDisplay == null) stringTextToDisplay = " "; try {
	 * ReadBehavior readBehavior = new ReadBehavior(prop.getBehavior()); if
	 * (readBehavior.getModel().trim().equals("Reward")) { //Reward reward =
	 * GameModel.getGameModelObject().getCharacter().getReward();
	 * edu.utdallas.gamespecification.Reward
	 * rewards=GameAdapter.getGameAdapterObj
	 * ().getGame().getCharacter().get(0).getRewards(); stringTextToDisplay =
	 * stringTextToDisplay + " " + rewards.getPoints() + " " ; //TO DO-- TITLE
	 * //if(reward.getTitle() != null) stringTextToDisplay +=
	 * " Your title is YYYYY" ; } } catch(Exception e){
	 * 
	 * } } catch(Exception e){ stringTextToDisplay = " "; }
	 * 
	 * // Zac ZHANG: Set the size Graphics2D graphics = (Graphics2D) g;
	 * Dimension dim = null; try{ dim =
	 * Util.panelDimension(this.prop.getSize()); }catch(Exception e){ dim =
	 * Util.panelDimension(Size.MEDIUM); }
	 * 
	 * // Zac ZHANG: Use new coordinate xcoords[0] = x; ycoords[0] = y - height
	 * + (int)dim.getHeight() - 5; xcoords[1] = x + ARROW_WIDTH; ycoords[1] = y
	 * - height + (int)dim.getHeight() - 5 - ARROW_HEIGHT; xcoords[2] = x +
	 * ARROW_WIDTH * 2; ycoords[2] = y - height + (int)dim.getHeight() - 5 -
	 * ARROW_HEIGHT;
	 * 
	 * // Zac ZHANG: Set the font graphics.setStroke(new BasicStroke());// Sets
	 * strokes to default, is better. try{ graphics.setFont(new
	 * Font(prop.getFont().toString(), Font.PLAIN,
	 * Integer.parseInt(this.prop.getTextSize()))); }catch(Exception e){
	 * graphics.setFont(new Font("Serif",Font.PLAIN,Integer.parseInt("12"))); }
	 * 
	 * // Draw the base shape -- the rectangle the image will fit into as well
	 * as its outline // Zac ZHANG: Get the color from the XML file try{
	 * g.setColor(Util.StringToColor(this.prop.getColor().toString().trim()));
	 * System.out.println("the color of the information box is: " +
	 * this.prop.getColor().toString().trim()); }catch(Exception e){
	 * g.setColor(Util.StringToColor("YELLOW")); }
	 * 
	 * // Zac ZHANG: Use the size in XML file to draw the shape
	 * g.fillRoundRect(x, y - height - ARROW_HEIGHT, (int)dim.getWidth()-5,
	 * (int)dim.getHeight()-5, PADDING * 2, PADDING * 2);
	 * g.setColor(Color.BLACK); // Zac ZHANG: Use the size in XML file to draw
	 * the shape g.drawRoundRect(x, y - height - ARROW_HEIGHT,
	 * (int)dim.getWidth()-5, (int)dim.getHeight()-5, PADDING * 2, PADDING * 2);
	 * 
	 * // Next is to draw the pointy part that indicates who is speaking // Zac
	 * ZHANG: Get the color from the XML file try{
	 * g.setColor(Util.StringToColor(this.prop.getColor().toString().trim()));
	 * }catch(Exception e){ g.setColor(Util.StringToColor("YELLOW")); }
	 * g.fillPolygon(xcoords, ycoords, numTriangleCoords); g.drawLine(xcoords[1]
	 * + 1, ycoords[1], xcoords[2] - 1, ycoords[2]);
	 * 
	 * // This is the outline to the pointy part g.setColor(Color.BLACK);
	 * g.drawLine(xcoords[0], ycoords[0], xcoords[1], ycoords[1]);
	 * g.drawLine(xcoords[0], ycoords[0], xcoords[2], ycoords[2]);
	 * 
	 * // Zac ZHANG: Add the text drawStringRect(graphics, 3, 3,
	 * (int)dim.getWidth()-5, (int)dim.getHeight()-5, 1.5f,
	 * stringTextToDisplay);
	 * 
	 * // Restore the font and color g.setColor(origColor); g.setFont(origFont);
	 * }
	 *//**
	 * Draw an empty speech bubble with the point on the right
	 * 
	 * @param g
	 *            , the graphics object to paint to
	 * @param x
	 *            , the x position of the object (relative to the pointy part)
	 * @param y
	 *            , the y position of the object (relative to the pointy part)
	 * @param height
	 *            , the height of the body part
	 * @param width
	 *            , the width of the body part
	 */
	/*
	 * private void drawBubblePointRight(Graphics g, int x, int y, int width,
	 * int height) { // Save the graphics object color and font so that we may
	 * restore it later Color origColor = g.getColor(); Font origFont =
	 * g.getFont(); int numTriangleCoords = 3; int[] xcoords = new
	 * int[numTriangleCoords]; int[] ycoords = new int[numTriangleCoords];
	 * xcoords[0] = x; ycoords[0] = y; xcoords[1] = x - ARROW_WIDTH; ycoords[1]
	 * = y - ARROW_HEIGHT; xcoords[2] = x - ARROW_WIDTH * 2; ycoords[2] = y -
	 * ARROW_HEIGHT;
	 * 
	 * // Draw the base shape -- the rectangle the image will fit into as well
	 * as its outline g.setColor(Color.WHITE); g.fillRoundRect(x-width, y -
	 * height - ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);
	 * g.setColor(Color.BLACK); g.drawRoundRect(x-width, y - height -
	 * ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);
	 * 
	 * // Next is to draw the pointy part that indicates who is speaking
	 * g.setColor(Color.WHITE); g.fillPolygon(xcoords, ycoords,
	 * numTriangleCoords); g.drawLine(xcoords[1] - 1, ycoords[1], xcoords[2] +
	 * 1, ycoords[2]);
	 * 
	 * // This is the outline to the pointy part g.setColor(Color.BLACK);
	 * g.drawLine(x, y, xcoords[1], ycoords[1]); g.drawLine(x, y, xcoords[2],
	 * ycoords[2]);
	 * 
	 * // Restore the font and color g.setColor(origColor); g.setFont(origFont);
	 * }
	 * 
	 * protected void paintComponent(Graphics g) { super.paintComponent(g);
	 * g.setColor(Color.BLACK); switch(directionC) { case LEFT_DOWN:
	 * drawBubblePointLeft(g,0,heightC,widthC-2,heightC-ARROW_HEIGHT-1); break;
	 * case CENTER_DOWN:
	 * drawBubblePointCenter(g,widthC/2,heightC,widthC-2,heightC
	 * -ARROW_HEIGHT-1); break; case RIGHT_DOWN:
	 * drawBubblePointRight(g,widthC,heightC,widthC-2,heightC-ARROW_HEIGHT-1);
	 * break; default: break; } paintString(stringTextToDisplay); }
	 * 
	 * public void setOpaque(boolean isOpaque) { Zac ZHANG: Disable this
	 * function boolean oldValue = getFlag(IS_OPAQUE); setFlag(IS_OPAQUE,
	 * isOpaque); setFlag(OPAQUE_SET, true); firePropertyChange("opaque",
	 * oldValue, isOpaque); }
	 * 
	 * public ConversationBubble(Prop prop) { super(); this.prop = prop;
	 * setOpaque(false);
	 * 
	 * System.out.println(
	 * "************************************************************");
	 * 
	 * Time time = prop.getType().getEvents().get(0).getTime(); List<Timer>
	 * timers = GameModel.getGameModelObject().getGameConstants().getTimers();
	 * int timerValue = 0; for(Timer timer:timers) {
	 * System.out.println("Timer name is..." + timer.getName());
	 * 
	 * if(timer.getName() != null && time != null){
	 * if(timer.getName().trim().equals(time.name())) { timerValue =
	 * Integer.parseInt(timer.getValue()); System.out.println("Timer value is" +
	 * timerValue); } } } //System.out.println("Timer Value is : "+timerValue +
	 * " *******$$$$$#############33"); int timerValue = 10000; animator = new
	 * Animator(timerValue,1, RepeatBehavior.REVERSE, this);
	 * animator.setStartFraction(1.0f);
	 * animator.setStartDirection(Direction.BACKWARD); animator.start(); }
	 * 
	 * private void drawStringRect(Graphics2D graphics, int x1, int y1, int x2,
	 * int y2, float interline, String txt) { AttributedString as = new
	 * AttributedString(txt); as.addAttribute(TextAttribute.FOREGROUND,
	 * graphics.getPaint()); as.addAttribute(TextAttribute.FONT,
	 * graphics.getFont()); AttributedCharacterIterator aci = as.getIterator();
	 * FontRenderContext frc = new FontRenderContext(null, true, false);
	 * LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc); float width = x2
	 * - x1 - 4;
	 * 
	 * while (lbm.getPosition() < txt.length()) { TextLayout tl =
	 * lbm.nextLayout(width); y1 += tl.getAscent(); tl.draw(graphics, x1 + 4,
	 * y1); y1 += tl.getDescent() + tl.getLeading() + (interline - 1.0f) *
	 * tl.getAscent(); if (y1 > y2) { break; } } }
	 * 
	 * //@Override public void timingEvent(float fraction) { alpha = fraction;
	 * if(alpha > 0){ repaint(); }else{ animator.stop(); } }
	 * 
	 * //@Override public void begin() {
	 * 
	 * }
	 * 
	 * //@Override public void end() {
	 * 
	 * }
	 * 
	 * //@Override public void repeat() {
	 * 
	 * }
	 */
}

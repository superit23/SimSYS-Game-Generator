package edu.utdallas.gamePlayEngine.model;

import edu.utdallas.gamePlayEngine.model.Location;
import edu.utdallas.gamePlayEngine.view.RoundedPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Field;

import javax.swing.JPanel;
import javax.swing.JLabel;

import simsys.schema.components.Profile;
import simsys.schema.components.Prop;

public class Util {

	// Change the string color to Color object.
	public static Color StringToColor(String col) {
		// Color c=new Color(255,0,0);
		Color c = null;
		if (col.equals("LIGHT_YELLOW")) {
			c = new Color(255, 255, 128);
		}
		if (col.equals("LIGHT_BLUE")) {
			c = new Color(32, 143, 255);
		}
		if (col.equals("LIGHT_TURQUOISE")) {
			c = new Color(126, 211, 188);
		}
		if (col.equals("LIGHT_PURPLE")) {
			c = new Color(188, 121, 255);
		}
		if (col.equals("LIGHT_RED")) {
			c = new Color(255, 117, 117);
		}
		if (col.equals("LIGHT_PINK")) {
			c = new Color(255, 193, 214);
		}
		if (col.equals("LIGHT_ORANGE")) {
			c = new Color(241, 142, 10);
		}
		if (col.equals("LIGHT_PEACH")) {
			c = new Color(255, 206, 132);
		}
		if (col.equals("RED") || col.equals("WHITE") || col.equals("YELLOW")
				|| col.equals("BLACK") || col.equals("GRAY")
				|| col.equals("LIGHT_GRAY") || col.equals("MAGENTA")
				|| col.equals("BLUE") || col.equals("DARK_GRAY")
				|| col.equals("ORANGE") || col.equals("PINK")
				|| col.equals("GREEN") || col.equals("CYAN")) {
			c = new Color(255, 0, 0);
			Field field;
			try {
				field = Color.class.getField(col);
				c = (Color) field.get(null);
			} catch (Exception e) {
				c = new Color(255, 0, 0);
			}
		}
		if (col.equals("PURPLE")) {
			c = new Color(130, 4, 255);
		}
		if (col.equals("TURQUOISE")) {
			c = new Color(62, 181, 148);
		}
		if (col.equals("DARK_YELLOW")) {
			c = new Color(244, 227, 11);
		}
		if (col.equals("DARK_BLUE")) {
			c = new Color(0, 0, 160);
		}
		if (col.equals("DARK_TURQUOISE")) {
			c = new Color(37, 109, 89);
		}
		if (col.equals("DARK_PURPLE")) {
			c = new Color(64, 0, 128);
		}
		if (col.equals("DARK_RED")) {
			c = new Color(196, 0, 0);
		}
		if (col.equals("DARK_PINK")) {
			c = new Color(232, 0, 75);
		}
		if (col.equals("DARK_ORANGE")) {
			c = new Color(241, 142, 10);
		}
		if (col.equals("DARK_PEACH")) {
			c = new Color(254, 163, 27);
		}
		if (col.equals("MEDIUM_PEACH")) {
			c = new Color(254, 182, 73);
		}
		if (col.equals("LIGHT_BROWN")) {
			c = new Color(222, 184, 135);
		}
		if (col.equals("MEDIUM_BROWN")) {
			c = new Color(205, 133, 63);
		}
		if (col.equals("DARK_BROWN")) {
			c = new Color(139, 69, 19);
		}

		// System.out.println("Inside Util!!");
		// System.out.println(col);

		/*
		 * Field field; try { field = Color.class.getField(col); c = (Color)
		 * field.get(null); } catch (Exception e) { c = new Color(255, 0, 0); }
		 * return c;
		 */
		/*
		 * Field field; Color clr; try { field = Color.class.getField(col); clr
		 * = (Color) field.get(); } catch (Exception e) {
		 * System.out.println("Inside Catch"); clr = new Color(255, 0, 0); }
		 */
		return c;
	}

	public static Dimension panelDimension(Size size) {
		Dimension dimension = new Dimension();
		switch (size) {
		case SMALL:
			dimension.height = 40;
			dimension.width = 150;
			break;
		case MEDIUM:
			dimension.height = 50;
			dimension.width = 200;
			break;
		case LARGE:
			dimension.height = 100;
			dimension.width = 200;
			break;
		case EXTRA_LARGE:
			dimension.height = 150;
			dimension.width = 200;
			break;
		case EXTRA_EXTRA_LARGE: // Zac ZHANG: Added for test game 4
			dimension.height = 200;
			dimension.width = 200;
			break;
		}
		return dimension;
	}

	public static JPanel panelPostionNew(int x, int y, int width, int height,
			boolean isInformationBox, simsys.schema.components.Prop prop) {

		JPanel jPanel = new JPanel();

		// TODO : Need to uncomment this.
		if (isInformationBox) {
			jPanel = new RoundedPanel(prop);
		}
		// check UUL, LLC
		jPanel.setBounds(x, y, width, height);
		jPanel.setOpaque(true);

		jPanel.setOpaque(false);
		System.out.println("Not info box!!!");

		return jPanel;

	}

	public static JPanel panelPosition(Location location,
			boolean isInformationBox, simsys.schema.components.Prop prop) {
		JPanel jPanel = new JPanel();

		// TODO : Need to uncomment this.
		if (isInformationBox) {
			jPanel = new RoundedPanel(prop);
		}
		// check UUL, LLC

		switch (location) {
		case UUR:
			jPanel.setBounds(-45, 174, 200, 160);
			jPanel.setOpaque(true);
			break;
		case T:
			jPanel.setBounds(0, 175, 200, 160);
			jPanel.setOpaque(true);
			break;
		case H:
			jPanel.setBounds(30, 176, 200, 160);
			jPanel.setOpaque(true);
			break;
		case Y:
			jPanel.setBounds(60, 177, 200, 160);
			jPanel.setOpaque(true);
			break;
		case A:
			jPanel.setBounds(90, 178, 200, 160);
			jPanel.setOpaque(true);
			break;
		case G:
			jPanel.setBounds(110, 179, 200, 160);
			jPanel.setOpaque(true);
			break;
		case U:
			jPanel.setBounds(140, 180, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RI:
			jPanel.setBounds(170, 181, 200, 160);
			jPanel.setOpaque(true);
			break;
		case TH:
			jPanel.setBounds(200, 182, 200, 160);
			jPanel.setOpaque(true);
			break;
		case IKA:
			jPanel.setBounds(230, 183, 200, 160);
			jPanel.setOpaque(true);
			break;
		case BI:
			jPanel.setBounds(260, 184, 200, 160);
			jPanel.setOpaque(true);
			break;
		case JU:
			jPanel.setBounds(290, 185, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LA:
			jPanel.setBounds(320, 186, 200, 160);
			jPanel.setOpaque(true);
			break;
		case I:
			jPanel.setBounds(350, 187, 200, 160);
			jPanel.setOpaque(true);
			break;
		case S:
			jPanel.setBounds(380, 188, 200, 160);
			jPanel.setOpaque(true);
			break;
		case AA:
			jPanel.setBounds(410, 189, 200, 160);
			jPanel.setOpaque(true);
			break;
		case CC:
			jPanel.setBounds(440, 190, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UURC:
			jPanel.setBounds(370, 170, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UURCC: // Zac ZHANG: Added for test game 4
			jPanel.setBounds(370, 220, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUC:
			jPanel.setBounds(370, 0, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UULC:
			jPanel.setBounds(600, 0, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUL:
			jPanel.setBounds(800, 0, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UR:
			jPanel.setBounds(600, 280, 200, 160);
			jPanel.setOpaque(true);
			break;
		case URC:
			jPanel.setBounds(600, 140, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UC:
			jPanel.setBounds(400, 160, 250, 700);
			jPanel.setOpaque(true);
			break;
		case ULC:
			jPanel.setBounds(-45, 180, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UL:
			jPanel.setBounds(200, 160, 250, 700);
			jPanel.setOpaque(true);
			break;
		case R:
			jPanel.setBounds(0, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RC:
			jPanel.setBounds(200, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case C:
			jPanel.setBounds(40, 370, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LC:
			jPanel.setBounds(50, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case L:
			jPanel.setBounds(800, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DR:
			jPanel.setBounds(0, 480, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DRC:
			jPanel.setBounds(100, 160, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DC:
			jPanel.setBounds(370, 350, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DLC:
			jPanel.setBounds(450, 240, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DL:
			jPanel.setBounds(400, 200, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DDR:
			jPanel.setBounds(0, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDRC:
			jPanel.setBounds(200, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDC:
			jPanel.setBounds(40, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDLC:
			jPanel.setBounds(600, 420, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDL:
			jPanel.setBounds(800, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		}

		jPanel.setOpaque(false);
		System.out.println("Not info box!!!");

		return jPanel;
	}

	public static JPanel hintPositionNew(int xValue, int yValue, int fixLocation) {
		JPanel jPanel = new JPanel();
		jPanel.setBounds(xValue, yValue + fixLocation, 200, 160);
		jPanel.setOpaque(false);
		System.out.println("Not info box!!!");

		return jPanel;
	}

	// Zac ZHANG: Added for hint in test game 4
	public static JPanel hintPosition(Location location, Profile prop,
			int fixLocation) {
		JPanel jPanel = new JPanel();

		switch (location) {
		case UUR:
			jPanel.setBounds(-45, 174 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case T:
			jPanel.setBounds(0, 175 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case H:
			jPanel.setBounds(30, 176 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case Y:
			jPanel.setBounds(60, 177 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case A:
			jPanel.setBounds(90, 178 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case G:
			jPanel.setBounds(110, 179 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case U:
			jPanel.setBounds(140, 180 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RI:
			jPanel.setBounds(170, 181 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case TH:
			jPanel.setBounds(200, 182 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case IKA:
			jPanel.setBounds(230, 183 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case BI:
			jPanel.setBounds(260, 184 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case JU:
			jPanel.setBounds(290, 185 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LA:
			jPanel.setBounds(320, 186 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case I:
			jPanel.setBounds(350, 187 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case S:
			jPanel.setBounds(380, 188 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case AA:
			jPanel.setBounds(410, 189 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case CC:
			jPanel.setBounds(440, 190 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UURC:
			jPanel.setBounds(370, 170 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UURCC: // Zac ZHANG: Added for test game 4
			jPanel.setBounds(370, 220 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUC:
			jPanel.setBounds(370, 0 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UULC:
			jPanel.setBounds(600, 0 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUL:
			jPanel.setBounds(800, 0 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UR:
			jPanel.setBounds(600, 280 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case URC:
			jPanel.setBounds(600, 140 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UC:
			jPanel.setBounds(400, 160 + fixLocation, 250, 700);
			jPanel.setOpaque(true);
			break;
		case ULC:
			jPanel.setBounds(-45, 180 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UL:
			jPanel.setBounds(200, 160 + fixLocation, 250, 700);
			jPanel.setOpaque(true);
			break;
		case R:
			jPanel.setBounds(0, 320 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RC:
			jPanel.setBounds(200, 320 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case C:
			jPanel.setBounds(40, 370 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LC:
			jPanel.setBounds(50, 320 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case L:
			jPanel.setBounds(800, 320 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DR:
			jPanel.setBounds(0, 480 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DRC:
			jPanel.setBounds(100, 160 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DC:
			jPanel.setBounds(370, 350 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DLC:
			jPanel.setBounds(450, 240 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DL:
			jPanel.setBounds(400, 200 + fixLocation, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DDR:
			jPanel.setBounds(0, 640 + fixLocation, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDRC:
			jPanel.setBounds(200, 640 + fixLocation, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDC:
			jPanel.setBounds(40, 640 + fixLocation, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDLC:
			jPanel.setBounds(600, 420 + fixLocation, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDL:
			jPanel.setBounds(800, 640 + fixLocation, 200, 160);
			// jPanel.setOpaque(true);
			break;
		}

		jPanel.setOpaque(false);
		System.out.println("Not info box!!!");

		return jPanel;
	}

	// Zac ZHANG: create a new class for ConversationBubble
	public static JLabel labelPosition(Location location, Prop prop) {
		// JLabel jPanel = new ConversationBubble(prop);
		// JLabel jPanel = new ConversationBubble();
		JLabel jPanel = new JLabel();
		// check UUL, LLC

		switch (location) {
		case UUR:
			jPanel.setBounds(-45, 174, 200, 160);
			jPanel.setOpaque(true);
			break;
		case T:
			jPanel.setBounds(0, 175, 200, 160);
			jPanel.setOpaque(true);
			break;
		case H:
			jPanel.setBounds(30, 176, 200, 160);
			jPanel.setOpaque(true);
			break;
		case Y:
			jPanel.setBounds(60, 177, 200, 160);
			jPanel.setOpaque(true);
			break;
		case A:
			jPanel.setBounds(90, 178, 200, 160);
			jPanel.setOpaque(true);
			break;
		case G:
			jPanel.setBounds(110, 179, 200, 160);
			jPanel.setOpaque(true);
			break;
		case U:
			jPanel.setBounds(140, 180, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RI:
			jPanel.setBounds(170, 181, 200, 160);
			jPanel.setOpaque(true);
			break;
		case TH:
			jPanel.setBounds(200, 182, 200, 160);
			jPanel.setOpaque(true);
			break;
		case IKA:
			jPanel.setBounds(230, 183, 200, 160);
			jPanel.setOpaque(true);
			break;
		case BI:
			jPanel.setBounds(260, 184, 200, 160);
			jPanel.setOpaque(true);
			break;
		case JU:
			jPanel.setBounds(290, 185, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LA:
			jPanel.setBounds(320, 186, 200, 160);
			jPanel.setOpaque(true);
			break;
		case I:
			jPanel.setBounds(350, 187, 200, 160);
			jPanel.setOpaque(true);
			break;
		case S:
			jPanel.setBounds(380, 188, 200, 160);
			jPanel.setOpaque(true);
			break;
		case AA:
			jPanel.setBounds(410, 189, 200, 160);
			jPanel.setOpaque(true);
			break;
		case CC:
			jPanel.setBounds(440, 190, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UURC:
			jPanel.setBounds(370, 170, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUC:
			jPanel.setBounds(370, 0, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UULC:
			jPanel.setBounds(600, 0, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UUL:
			jPanel.setBounds(800, 0, 200, 160);
			jPanel.setOpaque(true);
			break;

		case UR:
			jPanel.setBounds(600, 280, 200, 160);
			jPanel.setOpaque(true);
			break;
		case URC:
			jPanel.setBounds(600, 140, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UC:
			jPanel.setBounds(400, 160, 250, 700);
			jPanel.setOpaque(true);
			break;
		case ULC:
			jPanel.setBounds(-45, 180, 200, 160);
			jPanel.setOpaque(true);
			break;
		case UL:
			jPanel.setBounds(200, 160, 250, 700);
			jPanel.setOpaque(true);
			break;
		case R:
			jPanel.setBounds(0, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case RC:
			jPanel.setBounds(200, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case C:
			jPanel.setBounds(40, 370, 200, 160);
			jPanel.setOpaque(true);
			break;
		case LC:
			jPanel.setBounds(50, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case L:
			jPanel.setBounds(800, 320, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DR:
			jPanel.setBounds(0, 480, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DRC:
			jPanel.setBounds(100, 160, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DC:
			jPanel.setBounds(370, 350, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DLC:
			jPanel.setBounds(450, 240, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DL:
			jPanel.setBounds(400, 200, 200, 160);
			jPanel.setOpaque(true);
			break;
		case DDR:
			jPanel.setBounds(0, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDRC:
			jPanel.setBounds(200, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDC:
			jPanel.setBounds(40, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDLC:
			jPanel.setBounds(600, 420, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case DDL:
			jPanel.setBounds(800, 640, 200, 160);
			// jPanel.setOpaque(true);
			break;
		case LMC: // Zac ZHANG: Added a new location for test game 3
					// Conversation Bubble
			jPanel.setBounds(100, 80, 200, 160);
			break;
		}

		jPanel.setOpaque(false);
		System.out.println("Yes. it is in info box!!!");

		return jPanel;
	}
}

package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Image helper class. This file was included in the original GameGenerator
 */
public final class ImageHelper {
	/**
	 * Scales the image.
	 * @param orig
	 *            {@link BufferedImage}
	 * @param scale
	 *            {@link double}
	 * @return {@link BufferedImage}
	 */
	public static BufferedImage getScaledImage(BufferedImage orig,double scale) {
		if (orig.getWidth() <= 0 || orig.getHeight() <= 0 || scale <= 0)
			return null;

		int origW = orig.getWidth();
		int origH = orig.getHeight();

		double newW = origW * scale;
		double newH = origH * scale;

		BufferedImage resized = new BufferedImage((int) newW, (int) newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(orig, 0, 0, (int) newW, (int) newH, 0, 0, orig.getWidth(), orig.getHeight(), null);
		g.dispose();

		return resized;
	}

	/**
	 * Determines if the image can be loaded.
	 * @param path
	 *            {@link String}
	 * @return {@link boolean}
	 */
	public static boolean canLoadImage(final String path) {
		try {
			ImageIO.read(new File(path));
			return true;
		} catch (IOException e) { }
		
		return false;
	}
}

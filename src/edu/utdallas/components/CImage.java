package edu.utdallas.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/**
 * A custom JComponent which allows images to be shown, and automatically scale to the bounds of the component
 */
public class CImage extends JLabel {

	private static final long serialVersionUID = 2669367958443603768L;
	private BufferedImage image;
	
	public CImage(BufferedImage image)
	{
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponent(g);
	}
	

}

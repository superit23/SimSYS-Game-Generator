package edu.utdallas.gamePlayEngine.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image backgroundImage;

	// Some code to initialize the background image.
	// Here, we use the constructor to load the image. This
	// can vary depending on the use case of the panel.
	public JPanelWithBackground(String fileName) throws IOException {
		System.out.println("File name is" + fileName);
		backgroundImage = ImageIO.read(new File(fileName));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, this.getWidth(), this.getHeight(), this);
	}
}
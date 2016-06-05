package edu.utdallas.gamePlayEngine.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private Image image;
	private boolean tile;

	@SuppressWarnings("serial")
	public ImagePanel(Image image) {
		this.image = image;
		this.tile = false;
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setAction(new AbstractAction("Tile") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// tile = checkBox.isSelected();
				// repaint();
			}
		});
		// add(checkBox, BorderLayout.SOUTH);
		this.setOpaque(false);
		// Dimension size = new Dimension(image.getWidth(null),
		// image.getHeight(null));
		Dimension size = new Dimension(20, 30);
		// setPreferredSize(size);
		// setMinimumSize(size);
		// setMaximumSize(size);
		setSize(size);
		// setLayout(null);

	};

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (tile) {
			int iw = image.getWidth(this);
			int ih = image.getHeight(this);
			if (iw > 0 && ih > 0) {
				for (int x = 0; x < getWidth(); x += iw) {
					for (int y = 0; y < getHeight(); y += ih) {
						g.drawImage(image, x, y, iw, ih, this);
					}
				}
			}
		} else {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}

}

package edu.utdallas.gamePlayEngine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.simsysmain.InputWizard;

public class TriangleFrame extends JFrame {
	public static JButton button = new JButton();
	public static boolean submit = false, tooManyTriangles = false;
	public static int currentAngle = 0, currentAngleTemp = 0;
	public static TriangleFrame tri;
	public boolean gameWon = false;
	private Color yellowButton = new Color(255, 255, 0);
	private GameState gameState;

	public static TrianglePanel triangle;// = new TrianglePanel(new Point(100,
											// 10), new Point(50, 100), new
											// Point(150, 100));

	public TriangleFrame(int[] points, final GameState gameState) {
		triangle = new TrianglePanel(points);
		this.gameState = gameState;
		init();

	}

	private void init() {
		tri = this;
		this.setUndecorated(true);
		// this.setLocation(5,70);
		this.setLocation(InputWizard.window.getX() + 5,
				InputWizard.window.getY() + 70);

		// this.setLocation((int)InputWizard.getWindowLocation().getX(), (int)
		// InputWizard.getWindowLocation().getY());
		Color bBlue = new Color(54, 199, 214);
		triangle.setPreferredSize(new Dimension(1270, 645));
		triangle.setBackground(bBlue);
		button.setText("Submit");
		button.setLocation(375, 275);
		button.setSize(150, 50);
		button.setBackground(yellowButton);
		button.setFont(new Font("Aerial", Font.PLAIN, 16));
		button.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		this.add(button);
		// Action listener for submit button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Add the triange to the current angle
				currentAngle += currentAngleTemp;
				currentAngleTemp = 0;
				// User has gone over their desired amount
				if (currentAngle > GameView.desiredAngle) {
					currentAngleTemp = 0;
					// Close the frame
					tri.dispose();
					// Signal the GVF that the game was lost
					GameViewFrame.sbWin = false;
					GameViewFrame.btn.doClick();
				}
				// User has won the game
				if (currentAngle == GameView.desiredAngle) {
					currentAngleTemp = 0;
					// Close the frame
					tri.dispose();
					// Signal the GVF that
					GameViewFrame.sbWin = true;
					GameViewFrame.btn.doClick();
				}
				triangle.resetTrianglePosition();

				triangle.repaint();
				// tri.dispose();
			}
		});
		this.add(triangle);
		pack();
	}

	public static void redrawButton() {
		button.repaint();
	}

}

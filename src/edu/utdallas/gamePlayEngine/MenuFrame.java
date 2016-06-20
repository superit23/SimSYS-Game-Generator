package edu.utdallas.gamePlayEngine;

import edu.utdallas.gamePlayEngine.controller.GameController;
import edu.utdallas.gamePlayEngine.model.GameModelBoundary;
import edu.utdallas.gamePlayEngine.model.adapter.GameAdapter;
import edu.utdallas.gamePlayEngine.view.GameView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Sreeram Implements the menu of game engine
 *
 */
public class MenuFrame extends JFrame {
	/**
	 *
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * jFrame
	 */
	private JFrame jFrame = new JFrame();
	/**
	 * jPanel
	 */
	private JPanel jPanel;
	/**
	 * FRAME_WIDTH
	 */
	private static final int FRAME_WIDTH = 600;
	/**
	 * FRAME_HEIGHT
	 */
	private static final int FRAME_HEIGHT = 600;
	/**
	 * myFileChooser
	 */
	private JFileChooser myFileChooser = new JFileChooser();

	/**
	 * @param gameView
	 *            menu frame constructor
	 */
	public MenuFrame(final GameView gameView) {

		jPanel = new JPanel(new BorderLayout());
		// jPanel.setLocationRelativeTo(null);
		// jPanel.pack();
		jPanel.setVisible(true);
		jPanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		jPanel.setLayout(new BorderLayout());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jPanel.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.setOpaque(true);
		JMenu games = new JMenu("Games");
		JMenuItem openGame = new JMenuItem("Open Game");
		JMenuItem quit = new JMenuItem("Quit");

		openGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int retval = myFileChooser.showOpenDialog(MenuFrame.this);

				if (retval == JFileChooser.APPROVE_OPTION) {
					File myFile = myFileChooser.getSelectedFile();
					System.out.println("Opening File: " + myFile.toString());

					try {

						gameView.resetView();
						GameController gameController = new GameController(new GameAdapter(), gameView);

						final GameModelBoundary gameModelBoundary = gameController.getModelBoundary();
						gameModelBoundary.setView(gameView);
						gameModelBoundary.gmbEnd();
						gameModelBoundary.startGame(myFile.toString(), jPanel);

					} catch (Exception e) {
						System.out.println("Exception in GameViewFrame.java, startGame: " + e.toString());
					}
				}
			}
		});
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		games.add(openGame);
		games.addSeparator();
		games.add(quit);

		mainMenu.add(games);
		jFrame.setJMenuBar(mainMenu);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

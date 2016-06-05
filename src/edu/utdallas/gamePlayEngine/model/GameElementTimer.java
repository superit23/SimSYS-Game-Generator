package edu.utdallas.gamePlayEngine.model;

import edu.utdallas.gamePlayEngine.view.GameViewFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class GameElementTimer {

	public void startTimer(int time) {
		Timer timer = new Timer(time, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GameViewFrame.resetLayeredPane();

			}
		});
		timer.start();

	}

}

package edu.utdallas.gamePlayEngine.view;

import edu.utdallas.gamePlayEngine.controller.GameState;
import edu.utdallas.gamePlayEngine.model.Util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.Animator.Direction;
import org.jdesktop.animation.timing.Animator.RepeatBehavior;
import org.jdesktop.animation.timing.TimingTarget;

import simsys.schema.components.Prop;

/*
 * FadingButtonTF.java
 *
 * Created on May 3, 2007, 7:20 AM
 *
 * Copyright (c) 2007, Sun Microsystems, Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 * @author Sudharsan
 */
public class FadingButtonTF extends JButton implements ActionListener,
		TimingTarget {

	float alpha = 1.0f; // current opacity of button
	Animator animator; // for later start/stop actions
	int animationDuration = 2000; // each cycle will take 2 seconds
	BufferedImage buttonImage = null;

	private GameState gameState;
	@SuppressWarnings("rawtypes")
	private Prop prop;

	/** Creates a new instance of FadingButtonTF */
	public FadingButtonTF(GameState gameState) {
		// super(label);
		// setOpaque(true);
		/*
		 * super(((Prop) gameState.getGameElement()).getText()); this.gameState
		 * = gameState; this.prop = (Prop) gameState.getGameElement();
		 * setText(this.prop.getText()); if (this.prop.getColor() == null)
		 * setBackground(new Color(255, 255, 255)); else
		 * setBackground(Util.StringToColor(this.prop.getColor().toString()));
		 */

		super(((Prop) (gameState.getGameElementAdapter().getGameElement()))
				.getText().getText());
		this.gameState = gameState;
		this.prop = (Prop) gameState.getGameElementAdapter().getGameElement();
		setText(this.prop.getText().getText());
		if (this.prop.getColor() == null)
			setBackground(new Color(255, 255, 255));
		else
			setBackground(Util.StringToColor(this.prop.getColor().toString()));

		/*
		 * 
		 * 
		 * Time time = this.prop.getType().getEvents().get(0).getTime();
		 * List<Timer> timers =
		 * GameModel.getGameModelObject().getGameConstants().getTimers(); int
		 * timerValue = 0; for(Timer timer:timers) {
		 * System.out.println("Timer name is..." + timer.getName());
		 * if(timer.getName() != null){
		 * if(timer.getName().trim().equals(time.name())) { timerValue =
		 * Integer.parseInt(timer.getValue());
		 * System.out.println("Timer value is" + timerValue); } } }
		 */

		int timerValue = 10000;
		animator = new Animator(timerValue, 1, RepeatBehavior.REVERSE, this);
		animator.setStartFraction(1.0f);
		animator.setStartDirection(Direction.BACKWARD);
		// this.setBackground(Color.YELLOW);
		addActionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		// Create an image for the button graphics if necessary
		System.out.println("In the paint method !!!");
		// setOpaque(false);
		if (buttonImage == null || buttonImage.getWidth() != getWidth()
				|| buttonImage.getHeight() != getHeight()) {
			System.out.println("Yes.. Here it is");
			buttonImage = getGraphicsConfiguration().createCompatibleImage(
					getWidth(), getHeight());
			System.out.println("Yes.. Here it was");
		} else {
			setOpaque(false);
		}
		Graphics gButton = buttonImage.getGraphics();
		gButton.setClip(g.getClip());
		// gButton.setColor(Color.GREEN);
		// Have the superclass render the button for us
		super.paint(gButton);
		// setBackground(Color.YELLOW);
		// Make the graphics object sent to this paint() method translucent
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite newComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, alpha);
		g2d.setComposite(newComposite);

		// Copy the button's image to the destination graphics, translucently
		g2d.drawImage(buttonImage, 0, 0, null);
	}

	/**
	 * This method receives click events, which start and stop the animation
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("Animation sTarted !! WWohoh");
		animator.start();
	}

	// Ununsed MouseListener implementations
	@Override
	public void begin() {
	}

	@Override
	public void end() {/*
						 * 
						 * ReadBehavior readBehavior = new
						 * ReadBehavior(this.prop .getBehavior());
						 * 
						 * // Code if we get the behavior like this. //
						 * Reward.AddPoints.5000
						 * 
						 * if (readBehavior.getModel().trim().equals("Reward"))
						 * { // 2 Actions. // getPoints and AddPoints
						 * 
						 * if
						 * (readBehavior.getAction().trim().equals("AddPoints"))
						 * { if (readBehavior.getParameter() != null) { int
						 * pointsToAdd = Integer.parseInt(readBehavior
						 * .getParameter().trim());
						 * 
						 * // Get the Reward object and add the points. Reward
						 * reward = GameModel.getGameModelObject()
						 * .getCharacter().getReward();
						 * reward.addPoints(pointsToAdd); // Set the reward back
						 * to the game character.
						 * GameModel.getGameModelObject().getCharacter()
						 * .setReward(reward);
						 * System.out.println("Reward Points are" +
						 * reward.getPoints()); } } if
						 * (readBehavior.getAction().equals("getPoints")) {
						 * 
						 * } }
						 * 
						 * String next = this.prop.getNext();
						 * System.out.println("Next to display is" +
						 * this.prop.getNext());
						 * 
						 * // Start the nextscreen
						 * 
						 * if (next.contains("screen")) {
						 * GameController.startNextScreen(next, this.gameState);
						 * }
						 */
	}

	@Override
	public void repeat() {
	}

	/**
	 * TimingTarget implementation: this method sets the alpha of our button to
	 * be equal to the current elapsed fraction of the animation
	 */
	@Override
	public void timingEvent(float fraction) {
		alpha = fraction;
		if (alpha > 0) {
			repaint();
		} else {
			animator.stop();
		}
	}
}

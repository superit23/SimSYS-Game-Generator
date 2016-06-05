package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

import simsys.schema.components.Behavior;

/**
 * User: clocke. Date: 4/13/13 Time: 4:41 PM
 */
@XmlType(name = "EndGameBehavior")
public class EndGameBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public EndGameBehavior() {
	}

	/**
	 * Sets end game behavior details from another Behavior object.
	 * 
	 * @param behavior
	 *            {@link Behavior}
	 */
	public EndGameBehavior(final Behavior behavior) {
		super(behavior);
		// behavior.setDisplayName("End Game Behavior");
	}
}

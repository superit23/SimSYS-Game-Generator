package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

import simsys.schema.components.Behavior;

/**
 * User: clocke. Date: 4/13/13 Time: 4:37 PM
 */
@XmlType(name = "TransitionBehavior")
public class TransitionBehavior extends Behavior {
	/**
	 * Default constructor.
	 */
	public TransitionBehavior() {
	}

	/**
	 * Sets transition behavior based on another behavior's details.
	 * 
	 * @param behavior
	 *            {@link Behavior}
	 */
	public TransitionBehavior(final Behavior behavior) {
		super(behavior);
		// setTransition(null);
	}
}

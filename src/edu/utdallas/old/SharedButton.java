package edu.utdallas.old;

import simsys.schema.components.Behavior;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameObject;

/**
 * User: clocke. Date: 3/3/13 Time: 10:24 PM
 */
public class SharedButton extends GameObject {
	/**
	 * The button name.
	 */
	private String name;
	/**
	 * The button's behavior.
	 */
	private Behavior behavior;

	/**
	 * Default constructor.
	 */
	public SharedButton() {
		super();
	}

	/**
	 * Builds the button with the below items.
	 * 
	 * @param nameVal
	 *            {@link String}
	 * @param locX
	 *            {@link int}
	 * @param locY
	 *            {@link int}
	 * @param width
	 *            {@link int}
	 * @param height
	 *            {@link int}
	 * @param behaviorVal
	 *            {@link Behavior}
	 */
	public SharedButton(final String nameVal, final int locX, final int locY,
			final int width, final int height, final Behavior behaviorVal) {
		// super(locX, locY, width, height, null);
		this.name = nameVal;
		this.behavior = behaviorVal;
	}

	/**
	 * Gets the button name.
	 * 
	 * @return {@link String}
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the button name.
	 * 
	 * @param nameVal
	 *            {@link String}
	 */
	public final void setName(final String nameVal) {
		this.name = nameVal;
	}

	/**
	 * Returns the behavior.
	 * 
	 * @return {@link Behavior}
	 */
	public final Behavior getBehavior() {
		return behavior;
	}

	/**
	 * Sets the behavior.
	 * 
	 * @param behaviorVal
	 *            {@link Behavior}
	 */
	public final void setBehavior(final Behavior behaviorVal) {
		this.behavior = behaviorVal;
	}
}

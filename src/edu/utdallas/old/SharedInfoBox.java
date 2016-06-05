package edu.utdallas.old;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameObject;

/**
 * User: clocke. Date: 3/7/13 Time: 8:57 PM
 */

public class SharedInfoBox extends GameObject {
	/**
	 * Holds the name of the box.
	 */
	private String name;

	/**
	 * Default constructor.
	 */
	public SharedInfoBox() {
		super();
	}

	/**
	 * Builds a box with the following info.
	 * 
	 * @param locX
	 *            {@link int}
	 * @param locY
	 *            {@link int}
	 * @param width
	 *            {@link int}
	 * @param height
	 *            {@link int}
	 * @param pathToAsset
	 *            {@link String}
	 * @param nameVal
	 *            {@link String}
	 */
	public SharedInfoBox(final int locX, final int locY, final int width,
			final int height, final String pathToAsset, final String nameVal) {
		// super(locX, locY, width, height, pathToAsset);
		this.name = nameVal;
	}

	/**
	 * Returns the name.
	 * 
	 * @return {@link String}
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param nameVal
	 *            {@link String}
	 */
	public final void setName(final String nameVal) {
		this.name = nameVal;
	}
}

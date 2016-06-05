package edu.utdallas.old;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.CharacterAssetType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.ObjectMovementType;

import java.util.List;

/**
 * User: clocke. Date: 2/17/13 Time: 6:33 PM
 */
public class ObjectMovement {
	/**
	 * Holds the movement (animation) type for the object.
	 */
	private ObjectMovementType movementType;
	/**
	 * Start location, x value.
	 */
	private double startX;
	/**
	 * Start location, y value.
	 */
	private double startY;
	/**
	 * End location, x value.
	 */
	private double endX;
	/**
	 * End location, y value.
	 */
	private double endY;
	/**
	 * Speed of the animation.
	 */
	private double speed;
	/**
	 * List of animation assets.
	 */
	private List<CharacterAssetType> animationSequence;

	/**
	 * Default constructor.
	 */
	public ObjectMovement() {
	}

	/**
	 * Constructor for the movement. Sets a defaults speed of 1. Does not set
	 * the animation assets.
	 * 
	 * @param movement
	 *            {@link ObjectMovementType}
	 * @param startXVal
	 *            {@link double}
	 * @param startYVal
	 *            {@link double}
	 * @param endXVal
	 *            {@link double}
	 * @param endYVal
	 *            {@link double}
	 */
	public ObjectMovement(final ObjectMovementType movement,
			final double startXVal, final double startYVal,
			final double endXVal, final double endYVal) {
		this.movementType = movement;
		this.startX = startXVal;
		this.startY = startYVal;
		this.endX = endXVal;
		this.endY = endYVal;
		this.speed = 1;
	}

	/**
	 * Gets the movement type.
	 * 
	 * @return {@link ObjectMovementType}
	 */
	public final ObjectMovementType getMovementType() {
		return movementType;
	}

	/**
	 * Sets the object movement type.
	 * 
	 * @param movementVal
	 *            {@link ObjectMovementType}
	 */
	@XmlElement(name = "MovementType")
	public final void setMovementType(final ObjectMovementType movementVal) {
		this.movementType = movementVal;
	}

	/**
	 * Gets the starting X position.
	 * 
	 * @return {@link double}
	 */
	public final double getStartX() {
		return startX;
	}

	/**
	 * Sets the starting X Position.
	 * 
	 * @param startXVal
	 *            {@link double}
	 */
	@XmlElement(name = "StartX")
	public final void setStartX(final double startXVal) {
		this.startX = startXVal;
	}

	/**
	 * Gets the starting Y position.
	 * 
	 * @return {@link double}
	 */
	public final double getStartY() {
		return startY;
	}

	/**
	 * Sets the starting Y Position.
	 * 
	 * @param startYVal
	 *            {@link double}
	 */
	@XmlElement(name = "StartY")
	public final void setStartY(final double startYVal) {
		this.startY = startYVal;
	}

	/**
	 * Gets the ending X position.
	 * 
	 * @return {@link double}
	 */
	public final double getEndX() {
		return endX;
	}

	/**
	 * Sets the ending X position.
	 * 
	 * @param endXVal
	 *            {@link double}
	 */
	@XmlElement(name = "EndX")
	public final void setEndX(final double endXVal) {
		this.endX = endXVal;
	}

	/**
	 * Gets the ending Y position.
	 * 
	 * @return {@link double}
	 */
	public final double getEndY() {
		return endY;
	}

	/**
	 * Sets the ending Y position.
	 * 
	 * @param endYVal
	 *            {@link double}
	 */
	@XmlElement(name = "EndY")
	public final void setEndY(final double endYVal) {
		this.endY = endYVal;
	}

	/**
	 * Gets the speed.
	 * 
	 * @return {@link double}
	 */
	public final double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * 
	 * @param speedVal
	 *            {@link double}
	 */
	@XmlElement(name = "Speed")
	public final void setSpeed(final double speedVal) {
		this.speed = speedVal;
	}

	/**
	 * Gets the list of assets for the animation.
	 * 
	 * @return {@link CharacterAssetType}
	 */
	public final List<CharacterAssetType> getAnimationSequence() {
		return animationSequence;
	}

	/**
	 * Sets the list of assets for the animation.
	 * 
	 * @param assetList
	 *            {@link CharacterAssetType}
	 */
	@XmlElementWrapper(name = "AnimationSequences")
	@XmlElement(name = "AnimationSequence")
	public final void setAnimationSequence(
			final List<CharacterAssetType> assetList) {
		this.animationSequence = assetList;
	}
}

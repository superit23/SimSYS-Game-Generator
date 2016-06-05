package edu.utdallas.old;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import edu.utdallas.old.ConversationBubble.PointDirection;

/**
 * User: Jacob Dahleen. Date: 2/7/14 Time: 2:20 PM
 */
@XmlType(name = "ConversationBubbleAsset")
public class ConversationBubbleAsset extends Asset {
	/**
	 * The direction of the bubble's stem.
	 */
	private PointDirection point;

	/**
	 * Default constructor.
	 */
	public ConversationBubbleAsset() {
	}

	/**
	 * Constructor, takes an asset as the base.
	 * 
	 * @param asset
	 *            {@link Asset}
	 */
	public ConversationBubbleAsset(final Asset asset) {
		super(asset);
	}

	/**
	 * Gets the point direction.
	 * 
	 * @return {@link PointDirection}
	 */
	@XmlElement(name = "PointDirection")
	public final PointDirection getPointDirection() {
		return point;
	}

	/**
	 * Sets the point direction.
	 * 
	 * @param direction
	 *            {@link PointDirection}
	 */
	public final void setPointDirection(final PointDirection direction) {
		this.point = direction;
	}
}

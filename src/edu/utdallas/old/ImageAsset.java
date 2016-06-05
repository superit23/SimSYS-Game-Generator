package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

/**
 * User: clocke. Date: 4/13/13 Time: 4:00 PM
 */
@XmlType(name = "ImageAsset")
public class ImageAsset extends Asset {
	/**
	 * Default constructor.
	 */
	public ImageAsset() {
	}

	/**
	 * Sets the constructor using an Asset as the base.
	 * 
	 * @param asset
	 *            {@link Asset}
	 */
	public ImageAsset(final Asset asset) {
		super(asset);
	}
}

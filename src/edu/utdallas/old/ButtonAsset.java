package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

/**
 * User: clocke. Date: 4/13/13 Time: 4:22 PM
 */
@XmlType(name = "ButtonAsset")
public class ButtonAsset extends Asset {
	/**
	 * Default constructor.
	 */
	public ButtonAsset() {
	}

	/**
	 * Constructor, takes an Asset as the base.
	 * 
	 * @param asset
	 *            {@link Asset}
	 */
	public ButtonAsset(final Asset asset) {
		super(asset);
	}
}

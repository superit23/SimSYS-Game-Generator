package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

/**
 * User: clocke. Date: 4/13/13 Time: 4:18 PM
 */
@XmlType(name = "CharacterAsset")
public class CharacterAsset extends Asset {
	/**
	 * Default constructor.
	 */
	public CharacterAsset() {
	}

	/**
	 * Sets the character asset, using an Asset item as the base.
	 * 
	 * @param asset
	 *            {@link Asset}
	 */
	public CharacterAsset(final Asset asset) {
		super(asset);
	}
}

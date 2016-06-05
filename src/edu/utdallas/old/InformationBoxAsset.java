package edu.utdallas.old;

import javax.xml.bind.annotation.XmlType;

/**
 * User: clocke. Date: 4/13/13 Time: 4:20 PM
 */
@XmlType(name = "InformationBoxAsset")
public class InformationBoxAsset extends Asset {
	/**
	 * Default constructor.
	 */
	public InformationBoxAsset() {
	}

	/**
	 * Constructor, takes an asset.
	 * 
	 * @param asset
	 *            {@link Asset}
	 */
	public InformationBoxAsset(final Asset asset) {
		super(asset);
	}
}

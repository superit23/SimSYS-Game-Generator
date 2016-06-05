package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.Location;
import simsys.schema.components.Size;

@XmlRootElement(name = "GameObject")
public class GameObject {
	private Location location;
	private Size size;
	private String pathToAsset;
	private String text;

	public GameObject() { }

	/**
	 * Sets the object with the below data.
	 * @param sizeVal
	 *            {@link Size}
	 * @param locVal
	 *            {@link Location}
	 * @param path
	 *            {@link String}
	 */
	public GameObject(Size sizeVal, Location locVal, String path) {
		this.location = locVal;
		this.size = sizeVal;
		this.pathToAsset = path;
	}

	/**
	 * Gets the path to the asset.
	 * @return {@link String}
	 */
	public final String getPathToAsset() {
		return pathToAsset;
	}

	/**
	 * Sets the path to the asset.
	 * 
	 * @param path
	 *            {@link String}
	 */
	@XmlElement(name = "PathToAsset")
	public final void setPathToAsset(String path) {
		pathToAsset = path;
	}

	/**
	 * Gets the text.
	 * @return {@link String}
	 */
	public final String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * @param textVal
	 *            {@link String}
	 */
	@XmlElement(name = "Text")
	public final void setText(String textVal) {
		text = textVal;
	}

	/**
	 * Gets the location.
	 * @return {@link Location}
	 */
	@XmlElement(name = "Location")
	public final Location getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 * @param locVal
	 *            {@link Location}
	 */
	public final void setLocation(Location locVal) {
		location = locVal;
	}

	/**
	 * Gets the Size.
	 * @return {@link Size}
	 */
	@XmlElement(name = "Size")
	public final Size getSize() {
		return size;
	}

	/**
	 * Sets the Size.
	 * @param sizeVal
	 *            {@link Size.}
	 */
	public final void setSize(Size sizeVal) {
		size = sizeVal;
	}

}

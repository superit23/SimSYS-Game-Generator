package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.HashMap;
import java.util.Map;

public class GameCharacter {

	private String directory;
	private String prefix;
	private Map<CharacterAssetType, String> characterAssets;
	private String name;

	/**
	 * Builds the assets.
	 */
	private void buildCharacterAssets() {
		characterAssets = new HashMap<CharacterAssetType, String>();
		for (CharacterAssetType assetType : CharacterAssetType.values()) {
			characterAssets.put(assetType, buildCharacterAsset(assetType));
		}
	}

	/**
	 * Returns the string displaying the location in the filesystem of the
	 * asset.
	 * @param characterAssetType
	 *            {@link CharacterAssetType}
	 * @return {@link String}
	 */
	private String buildCharacterAsset(CharacterAssetType characterAssetType) {
		return directory + "\\" + prefix + "_" + characterAssetType.toString() + ".png";
	}

	/**
	 * Returns the character asset.
	 * @param characterAssetType
	 *            {@link CharacterAssetType}
	 * @return {@link String}
	 *
	 */
	public final String getCharacterAsset(CharacterAssetType characterAssetType) {
		if (characterAssets == null)
			buildCharacterAssets();

		return characterAssets.get(characterAssetType);
	}

	/**
	 * Returns the directory.
	 * @return {@link String}
	 */
	public final String getDirectory() {
		return directory;
	}

	/**
	 * Sets the directory.
	 * @param dir
	 *            {@link String}
	 */
	@XmlElement(name = "Directory")
	public final void setDirectory(String dir) {
		directory = dir;
	}

	/**
	 * Returns the prefix.
	 * @return {@link String}
	 */
	public final String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix.
	 * @param prefixVal
	 *            {@link String}
	 */
	@XmlElement(name = "Prefix")
	public final void setPrefix(String prefixVal) {
		prefix = prefixVal;
	}

	/**
	 * Gets the assets.
	 * @return {@link CharacterAssetType} {@link String}
	 */
	public final Map<CharacterAssetType, String> getCharacterAssets() {
		return characterAssets;
	}

	/**
	 * Sets the character assets.
	 * @param assetMap
	 *            {@link CharacterAssetType} {@link String}
	 */
	@XmlTransient
	public final void setCharacterAssets(Map<CharacterAssetType, String> assetMap) {
		characterAssets = assetMap;
	}

	/**
	 * Gets the name.
	 * @return {@link String}
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * @param nameVal
	 *            {@link String}
	 */
	@XmlElement(name = "Name")
	public final void setName(String nameVal) {
		name = nameVal;
	}
}

package edu.utdallas.old;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.CharacterAssetType;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.GameObject;
import edu.utdallas.gamegeneratorcollection.ComponentCreation.LearningActCharacterType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

/**
 * User: clocke. Date: 2/17/13 Time: 6:30 PM
 */
public class SharedCharacter extends GameObject {
	/**
	 * Holds the character type.
	 */
	private LearningActCharacterType characterType;
	/**
	 * Holds the movements (animations).
	 */
	private List<ObjectMovement> movements;
	/**
	 * Holds the asset type.
	 */
	private CharacterAssetType characterAssetType;

	/**
	 * Default constructor.
	 */
	public SharedCharacter() {
		super();
	}

	/**
	 * Builds a character with the following details.
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
	 * @param charType
	 *            {@link LearningActCharacterType}
	 * @param movementList
	 *            {@link ObjectMovement}
	 */
	public SharedCharacter(final int locX, final int locY, final int width,
			final int height, final String pathToAsset,
			final LearningActCharacterType charType,
			final List<ObjectMovement> movementList) {
		// super(locX, locY, width, height, pathToAsset);
		this.characterType = charType;
		this.movements = movementList;
	}

	/**
	 * Gets the character type.
	 * 
	 * @return {@link LearningActCharacterType}
	 */
	public final LearningActCharacterType getCharacterType() {
		return characterType;
	}

	/**
	 * Sets the character type.
	 * 
	 * @param charType
	 *            {@link LearningActCharacterType}
	 */
	@XmlElement(name = "CharacterType")
	public final void setCharacterType(final LearningActCharacterType charType) {
		this.characterType = charType;
	}

	/**
	 * Gets the object movement list.
	 * 
	 * @return {@link ObjectMovement}
	 */
	public final List<ObjectMovement> getMovements() {
		return movements;
	}

	/**
	 * Sets the movement list.
	 * 
	 * @param moveList
	 *            {@link ObjectMovement}
	 */
	@XmlElementWrapper(name = "Movements")
	@XmlElement(name = "Movement")
	public final void setMovements(final List<ObjectMovement> moveList) {
		this.movements = moveList;
	}

	/**
	 * Gets the asset type.
	 * 
	 * @return {@link CharacterAssetType}
	 */
	public final CharacterAssetType getCharacterAssetType() {
		return characterAssetType;
	}

	/**
	 * Sets the asset type.
	 * 
	 * @param assetType
	 *            {@link CharacterAssetType}
	 */
	@XmlElement(name = "CharacterAssetType")
	public final void setCharacterAssetType(final CharacterAssetType assetType) {
		this.characterAssetType = assetType;
	}
}

package edu.utdallas.gamegeneratorcollection.ComponentCreation;

/**
 *Possible character states represented with assests"
 */

public enum CharacterAssetType {
	STAND_SMILE_CLOSED("StandSmileClosed"),
	WALK_RIGHT_BEHIND("WalkRBehind"),
	WALK_LEFT_OPEN("WalkLOpen"),
	RIGHT_POINT_UP("RPointUp"),
	RIGHT_POINT_NO("RPointNo"),
	WALK_RIGHT_CLOSED("WalkRClosed"),
	WALK_RIGHT_OPEN("WalkROpen"),
	WALK_LEFT_BEHIND("WalkLBehind"),
	LEFT_POINT_NO("LPointNo"),
	LEFT_EVIL("LEvil"),
	LEFT_POINT_UP("LPointUp");

	private String value;

	/**
	 * @param typeVal The state of the character
	 */
	CharacterAssetType(final String typeVal) {
		this.value = typeVal;
	}

	/**
	 * Returns the value as a string.
	 * @return {@link String}
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * Returns the asset type. Will throw an illegal argument exception if a
	 * type not listed in this file is passed.
	 * @throws IllegalArgumentException
	 * @return {@link CharacterAssetType}
	 */
	public static CharacterAssetType fromValue(String value) throws IllegalArgumentException {
		for (CharacterAssetType character : CharacterAssetType.values()) {
			if (character.value.equals(value)) {
				return character;
			}
		}
		throw new IllegalArgumentException(value);
	}
}

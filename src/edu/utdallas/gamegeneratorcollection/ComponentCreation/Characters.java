package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simsys.schema.components.CharacterType;

@XmlRootElement(name = "Characters")
public class Characters {

	private GameCharacter player;
	private GameCharacter hero;
	private GameCharacter villain;
	//The alt (narrator) character details.
	private GameCharacter alt;

	/**
	 * @return {@link GameCharacter}
	 */
	public final GameCharacter getHero() {
		return hero;
	}

	/**
	 * Returns the appropriate character.
	 * @param characterType
	 *            {@link LearningActCharacterType}
	 * @return {@link GameCharacter}
	 */
	public final GameCharacter getCharacter(final CharacterType characterType) {
		switch (characterType) {
			case PROTAGONIST:
				return hero;
			case INTERLOCUTOR:
				return alt;
			case ANTAGONIST:
				return villain;
			case PLAYER:
				return player;
			default:
				break;
		}
		return null;
	}

	/**
	 * @param heroChar
	 *            {@link GameCharacter}
	 */
	@XmlElement(name = "Protagonist", required = true)
	public final void setHero(final GameCharacter heroChar) {
		hero = heroChar;
	}

	/**
	 * @return {@link GameCharacter}
	 */
	public final GameCharacter getVillain() {
		return villain;
	}

	/**
	 * @param villainChar
	 *            {@link GameCharacter}
	 */
	@XmlElement(name = "Antagonist", required = true)
	public final void setVillain(final GameCharacter villainChar) {
		this.villain = villainChar;
	}

	/**
	 * @return {@link GameCharacter}
	 */
	public final GameCharacter getAlt() {
		return alt;
	}

	/**
	 * @param altChar
	 *            {@link GameCharacter}
	 */
	@XmlElement(name = "Interlocutor", required = true)
	public final void setAlt(final GameCharacter altChar) {
		alt = altChar;
	}

	/**
	 * @return {@link GameCharacter}
	 */
	public final GameCharacter getPlayer() {
		return player;
	}

	/**
	 * @param playerChar
	 *            {@link GameCharacter}
	 */
	@XmlElement(name = "Player", required = true)
	public final void setPlayer(final GameCharacter playerChar) {
		player = playerChar;
	}
}

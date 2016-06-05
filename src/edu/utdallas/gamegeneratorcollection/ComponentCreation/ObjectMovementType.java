package edu.utdallas.gamegeneratorcollection.ComponentCreation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * User: clocke. Date: 2/24/13 Time: 8:46 PM
 */
@XmlType(name = "ObjectMovementType")
@XmlEnum
public enum ObjectMovementType {
	/**
	 * Walk onto the screen.
	 */
	WALK_ONTO_SCREEN,
	/**
	 * Walk off of the screen.
	 */
	WALK_OFF_SCREEN;
}

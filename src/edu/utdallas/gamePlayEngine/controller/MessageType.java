/**
 * This file contains an enumeration for type of message
 */
package edu.utdallas.gamePlayEngine.controller;

/**
 * An enumeration for MessageType. Additional MessageTypes are to be added to
 * support complex games.
 *
 */
public enum MessageType {
	Internal, // This type of message is generated within the Game e.g. Timer
	External // This type of message is due to user interaction.
}

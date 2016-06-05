package edu.utdallas.previewtool.Errors;

/**
 * The Class PreviewError.
 */
public abstract class PreviewError {

	/**
	 * The Enum Severity.
	 */
	public static enum Severity {
		/** The low. */
		LOW,
		/** The medium. */
		MEDIUM,
		/** The high. */
		HIGH
	};

	/**
	 * The Enum Level.
	 */
	public static enum Level {
		/** The game. */
		GAME,
		/** The act. */
		ACT,
		/** The scene. */
		SCENE,
		/** The screen. */
		SCREEN
	}

	/** The message. */
	private String message;

	/** The severity. */
	private Severity severity;

	/** The level. */
	private Level level;

	/**
	 * Instantiates a new preview error.
	 *
	 * @param l
	 *            the l
	 * @param s
	 *            the s
	 * @param msg
	 *            the msg
	 */
	public PreviewError(final Level l, final Severity s, final String msg) {
		level = l;
		severity = s;
		message = msg;
	}

	/**
	 * Instantiates a new preview error.
	 */
	public PreviewError() {
	}

	/**
	 * Gets the severity.
	 *
	 * @return the severity
	 */
	public final Severity getSeverity() {
		return severity;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public final Level getLevel() {
		return level;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * Fix error.
	 */
	public abstract void fixError();

	/**
	 * Gets the message as String.
	 *
	 * @return the message
	 */

	@Override
	public final String toString() {
		return message;
	}
}

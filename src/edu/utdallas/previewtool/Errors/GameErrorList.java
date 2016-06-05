package edu.utdallas.previewtool.Errors;

import java.util.ArrayList;

import edu.utdallas.previewtool.Errors.PreviewError.Severity;

/**
 * The Class GameErrorList.
 */
public class GameErrorList extends ArrayList<PreviewError> {

	/**
	 * Default SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Checks for critical errors.
	 *
	 * @return true, if successful
	 */
	public final boolean hasCriticalErrors() {
		for (PreviewError e : this) {
			if (e.getSeverity() == Severity.HIGH) {
				return true;
			}
		}
		return false;
	}
}

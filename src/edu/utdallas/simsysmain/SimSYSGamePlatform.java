package edu.utdallas.simsysmain;

/**
 * The sole responsibility for this class is to provide an entry point into the
 * SimSys system, which is achieved by launching the UI.
 * 
 * @author Sean
 *
 */
public final class SimSYSGamePlatform {
	/**
	 * Creates the UI object, which launches it as a result.
	 * 
	 * @param args
	 *            {@link String}
	 */
	public static void main(final String[] args) {
		@SuppressWarnings("unused")
		InputWizard inputs = new InputWizard();
	}

	/**
	 * Constructor. Should not be called directly.
	 */
	private SimSYSGamePlatform() {
		super();

	}

}

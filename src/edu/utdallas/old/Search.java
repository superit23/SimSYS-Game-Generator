package edu.utdallas.old;

import edu.utdallas.gamegeneratorcollection.ComponentSelection.SearchAlgo;

/**
 * 1) Initializes SearchInput - Creates a LinkedList of desired criteria. 2)
 * Initializes SearchAlgo - Uses SearchComponents List and SearchInput List for
 * SearchAlgo. 3) Returns the Search Results as an array.
 * */
public class Search {

	/**
	 * The number of components that are pulled from: Characters, Theme,
	 * Lessons, Challenges, Locale, and Subject. Most of the matrices and arrays
	 * in the ComponentSelection components of SimSys will be of this size.
	 */
	private static final int NUMCOMPONENTS = 6;
	/**
	 * The index used for character files in this component.
	 */
	private static final int CHARACTERINDEX = 0;
	/**
	 * The index used for lesson files in this component.
	 */
	private static final int LESSONSINDEX = 1;
	/**
	 * The index used for challenge files in this component.
	 */
	private static final int CHALLENGESINDEX = 2;
	/**
	 * The index used for locale files in this component.
	 */
	private static final int LOCALEINDEX = 3;
	/**
	 * The index used for subject files in this component.
	 */
	private static final int SUBJECTINDEX = 4;
	/**
	 * The index used for theme files in this component.
	 */
	private static final int THEMEINDEX = 5;
	/**
	 * The search algorithm.
	 */
	private SearchAlgo searchAlgo;
	/**
	 * The list of file strings.
	 */
	private String[] allFiles = new String[NUMCOMPONENTS];

	/**
	 * Default constructor. Sets searchAlgo and uses it to populate allFiles.
	 */
	public Search() {
		// searchAlgo = new SearchAlgo();
		allFiles = searchAlgo.searchResults();
		printAllFiles();

	}

	/*   *//**
	 * Returns the file location.
	 * 
	 * @return {@link String}
	 */
	/*
	 * public final String getFileLocation() { //return
	 * searchAlgo.getFileLocation(); }
	 */

	/**
	 * Prints the strings in allFiles to the console.
	 */
	public final void printAllFiles() {
		System.out.println(allFiles[CHARACTERINDEX]);
		System.out.println(allFiles[LESSONSINDEX]);
		System.out.println(allFiles[CHALLENGESINDEX]);
		System.out.println(allFiles[LOCALEINDEX]);
		System.out.println(allFiles[SUBJECTINDEX]);
		System.out.println(allFiles[THEMEINDEX]);
	}

	/**
	 * Gets allFiles.
	 * 
	 * @return {@link String}
	 */
	public final String[] getAllFiles() {
		return allFiles;
	}

}

package edu.utdallas.gamegeneratorcollection.ComponentSelection;

import edu.utdallas.gamegeneratorcollection.ComponentCreation.Layers;
//import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * Implements the search algorithm.
 * 
 * @author Sean
 *
 */
public class SearchAlgo {
	/**
	 * The number of components that are pulled from: Characters, Theme,
	 * Lessons, Challenges, Locale, and Subject. Most of the matrices built in
	 * this file will be using this as the size.
	 */
	private static final int NUMCOMPONENTS = 6;
	/**
	 * The index used for subject files in this component.
	 */
	private static final int SUBJECTINDEX = 4;
	/**
	 * For Character XML.
	 */
	private String xmlCharacters;
	/**
	 * For Character XML.
	 */
	private String xmlLessons;
	/**
	 * For Character XML.
	 */
	private String xmlChallenges;
	/**
	 * For Character XML.
	 */
	private String xmlLocale;
	/**
	 * For Character XML.
	 */
	private String xmlSubject;
	/**
	 * For Character XML.
	 */
	private String xmlTheme;
	/**
	 * Holds the list of files.
	 */
	private String[] allFiles = new String[NUMCOMPONENTS];
	/**
	 * For Character XML.
	 */
	private String[] gameComponents = { "Characters", "Lesson", "Challenge",
			"Locale", "Subject", "Theme" };
	/**
	 * Stores the file numbers (e.g. Characters0.xml, Theme1.xml)
	 */
	private int[] allFileNumbers = new int[NUMCOMPONENTS];

	/**
	 * Holds the UI.
	 */

	/**
	 * Constructor. The wizardInputs come from the UI, while the filePath is
	 * passed along to the next part of the pipeline, for when the game is
	 * exported.
	 * 
	 * @param wizardInputs
	 *            {@link Matrix}
	 * @param filePath
	 *            {@link String}
	 */
	public SearchAlgo(final Matrix[] wizardInputs, final String filePath) {

		xmlCharacters = "Characters0";
		xmlLessons = "Lesson0";
		xmlChallenges = "Challenge0";
		xmlLocale = "Locale0";
		xmlSubject = "Subject0";
		xmlTheme = "Theme0";
		Matrix[] componentInputs = new Matrix[NUMCOMPONENTS];

		SearchSpace[] searchSpaces = new SearchSpace[NUMCOMPONENTS];
		Matrix[] componentInputSearchSpace = new Matrix[NUMCOMPONENTS];

		for (int x = 0; x < componentInputs.length; x++) {
			searchSpaces[x] = new SearchSpace(gameComponents[x]);
			// searchSpace which should be from the metadata tags
			componentInputSearchSpace[x] = new Matrix(
					searchSpaces[x].getSearchSpace());
			// changes the SearchSpace array into a Matrix object
		}

		for (int x = 0; x < componentInputs.length; x++) {
			componentInputs[x] = new Matrix(
					new double[searchSpaces[x].getNumberOfCriteria()][searchSpaces[x]
							.getNumberOfCriteria()]);
		}

		componentInputs = wizardInputs;
		printAllMatrixes(componentInputs);
		// AHP Matrix Math
		for (int x = gameComponents.length - 1; x >= 0; x--) {
			System.out.println("Matrcies for " + gameComponents[x]);
			System.out.println("Search Input");
			printMatrix(componentInputs[x]); // brings the input into this class

			System.out.println("Weighted Matrix / Eigenvector");
			Matrix weightedMatrix = eigenvectorCalculation(componentInputs[x]);
			printMatrix(weightedMatrix);
			System.out.println("Component Metadata Input");
			printMatrix(componentInputSearchSpace[x]);
			Matrix criteriaScore = componentInputSearchSpace[x]
					.times(weightedMatrix);
			// multiplies the weighted score matrix by the input matrix.
			printMatrix(criteriaScore);
			allFileNumbers[x] = getLargestValue(criteriaScore, x);
			allFiles[x] = gameComponents[x] + allFileNumbers[x];
			System.out.println(allFiles[x]);

		} // end of loop with x
		Layers layers = new Layers(allFiles, filePath);
	}

	/**
	 * FAKE EIGENVECTOR CALCULATION The original eigenvector calculation through
	 * the library returned poor, unreadable results with no documentation. This
	 * prompted me (Kaleb) to find and employ this alternative method. Its
	 * basically weighting the matrix and putting into a 1 dimensional matrix.
	 * 
	 * @param inputMatrix
	 *            {@link Matrix}
	 * @return {@link Matrix}
	 */
	private Matrix eigenvectorCalculation(final Matrix inputMatrix) {
		double[][] inputArray = inputMatrix.getArray();
		double[][] outputArray = new double[inputArray.length][1];
		double[] rowSums = new double[inputArray.length];
		for (int y = 0; y < inputArray.length; y++) {
			for (int x = 0; x < inputArray[y].length; x++) {
				rowSums[x] += inputArray[x][y];
			}
		}
		double rowSumsTotal = 0;
		for (int x = 0; x < rowSums.length; x++) {
			rowSums[x] = Math.pow(rowSums[x], 1.0 / rowSums.length);
			rowSumsTotal += rowSums[x];
		}
		for (int x = 0; x < rowSums.length; x++) {
			rowSums[x] /= rowSumsTotal;
		}
		for (int x = 0; x < rowSums.length; x++) {
			outputArray[x][0] = rowSums[x];
		}
		return new Matrix(outputArray);
	}

	/**
	 * Prints the values in the matrices passed.
	 * 
	 * @param componentInputs
	 *            {@link Matrix}
	 */
	private void printAllMatrixes(final Matrix[] componentInputs) {
		for (int x = 0; x < componentInputs.length; x++) {
			System.out.println(gameComponents[x]);
			printMatrix(componentInputs[x]);
		}
	}

	/**
	 * Gets the largest value in a matrix.
	 * 
	 * @param in
	 *            {@link Matrix}
	 * @param componentNumber
	 *            {@link int}
	 * @return {@link int}
	 */
	public final int getLargestValue(final Matrix in, final int componentNumber) {
		double[][] inputArray = in.getArray();
		double largestValue = inputArray[0][0];
		int largestIndex = 0;
		for (int x = 0; x < inputArray.length; x++) {
			if (inputArray[x][0] > largestValue) {
				largestValue = inputArray[x][0];
				largestIndex = x;
			}
			if (inputArray[x][0] == largestValue
					&& (componentNumber == 1 || componentNumber == 2)
					&& x == allFileNumbers[SUBJECTINDEX]) {
				System.out.println("Adjusting "
						+ gameComponents[componentNumber] + " " + x
						+ " to Subject " + allFileNumbers[SUBJECTINDEX]);
				largestValue = inputArray[x][0];
				largestIndex = x;
			}
		} // end of loop with x
		return largestIndex;
	}

	/**
	 * Gets the last column in a matrix.
	 * 
	 * @param inputMatrix
	 *            {@link Matrix}
	 * @return {@link Matrix}
	 */
	public final Matrix getLastColumn(final Matrix inputMatrix) {
		double[][] inputArray = inputMatrix.getArray();
		double[][] outputArray = new double[inputArray[0].length][1];
		for (int x = 0; x < inputArray[0].length; x++) {
			outputArray[x][0] = inputArray[x][0];
		}

		return new Matrix(outputArray);
	}

	/**
	 * Prints out the passed matrix.
	 * 
	 * @param inputMatrix
	 *            {@link Matrix}
	 */
	public final void printMatrix(final Matrix inputMatrix) {
		double[][] inputArray = inputMatrix.getArray();
		for (int x = 0; x < inputArray.length; x++) {
			for (int y = 0; y < inputArray[x].length; y++) {
				System.out.printf("%.3f ", inputArray[x][y]);
			}
			System.out.println("");
		}
	}

	/**
	 * Returns the Character file name (e.g. Character0, Character1).
	 * 
	 * @return {@link String}
	 */
	public final String getCharacters() {
		return xmlCharacters;
	}

	/**
	 * Returns the Lesson file name. (e.g. Lesson0, Lesson1).
	 * 
	 * @return {@link String}
	 */
	public final String getLessons() {
		return xmlLessons;
	}

	/**
	 * Returns the Challenge file name. (e.g. Challenge0, Challenge1).
	 * 
	 * @return {@link String}
	 */
	public final String getChallenges() {
		return xmlChallenges;
	}

	/**
	 * Returns the Locale file name. (e.g. Locale0, Locale1).
	 * 
	 * @return {@link String}
	 */
	public final String getLocale() {
		return xmlLocale;
	}

	/**
	 * Returns the Subject file name. (e.g. Subject0, Subject1).
	 * 
	 * @return {@link String}
	 */
	public final String getSubject() {
		return xmlSubject;
	}

	/**
	 * Returns the Theme file name. (e.g. Theme0, Theme1).
	 * 
	 * @return {@link String}
	 */
	public final String getTheme() {
		return xmlTheme;
	}

	/**
	 * Returns the search results (the list of files to pull from).
	 * 
	 * @return {@link String}
	 */
	public final String[] searchResults() {
		return allFiles;
	}
}

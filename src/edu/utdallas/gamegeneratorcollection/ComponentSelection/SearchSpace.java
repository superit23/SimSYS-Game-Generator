package edu.utdallas.gamegeneratorcollection.ComponentSelection;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * Helps to define the search space used in the SearchAlgo class.
 *
 * This class not updated in the Summer 2014 update of the SimSys system.
 * Javadocs may contain inaccuracies due to unfamiliarity with this part of the
 * system.
 * 
 * @author Sean
 *
 */
public class SearchSpace {

	/**
	 * The size of the search space.
	 */
	private double[][] searchSpace;
	/**
	 * The number of criteria.
	 */
	private int criteria = 0;
	/**
	 * The number of rows in the space.
	 */
	private int row = 0;
	/**
	 * The number of columns in the space.
	 */
	private int col = 0;

	/**
	 * Default constructor.
	 * 
	 * @param type
	 *            {@link String}
	 */
	public SearchSpace(final String type) {
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document component = docBuilder.parse(new File("XMLmetadata//"
					+ type + ".xml"));

			component.getDocumentElement().normalize();

			NodeList listOfComponents = component.getElementsByTagName(type);
			int totalComponents = listOfComponents.getLength();
			// rowSizeCalculator counts ALL the leaf nodes which
			// is totalComponents*Criteria so divide that out
			rowSizeCalculator(listOfComponents, 0);
			criteria /= totalComponents;

			searchSpace = new double[totalComponents][criteria];
			xmlInputRecurr(listOfComponents, 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Gets the number of criteria.
	 * 
	 * @return {@link int}
	 */
	public final int getNumberOfCriteria() {
		return criteria;
	}

	/**
	 * Takes the input string, parses as double. Adjusts the row and column
	 * rates.
	 * 
	 * @param input
	 *            {@link String}
	 */
	private void matrixHandoff(final String input) {
		double inputDouble = Double.parseDouble(input);
		searchSpace[col][row] = inputDouble;
		row++;
		if (row >= searchSpace[col].length) {
			col++;
			row = 0;
		}
		if (col > searchSpace.length) {
			col = -1;
		}
	}

	/**
	 * Increments criteria.
	 */
	private void countUp() {
		criteria++;
	}

	/**
	 *
	 * @param inList
	 *            {@link NodeList}
	 * @param depthLevel
	 *            {@link int}
	 */
	private void rowSizeCalculator(final NodeList inList, final int depthLevel) {

		for (int j = 0; j < inList.getLength(); j++) {
			if (j % 2 == 1 || depthLevel == 0) {
				if (inList.item(j).getChildNodes().item(1) != null) {
					rowSizeCalculator(inList.item(j).getChildNodes(),
							depthLevel + 1);
				} else {
					countUp();
				}
			}
		}
	}

	/**
	 * This method takes the text context from the XML files in tree format and
	 * hands them off to be put in a matrix.
	 * 
	 * @param inList
	 *            the list of components, depth level of 0 {@link NodeList}
	 * @param depthLevel
	 *            {@link int}
	 *
	 */
	private void xmlInputRecurr(final NodeList inList, final int depthLevel) {
		for (int j = 0; j < inList.getLength(); j++) {
			if (j % 2 == 1 || depthLevel == 0) {
				if (inList.item(j).getChildNodes().item(1) != null) {
					xmlInputRecurr(inList.item(j).getChildNodes(),
							depthLevel + 1);
				} else {
					String output = inList.item(j).getTextContent().trim();

					matrixHandoff(output);
				}
			}
		}
	}

	/**
	 * Returns the search space.
	 * 
	 * @return {@link double}
	 */
	public final double[][] getSearchSpace() {
		return searchSpace;
	}
}

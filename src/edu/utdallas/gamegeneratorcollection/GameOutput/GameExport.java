package edu.utdallas.gamegeneratorcollection.GameOutput;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.SAXException;

import simsys.schema.components.Game;

/**
 * The sole responsibility of this class is to take a Game object and export it
 * to XML.
 * 
 * @author Sean
 *
 */
public class GameExport {

	/**
	 * Exports the Game object to xml. TODO currently this file does not
	 * validate against the schema. It provides errors related to the typing of
	 * GenericInteraction. Uncommenting the code below will allow a user to
	 * attempt schema validation.
	 * 
	 * @param game
	 *            the Game object containing the game
	 * @param exportFilename
	 *            the name of the file to export to
	 * @throws JAXBException
	 *             if the data is badly formed
	 * @throws SAXException
	 *             If the file is not correct.
	 */
	public final void exportGame(final Game game, final String exportFilename)
			throws JAXBException, SAXException {
		/*
		 * SchemaFactory sf = SchemaFactory.newInstance(
		 * XMLConstants.W3C_XML_SCHEMA_NS_URI); Schema schema = sf.newSchema(new
		 * File( "src\\edu\\utdallas\\gamespecification\\" + "Game.xsd"));
		 */
		JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		/*
		 * marshaller.setSchema(schema); marshaller.setEventHandler( new
		 * javax.xml.bind.helpers.DefaultValidationEventHandler());
		 */
		File file = new File(exportFilename);
		marshaller.marshal(game, file);
	}
}

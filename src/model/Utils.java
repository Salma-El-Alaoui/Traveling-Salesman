package model;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Class with useful tools
 */
public class Utils {

	/**
	 * Constructor
	 */
	public Utils() {
	}

	/**
	 * Validate the content of a XML file against an xsd file
	 * 
	 * @param File
	 *            file to be validated
	 * @param pathToXSDFile
	 *            path to the XSD validation sheet.
	 * @return "OK" if the file is correct
	 */
	public static String FileValidator(Document xmlDocument,
			String pathToXsdFile, Network n) throws InvalidNetworkFileException,
			InvalidDeliveryRequestFileException {

		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(n.getClass().getResource(pathToXsdFile).toString());
		Schema schema;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			if (pathToXsdFile.contains("plan")) {
				throw new InvalidNetworkFileException(e.getMessage());
			} else {
				throw new InvalidDeliveryRequestFileException(e.getMessage());
			}

		}

		// create a Validator instance, which can be used to validate an
		// instance document
		Validator validator = schema.newValidator();

		// validate the DOM tree
		try {
			try {
				validator.validate((Source) new DOMSource(xmlDocument));
			} catch (IOException e) {
				if (pathToXsdFile.contains("plan")) {
					throw new InvalidNetworkFileException(e.getMessage());
				} else {
					throw new InvalidDeliveryRequestFileException(
							e.getMessage());
				}
			}
		} catch (SAXException e) {
			// instance document is invalid!
			if (pathToXsdFile.contains("plan")) {
				String s = xmlDocument.getDocumentElement().getNodeName();
				if (s.equals("JourneeType")) {
					throw new InvalidNetworkFileException(
							"Vous essayez de charger des demandes de livraison et non un plan du reseau !");
				}
				throw new InvalidNetworkFileException(e.getMessage());
			} else {
				String s = xmlDocument.getDocumentElement().getNodeName();
				if (s.equals("Reseau")) {
					throw new InvalidNetworkFileException(
							"Vous essayez de charger un plan du reseau et non des demandes de livraisons!");
				}
				throw new InvalidDeliveryRequestFileException(e.getMessage());
			}

		}
		return "OK";

	}

}
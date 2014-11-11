package Model;

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
 * 
 */
public class Utils {

    /**
     * 
     */
    public Utils() {
    }

    /**
     * @param File 
     * @return
     */
    public static String FileValidator(Document xmlDocument, String pathToXsdFile) throws InvalidNetworkFileException {
       
    	 // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File(pathToXsdFile));
        Schema schema;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			return "Exception " + e.getMessage() + ", Caused by " + e.getCause();
		}

        // create a Validator instance, which can be used to validate an instance document
        Validator validator = schema.newValidator();
        
     // validate the DOM tree
        try {
            try {
				validator.validate((Source) new DOMSource(xmlDocument));
			} catch (IOException e) {
				return "Exception " + e.getMessage() + ", Caused by " + e.getCause();
			}
        } catch (SAXException e) {
            // instance document is invalid!
        	throw new InvalidNetworkFileException(e.getMessage());
        	
        }
    	return "OK";
        
    }

}
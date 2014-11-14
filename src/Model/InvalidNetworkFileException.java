package Model;

/**
 * 
 * Exception raised when Network File is not valid according to the XSD sheet defined (plan.xsd).
 * Wrapper for other XML parsing exceptions (SAX, IO ...)
 *
 */
public class InvalidNetworkFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public InvalidNetworkFileException(String msg) {
		super(msg);
	}

}

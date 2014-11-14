package Model;
/**
 * 
 * Exception raised when DeliveryRequest File is not valid according to the XSD sheet defined(livraison.xsd).
 * Wrapper for other XML parsing exceptions (SAX, IO ...)
 *
 */
public class InvalidDeliveryRequestFileException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidDeliveryRequestFileException(String msg) {
		super(msg);
	}
    
}

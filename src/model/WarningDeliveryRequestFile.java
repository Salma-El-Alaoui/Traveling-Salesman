package model;

public class WarningDeliveryRequestFile extends Exception {
	private static final long serialVersionUID = 3L;

	/**
	 * Warning during the parse (Delivery Request)
	 * 
	 * @param msg
	 *            String message
	 */
	public WarningDeliveryRequestFile(String msg) {
		super(msg);
	}
}

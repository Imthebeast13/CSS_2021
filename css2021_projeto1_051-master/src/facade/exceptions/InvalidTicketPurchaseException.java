package facade.exceptions;

public class InvalidTicketPurchaseException extends Exception {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 8679114907656056873L;
	
	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidTicketPurchaseException(String message, Exception e) {
		super (message, e);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidTicketPurchaseException(String message) {
		super (message);
	}

}

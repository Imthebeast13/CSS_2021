package facade.exceptions;

public class InvalidTicketPriceException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -4302957344605158979L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidTicketPriceException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidTicketPriceException (String message, Exception e) {
		super(message, e);
	}

}

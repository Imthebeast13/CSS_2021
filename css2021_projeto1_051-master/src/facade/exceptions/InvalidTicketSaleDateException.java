package facade.exceptions;

public class InvalidTicketSaleDateException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 3998015025123049882L;

	
	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidTicketSaleDateException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidTicketSaleDateException (String message, Exception e) {
		super(message, e);
	}

}

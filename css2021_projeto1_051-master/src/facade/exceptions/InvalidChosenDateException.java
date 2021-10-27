package facade.exceptions;

public class InvalidChosenDateException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -3100864741558366521L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidChosenDateException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidChosenDateException (String message, Exception e) {
		super(message, e);
	}

}

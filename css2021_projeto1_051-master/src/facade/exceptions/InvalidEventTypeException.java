package facade.exceptions;

public class InvalidEventTypeException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -5317605974071320157L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidEventTypeException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidEventTypeException (String message, Exception e) {
		super(message, e);
	}

}

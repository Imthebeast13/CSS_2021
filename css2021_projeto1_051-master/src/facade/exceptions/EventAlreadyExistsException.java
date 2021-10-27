package facade.exceptions;

public class EventAlreadyExistsException extends Exception {
	
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -6775358948342044435L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public EventAlreadyExistsException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public EventAlreadyExistsException (String message, Exception e) {
		super(message, e);
	}

}

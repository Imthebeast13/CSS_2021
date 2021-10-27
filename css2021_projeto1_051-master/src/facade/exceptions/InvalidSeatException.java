package facade.exceptions;

public class InvalidSeatException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -1030688246682801375L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidSeatException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidSeatException (String message, Exception e) {
		super(message, e);
	}

}

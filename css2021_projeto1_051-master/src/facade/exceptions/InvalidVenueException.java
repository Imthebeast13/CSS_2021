package facade.exceptions;

public class InvalidVenueException extends Exception {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 4418509450684904882L;
	
	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidVenueException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidVenueException (String message, Exception e) {
		super(message, e);
	}

}

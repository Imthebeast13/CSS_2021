package facade.exceptions;

public class OccupiedVenueException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8913580843845331689L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public OccupiedVenueException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public OccupiedVenueException (String message, Exception e) {
		super(message, e);
	}

}

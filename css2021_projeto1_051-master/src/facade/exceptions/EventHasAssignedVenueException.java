package facade.exceptions;

public class EventHasAssignedVenueException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -170262248498428910L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public EventHasAssignedVenueException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public EventHasAssignedVenueException (String message, Exception e) {
		super(message, e);
	}

}

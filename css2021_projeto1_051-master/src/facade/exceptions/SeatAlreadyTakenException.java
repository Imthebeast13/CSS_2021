package facade.exceptions;

public class SeatAlreadyTakenException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -8228944558524413798L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public SeatAlreadyTakenException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public SeatAlreadyTakenException (String message, Exception e) {
		super(message, e);
	}

}

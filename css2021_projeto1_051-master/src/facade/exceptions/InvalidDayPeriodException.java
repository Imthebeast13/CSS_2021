package facade.exceptions;

public class InvalidDayPeriodException extends Exception {
	
	
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -675095609794568929L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidDayPeriodException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidDayPeriodException (String message, Exception e) {
		super(message, e);
	}


}

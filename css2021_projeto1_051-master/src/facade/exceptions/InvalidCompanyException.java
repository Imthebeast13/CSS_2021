package facade.exceptions;

public class InvalidCompanyException extends Exception {
	
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -726043000047263662L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InvalidCompanyException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InvalidCompanyException (String message, Exception e) {
		super(message, e);
	}

}

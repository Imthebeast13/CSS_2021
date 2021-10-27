package facade.exceptions;

public class VenueNotFoundException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -8621615182341596015L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public VenueNotFoundException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public VenueNotFoundException (String message, Exception e) {
		super(message, e);
	}

}

package facade.exceptions;

public class EventNotFoundException extends Exception {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 9179506031716481838L;
	
	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public EventNotFoundException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public EventNotFoundException (String message, Exception e) {
		super(message, e);
	}

}

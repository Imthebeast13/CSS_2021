package facade.exceptions;

public class CompanyNotFoundException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -1714113271144436294L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public CompanyNotFoundException (String message) {
		super(message);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public CompanyNotFoundException (String message, Exception e) {
		super(message, e);
	}

}

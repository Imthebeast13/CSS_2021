package business.utils;

import java.util.Random;

/**
 * A mock utility class with mock payment utilities.
 *
 */
public class MockPayment {
	
	/**
	 * A utility class should not have public constructors
	 */
	private MockPayment () { }
	
	public static final Random RAND = new Random();
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int MIN_ENTITY_LENGTH = 4;
	
	
	/**
	 * Returns a random payment reference
	 * @return A random payment reference
	 */
	public static String generateMockReference() {
		return String.format("%04d", RAND.nextInt(10000)) + "-" + String.format("%04d", RAND.nextInt(10000)) + "-" + String.format("%04d", RAND.nextInt(10000)) + "-" + String.format("%04d", RAND.nextInt(10000));
	}
	
	/**
	 * Returns a random, (4 to 5) letter entity.
	 * @return A random payment entity
	 */
	public static String generateMockEntity () {
		StringBuilder sb = new StringBuilder();
		int len = MIN_ENTITY_LENGTH + RAND.nextInt(2); // For added name size diversity
		for (int i = 0; i < len; i++)
			sb.append(ALPHABET.charAt(RAND.nextInt(ALPHABET.length())));
	    return sb.toString().toUpperCase() + " Inc.";
		
	}
	

}

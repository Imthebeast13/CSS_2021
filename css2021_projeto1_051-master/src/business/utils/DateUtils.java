package business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * An utility class with date utilities.
 *
 */
public class DateUtils {
	
	private static SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final LocalDate MOCK_CURRENT_DATE = LocalDate.of(2021, 5, 1);
	
	/**
	 * An utility class should not have public constructors
	 */
	private DateUtils() {
	}

	/**
	 * Converts a string with the previously specified format to the respective date
	 * @param date A string representing the date
	 * @return A string with the previously specified format to the respective date
	 * @throws InvalidDateFormatException When the input is not in the expected format
	 */
	public static Date parse(String date) throws InvalidDateFormatException {
		try {
			return parser.parse(date);
		} catch (ParseException e) {
			throw new InvalidDateFormatException("The input was not in the expected format.");
		}
	}
	
	/**
	 * For test purposes only. 
	 * 
	 * Converts a string with the previously specified format to the respective date
	 * @param date A string representing the date
	 * @return A string with the previously specified format to the respective date
	 */
	public static Date fakeParse(String date) {
		try {
			return parser.parse(date);
		} catch (ParseException e) {
			// swallow exception and returns today
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * Converts the date component of a Date to LocalDate
	 * @param date The Date to convert
	 * @return The corresponding LocalDate
	 */
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	/**
	 * Converts the LocalDate to a Date
	 * WARNING: Note that the time component of the resulting Date is random.
	 * @param date The LocalDate to convert
	 * @return The corresponding Date
	 */
	public static Date toDate(LocalDate date) {
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Converts the time component of a Date to LocalTime
	 * @param date The Date to convert
	 * @return The corresponding LocalTime
	 */
	public static LocalTime toLocalTime(Date date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalTime();
	}
	
	/**
	 * Converts the LocalTime to a Date
	 * WARNING: Note that the date component of the resulting Date is random.
	 * @param date The LocalTime to convert
	 * @return The corresponding Date of the given date
	 */
	public static Date toDate(LocalTime time) {
		return Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Returns a mock, fixed date
	 * @return The mock current date
	 */
	public static LocalDate getMockCurrentDate() {
		return MOCK_CURRENT_DATE;
	}
	
}

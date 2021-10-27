
package facade.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.utils.DateUtils;

/**
 * Entity implementation class for Entity: DayPeriod.
 * A class representing a period of time in a single day.
 *
 */
@Embeddable
public class DayPeriod implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The start date with time
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
	private Date start;

	/**
	 * The end date with time
	 */
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
	private Date end;

	/**
	 * Constructor needed by JPA.
	 */
	public DayPeriod() {
	}

	/**
	 * Creates a new DayPeriod given its a start and end dates (with times)
	 * @param startDate The start date with time
	 * @param endDate The end date with time
	 */
	public DayPeriod(Date startDate, Date endDate) {
		this.start = startDate;
		this.end = endDate;
	}

	/**
	 * Returns the date of the DayPeriod 
	 * @return the date of the DayPeriod 
	 */
	public LocalDate getDate () {
		return DateUtils.toLocalDate(this.start);
	}

	/**
	 * Returns the start time of the DayPeriod 
	 * @return the start time of the DayPeriod 
	 */
	public LocalTime getStartTime () {
		return DateUtils.toLocalTime(this.start);
	}

	/**
	 * Returns the end time of the DayPeriod 
	 * @return the end time of the DayPeriod 
	 */
	public LocalTime getEndTime () {
		return DateUtils.toLocalTime(this.end);
	}
	
	/**
	 * Auxiliar method for validating whether both start and end dates
	 * of the event period are in the same day.
	 * 
	 * Returns the end date of the DayPeriod 
	 * @return the end date of the DayPeriod 
	 */
	public LocalDate getEndDate () {
		return DateUtils.toLocalDate(this.end);
	}

	@Override
	public String toString () {
		return getDate() + ", " + getStartTime() + " - " + getEndTime();
	}
	
	/**
	 * Checks if a dayperiod overlaps with another dayperiod (Date & Hours) 
	 * @return True if they overlap, false otherwise
	 */
	public boolean overlaps (DayPeriod other) {
		if (getDate().isEqual(other.getDate()))
			return getStartTime().isBefore(other.getEndTime()) && other.getStartTime().isBefore(getEndTime());
		return false;
	}


}
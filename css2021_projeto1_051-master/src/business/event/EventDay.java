package business.event;

import java.io.Serializable;
import javax.persistence.*;

import business.ticket.DailyTicket;
import facade.dtos.DayPeriod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static javax.persistence.CascadeType.ALL;

/**
 * Entity implementation class for Entity: EventDay
 * A class representing a day of an event
 */
@Entity
public class EventDay implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Embedded
	private DayPeriod day;
	
	@OneToMany(cascade = ALL)
	private List<DailyTicket> dailyTickets;

	/**
	 * Constructor needed by JPA.
	 */
	public EventDay () {
	}
	
	/**
	 * Creates a new EventDay given a day period
	 * @param day The day period
	 */
	public EventDay (DayPeriod day) {
		this.day = day;
	}
	
	/**
	 * Returns the DayPeriod 
	 * @return the DayPeriod 
	 */
	public DayPeriod getDayPeriod () {
		return this.day;
	}
	
	/**
	 * Returns the date of the event day
	 * @return the date of the event day 
	 */
	public LocalDate getDate () {
		return day.getDate();
	}
	
	/**
	 * Returns the list of daily tickets associated with the event day
	 * @return the list of daily tickets associated with the event day 
	 */
	public List<DailyTicket> getDailyTickets () {
		return dailyTickets;
	}

	/**
	 * Returns the start time of the event day
	 * @return the start time of the event day 
	 */
	public LocalTime getStartTime () {
		return day.getStartTime();
	}

	/**
	 * Returns the end time of the event day
	 * @return the end time of the event day 
	 */
	public LocalTime getEndTime () {
		return day.getEndTime();
	}
	
	/**
	 * Returns the id of the event day
	 * @return The id of the event day
	 */
	public int getId() {
		return id;
	}

	/**
	 * Adds a single daily ticket to the event day
	 */
	public void addDailyTicket(DailyTicket dt) {
		this.dailyTickets.add(dt);
		
	}
	
	@Override
	public String toString () {
		return getDate() + ", " + getStartTime() + " - " + getEndTime();
	}
	
   
}
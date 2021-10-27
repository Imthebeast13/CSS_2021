package business.event;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import business.company.Company;
import business.utils.DateUtils;
import business.venue.Venue;
import static javax.persistence.CascadeType.ALL;

/**
 * Entity implementation class for Entity: Event
 * Class that represents an event.
 *
 */
@Entity
@NamedQuery(name=Event.FIND_BY_DESIGNATION, query="SELECT e FROM Event e WHERE e.designation = :" + Event.EVENT_DESIGNATION)
public class Event implements Serializable {
	
	// Named query name constants
	
	public static final String FIND_BY_DESIGNATION = "Event.findByDesignation";
	public static final String EVENT_DESIGNATION = "designation";
	
	/**
	 * Event primary key. Needed by JPA.
	 */
	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = true) @Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@Column (nullable = false, unique = true) 
	private String designation;
	
	@OneToOne (optional = false)
	private Company company;
	
	@OneToOne 
	private Venue venue;
	
	@Column
	private boolean allowsTicketPass = false;
	
	@Column
	private double ticketPassPrice;
	
	@Temporal(TemporalType.DATE) 
	private Date sellingStart;
	
	@OneToMany(cascade = ALL) @JoinColumn
	private List<EventDay> days = new ArrayList<>();
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor needed by JPA.
	 */
	public Event () {
	}
	
	/**
	 * Constructs an Event given its data
	 * @param eventType The event type
	 * @param designation The name of the event
	 * @param company The responsible event promoter (company)
	 * @param days The list of event days
	 */
	public Event (EventType eventType, String designation, Company company, List<EventDay> days) {
		this.eventType = eventType; 
		this.designation = designation;
		this.company = company;
		this.days = days;
	}
	
	/**
	 * Sets the event's venue
	 * @param The desired venue
	 */
	public void setVenue (Venue venue) {
		this.venue = venue;
	}
	
	/**
	 * Sets the event's ticket selling start date
	 * @param The date in which the tickets are available to purchase
	 */
	public void setSellingStartDate (LocalDate sellingStart) {
		this.sellingStart = DateUtils.toDate(sellingStart);
	}
	
	/**
	 * Returns the event's ticket selling start date
	 * @param The event's ticket selling start date
	 */
	public LocalDate getSellingDateStart () {
		return DateUtils.toLocalDate(this.sellingStart);
	}
	
	/**
	 * Returns the event's event type id
	 * @return The event's event type id
	 */
	public EventType getEventType () {
		return eventType;
	}
	
	/**
	 * Returns the event's designation
	 * @return The event's designation
	 */
	public String getDesignation () {
		return designation;
	}
	
	/**
	 * Returns the event's company
	 * @return The event's company
	 */
	public Company getCompany () {
		return company;
	}
	
	/**
	 * Returns the event's venue
	 * @return The event's venue
	 */
	public Venue getVenue() {
		return this.venue;
	}
	
	/**
	 * Returns the event's days
	 * @return The event's days
	 */
	public List<EventDay> getEventDays () {
		return days;
	}
	
	/**
	 * Returns the event's ticket pass price
	 * @return The event's ticket pass price
	 */
	public double getTicketPassPrice () {
		return this.ticketPassPrice;
	}
	
	/**
	 * Sets the event's ticket pass price
	 * @param the event's ticket pass price
	 */
	public void setTicketPassPrice (double price) {
		this.ticketPassPrice = price;
	}
	
	/**
	 * Checks if the event allows creation and purchase of ticket passes
	 * @return True if if it allows ticket passes, false otherwise
	 */
	public boolean allowsTicketPass () {
		return this.allowsTicketPass;
	}
	
	/**
	 * Sets the event's ticket pass price
	 * @param the event's ticket pass price
	 */
	public void setTicketPassPermission (boolean value) {
		this.allowsTicketPass = value;
	}
	
	/**
	 * Returns the event's id
	 * @return The event's id
	 */
	public int getId () {
		return this.id;
	}


}

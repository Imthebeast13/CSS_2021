package business.ticket;

import java.io.Serializable;
import javax.persistence.*;

import business.event.Event;

import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static javax.persistence.DiscriminatorType.STRING;

/**
 * Entity implementation class for Entity: Ticket
 * Class that represents a Ticket for an event. It can be a DailyTicket or TicketPass.
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = STRING, name = "TICKET_TYPE")
public abstract class Ticket implements Serializable {
	
	@Id @GeneratedValue
	protected int id;

	@Column (nullable = false)
	protected double price;
	
	@OneToOne (optional = false)
	protected Event event;
	
	@Enumerated(EnumType.STRING) @Column (nullable = false)
	protected TicketStatus status = TicketStatus.AVAILABLE;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	protected Ticket() {
	}
	
	/**
	 * Protected constructor for a ticket.
	 * @param ev The event
	 * @param ticketPrice The ticket price
	 */
	protected Ticket(Event ev, double ticketPrice) {
		this.event = ev;
		this.price = ticketPrice;
	}

	/**
	 * Checks if the ticket is available
	 * @return True if it is available, False otherwise 
	 */
	public boolean isAvailable () {
		return this.status.equals(TicketStatus.AVAILABLE);
	}
	
	/**
	 * Returns the availability status of the ticket
	 * @return The availability status of the ticket
	 */
	public TicketStatus getStatus () {
		return this.status;
	}
	
	/**
	 * Updates the status of the ticket
	 * @param s The ticket status we're assigning to the ticket
	 */
	public void setStatus (TicketStatus s) {
		this.status = s;
	}
	
	/**
	 * Returns the price of the ticket
	 * @return The price of the ticket
	 */
	public double getPrice () {
		return this.price;
	}
	
	/**
	 * Returns the event of the ticket
	 * @return The event of the ticket
	 */
	public Event getEvent () {
		return this.event;
	}
	
	/**
	 * Returns the id of the ticket
	 * @return The id of the ticket
	 */
	public int getId() {
		return this.id;
	}
	
}	
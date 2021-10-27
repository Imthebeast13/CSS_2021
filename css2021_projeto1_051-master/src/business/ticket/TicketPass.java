package business.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import business.event.Event;

/**
 * Entity implementation class for Entity: TicketPass
 * Subclass of Ticket.
 *
 */
@Entity
@DiscriminatorValue(value = "Pass")
public class TicketPass extends Ticket {
	
	@OneToMany @JoinColumn 
	private List<DailyTicket> dailyTickets = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	protected TicketPass () {
	}
	
	/**
	 * Constructs a new TicketPass given an event
	 * @param event The event
	 */
	public TicketPass (Event event) {
		super(event, event.getTicketPassPrice());
	}
	
	/**
	 * Adds a daily ticket to the ticket pass
	 * @param s The daily ticket status we're adding to the pass
	 */
	public void addDailyTicket (DailyTicket t) {
		dailyTickets.add(t);
	}
	
	@Override
	public void setStatus (TicketStatus s) {
		this.status = s;
		for (DailyTicket t : dailyTickets)
			t.setStatus(s);
	}
	
	
}

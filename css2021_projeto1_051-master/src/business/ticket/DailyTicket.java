package business.ticket;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import business.event.Event;
import business.event.EventDay;
import business.seat.Seat;
import business.utils.DateUtils;

/**
 * Entity implementation class for Entity: DailyTicket.
 * Subclass of Ticket.
 *
 */
@Entity
@DiscriminatorValue(value = "Daily")
@NamedQueries({
	@NamedQuery(name=DailyTicket.FIND_SEATS_DATE_EVENT, query="SELECT t.seat FROM DailyTicket t WHERE t.status = business.ticket.TicketStatus.AVAILABLE AND t.event = :" + DailyTicket.EVENT_ID + " AND t.day = cast(:" + DailyTicket.EVENT_DATE + " as date) ORDER BY t.seat.seatLetter, t.seat.seatNumber"),
})
public class DailyTicket extends Ticket {

    public static final String FIND_SEATS_DATE_EVENT = "DailyTicket.getSeatsByDateAndEvent";
    public static final String EVENT_ID = "event";
    public static final String EVENT_DATE = "day";
    
	@Temporal(TemporalType.DATE)
	private Date day;
	
	@OneToOne(optional = true)
	private Seat seat;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public DailyTicket() {
	}

	/**
	 * Constructor for a DailyTicket for a SEATED event
	 * @param e The event
	 * @param seat The seat
	 * @param eventDay The event day in which it occurs
	 * @param ticketPrice The single ticket price
	 */
	public DailyTicket (Event e, Seat seat, EventDay eventDay, double ticketPrice) {
		super(e, ticketPrice);
		this.seat = seat;
		this.day = DateUtils.toDate(eventDay.getDate());
	}

	/**
	 * Constructor for a DailyTicket for a STANDING event
	 * @param e The event
	 * @param eventDay The event day in which it occurs
	 * @param ticketPrice The single ticket price
	 */
	public DailyTicket(Event e, EventDay eventDay, double ticketPrice) {
		super(e, ticketPrice);
		this.day = DateUtils.toDate(eventDay.getDate());
	}

	/**
	 * Returns the daily ticket's seat
	 * @return the daily ticket's seat
	 */
	public Seat getSeat () {
		return seat;
	}

	/**
	 * Returns the ticket's date
	 * @return the ticket's date
	 */
	public LocalDate getDate () {
		return DateUtils.toLocalDate(day);
	}

	@Override
	public String toString() {
		return "Ticket for the \"" + this.event.getDesignation() + "\", day: " + day + ". Seat: " + this.seat + ". Price: " + price + "€";
	}

}

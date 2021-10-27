package business.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.event.Event;
import business.seat.Seat;

/**
 * A catalog of tickets 
 *
 */
public class TicketCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	private EntityManager em;

	/**
	 * Constructs an event catalog given an entity manager
	 * @param em The entity manager
	 */
	public TicketCatalog(EntityManager em) {
		this.em = em;
	}

	/**
	 * Returns a list with all available seats for a determined event and date. If no seats are found an empty list
	 * is returned. The seats are sorted by letter and number. Returns an empty arraylist if none are found.
	 * @param event The event
	 * @param date The event date
	 * @return A list with all available seats, sorted by letter and number
	 */
	public List<Seat> getOrderedSeatsByEventAndDate(Event event, Date date) {
		try {
			TypedQuery<Seat> query = em.createNamedQuery(DailyTicket.FIND_SEATS_DATE_EVENT, Seat.class);
			query.setParameter(DailyTicket.EVENT_DATE, date);
			query.setParameter(DailyTicket.EVENT_ID, event);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}
	}
	
}

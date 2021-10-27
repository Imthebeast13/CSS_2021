package business.handlers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.EventDay;
import business.reservation.Reservation;
import business.ticket.DailyTicket;
import business.ticket.TicketPass;
import business.ticket.TicketStatus;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventNotFoundException;
import facade.exceptions.InvalidTicketPurchaseException;

/**
 * Handles the buy ticket passes use case
 */
public class BuyTicketPassesHandler {

	private static final int MAX_POSSIBLE_VALUE = Integer.MAX_VALUE;
	
	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;

	private Event event;
	private int nAvailableTickets;

	/**
	 * Creates a handler for the buy ticket passes use case given
	 * the application's entity manager factory
	 * @param emf The entity manager factory of the application
	 */
	public BuyTicketPassesHandler (EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Returns the number of ticket passes available for the event with the given designation
	 * 
	 * @param eventDesignation the event designation
	 * @return the number of ticket passes available for the event with the given designation
	 * @throws ApplicationException if an error occurs while attempting to fetch the number of ticket
	 * of ticket passes available
	 */
	public int getNumberOfTicketPasses (String eventDesignation) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();

			EventCatalog eventCatalog = new EventCatalog(em);

			Event e = eventCatalog.getEvent(eventDesignation);
			if (e == null) 
				throw new EventNotFoundException ("Could not find an event named " + eventDesignation);
			
			if (e.getSellingDateStart().isAfter(DateUtils.getMockCurrentDate()))
				throw new InvalidTicketPurchaseException ("Tickets for the event \"" + eventDesignation + "\" are not available yet");

			this.event = e;

			if (!event.allowsTicketPass()) 
				throw new InvalidTicketPurchaseException ("The event named \"" + eventDesignation + "\" does not support Ticket Pass selling.");

			List<EventDay> eventDays = event.getEventDays();

			int minimum = MAX_POSSIBLE_VALUE;
			int current = 0;

			for (EventDay d : eventDays) {
				current = getNumberOfFreeDailyTickets(d);
				if (current < minimum)
					minimum = current;
			}

			this.nAvailableTickets = minimum;
			em.getTransaction().commit();

			return nAvailableTickets;

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error trying to fetch number of available daily passes for the event \"" + eventDesignation + "\"", e);

		} finally {
			em.close();
		}

	}

	/**
	 * Returns the number available daily tickets, given an event day.
	 * 
	 * @param d The event day
	 * @return the number of available daily tickets in the given event day
	 */
	private int getNumberOfFreeDailyTickets(EventDay d) {
		int counter = 0;
		for (DailyTicket t : d.getDailyTickets()) {
			if (t.isAvailable())
				counter++;
		}
		return counter;
	}

	/**
	 * Makes a reservation for a given number of ticket passes for the previously named event,
	 * and returns the payment details
	 * 
	 * @param nPasses Number of desired ticket passes
	 * @param userEmail The user's email adress
	 * @return The reservation's payment details
	 * @throws ApplicationException if an error occurs attempting to make the reservation
	 */
	public String chooseTicketPassQuantity (int nPasses, String userEmail) throws ApplicationException {

		if (nPasses > nAvailableTickets)
			throw new ApplicationException ("Error: Specified " + nPasses + " ticket passes when there are only " + nAvailableTickets + " available.");

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			event = em.find(Event.class, event.getId());
			
			Reservation res = new Reservation (userEmail);
			em.persist(res);

			for (int i = 0; i < nPasses; i++) {

				TicketPass pass = new TicketPass (event);
				em.persist(pass);

				for (EventDay d : event.getEventDays()) {
					DailyTicket t = getSingleDailyTicket (d);
					pass.addDailyTicket(t);
				}
				pass.setStatus(TicketStatus.RESERVED);
				res.addTicket(pass);
			}

			res.finishReservation();
			em.getTransaction().commit();
			
			return res.getPaymentDetails();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error creating a reservation for a ticket pass, for the event \"" + event.getDesignation() + "\"", e);

		} finally {
			em.close();
		}

	}

	/**
	 * Returns the first daily ticket it finds in the given Event Day
	 * 
	 * @param d The EventDay
	 * @return An available daily ticket in the event day d.
	 * @throws ApplicationException If an error occurs attempting to find a free daily ticket.
	 */
	private DailyTicket getSingleDailyTicket(EventDay d) throws ApplicationException {
		for (DailyTicket t : d.getDailyTickets()) {
			if (t.isAvailable())
				return t;
		}
		throw new ApplicationException ("Could not find any free daily tickets for the date " + d.getDate() + " (Event: " + event.getDesignation() + ")");
	}

}

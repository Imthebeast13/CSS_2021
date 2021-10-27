package business.handlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.EventDay;
import business.reservation.Reservation;
import business.seat.Seat;
import business.ticket.DailyTicket;
import business.ticket.TicketCatalog;
import business.ticket.TicketStatus;
import business.utils.DateUtils;
import facade.dtos.SeatDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventNotFoundException;
import facade.exceptions.InvalidChosenDateException;
import facade.exceptions.InvalidSeatException;
import facade.exceptions.InvalidTicketPurchaseException;
import facade.exceptions.SeatAlreadyTakenException;

/**
 * Handles the buy daily tickets use case
 */
public class BuyDailyTicketsHandler {

	private Event event;
	private EventDay currentEventDay;
	private List<LocalDate> availableDates = new ArrayList<>();

	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;

	/**
	 * Creates a handler for the buy daily tickets use case given
	 * the application's entity manager factory
	 * @param emf The entity manager factory of the application
	 */
	public BuyDailyTicketsHandler (EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Returns the dates in which the event still has available daily tickets
	 * 
	 * @param eventDesignation The event's name
	 * @return The dates in which the event still has available daily tickets
	 * @throws ApplicationException if an error occurs while fetching the dates
	 */
	public List<LocalDate> getAvailableDates (String eventDesignation) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		EventCatalog eventCatalog = new EventCatalog(em);

		try {
			em.getTransaction().begin();

			Event e = eventCatalog.getEvent(eventDesignation);
			if (e == null) 
				throw new EventNotFoundException ("Could not find an event named " + eventDesignation);

			if (!e.getEventType().isSeated())
				throw new InvalidTicketPurchaseException ("The event " + e.getDesignation() + " is not an event with individual seats.");
			
			if (e.getVenue() == null)
				throw new InvalidTicketPurchaseException ("Tickets for the event \"" + eventDesignation + "\" are not available yet, as the event has no assigned venue");

			if (e.getSellingDateStart().isAfter(DateUtils.getMockCurrentDate()))
				throw new InvalidTicketPurchaseException ("Tickets for the event \"" + eventDesignation + "\" are not available yet");

			this.event = e;
			List<LocalDate> uniqueDates = new ArrayList<>();

			for (EventDay d : event.getEventDays()) {
				for (DailyTicket t : d.getDailyTickets()) {
					LocalDate ld = t.getDate();
					if (ld.equals(d.getDate()) && t.isAvailable()) {
						uniqueDates.add(ld);
						break;
					}
				}
			}
			em.getTransaction().commit();
			this.availableDates = uniqueDates;
			return availableDates;

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error trying to get available dates for the event \"" + eventDesignation + "\"", e);

		} finally {
			em.close();
		}
	}

	/**
	 * Returns the available seats for a given date of the event
	 * 
	 * @param date The specified date
	 * @return the list of available seats for the given date
	 * @throws ApplicationException if an error occurs while attempting to retrieve the seats
	 */
	public List<SeatDTO> chooseDate (LocalDate date) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		TicketCatalog ticketCatalog = new TicketCatalog (em);

		try {
			boolean foundDate = false;
			for (EventDay ed : event.getEventDays()) {
				if (ed.getDate().isEqual(date)) {
					if (!availableDates.contains(ed.getDate()))
						throw new InvalidChosenDateException ("The chosen date has no available tickets."); 
					this.currentEventDay = ed;
					foundDate = true;
					break;
				}
			}
			if (!foundDate) 
				throw new InvalidChosenDateException ("The chosen date does not belong to the event."); 

			em.getTransaction().begin();

			Date confirmedDate = DateUtils.toDate(date);

			List<SeatDTO> seatDTOs = new ArrayList<>();
			for (Seat s : ticketCatalog.getOrderedSeatsByEventAndDate(event, confirmedDate))
				seatDTOs.add(new SeatDTO (s.getSeatLetter(), s.getSeatNumber()));
			em.getTransaction().commit();
			return seatDTOs;

		} catch (Exception e) {
			throw new ApplicationException("Error selecting the desired event date", e);
		}
	}


	/**
	 * Makes a reservation for the given seats and returns the payment details for the reservation.
	 * 
	 * @param seats The list of seats the user wishes to reserve
	 * @param userEmail The user's email
	 * @return The payment details for the reservation
	 * @throws ApplicationException if an error occurs while attempting to make the reservation
	 */
	public String chooseSeats (List<SeatDTO> seats, String userEmail) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			Reservation res = new Reservation (userEmail);
			
			currentEventDay = em.find(EventDay.class, currentEventDay.getId());

			for (SeatDTO sd : seats) {
				boolean found = false;
				
				for (DailyTicket t : currentEventDay.getDailyTickets()) {
					Seat s = t.getSeat();
					if (s.getSeatLetter().equals(sd.getSeatLetter()) && s.getSeatNumber() == sd.getSeatNumber()) {
						found = true;
						if (t.isAvailable()) {
							t.setStatus(TicketStatus.RESERVED);
							res.addTicket(t);
						}else {
							throw new SeatAlreadyTakenException ("The seat " + t.getSeat() + " is already reserved (Date " + currentEventDay.getDate() + ")");
						}
						break;
					} 
				}
				if (!found)
					throw new InvalidSeatException ("Could not find the seat " + sd + " in the event " + event.getDesignation());
			}
			
			res.finishReservation();
			em.persist(res);
			
			em.getTransaction().commit();
			
			return res.getPaymentDetails();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error trying to make a reservation for a list of daily tickets", e);

		} finally {
			em.close();
		}

	}




}

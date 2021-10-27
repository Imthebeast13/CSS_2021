package business.handlers;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.event.Event;
import business.event.EventCatalog;
import business.event.EventDay;
import business.event.EventType;
import business.seat.Seat;
import business.ticket.DailyTicket;
import business.utils.DateUtils;
import business.venue.SeatedVenue;
import business.venue.Venue;
import business.venue.VenueCatalog;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventHasAssignedVenueException;
import facade.exceptions.EventNotFoundException;
import facade.exceptions.InvalidTicketPriceException;
import facade.exceptions.InvalidTicketSaleDateException;
import facade.exceptions.InvalidVenueException;
import facade.exceptions.OccupiedVenueException;

/**
 * Handles the assign venue to event use case
 *
 */
public class AssignVenueHandler {

	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;

	private Event event;

	/**
	 * Creates a handler for theassign venue to event use case given
	 * the application's entity manager factory
	 * @param emf The entity manager factory of the application
	 */
	public AssignVenueHandler (EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Given the required venue and event data, assigns a venue to an event, and creates the
	 * event tickets.
	 * 
	 * @param eventDesignation The event's name
	 * @param venueName The venue's name
	 * @param sellingStart The date in which the selling of the tickets begins
	 * @param ticketPrice The daily ticket price
	 * @throws ApplicationException if an error occurs while attempting to assign the venue to the event
	 */
	public void assignVenueToEvent (String eventDesignation, String venueName, LocalDate sellingStart, double ticketPrice) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		VenueCatalog venueCatalog = new VenueCatalog(em);

		try {
			em.getTransaction().begin();

			Event event = eventCatalog.getEvent(eventDesignation);
			if (event == null)
				throw new EventNotFoundException ("Could not find an event with designation \"" + eventDesignation + "\"");

			if (event.getVenue() != null)
				throw new EventHasAssignedVenueException ("The event \"" + eventDesignation + "\" already has a venue (\"" + event.getVenue().getName() + "\") assigned to it");

			this.event = event;

			Venue venue = venueCatalog.getVenue(venueName);
			EventType type = event.getEventType();

			if (venue instanceof SeatedVenue) {
				if (!type.isSeated())
					throw new InvalidVenueException ("Venue \"" + venueName + "\" only hosts seated events, and therefore cannot host \"" + type + "\" event types");
				if (venue.getCapacity() > type.getCapacity())
					throw new InvalidVenueException ("Venue \"" + venueName + "\"'s capacity (" + venue.getCapacity() + ") exceeds the event's type max lotation (" 
							+ type.getCapacity() + ")");
			} else {
				if (type.isSeated())
					throw new InvalidVenueException ("Venue \"" + venueName + "\" only hosts standing events, and therefore cannot host \"" + type + "\" event types");
			}

			if (sellingStart.isBefore(DateUtils.getMockCurrentDate()))
				throw new InvalidTicketSaleDateException ("Error: The specified ticket sale date must be in the future");

			if (!sellingStart.isBefore(event.getEventDays().get(0).getDate())) 
				throw new InvalidTicketSaleDateException ("Error: The specified ticket sale date is not prior to the event's first day");

			if (ticketPrice < 0)
				throw new InvalidTicketPriceException ("Invalid ticket price: ticket prices must be positive");

			// Verificar se a instalação está livre:
			if (daysOverlap (venue.getEventDays(), event.getEventDays()))
				throw new OccupiedVenueException ("The venue \"" + venue.getName() + "\" is occupied at the time of the specified event days.");

			event.setVenue(venue);
			event.setSellingStartDate(sellingStart);

			List<EventDay> eventDays = event.getEventDays();
			venue.addEventDays(eventDays);

			// Generating the tickets
			if (venue instanceof SeatedVenue) {
				for (EventDay day : eventDays) {
					for (Seat seat : ((SeatedVenue) venue).getSeats()) 
						day.addDailyTicket((new DailyTicket (event, seat, day, ticketPrice)));
				}
			} else {
				for (EventDay day : eventDays) {
					for (int i = 0; i < venue.getCapacity(); i++)
						day.addDailyTicket((new DailyTicket (event, day, ticketPrice)));
				}
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error assigning venue named \"" + venueName + "\" to the event \"" + eventDesignation + "\"", e);

		} finally {
			em.close();
		}

	}

	/**
	 * Allows the event to which the venue was assigned to sell ticket passes, with the price
	 * passed as a parameter.
	 * 
	 * @param ticketPassCost The ticket pass price
	 * @throws ApplicationException if an error occurs while updating the event's ticket pass permissions
	 */
	public void allowTicketPasses (double ticketPassCost) throws ApplicationException {
		EntityManager em = emf.createEntityManager();

		try {
			if (ticketPassCost < 0)
				throw new InvalidTicketPriceException ("Invalid ticket pass price: ticket prices must be positive");

			em.getTransaction().begin();

			event = em.find(Event.class, event.getId());
			event.setTicketPassPermission(true);
			event.setTicketPassPrice(ticketPassCost);

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) 
				em.getTransaction().rollback();
			throw new ApplicationException("Error while trying to attribute ticket pass permission to event \"" + event.getDesignation() + "\"", e);
		} finally {
			em.close();
		}

	}

	/**
	 * Returns a list with the all the existing venue names
	 * @return a list with the all the existing venue names
	 */
	public List<String> startVenueAssignment() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		VenueCatalog venueCatalog = new VenueCatalog(em);
		try {
			em.getTransaction().begin();
			List<String> result = venueCatalog.getAllVenueNames();
			em.getTransaction().commit();
			return result;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) 
				em.getTransaction().rollback();
			throw new ApplicationException("Error fetching venue names", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Checks, for two lists of EventDays, if there is any date/hour period intersection.
	 * 
	 * @param venueDays The list of the Venue's event days
	 * @param eventDays The list of the Event's days
	 * @return True if two days overlap, False otherwise
	 */
	private boolean daysOverlap (List<EventDay> venueDays, List<EventDay> eventDays) {
		if (venueDays.isEmpty())
			return false;
		for (EventDay vd : venueDays) {
			for (EventDay ed : eventDays) {
				if (vd.getDayPeriod().overlaps(ed.getDayPeriod()))
					return true;
			}
		}
		return false;

	}


}
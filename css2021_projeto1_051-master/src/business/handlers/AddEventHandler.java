package business.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.company.Company;
import business.company.CompanyCatalog;
import business.event.Event;
import business.event.EventCatalog;
import business.event.EventDay;
import business.event.EventType;
import facade.dtos.DayPeriod;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventAlreadyExistsException;
import facade.exceptions.InvalidCompanyException;
import facade.exceptions.InvalidDayPeriodException;
import facade.exceptions.InvalidEventTypeException;

/**
 * Handles the event creation use case
 *
 */
public class AddEventHandler {

	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManagerFactory emf;

	/**
	 * Creates a handler for the event creation use case given
	 * the application's entity manager factory
	 * @param emf The entity manager factory of the application
	 */
	public AddEventHandler (EntityManagerFactory emf) {
		this.emf = emf;
	}

	
	/**
	 * Given the required event data, creates a new event
	 * 
	 * @param designation The event's name
	 * @param type The event's type
	 * @param companyID The responsible company for the event
	 * @param days The days in which the event occurs
	 * @throws ApplicationException if an error occurs while attempting to create the event
	 */
	public void addEvent (String designation, String type, int companyID, List<DayPeriod> days) throws ApplicationException {

		EntityManager em = emf.createEntityManager();
		EventCatalog eventCatalog = new EventCatalog(em);
		CompanyCatalog companyCatalog = new CompanyCatalog(em);

		try {
			
			em.getTransaction().begin();

			if (!isValidEventType (type))
				throw new InvalidEventTypeException ("Invalid event type: " + type + " does not exist.");

			EventType eType = EventType.valueOf(type);

			Event e = eventCatalog.getEvent(designation);
			if (e != null)
				throw new EventAlreadyExistsException ("There already exists an event with the designation \"" + designation + "\"");

			if (!isValidDayPeriods (days))
					throw new InvalidDayPeriodException ("An event day must start and end in the same day");
					
			if (!isConsecutiveDays (days))
					throw new InvalidDayPeriodException ("The specified event days are not consecutive");
			
			if (!isValidHours(days))
				throw new InvalidDayPeriodException ("A day period has an invalid event start and end time");

			Company company = companyCatalog.getCompany(companyID);

			if (!isCompanyAllowed (company, eType))
				throw new InvalidCompanyException ("The company named \"" + company.getName() + "\" and id = " + company.getId() + " does not have a certificate for the " + eType + " event type");
			
			List<EventDay> eventDays = new ArrayList<>();
			for (DayPeriod d : days) 
				eventDays.add(new EventDay(d));
			
			Event newEvent = new Event (EventType.valueOf(type), designation, company, eventDays);
			
			em.persist(newEvent);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error adding event with designation \"" + designation + "\"", e);
		} finally {
			em.close();
		}

	}

	/**
	 * Checks if all the day periods in the given DayPeriod list are consecutive.
	 * 
	 * @param days The list of DayPeriod
	 * @return True if every DayPeriod in the list consecutive, False otherwise.
	 */
	private boolean isConsecutiveDays(List<DayPeriod> days) {
		if (days.isEmpty()) 
			return false;
		if (days.size() < 2)
			return true;

		DayPeriod lastDay = days.get(0);
		for (DayPeriod day : days) {
			if (days.indexOf(day) != 0 && (!day.getDate().equals(lastDay.getDate().plusDays(1))))
				return false;
			lastDay = day;
		}
		return true;
	}
	
	/**
	 * Checks if every single DayPeriod in the list happens in the same day.
	 * 
	 * @param days List of DayPeriod
	 * @return True if every single DayPeriod in the list is available, False otherwise
	 */
	private boolean isValidDayPeriods (List<DayPeriod> days) {
		for (DayPeriod d : days) {
			if (!d.getDate().equals(d.getEndDate()))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if in every single DayPeriod in the list its start time is before its end time.
	 * 
	 * @param days List of DayPeriod
	 * @return True if every single DayPeriod in the list its start time is before its end time
	 */
	private boolean isValidHours (List<DayPeriod> days) {
		for (DayPeriod d : days) {
			if (d.getStartTime().isAfter(d.getEndTime()))
				return false;
		}
		return true;
	}

	/**
	 * Checks if a given eventType (in String) exists or not
	 * 
	 * @param eventTypeStr The string to be checked
	 * @return True if the eventType exists, False otherwise
	 */
	private boolean isValidEventType (String eventTypeStr) {
		for (EventType type : EventType.values()) {
			if (type.name().equalsIgnoreCase(eventTypeStr))
				return true;
		}
		return false;
	}

	/**
	 * Checks if a given Company and EventType are compatible.
	 * 
	 * @param c The company to check
	 * @param type The event type to check
	 * @return True if they are compatible, False otherwise
	 */
	private boolean isCompanyAllowed (Company c, EventType type) {
		return c.getAllowedEventTypes().contains(type);
	}

	/**
	 * Returns a list with every existing event type
	 * @return a list with every existing event type
	 */
	public List<String> createNewEvent () {
		return Arrays.asList(EventType.values()).stream().map(eventEnum -> eventEnum.name()).collect(Collectors.toList());
	}


}
package facade.services;

import java.util.List;

import business.handlers.AddEventHandler;
import facade.dtos.DayPeriod;
import facade.exceptions.ApplicationException;

/**
 * A service that offers the required operations to create new Event, hiding its implementation
 * from the client.
 *
 */
public class AddEventService {
	
	/**
	 * The business object facade that handles the use case "Add Event"
	 */
	private AddEventHandler eventHandler;
	
	/**
	 * Constructs an add event service given an add event handler
	 * @param eventHandler The add event handler
	 */
	public AddEventService (AddEventHandler eventHandler) {
		this.eventHandler = eventHandler;
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
		eventHandler.addEvent(designation, type, companyID, days);
	}
	
	/**
	 * Returns a list with the available event types
	 * @return the list with the available event types
	 */
	public List<String> createNewEvent () {
		return eventHandler.createNewEvent();
	}

}

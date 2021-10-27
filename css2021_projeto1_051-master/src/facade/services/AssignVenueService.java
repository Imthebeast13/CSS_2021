package facade.services;

import java.time.LocalDate;
import java.util.List;

import business.handlers.AssignVenueHandler;
import facade.exceptions.ApplicationException;

/**
 * A service that offers the required operations to assign a venue to an existing event,
 *  hiding its implementation from the client.
 */
public class AssignVenueService {
	
	/**
	 * The business object facade that handles the use case "Assign Venue to Event"
	 */
	private AssignVenueHandler assignVenueHandler;
	
	/**
	 * Constructs an assign venue service given an assign venue handler
	 * @param assignVenueHandler The assign venue handler
	 */
	public AssignVenueService (AssignVenueHandler assignVenueHandler) {
		this.assignVenueHandler = assignVenueHandler;
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
		assignVenueHandler.assignVenueToEvent(eventDesignation, venueName, sellingStart, ticketPrice);
	}
	
	/**
	 * Allows the event to which the venue was assigned to sell ticket passes, with the price
	 * passed as a parameter.
	 * 
	 * @param ticketPassCost The ticket pass price
	 * @throws ApplicationException if an error occurs while updating the event's ticket pass permissions
	 */
	public void allowTicketPasses (double ticketPassCost) throws ApplicationException {
		assignVenueHandler.allowTicketPasses(ticketPassCost);
	}
	
	/**
	 * Returns a list with the all the existing venue names
	 * @return a list with the all the existing venue names
	 */
	public List<String> startVenueAssignment () throws ApplicationException {
		return assignVenueHandler.startVenueAssignment();
	}

}
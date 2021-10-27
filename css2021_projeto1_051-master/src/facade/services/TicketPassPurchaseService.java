package facade.services;

import business.handlers.BuyTicketPassesHandler;
import facade.exceptions.ApplicationException;

/**
 * A service that offers the required operations to make a reservation for a number of 
 * ticket passes for a given event, hiding its implementation from the client.
 */
public class TicketPassPurchaseService {

	/**
	 * The business object facade that handles the use case "Purchase Ticket Passes for an Event"
	 */
	private BuyTicketPassesHandler ticketPassHandler;

	/**
	 * Constructs a ticket pass purchase service given a buy ticket pass handler
	 * @param ticketPassHandler The buy ticket pass handler
	 */
	public TicketPassPurchaseService (BuyTicketPassesHandler ticketPassHandler) {
		this.ticketPassHandler = ticketPassHandler;
	}

	/**
	 * Returns the number of ticket passes available for the event with the given designation
	 * 
	 * @param eventDesignation the event designation
	 * @return the number of ticket passes available for the event with the given designation
	 * @throws ApplicationException if an error occurs while attempting to fetch the number of ticket
	 * of ticket passes available
	 */
	public int startTicketPassReservation (String eventDesignation) throws ApplicationException {
		return ticketPassHandler.getNumberOfTicketPasses(eventDesignation);
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
		return ticketPassHandler.chooseTicketPassQuantity(nPasses, userEmail);
	}


}

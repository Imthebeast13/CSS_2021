package facade.services;

import java.time.LocalDate;

import java.util.List;

import business.handlers.BuyDailyTicketsHandler;
import facade.dtos.SeatDTO;
import facade.exceptions.ApplicationException;

/**
 * A service that offers the required operations to make a reservation for a number of 
 * daily tickets for a given SEATED event, hiding its implementation from the client.
 */
public class DailyTicketPurchaseService {

	/**
	 * The business object facade that handles the use case "Purchase Daily Tickets for a seated Event"
	 */
	private BuyDailyTicketsHandler dailyTicketHandler;

	/**
	 * Constructs a daily ticket purchase service given a buy daily ticket handler
	 * @param dailyTicketHandler The buy daily ticket handler
	 */
	public DailyTicketPurchaseService (BuyDailyTicketsHandler dailyTicketHandler) {
		this.dailyTicketHandler = dailyTicketHandler;
	}

	/**
	 * Returns the dates in which the event still has available daily tickets
	 * 
	 * @param eventDesignation The event's name
	 * @return The dates in which the event still has available daily tickets
	 * @throws ApplicationException if an error occurs while fetching the dates
	 */
	public List<LocalDate> startDailyTicketReservation (String eventDesignation) throws ApplicationException  {
		return dailyTicketHandler.getAvailableDates (eventDesignation);
	}


	/**
	 * Returns the available seats for a given date of the event
	 * 
	 * @param date The specified date
	 * @return the list of available seats for the given date
	 * @throws ApplicationException if an error occurs while attempting to retrieve the seats
	 */
	public List<SeatDTO> chooseDate (LocalDate date) throws ApplicationException {
		return this.dailyTicketHandler.chooseDate(date);
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
		return this.dailyTicketHandler.chooseSeats(seats, userEmail);
	}


}

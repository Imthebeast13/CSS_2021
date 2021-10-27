package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.handlers.AddEventHandler;
import business.handlers.AssignVenueHandler;
import business.handlers.BuyDailyTicketsHandler;
import business.handlers.BuyTicketPassesHandler;
import facade.exceptions.ApplicationException;
import facade.services.AssignVenueService;
import facade.services.DailyTicketPurchaseService;
import facade.services.AddEventService;
import facade.services.TicketPassPurchaseService;

public class EventSys {
	
	private AddEventService eventService;
	private EntityManagerFactory emf;
	
	public void run() throws ApplicationException {
		try {
			emf = Persistence.createEntityManagerFactory("CSS JPA Projeto 1 Teste");
			eventService = new AddEventService (new AddEventHandler (emf));
		}catch (Exception e){
			throw new ApplicationException ("Error connecting to database!", e);
		}
	}
	
	/**
	 * Closes the database connection
	 */
	public void stopRun () {
		emf.close();
	}
	
	/**
	 * Returns the event service
	 * @return The event service
	 */
	public AddEventService getEventService () {
		return this.eventService;
	}

	/**
	 * Returns a new venue service
	 * @return a new venue service
	 */
	public AssignVenueService getVenueService() {
		return new AssignVenueService (new AssignVenueHandler(emf));
	}
	
	/**
	 * Returns a new daily ticker purchase service
	 * @return a new daily ticker purchase service
	 */
	public DailyTicketPurchaseService getDailyTicketPurchaseService() {
		return new DailyTicketPurchaseService (new BuyDailyTicketsHandler(emf));
	}
	
	/**
	 * Returns a new ticket pass purchase service
	 * @return a new ticket pass purchase service
	 */
	public TicketPassPurchaseService getTicketPassPurchaseService() {
		return new TicketPassPurchaseService (new BuyTicketPassesHandler(emf));
	}

}

package business.event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * A catalog of events 
 *
 */
public class EventCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	private EntityManager em;

	/**
	 * Constructs an event catalog given an entity manager
	 * @param em The entity manager
	 */
	public EventCatalog (EntityManager em) {
		this.em = em;
	}

	/**
	 * Finds an event given its designation. Returns null if it is not found.
	 * @param designation The designation of the event
	 * @return The designation with given designation
	 */
	public Event getEvent (String designation) { 
		TypedQuery<Event> query = em.createNamedQuery (Event.FIND_BY_DESIGNATION, Event.class);
		query.setParameter(Event.EVENT_DESIGNATION, designation);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

}

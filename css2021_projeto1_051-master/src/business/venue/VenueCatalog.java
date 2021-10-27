package business.venue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.VenueNotFoundException;

/**
 * A catalog of venues 
 *
 */
public class VenueCatalog {
	
	/**
	 * Entity manager for accessing the persistence service 
	 */
	private EntityManager em;

	/**
	 * Constructs an event catalog given an entity manager
	 * @param em The entity manager
	 */
	public VenueCatalog (EntityManager em) {
		this.em = em;
	}

	/**
	 * Finds a venue with a given name
	 * @param venueName The venue's name
	 * @return the venue with the specified name
	 * @throws VenueNotFoundException When the venue doesn't exist.
	 */
	public Venue getVenue (String venueName) throws VenueNotFoundException {
		TypedQuery<Venue> query = em.createNamedQuery (Venue.FIND_BY_NAME, Venue.class);
		query.setParameter(Venue.VENUE_NAME, venueName);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new VenueNotFoundException ("Could not find a venue with the name \"" + venueName + "\"", e);
		}
	}

	/**
	 * Returns a list with all the existing venue names
	 * @return A list with all the existing venue names
	 */
	public List<String> getAllVenueNames () {
		try {
			TypedQuery<String> query = em.createNamedQuery(Venue.GET_ALL_NAMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}	
	}

}

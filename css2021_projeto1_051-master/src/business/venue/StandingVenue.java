package business.venue;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: StandingVenue
 * Subclass of Venue.
 *
 */
@Entity
@DiscriminatorValue(value = "Standing")
public class StandingVenue extends Venue {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public StandingVenue() {
	}
	
	@Override
	public int getCapacity() {
		return this.capacity;
	}
   
}

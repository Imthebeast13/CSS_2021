package business.venue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import business.seat.Seat;

/**
 * Entity implementation class for Entity: SeatedVenue
 * Subclass of Venue.
 *
 */
@Entity
@DiscriminatorValue(value = "Seated")
public class SeatedVenue extends Venue {
	
	@OneToMany @JoinColumn (name = "VENUE_ID")
	private List<Seat> seats = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public SeatedVenue() {
	}
	
	@Override
	public int getCapacity() {
		return this.seats.size();
	}
	
	/**
	 * Returns a list with all the seats of the seated venue
	 * @return The list of seats of the seated venue 
	 */
	public List<Seat> getSeats () {
		return seats;
	}
   
}

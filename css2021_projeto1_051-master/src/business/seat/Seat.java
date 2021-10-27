package business.seat;

import java.io.Serializable;
import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

/**
 * Entity implementation class for Entity: Seat.
 * Class that represents a seat in a venue.
 */
@Entity 
public class Seat implements Serializable {

	@Id @GeneratedValue(strategy = AUTO) 
	private int id;

	@Column (nullable = false)
	private String seatLetter;
	
	@Column (nullable = false)
	private int seatNumber;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public Seat () {
	}

	/**
	 * Creates a new Seat given a seatLetter and seatNumber
	 * @param seatLetter the seat's seat letter
	 * @param seatNumber the seat's seat number
	 */
	public Seat (String seatLetter, int seatNumber) {
		this.seatLetter = seatLetter;
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString () {
		return seatLetter + "-" + seatNumber;
	}

	/**
	 * Returns the id of the seat
	 * @return The id of the seat
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Returns the letter of the seat
	 * @return The letter of the seat
	 */
	public String getSeatLetter () {
		return this.seatLetter;
	}
	
	/**
	 * Returns the number of the seat
	 * @return The number of the seat
	 */
	public int getSeatNumber () {
		return this.seatNumber;
	}

}


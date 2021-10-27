package facade.dtos;

/**
 * A class DTO representing a simplified version of a Seat (business side class)
 */
public class SeatDTO {

	private String seatLetter;
	private int seatNumber;

	/**
	 * Creates a new Seat DTO given a seatLetter and seatNumber
	 * @param seatLetter the seat's seat letter
	 * @param seatNumber the seat's seat number
	 */
	public SeatDTO (String seatLetter, int seatNumber) {
		this.seatLetter = seatLetter;
		this.seatNumber = seatNumber;
	}

	/**
	 * Returns the letter of the seat
	 * @return The letter of the seat
	 */
	public String getSeatLetter () {
		return seatLetter;
	}
	
	/**
	 * Returns the number of the seat
	 * @return The number of the seat
	 */
	public int getSeatNumber () {
		return seatNumber;
	}
	
	@Override
	public String toString () {
		return seatLetter + "-" + seatNumber;
	}
	
	

}

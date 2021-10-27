package business.paymentdetails;

import java.io.Serializable;
import javax.persistence.*;

import business.reservation.Reservation;
import business.utils.MockPayment;

/**
 * Entity implementation class for Entity: PaymentDetails
 * A class representing payment details of a single reservation.
 *
 */
@Embeddable
public class PaymentDetails implements Serializable {
	
	@Column (nullable = false)
	private String entity;
	
	@Column (nullable = false) 
	private String reference;
	
	@Column (nullable = false)
	private double value;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public PaymentDetails () {
	}
	
	/**
	 * Creates a new PaymentDetails object given a reservation
	 * @param res The reservation
	 */
	public PaymentDetails(Reservation res) {
		this.value = res.getTotalPrice();
		this.reference = MockPayment.generateMockReference ();
		this.entity = MockPayment.generateMockEntity ();
	}
	
	/**
	 * Returns the reservation's payment details
	 * @return The reservation's payment details
	 */
	public String getDetails () {
		return "Entity: " + entity + "\nPayment Reference: " + reference + "\nTotal: " + value + "€";
	}
	
   
}

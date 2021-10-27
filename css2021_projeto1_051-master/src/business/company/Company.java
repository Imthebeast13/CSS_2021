package business.company;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import business.event.EventType;
import static javax.persistence.GenerationType.AUTO;

/**
 * Entity implementation class for Entity: Company
 *
 */
@Entity
@NamedQuery(name=Company.FIND_BY_ID, query="SELECT c FROM Company c WHERE c.id = :" + 
		Company.COMPANY_ID)
public class Company implements Serializable {
	
	// Named query name constants

	public static final String FIND_BY_ID = "Company.findById";
	public static final String COMPANY_ID = "Company";

	/**
	 * Company primary key. Needed by JPA.
	 */
	@Id @GeneratedValue(strategy = AUTO)
	private int id;

	@Column (nullable = false, unique = true)
	private String name;

	@ElementCollection @Enumerated(EnumType.STRING)
	private List<EventType> allowedEventTypes;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor needed by JPA.
	 */
	public Company() {
	}

	/**
	 * Creates a company given a name
	 * @param name The desired company name
	 */
	public Company (String name) {
		this.name = name;
	}
	
	/**
	 * Returns the company's id
	 * @return The company's id
	 */
	public int getId () {
		return this.id;
	}

	/**
	 * Returns the company's name
	 * @return The company's name
	 */
	public String getName () {
		return this.name;
	}

	/**
	 * Returns the company's allowed (verified) event types
	 * @return The company's allowed (verified) event types
	 */
	public List<EventType> getAllowedEventTypes () {
		return this.allowedEventTypes;
	}
	
	@Override
	public String toString () {
		return name + " (Registration ID: " + id + ")";
	}

}

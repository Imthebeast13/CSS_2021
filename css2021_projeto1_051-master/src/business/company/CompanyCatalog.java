package business.company;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.CompanyNotFoundException;

/**
 * A catalog of companies 
 *
 */
public class CompanyCatalog {

	/**
	 * Entity manager for accessing the persistence service 
	 */
	private EntityManager em;

	/**
	 * Constructs a company catalog given an entity manager
	 * @param em The entity manager
	 */
	public CompanyCatalog (EntityManager em) {
		this.em = em;
	}

	/**
	 * Finds a company given its (registration) id
	 * @param companyID The id of the company
	 * @return The company with given id
	 * @throws CompanyNotFoundException When the company doesn't exist.
	 */
	public Company getCompany (int companyID) throws CompanyNotFoundException {

		TypedQuery<Company> query = em.createNamedQuery(Company.FIND_BY_ID, Company.class);
		query.setParameter(Company.COMPANY_ID, companyID);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new CompanyNotFoundException ("Could not find company with id: " + companyID, e);
		}
	}

}

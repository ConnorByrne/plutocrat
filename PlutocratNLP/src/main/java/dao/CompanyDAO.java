package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Company;

public class CompanyDAO {
	
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("connorsPU");
	

	public CompanyDAO() {
	}
	
	public void persistCompany(Company company) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(company);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Company> getAllCompanies(){
		EntityManager em = emf.createEntityManager();
		List <Company> companies = (List<Company>)em.createNamedQuery("Company.findAll").getResultList();
		em.close();
		return companies;
		
	}

	public Company mergeCompany(Company company) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Company updatedCompany = em.merge(company);
		em.getTransaction().commit();
		em.close();
		return updatedCompany;
	}
	
	public void removeCompany(Company company) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(em.contains(company)) {
			em.remove(company);
		}
		else {
			Company c1 = em.merge(company);
			em.remove(c1);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	public Company getCompanyByName(String name) {
		EntityManager em = emf.createEntityManager();
		List<Company> companies = (List<Company>)em.createNamedQuery("Company.findByCompanyName").getResultList();
		em.close();
		Company comp = new Company();
		for(Company c: companies) {
			comp = c;
		}
		return comp;
	}

}

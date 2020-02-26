package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Sentiment;

public class SentimentDAO {
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("connorsPU");

	public SentimentDAO() {
		
	}
	
	public void persistSentiment(Sentiment sentiment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(sentiment);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeSentiment(Sentiment sentiment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(em.contains(sentiment)) {
			em.remove(sentiment);
		}
		else {
			Sentiment s1 = em.merge(sentiment);
			em.remove(s1);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	public Sentiment mergeSentiment(Sentiment sentiment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Sentiment updatedSentiment = em.merge(sentiment);
		em.getTransaction().commit();
		em.close();
		return updatedSentiment;
	}
	
	public List<Sentiment> getAllSentiment(){
		EntityManager em = emf.createEntityManager();
		List<Sentiment> sentiment = (List<Sentiment>)em.createNamedQuery("Sentiment.findAll").getResultList();
		em.close();
		return sentiment;
	}

}

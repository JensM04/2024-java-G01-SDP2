package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	private final static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("SDPII"); //let op, moet matchen met persistence.xml
//            Persistence.createEntityManagerFactory("SDPII_Kevin_Local"); //let op, moet matchen met persistence.xml
//            Persistence.createEntityManagerFactory("SDPII"); //let op, moet matchen met persistence.xml

	private final static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public static EntityManagerFactory getEntityManagerFactory() {
			return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	public static void commitTransaction() {
		entityManager.getTransaction().commit();
	}

	private JPAUtil() {
	}
}

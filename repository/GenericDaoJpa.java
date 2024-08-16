package repository;

import java.util.List;

import domain.B2BBestelling;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import utils.JPAUtil;

public class GenericDaoJpa<T> implements GenericDao<T> {

	private static final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
	protected static final EntityManager em = JPAUtil.getEntityManager();

	private final Class<T> type;

	public GenericDaoJpa(Class<T> type) {
		this.type = type;
	}

	public static void closePersistency() {
		em.close();
		emf.close();
	}

	@Override
	public void startTransaction() {
		em.getTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		em.flush();
		em.getTransaction().commit();
	}

	public static void rollbackTransaction() {
		em.getTransaction().rollback();
	}

	@Override
	public List<T> findAll() {
		return em.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
	}

	@Override
	public T get(Long id) {
		T entity = em.find(type, id);
		return entity;
	}

	@Override
	public T update(T object) {
		startTransaction();
		T updatedObject = null;

		try {
		updatedObject = em.merge(object);
		if (updatedObject instanceof B2BBestelling b) {
			System.out.printf("Het id van deze bestelling is %s", b.getUuid());
		}
		em.flush();

		commitTransaction();
		} catch( Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}
		return updatedObject;
	}

	@Override
	public void delete(T object) {
		startTransaction();
		em.remove(em.merge(object));
		commitTransaction();
	}

	@Override
	public void insert(T object) {
		startTransaction();
		em.persist(object);
		commitTransaction();
	}

	@Override
	public boolean exists(Long id) {
		T entity = em.find(type, id);
		return entity != null;
	}

}

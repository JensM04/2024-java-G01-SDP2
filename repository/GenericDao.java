package repository;

import java.util.List;

//DAO pattern -> DAO = Data Access Object
public interface GenericDao<T> {
	public void startTransaction();
	public void commitTransaction();
	public List<T> findAll();
	public T get(Long id);
	public T update(T object);
	public void delete(T object);
	public void insert(T object);
	public boolean exists(Long id);
}

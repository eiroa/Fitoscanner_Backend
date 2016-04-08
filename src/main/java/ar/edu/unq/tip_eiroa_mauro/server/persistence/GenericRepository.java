package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic DAO
 * 
 * @param <T>
 */
public interface GenericRepository<T> {

	void save(T entity);

	void delete(T entity);

	void update(T entity);
	
	T findById(Serializable id);
	
	List<T> findAll();

	void deleteById(Serializable id);

	int count();

	List<T> findByExample(T exampleObject);

}
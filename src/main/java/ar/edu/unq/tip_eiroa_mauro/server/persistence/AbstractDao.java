package ar.edu.unq.tip_eiroa_mauro.server.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

public abstract class AbstractDao<T> implements GenericRepository<T>,Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1995541913227194088L;
	protected Class<T> persistentClass = this.getDomainClass();
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	@Override
	@SuppressWarnings("unchecked")
	public int count() {
		List<Long> list = (List<Long>) this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select count(*) from " + this.persistentClass.getName());
		Long count = list.get(0);
		return count.intValue();

	}

	@Override
	public void delete(final T entity) {
		this.getSessionFactory().getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(final Serializable id) {
		T obj = this.findById(id);
		this.getSessionFactory().getCurrentSession().delete(obj);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) this.getSessionFactory().getCurrentSession().createQuery(
				"from " + this.persistentClass.getName()).list();
	}


	@Override
	public T findById(final Serializable id) {
		return this.getSessionFactory().getCurrentSession().get(this.persistentClass, id);
	}

	protected abstract Class<T> getDomainClass();

	@Override
	public void save(final T entity) {
	        getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void update(final T entity) {
		this.getSessionFactory().getCurrentSession().update(entity);
	}
	
//	public List<?> find(String queryString) throws DataAccessException {
//		return find(queryString, (Object[]) null);
//	}
//
//	public List<?> find(String queryString, Object value) throws DataAccessException {
//		return find(queryString, new Object[] {value});
//	}
//
//	public List<?> find(final String queryString, final Object... values) throws DataAccessException {
//		return executeWithNativeSession(new HibernateCallback<List<?>>() {
//			@Override
//			public List<?> doInHibernate(Session session) throws HibernateException {
//				Query queryObject = session.createQuery(queryString);
//				prepareQuery(queryObject);
//				if (values != null) {
//					for (int i = 0; i < values.length; i++) {
//						queryObject.setParameter(i, values[i]);
//					}
//				}
//				return queryObject.list();
//			}
//		});
//	}

}

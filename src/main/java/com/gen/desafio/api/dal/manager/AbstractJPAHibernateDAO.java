package com.gen.desafio.api.dal.manager;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

 /**
  *  JPA / HIBERNATE 
  *  
  *  ABSTRACT DAO
  *  
  * @author GeronimoEzequiel
  */
@Repository
public abstract class AbstractJPAHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	public final static Logger log = Logger.getLogger(AbstractJPAHibernateDAO.class.getName());
	
	@PersistenceContext
	protected EntityManager entityManager;
	
    @Autowired
	@Qualifier("localSessionFactory")
    private SessionFactory sessionFactory; 
    
    //private final ThreadLocal<Session> session = new ThreadLocal<Session>();
    //private final ThreadLocal<Transaction> transaction = new ThreadLocal<Transaction>();
    
	
	private Class< T > clazz;
	private String entityPrefixName;
    
    
    // Default Constructor
    public AbstractJPAHibernateDAO() { }

    // SetterClass
  	protected final void setClazz( final Class< T > clazzToSet ){
  	      this.clazz = clazzToSet;
  	      this.entityPrefixName = getEntityClass().getName().substring(18);
  	}
  	
  	
    /*** JDBC CONNECTIVITY ***/
    protected static Connection getDataAccess() {
        return JDBConnectionFactory.getInstance().getConnection();  // CUSTOM JDBC POOL MANAGER
    }
    
    
    // Singleton HibernateSession Instance
    private Session getSession() {
        Session session = (Session) sessionFactory.getCurrentSession();
        if (session == null) {
           session = sessionFactory.openSession();
        }
        
       return session;
     }
    
    
    
    /*****  CRUD JPA OPERATIONS 
     * 
     * @throws SQLException ******/
  
    
    // INSERT INTO
    @Override
    @Transactional(readOnly = false)  // TransactionAttributeType.REQUIRED
    public void save(T entity) {
    	 this.entityManager.persist( entity );
    	 this.entityManager.flush(); //  force insert to receive the id of the entity
    	
    	 log.info("Saved inserted Object: " + entity.toString());
    	
    	 /*
        Session session = sessionFactory.getSession();
        try {
            session.beginTransaction(); // Transaction tx = session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
         } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            throw new WebApplicationException(ex.getCause(), Response.Status.INTERNAL_SERVER_ERROR);  
        }
        */ 
    }
    
    
    // UPDATE SET
    @Override
    @Transactional(readOnly = false)
    public void update(T entity) {
    	 this.entityManager.merge( entity );
    	 
    	 log.info("Updated/Merged Object: " + entity.toString());
    	
    	 /*
    	 Session session = sessionFactory.getSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
         } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
           throw new WebApplicationException(ex.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
        } 
        */
	}

    
    // SELECT by ID
    @Override
    @Transactional(readOnly = true)
    public T get(ID id) {
    	T single = (T) this.entityManager.createNamedQuery(entityPrefixName+"s.findById").setParameter(1, id).getSingleResult();
	    T entity = this.entityManager.find( getEntityClass(), id );
        
	    log.info("Retrieved Object: " + entity.toString());
	    
	    /*
	    Session session = sessionFactory.getSession();
        try {
            session.beginTransaction();
            T entity = (T) session.get(getEntityClass(), id);
            session.getTransaction().commit();

            return entity;
         } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            throw new WebApplicationException(ex.getCause(), Response.Status.NOT_FOUND);
        }
        */
	    
	    return entity;
    }
    

    // EXISTS ?
    @Override
    @Transactional(readOnly = true)
    public boolean exists(String username) {
    	try{
    		T entity = (T) this.entityManager.createNamedQuery(entityPrefixName+"s.findByUsername").setParameter(1, username).getSingleResult();
    		
    	}
    	catch(NoResultException e){
    		log.info("Exists Object? False");
    		return false;
    	}
    	
    	log.info("Exists Object? True");
    	return true;    	
        
	    /*
	    Session session = sessionFactory.getSession();
        try {
            session.beginTransaction();
            T entity = (T) session.get(getEntityClass(), id);
            session.getTransaction().commit();

            return true;
        } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
             throw new WebApplicationException(ex.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
             //return false;
        }
        */
    }
    


    // DELETE
    @Override
    @Transactional
    public void delete(ID id) {
    	T entity = entityManager.find(getEntityClass(), id);
    	this.entityManager.remove( entity );
    	
    	log.info("Deleted Object: " + entity.toString());
        
    	/*
    	Session session = sessionFactory.getSession();
        try {
            session.beginTransaction();
            T entity = (T) session.load(getEntityClass(), id); // session.get(T.class, id);
            if (entity == null) {
            	throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            session.delete(entity);
            session.getTransaction().commit();
         } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
             throw new WebApplicationException(ex.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
         }
         */
    }

    
    // SELECT *
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
         //CriteriaQuery<T> cq = (CriteriaQuery<T>) sessionFactory.getCurrentSession().createCriteria(getEntityClass());
         //List<T> list =  ((Criteria) cq).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
         //Root<T> root = cq.from(getEntityClass());
         //cq.select(root);
    	 
    	List<T> entities = this.entityManager.createNamedQuery(entityPrefixName+"s.findAll").getResultList();
    	
    	log.info("Retrieved Object: " + entities.toString());
    	
    	/*
    	Session session = sessionFactory.getSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery("findAll");
            List<T> entities = query.list();
            session.getTransaction().commit();

            return entities;
         } catch (Exception ex) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
              throw new WebApplicationException(Response.Status.NOT_FOUND);
        } 
        */
    	
    	return entities;
    }

    
    //Util:
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    
}

package com.gen.desafio.api.dal;


import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface GenericDAO<T,ID extends Serializable> {
	
	// CRUD OPERATIONS JPA REPOSITORY
    
    void save(T entity);
    void update(T entity);
    void delete(ID id);
    List<T> findAll();
    T get(ID id);
    boolean exists(String field);
     
}
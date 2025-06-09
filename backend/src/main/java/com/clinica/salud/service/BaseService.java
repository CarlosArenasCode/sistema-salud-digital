package com.clinica.salud.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio genérico base que proporciona operaciones CRUD comunes
 * Reduce duplicación de código en los servicios específicos
 */
public abstract class BaseService<T, ID> {
    
    protected abstract JpaRepository<T, ID> getRepository();
    
    public List<T> findAll() {
        return getRepository().findAll();
    }
    
    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }
    
    public T save(T entity) {
        return getRepository().save(entity);
    }
    
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }
    
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }
    
    public long count() {
        return getRepository().count();
    }
}

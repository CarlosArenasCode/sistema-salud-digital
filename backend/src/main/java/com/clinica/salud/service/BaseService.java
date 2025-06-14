package com.clinica.salud.service;

import com.clinica.salud.exception.RecursoNoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio base que incluye métodos comunes con manejo de excepciones
 * Elimina la necesidad de duplicar métodos en servicios específicos
 */
public abstract class BaseService<T, ID> {
    
    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract String getEntityName();
    
    // Métodos CRUD básicos existentes
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
    
    // Métodos consolidados con manejo de excepciones
    public T buscarPorId(ID id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    getEntityName() + " con id " + id + " no encontrado"));
    }
    
    public T guardar(T entity) {
        return save(entity);
    }
    
    public T actualizar(ID id, T entity) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException(
                getEntityName() + " con id " + id + " no encontrado");
        }
        return save(entity);
    }
    
    public void eliminar(ID id) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException(
                getEntityName() + " con id " + id + " no encontrado");
        }
        deleteById(id);
    }
    
    public List<T> buscarTodos() {
        return findAll();
    }
}

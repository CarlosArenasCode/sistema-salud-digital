package com.clinica.salud.controller;

import com.clinica.salud.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador genérico base que proporciona endpoints REST comunes
 * Reduce duplicación de código en los controladores específicos
 */
public abstract class BaseController<T, ID> {
    
    protected abstract BaseService<T, ID> getService();
    
    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> entities = getService().findAll();
        return ResponseEntity.ok(entities);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        return getService().findById(id)
                .map(entity -> ResponseEntity.ok(entity))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = getService().save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        if (!getService().existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        T updatedEntity = getService().save(entity);
        return ResponseEntity.ok(updatedEntity);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        if (!getService().existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

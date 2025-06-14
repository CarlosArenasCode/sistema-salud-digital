package com.clinica.salud.controller;

import com.clinica.salud.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador base con anotaciones Swagger centralizadas
 * Elimina duplicación de documentación API
 */
public abstract class BaseControllerWithSwagger<T, ID> {
    
    protected abstract BaseService<T, ID> getService();
    
    @Operation(summary = "Lista todos los elementos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> entities = getService().findAll();
        return ResponseEntity.ok(entities);
    }
    
    @Operation(summary = "Obtiene un elemento por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Elemento encontrado"),
        @ApiResponse(responseCode = "404", description = "Elemento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        return getService().findById(id)
                .map(entity -> ResponseEntity.ok(entity))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Crea un nuevo elemento")
    @ApiResponse(responseCode = "201", description = "Elemento creado correctamente")
    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T entity) {
        T savedEntity = getService().save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }
    
    @Operation(summary = "Actualiza un elemento existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Elemento actualizado"),
        @ApiResponse(responseCode = "404", description = "Elemento no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @Valid @RequestBody T entity) {
        if (!getService().existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        T updatedEntity = getService().save(entity);
        return ResponseEntity.ok(updatedEntity);
    }
    
    @Operation(summary = "Elimina un elemento por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Elemento eliminado"),
        @ApiResponse(responseCode = "404", description = "Elemento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        if (!getService().existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

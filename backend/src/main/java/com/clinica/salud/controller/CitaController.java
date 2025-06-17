package com.clinica.salud.controller;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.repository.jpa.CitaJpaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para citas
 */
@RestController
@RequestMapping("/citas")
public class CitaController {
    
    // === INYECCIÓN DE DEPENDENCIAS ===
    private final CitaJpaRepository repository;
    
    public CitaController(CitaJpaRepository repository) {
        this.repository = repository;
    }
    
    // === CONSULTA DE CITAS ===
    /**
     * Obtiene todas las citas registradas
     */
    @GetMapping
    public List<CitaEntity> getAll() {
        return repository.findAll();
    }
    
    /**
     * Busca una cita por su identificador
     */
    @GetMapping("/{id}")
    public CitaEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
    
    // === REGISTRO DE CITAS ===
    /**
     * Crea una nueva cita en el sistema
     */
    @PostMapping
    public CitaEntity create(@RequestBody CitaEntity cita) {
        return repository.save(cita);
    }
    
    // === ACTUALIZACIÓN DE CITAS ===
    /**
     * Modifica los datos de una cita existente
     */
    @PutMapping("/{id}")
    public CitaEntity update(@PathVariable Long id, @RequestBody CitaEntity cita) {
        return repository.save(cita);
    }
    
    // === ELIMINACIÓN DE CITAS ===
    /**
     * Elimina una cita del sistema
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

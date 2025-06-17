package com.clinica.salud.controller;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador ULTRA-SIMPLE para pacientes
 */
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    /* ==========================================
     * DEPENDENCIAS
     * Repositorio para acceso a datos de pacientes
     * ========================================== */
    private final PacienteJpaRepository repository;
    
    /* ==========================================
     * CONSTRUCTOR
     * Inicializa las dependencias requeridas
     * ========================================== */
    public PacienteController(PacienteJpaRepository repository) {
        this.repository = repository;
    }
    
    /* ==========================================
     * OPERACIONES DE CONSULTA
     * Métodos para obtener información de pacientes
     * ========================================== */
    @GetMapping
    public List<PacienteEntity> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public PacienteEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
    
    /* ==========================================
     * OPERACIONES DE CREACIÓN Y ACTUALIZACIÓN
     * Métodos para crear y modificar pacientes
     * ========================================== */
    @PostMapping
    public PacienteEntity create(@RequestBody PacienteEntity paciente) {
        return repository.save(paciente);
    }
    
    @PutMapping("/{id}")
    public PacienteEntity update(@PathVariable Long id, @RequestBody PacienteEntity paciente) {
        return repository.save(paciente);
    }
    
    /* ==========================================
     * OPERACIONES DE ELIMINACIÓN
     * Métodos para eliminar pacientes
     * ========================================== */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

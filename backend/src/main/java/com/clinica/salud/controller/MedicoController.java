package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.repository.jpa.MedicoJpaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador ULTRA-SIMPLE para médicos
 */
@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    // ==========================================
    // === INYECCIÓN DE DEPENDENCIAS ============
    // ==========================================
    
    private final MedicoJpaRepository repository;
    
    public MedicoController(MedicoJpaRepository repository) {
        this.repository = repository;
    }
    
    // ==========================================
    // === CONSULTA DE MÉDICOS =================
    // ==========================================
    
    @GetMapping
    public List<MedicoEntity> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public MedicoEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
    
    // ==========================================
    // === CREACIÓN
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

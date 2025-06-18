package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.repository.jpa.MedicoJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST para gestión de médicos
@RestController
@RequestMapping("/medicos")
public class MedicoController {    
    // Repositorio JPA para operaciones CRUD de médicos
    private final MedicoJpaRepository repository;
    
    // Constructor para inyección de dependencias
    public MedicoController(MedicoJpaRepository repository) {
        this.repository = repository;    }
    
    // Endpoint GET /medicos - Obtiene lista completa de médicos
    @GetMapping
    public List<MedicoEntity> getAll() {
        return repository.findAll();    }
    
    // Endpoint GET /medicos/{id} - Busca médico específico por ID
    @GetMapping("/{id}")
    public MedicoEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);    }
      // Endpoint DELETE /medicos/{id} - Elimina médico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

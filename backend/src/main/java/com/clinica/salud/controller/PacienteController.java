package com.clinica.salud.controller;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST para gestión de pacientes
@RestController
@RequestMapping("/pacientes")
public class PacienteController {    
    // Repositorio JPA para operaciones CRUD de pacientes
    private final PacienteJpaRepository repository;
    
    // Constructor para inyección de dependencias
    public PacienteController(PacienteJpaRepository repository) {
        this.repository = repository;    }
    
    // Endpoint GET /pacientes - Obtiene lista completa de pacientes
    @GetMapping
    public List<PacienteEntity> getAll() {
        return repository.findAll();    }
    
    // Endpoint GET /pacientes/{id} - Busca paciente específico por ID
    @GetMapping("/{id}")
    public PacienteEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);    }
    
    // Endpoint POST /pacientes - Crea nuevo paciente
    @PostMapping
    public PacienteEntity create(@RequestBody PacienteEntity paciente) {
        return repository.save(paciente);    }
    
    // Endpoint PUT /pacientes/{id} - Actualiza paciente existente
    @PutMapping("/{id}")
    public PacienteEntity update(@PathVariable Long id, @RequestBody PacienteEntity paciente) {
        return repository.save(paciente);    }
      // Endpoint DELETE /pacientes/{id} - Elimina paciente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.clinica.salud.controller;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.repository.jpa.CitaJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST para gestión de citas médicas
@RestController
@RequestMapping("/citas")
public class CitaController {    
    // Repositorio JPA para operaciones CRUD de citas
    private final CitaJpaRepository repository;
    
    // Constructor para inyección de dependencias
    public CitaController(CitaJpaRepository repository) {
        this.repository = repository;    }
    
    // Endpoint GET /citas - Obtiene lista completa de citas
    @GetMapping
    public List<CitaEntity> getAll() {
        return repository.findAll();    }
    
    // Endpoint GET /citas/{id} - Busca cita específica por ID
    @GetMapping("/{id}")
    public CitaEntity getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);    }
    
    // Endpoint POST /citas - Crea nueva cita médica
    @PostMapping
    public CitaEntity create(@RequestBody CitaEntity cita) {
        return repository.save(cita);    }
    
    // Endpoint PUT /citas/{id} - Actualiza cita existente
    @PutMapping("/{id}")
    public CitaEntity update(@PathVariable Long id, @RequestBody CitaEntity cita) {
        return repository.save(cita);    }
      // Endpoint DELETE /citas/{id} - Elimina cita por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

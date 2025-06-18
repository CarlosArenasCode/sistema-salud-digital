package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.repository.jpa.MedicamentoJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// Controlador REST para gestión de medicamentos
@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {
    // Repositorio JPA para operaciones CRUD de medicamentos
    private final MedicamentoJpaRepository repository;
    
    // Constructor para inyección de dependencias
    public MedicamentoController(MedicamentoJpaRepository repository) {
        this.repository = repository;    }
    
    // Endpoint GET /medicamentos - Obtiene lista completa de medicamentos
    @GetMapping
    public List<MedicamentoEntity> getAll() {
        return repository.findAll();    }
    
    // Endpoint GET /medicamentos/{id} - Busca medicamento específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoEntity> getById(@PathVariable Long id) {
        Optional<MedicamentoEntity> medicamento = repository.findById(id);
        if (medicamento.isPresent()) {
            return ResponseEntity.ok(medicamento.get());
        }
        return ResponseEntity.notFound().build();    }
    
    // Endpoint GET /medicamentos/buscar - Busca medicamentos por nombre
    @GetMapping("/buscar")
    public List<MedicamentoEntity> buscarPorNombre(@RequestParam String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);    }
    
    // Endpoint POST /medicamentos - Crea nuevo medicamento
    @PostMapping
    public MedicamentoEntity create(@RequestBody MedicamentoEntity medicamento) {
        return repository.save(medicamento);    }
    
    // Endpoint PUT /medicamentos/{id} - Actualiza medicamento existente
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoEntity> update(@PathVariable Long id, @RequestBody MedicamentoEntity medicamento) {
        if (repository.existsById(id)) {
            medicamento.setId(id);
            return ResponseEntity.ok(repository.save(medicamento));
        }
        return ResponseEntity.notFound().build();    }
    
    // Endpoint DELETE /medicamentos/{id} - Elimina medicamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();    }
      // Endpoint GET /medicamentos/stock-bajo - Lista medicamentos con stock bajo
    @GetMapping("/stock-bajo")
    public List<MedicamentoEntity> getMedicamentosStockBajo() {
        return repository.findMedicamentosConStockBajo();    }
    
    // Endpoint GET /medicamentos/vencidos - Lista medicamentos vencidos
    @GetMapping("/vencidos")
    public List<MedicamentoEntity> getMedicamentosVencidos() {
        return repository.findExpiredMedications();    }
    
    // Endpoint GET /medicamentos/disponibles - Lista medicamentos disponibles en stock
    @GetMapping("/disponibles")
    public List<MedicamentoEntity> getMedicamentosDisponibles() {
        return repository.findMedicamentosDisponibles();
    }
}

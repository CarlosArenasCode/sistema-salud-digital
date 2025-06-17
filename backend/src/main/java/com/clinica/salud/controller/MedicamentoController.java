package com.clinica.salud.controller;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.repository.jpa.MedicamentoJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controlador ULTRA-SIMPLE para medicamentos
 */
@RestController
@RequestMapping("/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {
    
    // ==========================================
    // === INYECCIÓN DE DEPENDENCIAS ============
    // ==========================================
    
    private final MedicamentoJpaRepository repository;
    
    public MedicamentoController(MedicamentoJpaRepository repository) {
        this.repository = repository;
    }
    
    // ==========================================
    // === CONSULTA DE MEDICAMENTOS =============
    // ==========================================
    
    @GetMapping
    public List<MedicamentoEntity> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoEntity> getById(@PathVariable Long id) {
        Optional<MedicamentoEntity> medicamento = repository.findById(id);
        if (medicamento.isPresent()) {
            return ResponseEntity.ok(medicamento.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // ==========================================
    // === BÚSQUEDA POR NOMBRE ==================
    // ==========================================
    
    @GetMapping("/buscar")
    public List<MedicamentoEntity> buscarPorNombre(@RequestParam String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // ==========================================
    // === CREACIÓN DE MEDICAMENTOS =============
    // ==========================================
    
    @PostMapping
    public MedicamentoEntity create(@RequestBody MedicamentoEntity medicamento) {
        return repository.save(medicamento);
    }
    
    // ==========================================
    // === ACTUALIZACIÓN DE MEDICAMENTOS ========
    // ==========================================
    
    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoEntity> update(@PathVariable Long id, @RequestBody MedicamentoEntity medicamento) {
        if (repository.existsById(id)) {
            medicamento.setId(id);
            return ResponseEntity.ok(repository.save(medicamento));
        }
        return ResponseEntity.notFound().build();
    }
    
    // ==========================================
    // === ELIMINACIÓN DE MEDICAMENTOS ==========
    // ==========================================
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
      // ==========================================
    // === MEDICAMENTOS CON STOCK BAJO ==========
    // ==========================================
    
    @GetMapping("/stock-bajo")
    public List<MedicamentoEntity> getMedicamentosStockBajo() {
        return repository.findMedicamentosConStockBajo();
    }
    
    // ==========================================
    // === MEDICAMENTOS VENCIDOS ================
    // ==========================================
    
    @GetMapping("/vencidos")
    public List<MedicamentoEntity> getMedicamentosVencidos() {
        return repository.findExpiredMedications();
    }
    
    // ==========================================
    // === MEDICAMENTOS DISPONIBLES =============
    // ==========================================
    
    @GetMapping("/disponibles")
    public List<MedicamentoEntity> getMedicamentosDisponibles() {
        return repository.findMedicamentosDisponibles();
    }
}

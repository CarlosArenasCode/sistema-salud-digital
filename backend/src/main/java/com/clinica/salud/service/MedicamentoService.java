package com.clinica.salud.service;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.repository.jpa.MedicamentoJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de Medicamento - SIMPLIFICADO
 * Incluye métodos específicos de medicamentos
 */
@Service
public class MedicamentoService extends BaseService<MedicamentoEntity, Long> {
    
    private final MedicamentoJpaRepository repository;
    
    public MedicamentoService(MedicamentoJpaRepository repository) {
        this.repository = repository;
    }
    
    @Override
    protected JpaRepository<MedicamentoEntity, Long> getRepository() { return repository; }

    @Override
    protected String getEntityName() { return "Medicamento"; }
    
    // Métodos específicos de medicamentos
    public List<MedicamentoEntity> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Optional<MedicamentoEntity> buscarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }
    
    public List<MedicamentoEntity> buscarStockBajo(Integer threshold) {
        return repository.findLowStockMedications(threshold);
    }
    
    public List<MedicamentoEntity> buscarVencidos() {
        return repository.findExpiredMedications();
    }
    
    public List<MedicamentoEntity> buscarPorVencer(LocalDateTime fecha) {
        return repository.findMedicationsExpiringSoon(fecha);
    }
}

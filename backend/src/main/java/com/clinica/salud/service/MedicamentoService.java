package com.clinica.salud.service;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.repository.jpa.MedicamentoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de Medicamento
 * Usa BaseService para eliminar código duplicado
 * Incluye métodos específicos de medicamentos
 */
@Service
public class MedicamentoService extends BaseService<MedicamentoEntity, Long> {
    
    private final MedicamentoJpaRepository medicamentoRepository;
    
    @Autowired
    public MedicamentoService(MedicamentoJpaRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }
    
    @Override
    protected JpaRepository<MedicamentoEntity, Long> getRepository() {
        return medicamentoRepository;
    }

    @Override
    protected String getEntityName() {
        return "Medicamento";
    }
    
    // Métodos específicos de medicamentos
    public List<MedicamentoEntity> buscarPorNombre(String nombre) {
        return medicamentoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Optional<MedicamentoEntity> buscarPorCodigo(String codigo) {
        return medicamentoRepository.findByCodigo(codigo);
    }
    
    public List<MedicamentoEntity> buscarStockBajo(Integer threshold) {
        return medicamentoRepository.findLowStockMedications(threshold);
    }
    
    public List<MedicamentoEntity> buscarVencidos() {
        return medicamentoRepository.findExpiredMedications();
    }
    
    public List<MedicamentoEntity> buscarPorVencer(LocalDateTime fecha) {
        return medicamentoRepository.findMedicationsExpiringSoon(fecha);
    }
}

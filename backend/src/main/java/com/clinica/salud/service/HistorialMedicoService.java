package com.clinica.salud.service;

import com.clinica.salud.entity.HistorialMedicoEntity;
import com.clinica.salud.repository.jpa.HistorialMedicoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de Historial Médico
 * Usa BaseService para eliminar código duplicado
 * Incluye métodos específicos de historiales médicos
 */
@Service
public class HistorialMedicoService extends BaseService<HistorialMedicoEntity, Long> {

    private final HistorialMedicoJpaRepository historialRepository;

    @Autowired
    public HistorialMedicoService(HistorialMedicoJpaRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    @Override
    protected JpaRepository<HistorialMedicoEntity, Long> getRepository() {
        return historialRepository;
    }

    @Override
    protected String getEntityName() {
        return "Historial Médico";
    }

    // Métodos específicos de HistorialMedico
    public List<HistorialMedicoEntity> buscarPorPaciente(Long idPaciente) {
        return historialRepository.findByPaciente_Id(idPaciente);
    }
    
    public List<HistorialMedicoEntity> buscarPorMedico(Long idMedico) {
        return historialRepository.findByMedicoId(idMedico);
    }
}

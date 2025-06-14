package com.clinica.salud.service;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.entity.EstadoCita;
import com.clinica.salud.repository.jpa.CitaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de Cita optimizado * Usa BaseService para eliminar código duplicado
 * Incluye métodos específicos de citas
 */
@Service
public class CitaService extends BaseService<CitaEntity, Long> {

    private final CitaJpaRepository citaRepository;

    @Autowired
    public CitaService(CitaJpaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    protected JpaRepository<CitaEntity, Long> getRepository() {
        return citaRepository;
    }

    @Override
    protected String getEntityName() {
        return "Cita";
    }

    // Métodos específicos de citas
    public List<CitaEntity> buscarPorPaciente(Long idPaciente) {
        return citaRepository.findByIdPaciente(idPaciente);
    }
    
    public List<CitaEntity> buscarPorMedico(Long idMedico) {
        return citaRepository.findByIdMedico(idMedico);
    }
    
    public List<CitaEntity> buscarPorFecha(LocalDate fecha) {
        return citaRepository.findByFechaCita(fecha);
    }
    
    public List<CitaEntity> buscarPorEstado(EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }
}
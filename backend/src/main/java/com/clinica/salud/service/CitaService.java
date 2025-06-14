package com.clinica.salud.service;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.entity.EstadoCita;
import com.clinica.salud.repository.jpa.CitaJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de Cita - SIMPLIFICADO
 * Incluye métodos específicos de citas
 */
@Service
public class CitaService extends BaseService<CitaEntity, Long> {

    private final CitaJpaRepository repository;

    public CitaService(CitaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<CitaEntity, Long> getRepository() { return repository; }

    @Override
    protected String getEntityName() { return "Cita"; }    // Métodos específicos de citas
    public List<CitaEntity> buscarPorPaciente(Long idPaciente) {
        return repository.findByIdPaciente(idPaciente);
    }
    
    public List<CitaEntity> buscarPorMedico(Long idMedico) {
        return repository.findByIdMedico(idMedico);
    }
    
    public List<CitaEntity> buscarPorFecha(LocalDate fecha) {
        return repository.findByFechaCita(fecha);
    }
    
    public List<CitaEntity> buscarPorEstado(EstadoCita estado) {
        return repository.findByEstado(estado);
    }
}
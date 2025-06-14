package com.clinica.salud.service;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de Paciente - SIMPLIFICADO
 * Reduce de 34 líneas a 18 líneas (-47% código)
 */
@Service
public class PacienteService extends BaseService<PacienteEntity, Long> {

    private final PacienteJpaRepository repository;
    
    public PacienteService(PacienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<PacienteEntity, Long> getRepository() { return repository; }

    @Override
    protected String getEntityName() { return "Paciente"; }
}

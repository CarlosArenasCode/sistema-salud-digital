package com.clinica.salud.service;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de Paciente
 * Usa BaseService para eliminar código duplicado
 */
@Service
public class PacienteService extends BaseService<PacienteEntity, Long> {

    private final PacienteJpaRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteJpaRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    protected JpaRepository<PacienteEntity, Long> getRepository() {
        return pacienteRepository;
    }

    @Override
    protected String getEntityName() {
        return "Paciente";
    }    // Todos los métodos CRUD comunes ya están en BaseService
    // Solo añadir métodos específicos de Paciente aquí si son necesarios
}

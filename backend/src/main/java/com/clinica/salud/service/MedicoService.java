package com.clinica.salud.service;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.repository.jpa.MedicoJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de Médico - SIMPLIFICADO
 */
@Service
public class MedicoService extends BaseService<MedicoEntity, Long> {

    private final MedicoJpaRepository repository;
    
    public MedicoService(MedicoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<MedicoEntity, Long> getRepository() { return repository; }

    @Override
    protected String getEntityName() { return "Médico"; }
}

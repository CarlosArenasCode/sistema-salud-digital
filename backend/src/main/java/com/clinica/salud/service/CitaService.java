package com.clinica.salud.service;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.repository.jpa.CitaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

    // Métodos específicos para citas pueden ir aquí
    // Por ejemplo: buscarPorFecha, buscarPorPaciente, buscarPorMedico, etc.
}
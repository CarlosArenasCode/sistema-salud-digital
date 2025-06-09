package com.clinica.salud.service;

import com.clinica.salud.entity.MedicoEntity;
import com.clinica.salud.repository.jpa.MedicoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicoService extends BaseService<MedicoEntity, Long> {

    private final MedicoJpaRepository medicoRepository;

    @Autowired
    public MedicoService(MedicoJpaRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    protected JpaRepository<MedicoEntity, Long> getRepository() {
        return medicoRepository;
    }

    // Métodos específicos para médicos pueden ir aquí
    // Por ejemplo: buscarPorEspecialidad, buscarDisponibles, etc.
}

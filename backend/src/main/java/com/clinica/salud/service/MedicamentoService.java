package com.clinica.salud.service;

import com.clinica.salud.entity.MedicamentoEntity;
import com.clinica.salud.repository.jpa.MedicamentoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService extends BaseService<MedicamentoEntity, Long> {
    
    private final MedicamentoJpaRepository medicamentoRepository;
    
    @Autowired
    public MedicamentoService(MedicamentoJpaRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }
    
    @Override
    protected JpaRepository<MedicamentoEntity, Long> getRepository() {
        return medicamentoRepository;    }
    
    // Métodos específicos de medicamentos pueden ir aquí
    // Por ejemplo: buscarPorNombre, buscarPorCategoria, etc.
}

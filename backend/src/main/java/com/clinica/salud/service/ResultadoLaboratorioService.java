package com.clinica.salud.service;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import com.clinica.salud.repository.jpa.ResultadoLaboratorioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de Resultado de Laboratorio
 * Usa BaseService para eliminar código duplicado
 * Incluye métodos específicos de resultados de laboratorio
 */
@Service
public class ResultadoLaboratorioService extends BaseService<ResultadoLaboratorioEntity, Long> {

    private final ResultadoLaboratorioJpaRepository resultadoRepository;

    @Autowired
    public ResultadoLaboratorioService(ResultadoLaboratorioJpaRepository resultadoRepository) {
        this.resultadoRepository = resultadoRepository;
    }

    @Override
    protected JpaRepository<ResultadoLaboratorioEntity, Long> getRepository() {
        return resultadoRepository;
    }

    @Override
    protected String getEntityName() {
        return "Resultado de Laboratorio";
    }

    // Métodos específicos de ResultadoLaboratorio
    public List<ResultadoLaboratorioEntity> buscarPorPaciente(Long idPaciente) {
        return resultadoRepository.findByIdPaciente(idPaciente);
    }
}

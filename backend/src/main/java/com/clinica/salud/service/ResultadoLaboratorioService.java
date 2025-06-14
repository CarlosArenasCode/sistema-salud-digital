package com.clinica.salud.service;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import com.clinica.salud.repository.jpa.ResultadoLaboratorioJpaRepository;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ResultadoLaboratorioEntity> listarTodos() {
        return findAll();
    }

    public ResultadoLaboratorioEntity buscarPorId(Long id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado"));
    }

    public ResultadoLaboratorioEntity insertar(ResultadoLaboratorioEntity resultado) {
        return save(resultado);
    }

    public ResultadoLaboratorioEntity actualizar(Long id, ResultadoLaboratorioEntity resultado) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado");
        }
        resultado.setId(id);
        return save(resultado);
    }

    public void eliminar(Long id) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado");
        }
        deleteById(id);
    }

    // Métodos específicos de ResultadoLaboratorio
    public List<ResultadoLaboratorioEntity> buscarPorPaciente(Long idPaciente) {
        return resultadoRepository.findByIdPaciente(idPaciente);
    }
}

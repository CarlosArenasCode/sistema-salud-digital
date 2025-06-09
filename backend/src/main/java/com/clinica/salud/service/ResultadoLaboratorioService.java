package com.clinica.salud.service;

import com.clinica.salud.dao.ResultadoLaboratorioDAO;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import com.clinica.salud.modelo.ResultadoLaboratorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadoLaboratorioService {

    private final ResultadoLaboratorioDAO resultadoLaboratorioDAO;

    @Autowired
    public ResultadoLaboratorioService(ResultadoLaboratorioDAO resultadoLaboratorioDAO) {
        this.resultadoLaboratorioDAO = resultadoLaboratorioDAO;
    }

    public List<ResultadoLaboratorio> listarTodos() {
        return resultadoLaboratorioDAO.listarTodos();
    }

    public ResultadoLaboratorio buscarPorId(int id) {
        ResultadoLaboratorio resultado = resultadoLaboratorioDAO.buscarPorId(id);
        if (resultado == null) {
            throw new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado");
        }
        return resultado;
    }

    public void insertar(ResultadoLaboratorio resultado) {
        resultadoLaboratorioDAO.insertar(resultado);
    }

    public void actualizar(int id, ResultadoLaboratorio resultado) {
        if (resultadoLaboratorioDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado");
        }
        resultado.setIdResultado(id);
        resultadoLaboratorioDAO.actualizar(resultado);
    }

    public void eliminar(int id) {
        if (resultadoLaboratorioDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Resultado laboratorio con id " + id + " no encontrado");
        }
        resultadoLaboratorioDAO.eliminar(id);
    }
}

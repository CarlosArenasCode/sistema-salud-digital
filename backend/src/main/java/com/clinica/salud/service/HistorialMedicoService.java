package com.clinica.salud.service;

import com.clinica.salud.dao.HistorialMedicoDAO;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import com.clinica.salud.modelo.HistorialMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService {

    private final HistorialMedicoDAO historialMedicoDAO;

    @Autowired
    public HistorialMedicoService(HistorialMedicoDAO historialMedicoDAO) {
        this.historialMedicoDAO = historialMedicoDAO;
    }

    public List<HistorialMedico> listarTodos() {
        return historialMedicoDAO.listarTodos();
    }

    public HistorialMedico buscarPorId(int id) {
        HistorialMedico historial = historialMedicoDAO.buscarPorId(id);
        if (historial == null) {
            throw new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado");
        }
        return historial;
    }

    public void insertar(HistorialMedico historial) {
        historialMedicoDAO.insertar(historial);
    }

    public void actualizar(int id, HistorialMedico historial) {
        if (historialMedicoDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado");
        }
        historial.setIdHistorial(id);
        historialMedicoDAO.actualizar(historial);
    }

    public void eliminar(int id) {
        if (historialMedicoDAO.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado");
        }
        historialMedicoDAO.eliminar(id);
    }
}

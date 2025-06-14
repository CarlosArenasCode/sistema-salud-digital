package com.clinica.salud.service;

import com.clinica.salud.entity.HistorialMedicoEntity;
import com.clinica.salud.repository.jpa.HistorialMedicoJpaRepository;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService extends BaseService<HistorialMedicoEntity, Long> {

    private final HistorialMedicoJpaRepository historialRepository;

    @Autowired
    public HistorialMedicoService(HistorialMedicoJpaRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    @Override
    protected JpaRepository<HistorialMedicoEntity, Long> getRepository() {
        return historialRepository;
    }    // Método con manejo de excepciones personalizado
    public HistorialMedicoEntity buscarPorId(Long id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado"));
    }

    // Método de actualización con validación
    public HistorialMedicoEntity actualizar(Long id, HistorialMedicoEntity historial) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado");
        }
        historial.setId(id);
        return save(historial);
    }

    // Método de eliminación con validación
    public void eliminar(Long id) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Historial médico con id " + id + " no encontrado");
        }
        deleteById(id);
    }// Métodos específicos de HistorialMedico
    public List<HistorialMedicoEntity> buscarPorPaciente(Long idPaciente) {
        return historialRepository.findByPaciente_Id(idPaciente);
    }
}

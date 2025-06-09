package com.clinica.salud.service;

import com.clinica.salud.entity.PacienteEntity;
import com.clinica.salud.repository.jpa.PacienteJpaRepository;
import com.clinica.salud.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
    }    // Métodos específicos para compatibilidad con código existente
    public PacienteEntity buscarPorId(Long id) {
        return findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente con id " + id + " no encontrado"));
    }
    
    public PacienteEntity guardar(PacienteEntity paciente) {
        return save(paciente);
    }    public PacienteEntity actualizar(Long id, PacienteEntity paciente) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Paciente con id " + id + " no encontrado");
        }
        // Usar save directamente - JPA se encarga de actualizar si la entidad tiene ID
        return save(paciente);
    }

    public void eliminar(Long id) {
        if (!existsById(id)) {
            throw new RecursoNoEncontradoException("Paciente con id " + id + " no encontrado");
        }
        deleteById(id);
    }

    // Método de compatibilidad
    public java.util.List<PacienteEntity> buscarTodos() {
        return findAll();
    }
}

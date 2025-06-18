package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Repository JPA para gestión de resultados de laboratorio con operaciones de persistencia
@Repository
public interface ResultadoLaboratorioJpaRepository extends JpaRepository<ResultadoLaboratorioEntity, Long> {
    
    // Busca todos los resultados de laboratorio de un paciente específico
    List<ResultadoLaboratorioEntity> findByIdPaciente(Long idPaciente);
    
    // Busca resultados por tipo de examen ignorando mayúsculas/minúsculas
    List<ResultadoLaboratorioEntity> findByTipoExamenContainingIgnoreCase(String tipoExamen);
    
    // Busca resultados de laboratorio por rango de fechas
    List<ResultadoLaboratorioEntity> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Busca resultados de un paciente ordenados por fecha descendente
    @Query("SELECT r FROM ResultadoLaboratorioEntity r WHERE r.idPaciente = :idPaciente ORDER BY r.fecha DESC")
    List<ResultadoLaboratorioEntity> findByIdPacienteOrderByFechaDesc(@Param("idPaciente") Long idPaciente);
}

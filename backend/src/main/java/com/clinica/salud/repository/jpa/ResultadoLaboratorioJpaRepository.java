package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la entidad ResultadoLaboratorio
 * Gestiona operaciones de persistencia para los resultados de laboratorio
 */
@Repository
public interface ResultadoLaboratorioJpaRepository extends JpaRepository<ResultadoLaboratorioEntity, Long> {
    
    /**
     * Métodos de búsqueda por paciente
     */
    List<ResultadoLaboratorioEntity> findByIdPaciente(Long idPaciente);
    
    /**
     * Métodos de búsqueda por tipo de examen
     */
    List<ResultadoLaboratorioEntity> findByTipoExamenContainingIgnoreCase(String tipoExamen);
    
    /**
     * Métodos de búsqueda por rango de fechas
     */
    List<ResultadoLaboratorioEntity> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Consultas personalizadas con JPQL
     */
    @Query("SELECT r FROM ResultadoLaboratorioEntity r WHERE r.idPaciente = :idPaciente ORDER BY r.fecha DESC")
    List<ResultadoLaboratorioEntity> findByIdPacienteOrderByFechaDesc(@Param("idPaciente") Long idPaciente);
}

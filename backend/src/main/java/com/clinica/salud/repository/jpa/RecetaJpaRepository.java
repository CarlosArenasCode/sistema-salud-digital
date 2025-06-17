package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.RecetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la entidad RecetaEntity.
 * Proporciona operaciones CRUD y consultas específicas para recetas médicas.
 */
@Repository
public interface RecetaJpaRepository extends JpaRepository<RecetaEntity, Long> {
    
    /**
     * Buscar recetas por paciente
     */
    List<RecetaEntity> findByPacienteIdOrderByFechaPrescripcionDesc(Long pacienteId);
    
    /**
     * Buscar recetas por médico
     */
    List<RecetaEntity> findByMedicoIdOrderByFechaPrescripcionDesc(Long medicoId);
    
    /**
     * Buscar recetas por medicamento
     */
    List<RecetaEntity> findByMedicamentoIdOrderByFechaPrescripcionDesc(Long medicamentoId);
    
    /**
     * Buscar recetas activas de un paciente
     */
    @Query("SELECT r FROM RecetaEntity r WHERE r.paciente.id = :pacienteId " +
           "AND r.estado = 'ACTIVA' AND r.fechaVencimientoReceta >= CURRENT_DATE")
    List<RecetaEntity> findRecetasActivasByPacienteId(@Param("pacienteId") Long pacienteId);
    
    /**
     * Buscar recetas por estado
     */
    List<RecetaEntity> findByEstadoOrderByFechaPrescripcionDesc(RecetaEntity.EstadoReceta estado);
    
    /**
     * Buscar recetas que vencen pronto
     */
    @Query("SELECT r FROM RecetaEntity r WHERE r.estado = 'ACTIVA' " +
           "AND r.fechaVencimientoReceta BETWEEN CURRENT_DATE AND :fechaLimite")
    List<RecetaEntity> findRecetasQueVencenPronto(@Param("fechaLimite") LocalDate fechaLimite);
    
    /**
     * Buscar recetas por historial médico
     */
    List<RecetaEntity> findByHistorialMedicoIdOrderByFechaPrescripcionDesc(Long historialId);
    
    /**
     * Contar recetas activas de un medicamento
     */
    @Query("SELECT COUNT(r) FROM RecetaEntity r WHERE r.medicamento.id = :medicamentoId " +
           "AND r.estado = 'ACTIVA'")
    long countRecetasActivasByMedicamentoId(@Param("medicamentoId") Long medicamentoId);
    
    /**
     * Buscar recetas por rango de fechas
     */
    List<RecetaEntity> findByFechaPrescripcionBetweenOrderByFechaPrescripcionDesc(
        LocalDate fechaInicio, LocalDate fechaFin);
    
    /**
     * Buscar recetas con medicamentos específicos para un paciente
     */
    @Query("SELECT r FROM RecetaEntity r WHERE r.paciente.id = :pacienteId " +
           "AND r.medicamento.codigo = :codigoMedicamento")
    List<RecetaEntity> findByPacienteAndMedicamentoCodigo(
        @Param("pacienteId") Long pacienteId, 
        @Param("codigoMedicamento") String codigoMedicamento);
}

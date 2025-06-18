package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.RecetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Repository JPA para gestión de recetas médicas con operaciones CRUD específicas
@Repository
public interface RecetaJpaRepository extends JpaRepository<RecetaEntity, Long> {
    
    // Busca recetas de un paciente ordenadas por fecha de prescripción descendente
    List<RecetaEntity> findByPacienteIdOrderByFechaPrescripcionDesc(Long pacienteId);
    
    // Busca recetas de un médico ordenadas por fecha de prescripción descendente
    List<RecetaEntity> findByMedicoIdOrderByFechaPrescripcionDesc(Long medicoId);
    
    // Busca recetas de un medicamento ordenadas por fecha de prescripción descendente
    List<RecetaEntity> findByMedicamentoIdOrderByFechaPrescripcionDesc(Long medicamentoId);
    
    // Busca recetas activas y vigentes de un paciente específico
    @Query("SELECT r FROM RecetaEntity r WHERE r.paciente.id = :pacienteId " +
           "AND r.estado = 'ACTIVA' AND r.fechaVencimientoReceta >= CURRENT_DATE")
    List<RecetaEntity> findRecetasActivasByPacienteId(@Param("pacienteId") Long pacienteId);
    
    // Busca recetas por estado específico ordenadas por fecha descendente
    List<RecetaEntity> findByEstadoOrderByFechaPrescripcionDesc(RecetaEntity.EstadoReceta estado);
    
    // Busca recetas activas que vencen antes de la fecha límite
    @Query("SELECT r FROM RecetaEntity r WHERE r.estado = 'ACTIVA' " +
           "AND r.fechaVencimientoReceta BETWEEN CURRENT_DATE AND :fechaLimite")
    List<RecetaEntity> findRecetasQueVencenPronto(@Param("fechaLimite") LocalDate fechaLimite);
    
    // Busca recetas por historial médico ordenadas por fecha descendente
    List<RecetaEntity> findByHistorialMedicoIdOrderByFechaPrescripcionDesc(Long historialId);
    
    // Cuenta recetas activas que incluyen un medicamento específico
    @Query("SELECT COUNT(r) FROM RecetaEntity r WHERE r.medicamento.id = :medicamentoId " +
           "AND r.estado = 'ACTIVA'")
    long countRecetasActivasByMedicamentoId(@Param("medicamentoId") Long medicamentoId);
    
    // Busca recetas por rango de fechas de prescripción ordenadas descendente
    List<RecetaEntity> findByFechaPrescripcionBetweenOrderByFechaPrescripcionDesc(
        LocalDate fechaInicio, LocalDate fechaFin);
    
    // Busca recetas de un paciente con medicamento específico por código
    @Query("SELECT r FROM RecetaEntity r WHERE r.paciente.id = :pacienteId " +
           "AND r.medicamento.codigo = :codigoMedicamento")
    List<RecetaEntity> findByPacienteAndMedicamentoCodigo(
        @Param("pacienteId") Long pacienteId, 
        @Param("codigoMedicamento") String codigoMedicamento);
}

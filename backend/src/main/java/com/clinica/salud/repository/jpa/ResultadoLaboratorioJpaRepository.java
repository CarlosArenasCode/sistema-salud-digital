package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.ResultadoLaboratorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultadoLaboratorioJpaRepository extends JpaRepository<ResultadoLaboratorioEntity, Long> {
    
    List<ResultadoLaboratorioEntity> findByIdPaciente(Long idPaciente);
    
    List<ResultadoLaboratorioEntity> findByTipoExamenContainingIgnoreCase(String tipoExamen);
    
    List<ResultadoLaboratorioEntity> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    @Query("SELECT r FROM ResultadoLaboratorioEntity r WHERE r.idPaciente = :idPaciente ORDER BY r.fecha DESC")
    List<ResultadoLaboratorioEntity> findByIdPacienteOrderByFechaDesc(@Param("idPaciente") Long idPaciente);
}

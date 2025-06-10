package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.CitaEntity;
import com.clinica.salud.entity.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository JPA para citas médicas
 */
@Repository
public interface CitaJpaRepository extends JpaRepository<CitaEntity, Long> {
    
    // Consultas por paciente
    List<CitaEntity> findByIdPaciente(Long idPaciente);
    Page<CitaEntity> findByIdPaciente(Long idPaciente, Pageable pageable);
    List<CitaEntity> findByIdPacienteAndEstado(Long idPaciente, EstadoCita estado);
    
    // Consultas por médico
    List<CitaEntity> findByIdMedico(Long idMedico);
    Page<CitaEntity> findByIdMedico(Long idMedico, Pageable pageable);
    List<CitaEntity> findByIdMedicoAndEstado(Long idMedico, EstadoCita estado);
    
    // Consultas por fecha
    List<CitaEntity> findByFechaCita(LocalDate fecha);
    List<CitaEntity> findByFechaCitaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    Page<CitaEntity> findByFechaCitaBetween(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
    
    // Consultas por estado
    List<CitaEntity> findByEstado(EstadoCita estado);
    Page<CitaEntity> findByEstado(EstadoCita estado, Pageable pageable);
    
    // Consultas combinadas
    List<CitaEntity> findByFechaCitaAndEstado(LocalDate fecha, EstadoCita estado);
    List<CitaEntity> findByIdMedicoAndFechaCita(Long idMedico, LocalDate fecha);
    List<CitaEntity> findByIdPacienteAndFechaCitaBetween(Long idPaciente, LocalDate fechaInicio, LocalDate fechaFin);
    
    // Verificación de disponibilidad
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CitaEntity c WHERE " +
           "c.idMedico = :idMedico AND " +
           "c.fechaCita = :fechaCita AND " +
           "c.estado = 'PROGRAMADA'")
    boolean existsScheduledAppointment(@Param("idMedico") Long idMedico, 
                                      @Param("fechaCita") LocalDateTime fechaCita);

    // Consultas avanzadas
    @Query("SELECT c FROM CitaEntity c WHERE " +
           "c.fechaCita >= :fechaInicio AND " +
           "c.fechaCita <= :fechaFin AND " +
           "c.estado = :estado " +
           "ORDER BY c.fechaCita")
    List<CitaEntity> findAppointmentsByDateRangeAndStatus(@Param("fechaInicio") LocalDate fechaInicio,
                                                          @Param("fechaFin") LocalDate fechaFin,
                                                          @Param("estado") EstadoCita estado);

    @Query("SELECT c FROM CitaEntity c WHERE " +
           "c.idMedico = :idMedico AND " +
           "c.fechaCita >= :fechaInicio AND " +
           "c.fechaCita <= :fechaFin " +
           "ORDER BY c.fechaCita")
    List<CitaEntity> findDoctorAppointmentsByDateRange(@Param("idMedico") Long idMedico,
                                                       @Param("fechaInicio") LocalDate fechaInicio,
                                                       @Param("fechaFin") LocalDate fechaFin);    // Búsquedas con texto
    @Query("SELECT c FROM CitaEntity c WHERE " +
           "LOWER(c.motivoConsulta) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(CONCAT(c.paciente.nombres, ' ', c.paciente.apellidos)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(CONCAT(c.medico.nombres, ' ', c.medico.apellidos)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<CitaEntity> searchAppointments(@Param("searchTerm") String searchTerm);

    @Query("SELECT c FROM CitaEntity c WHERE " +
           "c.fechaCita >= :fecha AND " +
           "(LOWER(c.motivoConsulta) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(CONCAT(c.paciente.nombres, ' ', c.paciente.apellidos)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(CONCAT(c.medico.nombres, ' ', c.medico.apellidos)) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<CitaEntity> searchUpcomingAppointments(@Param("searchTerm") String searchTerm, 
                                               @Param("fecha") LocalDate fecha, 
                                               Pageable pageable);
    
    // Estadísticas
    @Query("SELECT COUNT(c) FROM CitaEntity c WHERE c.estado = :estado")
    long countByStatus(@Param("estado") EstadoCita estado);
    
    @Query("SELECT c.estado, COUNT(c) FROM CitaEntity c GROUP BY c.estado")
    List<Object[]> countAppointmentsByStatus();
    
    @Query("SELECT COUNT(c) FROM CitaEntity c WHERE c.fechaCita = :fecha")
    long countAppointmentsByDate(@Param("fecha") LocalDate fecha);
      @Query("SELECT COUNT(c) FROM CitaEntity c WHERE " +
           "c.fechaCita >= :fechaInicio AND " +
           "c.fechaCita <= :fechaFin")
    long countAppointmentsByDateRange(@Param("fechaInicio") LocalDate fechaInicio, 
                                     @Param("fechaFin") LocalDate fechaFin);
    
    // Citas próximas
    @Query("SELECT c FROM CitaEntity c WHERE " +
           "c.fechaCita = :fecha AND " +
           "c.estado = 'PROGRAMADA' " +
           "ORDER BY c.fechaCita")
    List<CitaEntity> findTodayScheduledAppointments(@Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM CitaEntity c WHERE " +
           "c.fechaCita BETWEEN :hoy AND :fechaFin AND " +
           "c.estado = 'PROGRAMADA' " +
           "ORDER BY c.fechaCita")
    List<CitaEntity> findUpcomingAppointments(@Param("hoy") LocalDate hoy, 
                                             @Param("fechaFin") LocalDate fechaFin);
    
    // Cancelaciones
    @Query("UPDATE CitaEntity c SET c.estado = 'CANCELADA' WHERE c.id = :id")
    void cancelAppointment(@Param("id") Long id);
    
    @Query("UPDATE CitaEntity c SET c.estado = 'COMPLETADA' WHERE c.id = :id")
    void completeAppointment(@Param("id") Long id);
}

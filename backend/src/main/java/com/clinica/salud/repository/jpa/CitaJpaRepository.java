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
// Repository interface for managing medical appointments (CitaEntity) providing CRUD operations and specialized queries for patient/doctor appointments, date filtering, status management, availability checking, and appointment statistics
@Repository
public interface CitaJpaRepository extends JpaRepository<CitaEntity, Long> {
       
       // Métodos para buscar y filtrar citas por paciente
       
       List<CitaEntity> findByIdPaciente(Long idPaciente);
       Page<CitaEntity> findByIdPaciente(Long idPaciente, Pageable pageable);
       List<CitaEntity> findByIdPacienteAndEstado(Long idPaciente, EstadoCita estado);
       List<CitaEntity> findByIdPacienteAndFechaCitaBetween(Long idPaciente, LocalDate fechaInicio, LocalDate fechaFin);
       
       // Métodos para buscar y filtrar citas por médico
       
       List<CitaEntity> findByIdMedico(Long idMedico);
       Page<CitaEntity> findByIdMedico(Long idMedico, Pageable pageable);
       List<CitaEntity> findByIdMedicoAndEstado(Long idMedico, EstadoCita estado);
       List<CitaEntity> findByIdMedicoAndFechaCita(Long idMedico, LocalDate fecha);
       
       // Métodos para filtrar citas por fecha o rangos de fechas
       
       List<CitaEntity> findByFechaCita(LocalDate fecha);
       List<CitaEntity> findByFechaCitaBetween(LocalDate fechaInicio, LocalDate fechaFin);
       Page<CitaEntity> findByFechaCitaBetween(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
       List<CitaEntity> findByFechaCitaAndEstado(LocalDate fecha, EstadoCita estado);
       
       // Métodos para filtrar citas según su estado actual
       
       List<CitaEntity> findByEstado(EstadoCita estado);
       Page<CitaEntity> findByEstado(EstadoCita estado, Pageable pageable);
       
       // Métodos para comprobar la disponibilidad de horarios
       
       @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CitaEntity c WHERE " +
                 "c.idMedico = :idMedico AND " +
                 "c.fechaCita = :fechaCita AND " +
                 "c.estado = 'PROGRAMADA'")
       boolean existsScheduledAppointment(@Param("idMedico") Long idMedico, 
                                         @Param("fechaCita") LocalDateTime fechaCita);

       // Métodos con múltiples parámetros para búsquedas específicas
       
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
                                                         @Param("fechaFin") LocalDate fechaFin);
       
       // Métodos para buscar citas por términos en diferentes campos
       
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
       
       // Métodos para generar estadísticas y conteos de citas
       
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

       // Métodos para obtener citas programadas en fechas futuras
       
       @Query("SELECT c FROM CitaEntity c WHERE " +
                 "c.fechaCita = :fecha AND " +
                 "c.estado = 'PROGRAMADA' " +
                 "ORDER BY c.fechaCita")
       List<CitaEntity> findTodayScheduledAppointments(@Param("fecha") LocalDate fecha);

       @Query("SELECT c FROM CitaEntity c WHERE " +
                 "c.fechaCita BETWEEN :hoy AND :fechaFin AND " +
                 "c.estado = 'PROGRAMADA' " +
                 "ORDER BY c.fechaCita")
       List<CitaEntity> findUpcomingAppointments(@Param("hoy") LocalDate hoy,  @Param("fechaFin") LocalDate fechaFin);                                         
       
       // Seccion 10: Operaciones de Actualizacion de Estado
       // Métodos para cambiar el estado de las citas
         @Query("UPDATE CitaEntity c SET c.estado = 'CANCELADA' WHERE c.id = :id")
       void cancelAppointment(@Param("id") Long id);
       
       @Query("UPDATE CitaEntity c SET c.estado = 'COMPLETADA' WHERE c.id = :id")
       void completeAppointment(@Param("id") Long id);
       
       // Métodos para detectar y prevenir solapamientos de citas
       
       @Query("SELECT c FROM CitaEntity c WHERE " +
                 "c.idMedico = :idMedico AND " +
                 "c.fechaCita = :fechaCita AND " +
                 "c.estado IN :estados")
       List<CitaEntity> findByIdMedicoAndFechaCitaAndEstadoIn(@Param("idMedico") Long idMedico,
                                                             @Param("fechaCita") LocalDateTime fechaCita,
                                                             @Param("estados") List<String> estados);
       
       @Query("SELECT c FROM CitaEntity c WHERE " +
                 "c.idMedico = :idMedico AND " +
                 "c.fechaCita >= :fechaInicio AND " +
                 "c.fechaCita <= :fechaFin " +
                 "ORDER BY c.fechaCita")
       List<CitaEntity> findByIdMedicoAndFechaCitaBetween(@Param("idMedico") Long idMedico,
                                                         @Param("fechaInicio") LocalDateTime fechaInicio,
                                                         @Param("fechaFin") LocalDateTime fechaFin);
}

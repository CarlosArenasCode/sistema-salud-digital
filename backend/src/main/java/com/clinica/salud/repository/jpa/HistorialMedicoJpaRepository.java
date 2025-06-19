package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.HistorialMedicoEntity;
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
 * Repository JPA para historiales médicos
 * Proporciona métodos de consulta y seguimiento médico especializados
 */
@Repository
public interface HistorialMedicoJpaRepository extends JpaRepository<HistorialMedicoEntity, Long> {
       // Consultas por paciente
       
       // Busca todos los historiales de un paciente específico
       List<HistorialMedicoEntity> findByPaciente_Id(Long patientId);
       // Busca historiales de un paciente con paginación
       Page<HistorialMedicoEntity> findByPaciente_Id(Long patientId, Pageable pageable);
       
       // Obtiene historiales de un paciente ordenados por fecha descendente
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita DESC")       List<HistorialMedicoEntity> findByPatientOrderByDateDesc(@Param("patientId") Long patientId);
       
       // Consultas por médico
       
       // Busca todos los historiales creados por un médico específico
       List<HistorialMedicoEntity> findByMedicoId(Long doctorId);
       // Busca historiales de un médico con paginación
       Page<HistorialMedicoEntity> findByMedicoId(Long doctorId, Pageable pageable);
       
       // Obtiene historiales de un médico ordenados por fecha descendente
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.id = :doctorId " +
                 "ORDER BY h.fechaVisita DESC")      
                List<HistorialMedicoEntity> findByDoctorOrderByDateDesc(@Param("doctorId") Long doctorId);
       
       // Consultas por fecha
       
       // Busca historiales de una fecha específica
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) = :date")
       List<HistorialMedicoEntity> findByVisitDate(@Param("date") LocalDate date);
       
       // Busca historiales en un rango de fechas
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       List<HistorialMedicoEntity> findByVisitDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
       
       // Busca historiales en un rango de fechas con paginación
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       Page<HistorialMedicoEntity> findByVisitDateBetweenPageable(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
       
       // Busca historiales en rango de fechas ordenados descendentemente
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "DATE(h.fechaVisita) >= :startDate AND " +
                 "DATE(h.fechaVisita) <= :endDate " +
                 "ORDER BY h.fechaVisita DESC")       
                 List<HistorialMedicoEntity> findRecordsByDateRangeOrderedDesc(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
                                                                                                                  
       
       // Búsquedas por diagnóstico
       
       // Busca historiales que contengan un diagnóstico específico
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :diagnosis, '%'))")
       List<HistorialMedicoEntity> findByDiagnosisContaining(@Param("diagnosis") String diagnosis);
       
       // Búsqueda de texto completo en diagnóstico, tratamiento y observaciones
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
       List<HistorialMedicoEntity> searchMedicalRecords(@Param("searchTerm") String searchTerm);
       
       // Búsqueda de texto completo con paginación
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")       
                 Page<HistorialMedicoEntity> searchMedicalRecordsWithPagination(@Param("searchTerm") String searchTerm, Pageable pageable);
       
       // Consultas por seguimiento
       
       // Busca historiales con una próxima cita específica
       List<HistorialMedicoEntity> findByProximaCita(LocalDateTime nextAppointment);
       
       // Busca historiales con seguimiento vencido hasta una fecha
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "DATE(h.proximaCita) <= :date")
       List<HistorialMedicoEntity> findRecordsWithFollowUpDue(@Param("date") LocalDate date);
       
       // Busca historiales con seguimiento en un rango de fechas
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "DATE(h.proximaCita) BETWEEN :startDate AND :endDate")
       List<HistorialMedicoEntity> findRecordsWithFollowUpBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
                                                                                                            
       
       // Busca historiales con seguimiento vencido
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "h.proximaCita < CURRENT_TIMESTAMP")       List<HistorialMedicoEntity> findOverdueFollowUps();
       
       // Consultas combinadas
       
       // Busca historiales de un paciente en un rango de fechas
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId AND " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findPatientRecordsByDateRange(@Param("patientId") Long patientId, @Param("endDate") LocalDate endDate);
                                                                                                           
                                                                                                           
       
       // Busca historiales de un médico en un rango de fechas
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.id = :doctorId AND " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate " +
                 "ORDER BY h.fechaVisita DESC")       
                 List<HistorialMedicoEntity> findDoctorRecordsByDateRange(@Param("doctorId") Long doctorId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
                                                                                                          
                                                                                                          
       
       // Últimos registros
       
       // Obtiene los últimos registros de un paciente con paginación
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findLastRecordsByPatient(@Param("patientId") Long patientId, Pageable pageable);
       
       // Obtiene los registros más recientes del sistema
       @Query("SELECT h FROM HistorialMedicoEntity h " +
                 "ORDER BY h.fechaVisita DESC")       
                 List<HistorialMedicoEntity> findRecentRecords(Pageable pageable);
       
       // Estadísticas
       
       // Cuenta el total de historiales médicos
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h")
       long countAllRecords();
       
       // Cuenta historiales médicos de una fecha específica
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) = :date")
       long countRecordsByDate(@Param("date") LocalDate date);
       
       // Cuenta historiales médicos en un rango de fechas
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       long countRecordsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
                                                        
       
       // Cuenta historiales médicos agrupados por médico
       @Query("SELECT h.medico.id, COUNT(h) FROM HistorialMedicoEntity h " +
                 "GROUP BY h.medico.id " +
                 "ORDER BY COUNT(h) DESC")
       List<Object[]> countRecordsByDoctor();
       
       // Cuenta seguimientos vencidos
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "h.proximaCita < CURRENT_TIMESTAMP")
       long countOverdueFollowUps();
       
       // Cuenta historiales creados después de una fecha
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE h.fechaCreacion >= :date")       
       long countRecordsCreatedAfter(@Param("date") LocalDateTime date);
       
       // Diagnósticos más comunes
       
       // Encuentra los diagnósticos más comunes desde una fecha específica
       @Query("SELECT h.diagnostico, COUNT(h) FROM HistorialMedicoEntity h " +
                 "WHERE DATE(h.fechaVisita) >= :date " +
                 "GROUP BY h.diagnostico " +
                 "ORDER BY COUNT(h) DESC")
       List<Object[]> findMostCommonDiagnoses(@Param("date") LocalDate date, Pageable pageable);
       
       // Cuenta todos los diagnósticos ordenados por frecuencia
       @Query("SELECT h.diagnostico, COUNT(h) FROM HistorialMedicoEntity h " +
                 "GROUP BY h.diagnostico " +
                 "ORDER BY COUNT(h) DESC")       
                 List<Object[]> findAllDiagnosesCount(Pageable pageable);
       
       // Consultas por tratamiento
       
       // Busca historiales que contengan un tratamiento específico
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.tratamiento IS NOT NULL AND " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :treatment, '%'))")       
                 List<HistorialMedicoEntity> findByTreatmentContaining(@Param("treatment") String treatment);
       
       // Consultas por observaciones
       
       // Busca historiales que contengan observaciones específicas
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.observaciones IS NOT NULL AND " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :observations, '%'))")       
                 List<HistorialMedicoEntity> findByObservationsContaining(@Param("observations") String observations);
       
       // Historial completo del paciente
       
       // Obtiene el historial completo de un paciente ordenado cronológicamente
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +           
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita ASC")       
                 List<HistorialMedicoEntity> findCompletePatientHistory(@Param("patientId") Long patientId);
       
       // Consultas por especialización del médico
       
       // Busca historiales por especialización médica ordenados por fecha
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.especializacion = :especializacion " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findRecordsByMedicalSpecialization(@Param("especializacion") String especializacion);
}

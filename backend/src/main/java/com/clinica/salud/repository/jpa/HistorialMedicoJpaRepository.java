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
 * Proporciona métodos avanzados de consulta y seguimiento médico
 * Versión corregida: Todos los campos en español según entidad HistorialMedicoEntity
 */
@Repository
public interface HistorialMedicoJpaRepository extends JpaRepository<HistorialMedicoEntity, Long> {
       
       // ===============================================================
       // SECCIÓN 1: CONSULTAS POR PACIENTE
       // Métodos para recuperar historiales médicos según el paciente
       // ===============================================================
       
       List<HistorialMedicoEntity> findByPaciente_Id(Long patientId);
       Page<HistorialMedicoEntity> findByPaciente_Id(Long patientId, Pageable pageable);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findByPatientOrderByDateDesc(@Param("patientId") Long patientId);
       
       // ===============================================================
       // SECCIÓN 2: CONSULTAS POR MÉDICO
       // Métodos para recuperar historiales médicos según el médico
       // ===============================================================
       
       List<HistorialMedicoEntity> findByMedicoId(Long doctorId);
       Page<HistorialMedicoEntity> findByMedicoId(Long doctorId, Pageable pageable);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.id = :doctorId " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findByDoctorOrderByDateDesc(@Param("doctorId") Long doctorId);
       
       // ===============================================================
       // SECCIÓN 3: CONSULTAS POR FECHA
       // Métodos para recuperar historiales médicos por fecha o rango de fechas
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) = :date")
       List<HistorialMedicoEntity> findByVisitDate(@Param("date") LocalDate date);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       List<HistorialMedicoEntity> findByVisitDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       Page<HistorialMedicoEntity> findByVisitDateBetweenPageable(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "DATE(h.fechaVisita) >= :startDate AND " +
                 "DATE(h.fechaVisita) <= :endDate " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findRecordsByDateRangeOrderedDesc(@Param("startDate") LocalDate startDate, 
                                                                                                                  @Param("endDate") LocalDate endDate);    
       
       // ===============================================================
       // SECCIÓN 4: BÚSQUEDAS POR DIAGNÓSTICO
       // Métodos para buscar historiales médicos por diagnóstico o términos de búsqueda
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :diagnosis, '%'))")
       List<HistorialMedicoEntity> findByDiagnosisContaining(@Param("diagnosis") String diagnosis);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
       List<HistorialMedicoEntity> searchMedicalRecords(@Param("searchTerm") String searchTerm);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "LOWER(h.diagnostico) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
       Page<HistorialMedicoEntity> searchMedicalRecordsWithPagination(@Param("searchTerm") String searchTerm, Pageable pageable);    
       
       // ===============================================================
       // SECCIÓN 5: CONSULTAS POR SEGUIMIENTO
       // Métodos para encontrar historiales con citas de seguimiento
       // ===============================================================
       
       List<HistorialMedicoEntity> findByProximaCita(LocalDateTime nextAppointment);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "DATE(h.proximaCita) <= :date")
       List<HistorialMedicoEntity> findRecordsWithFollowUpDue(@Param("date") LocalDate date);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "DATE(h.proximaCita) BETWEEN :startDate AND :endDate")
       List<HistorialMedicoEntity> findRecordsWithFollowUpBetween(@Param("startDate") LocalDate startDate, 
                                                                                                            @Param("endDate") LocalDate endDate);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "h.proximaCita < CURRENT_TIMESTAMP")
       List<HistorialMedicoEntity> findOverdueFollowUps();    
       
       // ===============================================================
       // SECCIÓN 6: CONSULTAS COMBINADAS
       // Métodos que combinan varios criterios de búsqueda
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId AND " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findPatientRecordsByDateRange(@Param("patientId") Long patientId,
                                                                                                           @Param("startDate") LocalDate startDate,
                                                                                                           @Param("endDate") LocalDate endDate);
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.id = :doctorId AND " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findDoctorRecordsByDateRange(@Param("doctorId") Long doctorId,
                                                                                                          @Param("startDate") LocalDate startDate,
                                                                                                          @Param("endDate") LocalDate endDate);
       
       // ===============================================================
       // SECCIÓN 7: ÚLTIMOS REGISTROS
       // Métodos para obtener los registros más recientes
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findLastRecordsByPatient(@Param("patientId") Long patientId, Pageable pageable);
       
       @Query("SELECT h FROM HistorialMedicoEntity h " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findRecentRecords(Pageable pageable);    
       
       // ===============================================================
       // SECCIÓN 8: ESTADÍSTICAS
       // Métodos para obtener datos estadísticos de los historiales
       // ===============================================================
       
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h")
       long countAllRecords();
       
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE DATE(h.fechaVisita) = :date")
       long countRecordsByDate(@Param("date") LocalDate date);
       
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE " +
                 "DATE(h.fechaVisita) BETWEEN :startDate AND :endDate")
       long countRecordsByDateRange(@Param("startDate") LocalDate startDate, 
                                                         @Param("endDate") LocalDate endDate);
       
       @Query("SELECT h.medico.id, COUNT(h) FROM HistorialMedicoEntity h " +
                 "GROUP BY h.medico.id " +
                 "ORDER BY COUNT(h) DESC")
       List<Object[]> countRecordsByDoctor();
       
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE " +
                 "h.proximaCita IS NOT NULL AND " +
                 "h.proximaCita < CURRENT_TIMESTAMP")
       long countOverdueFollowUps();
       
       @Query("SELECT COUNT(h) FROM HistorialMedicoEntity h WHERE h.fechaCreacion >= :date")
       long countRecordsCreatedAfter(@Param("date") LocalDateTime date);    
       
       // ===============================================================
       // SECCIÓN 9: DIAGNÓSTICOS MÁS COMUNES
       // Métodos para analizar los diagnósticos más frecuentes
       // ===============================================================
       
       @Query("SELECT h.diagnostico, COUNT(h) FROM HistorialMedicoEntity h " +
                 "WHERE DATE(h.fechaVisita) >= :date " +
                 "GROUP BY h.diagnostico " +
                 "ORDER BY COUNT(h) DESC")
       List<Object[]> findMostCommonDiagnoses(@Param("date") LocalDate date, Pageable pageable);
       
       @Query("SELECT h.diagnostico, COUNT(h) FROM HistorialMedicoEntity h " +
                 "GROUP BY h.diagnostico " +
                 "ORDER BY COUNT(h) DESC")
       List<Object[]> findAllDiagnosesCount(Pageable pageable);
       
       // ===============================================================
       // SECCIÓN 10: CONSULTAS POR TRATAMIENTO
       // Métodos para buscar historiales por tratamiento aplicado
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.tratamiento IS NOT NULL AND " +
                 "LOWER(h.tratamiento) LIKE LOWER(CONCAT('%', :treatment, '%'))")
       List<HistorialMedicoEntity> findByTreatmentContaining(@Param("treatment") String treatment);
       
       // ===============================================================
       // SECCIÓN 11: CONSULTAS POR OBSERVACIONES
       // Métodos para buscar historiales por observaciones médicas
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.observaciones IS NOT NULL AND " +
                 "LOWER(h.observaciones) LIKE LOWER(CONCAT('%', :observations, '%'))")
       List<HistorialMedicoEntity> findByObservationsContaining(@Param("observations") String observations);    
       
       // ===============================================================
       // SECCIÓN 12: HISTORIAL COMPLETO DEL PACIENTE
       // Métodos para obtener todo el historial de un paciente
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +           
                 "h.paciente.id = :patientId " +
                 "ORDER BY h.fechaVisita ASC")
       List<HistorialMedicoEntity> findCompletePatientHistory(@Param("patientId") Long patientId);
       
       // ===============================================================
       // SECCIÓN 13: CONSULTAS POR ESPECIALIZACIÓN DEL MÉDICO
       // Métodos para buscar historiales según especialidad médica
       // ===============================================================
       
       @Query("SELECT h FROM HistorialMedicoEntity h WHERE " +
                 "h.medico.especializacion = :especializacion " +
                 "ORDER BY h.fechaVisita DESC")
       List<HistorialMedicoEntity> findRecordsByMedicalSpecialization(@Param("especializacion") String especializacion);
}

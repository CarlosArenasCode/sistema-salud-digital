package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.PacienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Pacientes con métodos de búsqueda básicos
 */
@Repository
public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Long> {
      // Búsquedas básicas por número de identificación
    Optional<PacienteEntity> findByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
      // Búsquedas por nombres
    @Query("SELECT p FROM PacienteEntity p WHERE LOWER(p.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))")
    List<PacienteEntity> findByNombresContainingIgnoreCase(@Param("nombres") String nombres);
      // Búsqueda general
    @Query("SELECT p FROM PacienteEntity p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "p.numeroIdentificacion LIKE CONCAT('%', :searchTerm, '%')")
    Page<PacienteEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}

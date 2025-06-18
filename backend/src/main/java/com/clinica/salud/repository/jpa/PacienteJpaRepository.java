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

// Repository JPA para gestión de pacientes con búsquedas básicas
@Repository
public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Long> {
    
    // Busca paciente por número de identificación único
    Optional<PacienteEntity> findByNumeroIdentificacion(String numeroIdentificacion);
    
    // Verifica si existe paciente con el número de identificación
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
    
    // Busca pacientes por nombres parciales ignorando mayúsculas
    @Query("SELECT p FROM PacienteEntity p WHERE LOWER(p.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))")
    List<PacienteEntity> findByNombresContainingIgnoreCase(@Param("nombres") String nombres);
    
    // Búsqueda general por nombres, apellidos o identificación paginada
    @Query("SELECT p FROM PacienteEntity p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "p.numeroIdentificacion LIKE CONCAT('%', :searchTerm, '%')")
    Page<PacienteEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}

package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.MedicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Médicos con métodos de búsqueda básicos
 */
@Repository
public interface MedicoJpaRepository extends JpaRepository<MedicoEntity, Long> {
      // Búsquedas por especialización
    List<MedicoEntity> findByEspecializacion(String especializacion);
    
    // Búsquedas por número de licencia
    Optional<MedicoEntity> findByNumeroLicencia(String numeroLicencia);
    
    // Búsquedas por nombres
    @Query("SELECT m FROM MedicoEntity m WHERE LOWER(m.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))")
    List<MedicoEntity> findByNombresContainingIgnoreCase(@Param("nombres") String nombres);
    
    // Búsqueda general
    @Query("SELECT m FROM MedicoEntity m WHERE " +
           "LOWER(m.nombres) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.especializacion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<MedicoEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    // Médicos disponibles
    @Query("SELECT m FROM MedicoEntity m WHERE m.activo = true")
    List<MedicoEntity> findMedicosActivos();
}

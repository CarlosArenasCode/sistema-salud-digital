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

// Repository JPA para gestión de médicos con búsquedas básicas
@Repository
public interface MedicoJpaRepository extends JpaRepository<MedicoEntity, Long> {
    
    // Busca médicos por especialización específica
    List<MedicoEntity> findByEspecializacion(String especializacion);
    
    // Busca médico por número de licencia único
    Optional<MedicoEntity> findByNumeroLicencia(String numeroLicencia);
    
    // Busca médicos por nombres parciales ignorando mayúsculas
    @Query("SELECT m FROM MedicoEntity m WHERE LOWER(m.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))")
    List<MedicoEntity> findByNombresContainingIgnoreCase(@Param("nombres") String nombres);
    
    // Búsqueda general por nombres, apellidos o especialización paginada
    @Query("SELECT m FROM MedicoEntity m WHERE " +
           "LOWER(m.nombres) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.especializacion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<MedicoEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    // Obtiene todos los médicos activos disponibles
    @Query("SELECT m FROM MedicoEntity m WHERE m.activo = true")
    List<MedicoEntity> findMedicosActivos();
}

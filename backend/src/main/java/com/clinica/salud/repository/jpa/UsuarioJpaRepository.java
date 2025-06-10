package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository JPA para usuarios del sistema
 * Proporciona métodos avanzados de consulta
 */
@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
      // Consultas básicas
    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);    // Consultas por rol
    List<UsuarioEntity> findByRol(String rol);
    Page<UsuarioEntity> findByRol(String rol, Pageable pageable);
    
    // Consultas por estado
    List<UsuarioEntity> findByActivo(Boolean activo);
    Page<UsuarioEntity> findByActivo(Boolean activo, Pageable pageable);
      // Consultas combinadas
    List<UsuarioEntity> findByRolAndActivo(String rol, Boolean activo);
    Page<UsuarioEntity> findByRolAndActivo(String rol, Boolean activo, Pageable pageable);
      // Búsquedas con texto
    @Query("SELECT u FROM UsuarioEntity u WHERE " +
           "LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<UsuarioEntity> findByUsernameOrEmailContainingIgnoreCase(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT u FROM UsuarioEntity u WHERE " +
           "u.activo = true AND " +
           "(LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<UsuarioEntity> findActiveUsersWithSearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
      // Consultas por fecha
    List<UsuarioEntity> findByFechaCreacionBetween(LocalDateTime start, LocalDateTime end);
    List<UsuarioEntity> findByFechaCreacionAfter(LocalDateTime date);
      // Estadísticas
    @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.activo = true")
    long countActiveUsers();
    
    @Query("SELECT u.rol, COUNT(u) FROM UsuarioEntity u WHERE u.activo = true GROUP BY u.rol")
    List<Object[]> countUsersByRole();
      @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.fechaCreacion >= :date")
    long countUsersCreatedAfter(@Param("date") LocalDateTime date);
      // Métodos de administración
    @Query("UPDATE UsuarioEntity u SET u.activo = false WHERE u.id = :id")
    void deactivateUser(@Param("id") Long id);
    
    @Query("UPDATE UsuarioEntity u SET u.activo = true WHERE u.id = :id")
    void activateUser(@Param("id") Long id);
      // Validaciones de seguridad
    @Query("SELECT u FROM UsuarioEntity u WHERE u.nombreUsuario = :nombreUsuario AND u.activo = true")
    Optional<UsuarioEntity> findActiveUserByUsername(@Param("nombreUsuario") String nombreUsuario);
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UsuarioEntity u WHERE u.email = :email AND u.id <> :excludeId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("excludeId") Long excludeId);
      @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UsuarioEntity u WHERE u.nombreUsuario = :nombreUsuario AND u.id <> :excludeId")
    boolean existsByUsernameAndIdNot(@Param("nombreUsuario") String nombreUsuario, @Param("excludeId") Long excludeId);
}

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

// Repository JPA para usuarios del sistema con métodos avanzados de consulta
@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
  
  // Busca usuario por nombre de usuario único
  Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
  
  // Busca usuario por email único
  Optional<UsuarioEntity> findByEmail(String email);
  
  // Verifica si existe usuario con el nombre de usuario
  boolean existsByNombreUsuario(String nombreUsuario);
    // Verifica si existe usuario con el email
  boolean existsByEmail(String email);
  
  // Busca usuarios por rol específico
  List<UsuarioEntity> findByRol(String rol);
  
  // Busca usuarios por rol específico con paginación
  Page<UsuarioEntity> findByRol(String rol, Pageable pageable);
  
  // Busca usuarios por estado activo/inactivo
  List<UsuarioEntity> findByActivo(Boolean activo);
  
  // Busca usuarios por estado activo/inactivo con paginación
  Page<UsuarioEntity> findByActivo(Boolean activo, Pageable pageable);
  
  // Busca usuarios por rol y estado específicos
  List<UsuarioEntity> findByRolAndActivo(String rol, Boolean activo);
    // Busca usuarios por rol y estado específicos con paginación
  Page<UsuarioEntity> findByRolAndActivo(String rol, Boolean activo, Pageable pageable);
  
  // Busca usuarios por nombre de usuario o email conteniendo el término
  @Query("SELECT u FROM UsuarioEntity u WHERE " +
       "LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
       "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
  List<UsuarioEntity> findByUsernameOrEmailContainingIgnoreCase(@Param("searchTerm") String searchTerm);
  
  // Busca usuarios activos por nombre de usuario o email con paginación
  @Query("SELECT u FROM UsuarioEntity u WHERE " +
       "u.activo = true AND " +
       "(LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
       "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
  Page<UsuarioEntity> findActiveUsersWithSearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
  
  // Busca usuarios por rango de fechas de creación
  List<UsuarioEntity> findByFechaCreacionBetween(LocalDateTime start, LocalDateTime end);
    // Busca usuarios creados después de una fecha específica
  List<UsuarioEntity> findByFechaCreacionAfter(LocalDateTime date);
  
  // Cuenta usuarios activos en el sistema
  @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.activo = true")
  long countActiveUsers();
  
  // Cuenta usuarios activos agrupados por rol
  @Query("SELECT u.rol, COUNT(u) FROM UsuarioEntity u WHERE u.activo = true GROUP BY u.rol")
  List<Object[]> countUsersByRole();
  
  // Cuenta usuarios creados después de una fecha específica
  @Query("SELECT COUNT(u) FROM UsuarioEntity u WHERE u.fechaCreacion >= :date")
  long countUsersCreatedAfter(@Param("date") LocalDateTime date);
  
  // Desactiva un usuario por su ID
  @Query("UPDATE UsuarioEntity u SET u.activo = false WHERE u.id = :id")
  void deactivateUser(@Param("id") Long id);
  
  // Activa un usuario por su ID
  @Query("UPDATE UsuarioEntity u SET u.activo = true WHERE u.id = :id")
  void activateUser(@Param("id") Long id);
  
  // Busca usuario activo por nombre de usuario
  @Query("SELECT u FROM UsuarioEntity u WHERE u.nombreUsuario = :nombreUsuario AND u.activo = true")
  Optional<UsuarioEntity> findActiveUserByUsername(@Param("nombreUsuario") String nombreUsuario);
  
  // Verifica si existe email excluyendo un ID específico
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UsuarioEntity u WHERE u.email = :email AND u.id <> :excludeId")
  boolean existsByEmailAndIdNot(@Param("email") String email, @Param("excludeId") Long excludeId);
  
  // Verifica si existe nombre de usuario excluyendo un ID específico
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UsuarioEntity u WHERE u.nombreUsuario = :nombreUsuario AND u.id <> :excludeId")
  boolean existsByUsernameAndIdNot(@Param("nombreUsuario") String nombreUsuario, @Param("excludeId") Long excludeId);
}

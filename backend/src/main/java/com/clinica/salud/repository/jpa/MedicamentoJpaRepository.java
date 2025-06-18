package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.MedicamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Repository JPA para gestión de medicamentos con funcionalidad optimizada
@Repository
public interface MedicamentoJpaRepository extends JpaRepository<MedicamentoEntity, Long> {

    // Busca medicamentos por nombre parcial ignorando mayúsculas
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<MedicamentoEntity> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Busca medicamento por nombre exacto
    Optional<MedicamentoEntity> findByNombreIgnoreCase(String nombre);
    
    // Busca medicamento por código único
    Optional<MedicamentoEntity> findByCodigo(String codigo);    // Busca medicamentos por forma de dosificación
    List<MedicamentoEntity> findByFormaDosificacion(String formaDosificacion);

    // Encuentra medicamentos con stock menor o igual al umbral especificado
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock <= :threshold")
    List<MedicamentoEntity> findLowStockMedications(@Param("threshold") Integer threshold);
    
    // Encuentra medicamentos sin stock disponible
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock = 0 OR m.stock IS NULL")
    List<MedicamentoEntity> findOutOfStockMedications();    // Encuentra medicamentos con stock disponible
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock > 0")
    List<MedicamentoEntity> findAvailableMedications();

    // Encuentra medicamentos vencidos a la fecha actual
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_DATE")
    List<MedicamentoEntity> findExpiredMedications();
      // Encuentra medicamentos que vencen antes de la fecha especificada
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento BETWEEN CURRENT_DATE AND :futureDate")
    List<MedicamentoEntity> findMedicationsExpiringSoon(@Param("futureDate") LocalDateTime futureDate);

    // Búsqueda avanzada con múltiples filtros opcionales paginada
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "(:nombre IS NULL OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:fabricante IS NULL OR LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%'))) AND " +
           "(:categoria IS NULL OR LOWER(m.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))) AND " +
           "(:minStock IS NULL OR m.stock >= :minStock)")
    Page<MedicamentoEntity> findMedicamentosConFiltros(
            @Param("nombre") String nombre,
            @Param("fabricante") String fabricante,
            @Param("categoria") String categoria,
            @Param("minStock") Integer minStock,
            Pageable pageable);    // Búsqueda general por texto en nombre, descripción y fabricante
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<MedicamentoEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Cuenta medicamentos con stock menor o igual al umbral
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.stock <= :threshold")
    Long countLowStockMedications(@Param("threshold") Integer threshold);
    
    // Obtiene el total de unidades en stock
    @Query("SELECT SUM(m.stock) FROM MedicamentoEntity m WHERE m.stock IS NOT NULL")
    Long getTotalStock();    // Cuenta medicamentos vencidos
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_DATE")
    Long countExpiredMedications();
    
    // Busca medicamentos activos con stock bajo según stock mínimo
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock <= m.stockMinimo AND m.activo = true")
    List<MedicamentoEntity> findMedicamentosConStockBajo();
    
    // Busca medicamentos por rango de fecha de vencimiento con stock mayor al mínimo
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin AND m.stock > :stockMinimo")
    List<MedicamentoEntity> findByFechaVencimientoBetweenAndStockGreaterThan(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin,
        @Param("stockMinimo") Integer stockMinimo);
    
    // Busca medicamentos vencidos con stock mayor al mínimo
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento < :fecha AND m.stock > :stockMinimo")
    List<MedicamentoEntity> findByFechaVencimientoBeforeAndStockGreaterThan(
        @Param("fecha") LocalDate fecha,
        @Param("stockMinimo") Integer stockMinimo);
    
    // Busca medicamentos disponibles (activos, no vencidos, con stock)
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.activo = true AND " +
           "(m.fechaVencimiento IS NULL OR m.fechaVencimiento >= CURRENT_DATE) AND " +
           "m.stock > 0")
    List<MedicamentoEntity> findMedicamentosDisponibles();
    
    // Busca por nombre o código conteniendo el término de búsqueda
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(m.codigo) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<MedicamentoEntity> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(
        @Param("termino") String termino1, @Param("termino") String termino2);
    
    // Busca por categoría ignorando mayúsculas/minúsculas
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.categoria) = LOWER(:categoria)")
    List<MedicamentoEntity> findByCategoriaIgnoreCase(@Param("categoria") String categoria);
}

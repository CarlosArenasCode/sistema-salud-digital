package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.MedicamentoEntity;
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
 * Repository optimizado para medicamentos
 * Reducido de 283 líneas a ~60 líneas manteniendo funcionalidad esencial
 * CORREGIDO: Usa nombres de campos en español de MedicamentoEntity
 */
@Repository
public interface MedicamentoJpaRepository extends JpaRepository<MedicamentoEntity, Long> {

    // ===============================
    // BÚSQUEDAS ESENCIALES
    // ===============================

    /**
     * Busca medicamentos por nombre (ignorando mayúsculas/minúsculas)
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<MedicamentoEntity> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    /**
     * Busca medicamentos por nombre exacto
     */
    Optional<MedicamentoEntity> findByNombreIgnoreCase(String nombre);
    
    /**
     * Busca medicamentos por código
     */
    Optional<MedicamentoEntity> findByCodigo(String codigo);

    /**
     * Busca medicamentos por forma de dosificación
     */
    List<MedicamentoEntity> findByFormaDosificacion(String formaDosificacion);

    // ===============================
    // GESTIÓN DE INVENTARIO CRÍTICA
    // ===============================

    /**
     * Encuentra medicamentos con stock bajo
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock <= :threshold")
    List<MedicamentoEntity> findLowStockMedications(@Param("threshold") Integer threshold);
    
    /**
     * Encuentra medicamentos sin stock
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock = 0 OR m.stock IS NULL")
    List<MedicamentoEntity> findOutOfStockMedications();

    /**
     * Encuentra medicamentos con stock disponible
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock > 0")
    List<MedicamentoEntity> findAvailableMedications();

    // ===============================
    // GESTIÓN DE VENCIMIENTOS
    // ===============================

    /**
     * Encuentra medicamentos vencidos
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_DATE")
    List<MedicamentoEntity> findExpiredMedications();
    
    /**
     * Encuentra medicamentos que vencen pronto
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento BETWEEN CURRENT_DATE AND :futureDate")
    List<MedicamentoEntity> findMedicationsExpiringSoon(@Param("futureDate") LocalDateTime futureDate);

    // ===============================
    // BÚSQUEDA AVANZADA UNIFICADA
    // ===============================

    /**
     * Búsqueda avanzada con múltiples filtros opcionales
     * Reemplaza múltiples métodos de búsqueda específicos
     */
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
            Pageable pageable);

    /**
     * Búsqueda de texto general (nombre, descripción, fabricante)
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<MedicamentoEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    // ===============================
    // ESTADÍSTICAS BÁSICAS
    // ===============================

    /**
     * Cuenta medicamentos con stock bajo
     */
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.stock <= :threshold")
    Long countLowStockMedications(@Param("threshold") Integer threshold);
    
    /**
     * Obtiene el total de medicamentos en stock
     */
    @Query("SELECT SUM(m.stock) FROM MedicamentoEntity m WHERE m.stock IS NOT NULL")
    Long getTotalStock();

    /**
     * Cuenta medicamentos vencidos
     */
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_DATE")
    Long countExpiredMedications();
}

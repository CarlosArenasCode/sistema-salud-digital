package com.clinica.salud.repository.jpa;

import com.clinica.salud.entity.MedicamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para gestión avanzada de medicamentos
 * Proporciona operaciones de búsqueda, inventario y farmacia
 * CORREGIDO: Usa nombres de campos en español de MedicamentoEntity
 */
@Repository
public interface MedicamentoJpaRepository extends JpaRepository<MedicamentoEntity, Long> {

    // ===============================
    // BÚSQUEDAS BÁSICAS
    // ===============================

    /**
     * Busca medicamentos por nombre (ignorando mayúsculas/minúsculas)
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<MedicamentoEntity> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    /**
     * Busca medicamentos por fabricante
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%'))")
    List<MedicamentoEntity> findByFabricanteContainingIgnoreCase(@Param("fabricante") String fabricante);

    /**
     * Busca medicamentos por forma farmacéutica exacta
     */
    List<MedicamentoEntity> findByForma(String forma);

    /**
     * Busca medicamentos por nombre exacto
     */
    Optional<MedicamentoEntity> findByNombreIgnoreCase(String nombre);

    /**
     * Busca medicamentos por categoría
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<MedicamentoEntity> findByCategoriaContainingIgnoreCase(@Param("categoria") String categoria);

    /**
     * Busca medicamentos por código
     */
    Optional<MedicamentoEntity> findByCodigo(String codigo);

    /**
     * Busca medicamentos activos
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.activo = true")
    List<MedicamentoEntity> findMedicamentosActivos();

    /**
     * Busca medicamentos que requieren receta
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.requiereReceta = true")
    List<MedicamentoEntity> findMedicamentosConReceta();    // ===============================
    // GESTIÓN DE INVENTARIO
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

    /**
     * Encuentra medicamentos ordenados por stock ascendente
     */
    @Query("SELECT m FROM MedicamentoEntity m ORDER BY m.stock ASC")
    List<MedicamentoEntity> findAllOrderByStockAsc();

    /**
     * Obtiene el total de medicamentos en stock
     */
    @Query("SELECT SUM(m.stock) FROM MedicamentoEntity m WHERE m.stock > 0")
    Long getTotalMedicationsInStock();

    /**
     * Cuenta medicamentos con stock bajo
     */
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.stock <= :threshold")
    Long countLowStockMedications(@Param("threshold") Integer threshold);

    /**
     * Encuentra medicamentos por debajo del stock mínimo
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock < m.stockMinimo")
    List<MedicamentoEntity> findBelowMinimumStock();

    /**
     * Encuentra medicamentos por stock igual o mayor a una cantidad
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock >= :minStock")
    List<MedicamentoEntity> findByStockGreaterThanEqual(@Param("minStock") Integer minStock);    // ===============================
    // GESTIÓN DE VENCIMIENTOS
    // ===============================

    /**
     * Encuentra medicamentos vencidos
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_TIMESTAMP")
    List<MedicamentoEntity> findExpiredMedications();

    /**
     * Encuentra medicamentos que vencen pronto
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento BETWEEN CURRENT_TIMESTAMP AND :futureDate")
    List<MedicamentoEntity> findMedicationsExpiringSoon(@Param("futureDate") LocalDateTime futureDate);

    /**
     * Encuentra medicamentos por rango de fechas de vencimiento
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento BETWEEN :startDate AND :endDate")
    List<MedicamentoEntity> findByExpirationDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Cuenta medicamentos vencidos
     */
    @Query("SELECT COUNT(m) FROM MedicamentoEntity m WHERE m.fechaVencimiento < CURRENT_TIMESTAMP")
    Long countExpiredMedications();

    /**
     * Encuentra medicamentos sin fecha de vencimiento
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaVencimiento IS NULL")
    List<MedicamentoEntity> findMedicationsWithoutExpirationDate();

    // ===============================
    // BÚSQUEDAS POR PRECIO
    // ===============================

    /**
     * Encuentra medicamentos por rango de precios
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.precio BETWEEN :minPrice AND :maxPrice")
    List<MedicamentoEntity> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, 
                                              @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Encuentra medicamentos más baratos que un precio específico
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.precio <= :maxPrice ORDER BY m.precio ASC")
    List<MedicamentoEntity> findByPriceLessThanEqualOrderByPriceAsc(@Param("maxPrice") BigDecimal maxPrice);

    /**
     * Encuentra medicamentos más caros que un precio específico
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.precio >= :minPrice ORDER BY m.precio DESC")
    List<MedicamentoEntity> findByPriceGreaterThanEqualOrderByPriceDesc(@Param("minPrice") BigDecimal minPrice);

    /**
     * Obtiene el precio promedio de los medicamentos
     */
    @Query("SELECT AVG(m.precio) FROM MedicamentoEntity m WHERE m.precio IS NOT NULL")
    Double getAveragePrice();

    /**
     * Encuentra medicamentos sin precio definido
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.precio IS NULL")
    List<MedicamentoEntity> findMedicationsWithoutPrice();    // ===============================
    // BÚSQUEDAS AVANZADAS Y COMBINADAS
    // ===============================

    /**
     * Búsqueda completa por múltiples criterios
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "(:nombre IS NULL OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:fabricante IS NULL OR LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%'))) AND " +
           "(:forma IS NULL OR m.forma = :forma) AND " +
           "(:minPrice IS NULL OR m.precio >= :minPrice) AND " +
           "(:maxPrice IS NULL OR m.precio <= :maxPrice) AND " +
           "(:minStock IS NULL OR m.stock >= :minStock)")
    List<MedicamentoEntity> findByCriteria(@Param("nombre") String nombre,
                                          @Param("fabricante") String fabricante,
                                          @Param("forma") String forma,
                                          @Param("minPrice") BigDecimal minPrice,
                                          @Param("maxPrice") BigDecimal maxPrice,
                                          @Param("minStock") Integer minStock);

    /**
     * Búsqueda con paginación
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "(:searchTerm IS NULL OR LOWER(m.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<MedicamentoEntity> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Encuentra medicamentos disponibles con suficiente stock
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.stock >= :requiredStock AND " +
           "(m.fechaVencimiento IS NULL OR m.fechaVencimiento > CURRENT_TIMESTAMP)")
    List<MedicamentoEntity> findAvailableWithStock(@Param("requiredStock") Integer requiredStock);    // ===============================
    // ESTADÍSTICAS Y REPORTES
    // ===============================

    /**
     * Obtiene estadísticas generales de medicamentos
     */
    @Query("SELECT " +
           "COUNT(m) as total, " +
           "COUNT(CASE WHEN m.stock > 0 THEN 1 END) as available, " +
           "COUNT(CASE WHEN m.stock = 0 OR m.stock IS NULL THEN 1 END) as outOfStock, " +
           "COUNT(CASE WHEN m.fechaVencimiento < CURRENT_TIMESTAMP THEN 1 END) as expired " +
           "FROM MedicamentoEntity m")
    Object[] getMedicationStatistics();

    /**
     * Encuentra los medicamentos con más stock
     */
    @Query("SELECT m FROM MedicamentoEntity m ORDER BY m.stock DESC")
    List<MedicamentoEntity> findTopMedications(Pageable pageable);

    /**
     * Encuentra medicamentos por fabricante con stock disponible
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%')) AND " +
           "m.stock > 0 ORDER BY m.nombre ASC")
    List<MedicamentoEntity> findAvailableByManufacturer(@Param("fabricante") String fabricante);

    /**
     * Obtiene el valor total del inventario
     */
    @Query("SELECT SUM(m.precio * m.stock) FROM MedicamentoEntity m WHERE m.precio IS NOT NULL AND m.stock > 0")
    BigDecimal getTotalInventoryValue();

    /**
     * Encuentra medicamentos que necesitan reposición urgente
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "(m.stock <= :criticalThreshold) OR " +
           "(m.fechaVencimiento BETWEEN CURRENT_TIMESTAMP AND :nearExpirationDate)")
    List<MedicamentoEntity> findCriticalMedications(@Param("criticalThreshold") Integer criticalThreshold,
                                                   @Param("nearExpirationDate") LocalDateTime nearExpirationDate);    // ===============================
    // OPERACIONES DE MANTENIMIENTO
    // ===============================

    /**
     * Encuentra medicamentos duplicados por nombre
     */
    @Query("SELECT m.nombre, COUNT(m) FROM MedicamentoEntity m GROUP BY m.nombre HAVING COUNT(m) > 1")
    List<Object[]> findDuplicateMedicationNames();

    /**
     * Obtiene medicamentos creados en un período específico
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.fechaCreacion BETWEEN :startDate AND :endDate")
    List<MedicamentoEntity> findMedicationsCreatedBetween(@Param("startDate") LocalDateTime startDate,
                                                          @Param("endDate") LocalDateTime endDate);

    /**
     * Búsqueda de texto completo (nombre, descripción, fabricante)
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.descripcion) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(m.forma) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<MedicamentoEntity> fullTextSearch(@Param("searchText") String searchText);    // ===============================
    // MÉTODOS PERSONALIZADOS PARA FARMACIA
    // ===============================

    /**
     * Verifica si hay suficiente stock para una venta
     */
    @Query("SELECT CASE WHEN m.stock >= :requiredQuantity THEN true ELSE false END " +
           "FROM MedicamentoEntity m WHERE m.id = :medicationId")
    Boolean hasEnoughStock(@Param("medicationId") Long medicationId, 
                          @Param("requiredQuantity") Integer requiredQuantity);

    /**
     * Encuentra alternativas basadas en el mismo principio activo (por nombre similar)
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE " +
           "m.id != :originalId AND " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :activeIngredient, '%')) AND " +
           "m.stock > 0")
    List<MedicamentoEntity> findAlternatives(@Param("originalId") Long originalId,
                                           @Param("activeIngredient") String activeIngredient);

    /**
     * Encuentra medicamentos por lote
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE m.lote = :lote")
    List<MedicamentoEntity> findByLote(@Param("lote") String lote);

    /**
     * Encuentra medicamentos por presentación
     */
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.presentacion) LIKE LOWER(CONCAT('%', :presentacion, '%'))")
    List<MedicamentoEntity> findByPresentacionContainingIgnoreCase(@Param("presentacion") String presentacion);
}
